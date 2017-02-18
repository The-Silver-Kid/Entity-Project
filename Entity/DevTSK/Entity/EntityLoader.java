package DevTSK.Entity;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import DevTSK.Util.Day;
import DevTSK.Util.LoggerPro;
import DevTSK.Util.StringWriter;
import DevTSK.Util.DAG.ConfigException;

/**
 * @author The_Silver_Kid
 *
 *         The Main Guts of the program.
 * 
 *         Handles all input and output.
 */
public class EntityLoader {

	private static String lastCmd, name;
	private static String[] sl;

	private final Day compDay;

	private static FileOutputStream send;

	private static final String[] commandsyntax = new String[] { "Color <R> <G> <B>", "InputColor <R> <G> <B>",
			"OutputColor <R> <G> <B>", "OutputTextColor <R> <G> <B>", "InputTextColor <R> <G> <B>", "Exit",
			"extract [Entity name]", "breed <Mother> <Father> (broken)", "last / l / lastcmd", "cfg / config",
			"listNonOC", "listall", "listalldna", "info <entity name>", "charset",
			"dump (developer only!)", "WIP (developer only!)" };

	private static final String[] commandexpl = new String[] { "Changes Background Color", "Changes input box color",
			"Changes outputbox color", "Changes outputbox text color", "Changes input box text color",
			"Exit the program", "extracts the given entity's image or all if none provided",
			"Generates a string derived from both parents",
			"re-inputs last given command into the input box for editing or re-execution",
			"saves color scheme to config file : Poniconfig.cfg", "lists all defined OC entities",
			"lists all defined entities", "lists all entities with DNA input", "gives general on the given entity type",
			"prints to console the current entity list String Identifier",
			"Dumps a list of all ponii-position numbers into EntityList.txt",
			"Dumps a list of all entities that have WIP as one or more values" };

	private static final String[] commands = new String[] { "Colour", "Color", "InputColour", "InputColor",
			"OutputColour", "OutputColor", "Exit", "OutputTextColor", "InputTextColor", "OutputTextColour",
			"InputTextColour", "errorcheck", "extract", "breed", "last", "l", "lastcmd", "cfg", "config", "listNonOC",
			"listall", "listalldna", "info", "charset", "help", "dump", "wip" };

	private static final String[] modes = new String[] { "0", "1", "2" };

	private LoggerPro logbook;

	Entity[] OC;
	Entity[] show;

	/**
	 * Main Constructor that sets up the Entity Loader.
	 * 
	 * @param Entity[]
	 *            OC Entity List
	 * @param Entity[]
	 *            Non-OC Entity List
	 * @param Day
	 *            Day used for comparison
	 */
	public EntityLoader(Entity[] o, Entity[] c, Day d, LoggerPro logbook) {
		OC = o;
		show = c;
		compDay = d;
		this.logbook = logbook;
		this.logbook.log("Constructed EntityLoader");
		this.logbook.log("Setting date to : " + compDay.toString(true));
	}

	/**
	 * This method takes the input String and executes the applicable action.
	 * 
	 * @param String
	 *            Input String
	 */
	public void handle(String s) {
		sl = s.split("\\s+");
		this.logbook.log("Processing input : " + s);
		int handler = -1;
		Boolean origin = true, controlVar = false;
		for (int i = 0; i < OC.length; i++) {
			if (OC[i].getName().equalsIgnoreCase(s)) {
				handler = i;
			}
			if (s.equalsIgnoreCase(OC[i].getAltName())) {
				handler = i;
			}
		}
		for (int i = 0; i < show.length; i++) {
			if (show[i].getName().equalsIgnoreCase(s)) {
				handler = i;
				origin = false;
			}
			if (show[i].getAltName().equalsIgnoreCase(s)) {
				handler = i;
				origin = false;
			}
		}

		if (handler == -1)
			this.logbook.log("Input : " + s + " Was not an Entity. Checking if command...");

		for (int i = 0; i < commands.length; i++) {
			if (commands[i].equalsIgnoreCase(sl[0])) {
				controlVar = true;
			}
		}

		if (!controlVar && handler == -1)
			this.logbook.log("Input : " + sl[0] + " Was not a command. Displaying Help.");
		else if (controlVar)
			this.logbook.log("Input : " + sl[0] + " Was a command. Executing...");

		if (!sl[0].equalsIgnoreCase("last") && !sl[0].equalsIgnoreCase("lastcmd") && !sl[0].equalsIgnoreCase("l")) {
			lastCmd = MasterControl.poni.lblTextArea.getText();
		}
		MasterControl.poni.lblTextArea.setText("");
		try {
			MasterControl.poni.lblPoniiPic.setIcon(MasterControl.poni.getImageIcn("/DevTSK/Entity/files/null.png"));
			MasterControl.poni.lblCMPic.setIcon(MasterControl.poni.getImageIcn("/DevTSK/Entity/files/null.png"));
		} catch (IOException e) {
			this.logbook.log(2, "A picture wasn't found!");
			this.logbook.log(2, e.getMessage());
			for (StackTraceElement y : e.getStackTrace())
				this.logbook.log(2, y.toString());
		}
		if (handler >= 0) {
			getInfo(origin, handler);
		} else if (!controlVar) {
			MasterControl.poni.println(help());
		} else if (controlVar) {
			try {
				control(s);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Executes proper functions based on the input command.
	 * 
	 * @param String
	 *            Input Command
	 * @throws IOException
	 * @throws ConfigException
	 */
	private void control(String s) {
		String cmd = s;
		String[] sl = cmd.split("\\s+");
		this.logbook.log("Attempting execution of command : " + sl[0]);
		if (sl[0].equalsIgnoreCase("Exit")) {
			this.logbook.log(1, "System is shutting down!");
			this.logbook.killStream();
			System.exit(0);
		}
		if (sl[0].equalsIgnoreCase("ErrorCheck")) {
			System.out.println("Stub command");
		}
		if (sl[0].equalsIgnoreCase("Color") || sl[0].equalsIgnoreCase("Colour")) {
			if (sl.length == 4) {
				int r, g, b;
				r = Integer.parseInt(sl[1]);
				g = Integer.parseInt(sl[2]);
				b = Integer.parseInt(sl[3]);
				MasterControl.poni.frmPoniiPic.getContentPane().setBackground(new Color(r, g, b));
				this.logbook.log("Command " + sl[0] + " completed successfully.");
			} else
				this.logbook.log(2, "Command " + sl[0] + " failed to complete successfully. Required args not met.");
		}
		if (sl[0].equalsIgnoreCase("InputColor") || sl[0].equalsIgnoreCase("InputColour")) {
			if (sl.length == 4) {
				int r, g, b;
				r = Integer.parseInt(sl[1]);
				g = Integer.parseInt(sl[2]);
				b = Integer.parseInt(sl[3]);
				MasterControl.poni.lblTextArea.setBackground(new Color(r, g, b));
				this.logbook.log("Command " + sl[0] + " completed successfully.");
			} else
				this.logbook.log(2, "Command " + sl[0] + " failed to complete successfully. Required args not met.");
		}
		if (sl[0].equalsIgnoreCase("OutputColor") || sl[0].equalsIgnoreCase("OutputColour")) {
			if (sl.length == 4) {
				int r, g, b;
				r = Integer.parseInt(sl[1]);
				g = Integer.parseInt(sl[2]);
				b = Integer.parseInt(sl[3]);
				MasterControl.poni.lblInfo.setBackground(new Color(r, g, b));
				this.logbook.log("Command " + sl[0] + " completed successfully.");
			} else
				this.logbook.log(2, "Command " + sl[0] + " failed to complete successfully. Required args not met.");
		}
		if (sl[0].equalsIgnoreCase("OutputTextColor") || sl[0].equalsIgnoreCase("OutputTextColour")) {
			if (sl.length == 4) {
				int r, g, b;
				r = Integer.parseInt(sl[1]);
				g = Integer.parseInt(sl[2]);
				b = Integer.parseInt(sl[3]);
				MasterControl.poni.lblInfo.setForeground(new Color(r, g, b));
				this.logbook.log("Command " + sl[0] + " completed successfully.");
			} else
				this.logbook.log(2, "Command " + sl[0] + " failed to complete successfully. Required args not met.");
		}
		if (sl[0].equalsIgnoreCase("InputTextColor") || sl[0].equalsIgnoreCase("InputTextColour")) {
			if (sl.length == 4) {
				int r, g, b;
				r = Integer.parseInt(sl[1]);
				g = Integer.parseInt(sl[2]);
				b = Integer.parseInt(sl[3]);
				MasterControl.poni.lblTextArea.setForeground(new Color(r, g, b));
				this.logbook.log("Command " + sl[0] + " completed successfully.");
			} else
				this.logbook.log(1, "Command " + sl[0] + " failed to complete successfully. Required args not met.");
		}
		if (sl[0].equalsIgnoreCase("extract")) {
			if (sl.length > 1) {
				try {
					extract(sl[1], false);
				} catch (IOException e) {
					this.logbook.log(2, "Command " + sl[0] + " failed to complete successfully. IOException");
					this.logbook.log(2, e.getMessage());
					for (StackTraceElement y : e.getStackTrace())
						this.logbook.log(2, y.toString());
				}
			} else if (sl.length == 1) {
				try {
					extract();
				} catch (IOException e) {
					this.logbook.log(2, "Command " + sl[0] + " failed to complete successfully. IOException");
					this.logbook.log(2, e.getMessage());
					for (StackTraceElement y : e.getStackTrace())
						this.logbook.log(2, y.toString());
				}
			} else
				this.logbook.log(2, "Command " + sl[0] + " failed to complete successfully. Required args not met.");
		}
		if (sl[0].equalsIgnoreCase("breed")) {
			String say = "Syntax is breed <mode> <Father> <Mother> [times]";
			Breeder b = new Breeder(Breeder.INTACT_COLOURS);
			Boolean isValidMode = true;
			for (int i = 0; i < modes.length; i++) {
				if (sl[1].equalsIgnoreCase(modes[i]))
					isValidMode = true;
			}
			if (sl.length < 4 || sl.length > 5) {
				this.logbook.log(2, "Command " + sl[0] + " failed to complete successfully. Required args not met.");
			} else if (!isValidMode) {
				say = "Invalid mode : " + sl[1] + "\n\nValid Modes are:\n"
						+ "0 = Colour chosen will be from one of the parents.\n"
						+ "1 = Colour chosen will be a mix of the RGB values of the parents.\n"
						+ "2 = Colour will be chosen by chopping the parents colours and mixing the values.";
				this.logbook.log(2, "Command " + sl[0] + " failed to complete successfully. Invalid mode.");
			} else {
				Boolean OCo = true, OCt = true;
				int f = -1, m = -1;
				for (int i = 0; i < OC.length; i++) {
					if (sl[2].equalsIgnoreCase(OC[i].getName())) {
						f = i;
					}
					if (sl[2].equalsIgnoreCase(OC[i].getAltName())) {
						f = i;
					}
				}
				for (int i = 0; i < show.length; i++) {
					if (sl[2].equalsIgnoreCase(show[i].getName())) {
						f = i;
						OCo = false;
					}
					if (sl[2].equalsIgnoreCase(show[i].getAltName())) {
						f = i;
						OCo = false;
					}
				}
				for (int i = 0; i < OC.length; i++) {
					if (sl[3].equalsIgnoreCase(OC[i].getName())) {
						m = i;
					}
					if (sl[3].equalsIgnoreCase(OC[i].getAltName())) {
						m = i;
					}
				}
				for (int i = 0; i < show.length; i++) {
					if (sl[3].equalsIgnoreCase(show[i].getName())) {
						m = i;
						OCt = false;
					}
					if (sl[3].equalsIgnoreCase(show[i].getAltName())) {
						m = i;
						OCt = false;
					}
				}
				if (sl[1].equals("0"))
					b = new Breeder(Breeder.INTACT_COLOURS);
				if (sl[1].equals("1"))
					b = new Breeder(Breeder.SAMERGB_COLOURS);
				if (sl[1].equals("2"))
					b = new Breeder(Breeder.RANDOM);
				if (f != -1 && m != -1) {

					if (OCo && OCt)
						say = b.breed(OC[f], OC[m]);
					if (OCo && !OCt)
						say = b.breed(OC[f], show[m]);
					if (!OCo && OCt)
						say = b.breed(show[f], OC[m]);
					if (!OCo && !OCt)
						say = b.breed(show[f], show[m]);
					if (sl.length == 5) {
						say = "";
						for (int i = 1; i < Integer.parseInt(sl[4]); i++) {
							if (sl[0].equalsIgnoreCase("breed")) {
								if (OCo && OCt)
									say += "\n" + b.breed(OC[f], OC[m]);
								if (OCo && !OCt)
									say += "\n" + b.breed(OC[f], show[m]);
								if (!OCo && OCt)
									say += "\n" + b.breed(show[f], OC[m]);
								if (!OCo && !OCt)
									say += "\n" + b.breed(show[f], show[m]);
							}
						}
					}
				} else
					this.logbook.log(2, "Command " + sl[0] + " failed to complete successfully. Could not find one or both parents.");
			}
			if (say.contains("Error"))
				this.logbook.log(2, "Command " + sl[0] + " failed to complete successfully. Missing DNA on one or both parents.");
			else
				this.logbook.log("Command " + sl[0] + " completed successfully.");
			MasterControl.poni.lblInfo.setText(say);
		}
		if (sl[0].equalsIgnoreCase("listNonOC") || sl[0].equalsIgnoreCase("listall") || sl[0].equalsIgnoreCase("listalldna")) {
			MasterControl.poni.printCl();
			if (sl[0].equalsIgnoreCase("listall")) {
				MasterControl.poni.println("Acceptable OC/NonOC Ponii Names: " + (OC.length + show.length) + "\n");
				for (int i = 0; i < OC.length; i++) {
					MasterControl.poni.println(OC[i].getName() + "\tAKA\t" + OC[i].getAltName());
				}
				for (int i = 0; i < show.length; i++) {
					MasterControl.poni.println(show[i].getName() + "\tAKA\t" + show[i].getAltName());
				}
			} else if (sl[0].equalsIgnoreCase("listalldna")) {
				MasterControl.poni.println("OC/NonOC Poniis with DNA: \n");
				for (int i = 0; i < OC.length; i++) {
					try {
						if (OC[i].getDNA() != null)
							MasterControl.poni.println(OC[i].getName() + "\tAKA\t" + OC[i].getAltName());
					} catch (Exception e) {
					}
				}

				for (int i = 0; i < show.length; i++) {
					try {
						if (show[i].getDNA() != null)
							MasterControl.poni.println(show[i].getName() + "\tAKA\t" + show[i].getAltName());
					} catch (Exception e) {
					}
				}

			} else {
				MasterControl.poni.println("Acceptable NonOC Ponii Names: " + show.length + "\n");
				for (int i = 0; i < show.length; i++) {
					MasterControl.poni.println(show[i].getName() + "\tAKA\t" + show[i].getAltName());
				}
			}
			this.logbook.log("Command " + sl[0] + " completed successfully.");
		}
		if (sl[0].equalsIgnoreCase("last") || sl[0].equalsIgnoreCase("lastcmd") || sl[0].equalsIgnoreCase("l"))

		{
			MasterControl.poni.lblTextArea.setText(lastCmd);
			this.logbook.log("Command " + sl[0] + " completed successfully.");
		}
		if (sl[0].equalsIgnoreCase("cfg") || sl[0].equalsIgnoreCase("config")) {
			Boolean good = true;
			System.out.println("Saving Configuration...");

			byte[] tst = new byte[] {};
			String strnj = "version = 2.0;\n\n" + "bgr = "
					+ MasterControl.poni.frmPoniiPic.getContentPane().getBackground().getRed() + ";\n" + "bgg = "
					+ MasterControl.poni.frmPoniiPic.getContentPane().getBackground().getGreen() + ";\n" + "bgb = "
					+ MasterControl.poni.frmPoniiPic.getContentPane().getBackground().getBlue() + ";\n\n" + "inbr = "
					+ MasterControl.poni.lblTextArea.getBackground().getRed() + ";\n" + "inbg = "
					+ MasterControl.poni.lblTextArea.getBackground().getGreen() + ";\n" + "inbb = "
					+ MasterControl.poni.lblTextArea.getBackground().getBlue() + ";\n\n" + "infr = "
					+ MasterControl.poni.lblTextArea.getForeground().getRed() + ";\n" + "infg = "
					+ MasterControl.poni.lblTextArea.getForeground().getGreen() + ";\n" + "infb = "
					+ MasterControl.poni.lblTextArea.getForeground().getBlue() + ";\n\n" + "outbr = "
					+ MasterControl.poni.lblInfo.getBackground().getRed() + ";\n" + "outbg = "
					+ MasterControl.poni.lblInfo.getBackground().getGreen() + ";\n" + "outbb = "
					+ MasterControl.poni.lblInfo.getBackground().getBlue() + ";\n\n" + "outfr = "
					+ MasterControl.poni.lblInfo.getForeground().getRed() + ";\n" + "outfg = "
					+ MasterControl.poni.lblInfo.getForeground().getGreen() + ";\n" + "outfb = "
					+ MasterControl.poni.lblInfo.getForeground().getBlue() + ";\n\n" + "sep = " + "false;";
			// + "frame = " + framew + ";";
			tst = strnj.getBytes();

			try {
				FileOutputStream send = new FileOutputStream("./PoniiConfig.cfg");
				send.write(tst);
				send.close();
			} catch (IOException e) {
				this.logbook.log(2, "Command " + sl[0] + " failed to complete successfully. IOException");
				this.logbook.log(2, e.getMessage());
				for (StackTraceElement y : e.getStackTrace())
					this.logbook.log(2, y.toString());
				good = false;
			}
			if (good)
				this.logbook.log("Command " + sl[0] + " completed successfully.");
		}
		if (sl[0].equalsIgnoreCase("info")) {
			if (sl.length < 2 || sl.length > 2) {
				MasterControl.poni.printCl();
				MasterControl.poni.println(
						"Syntax is info <Entity>\nGives Info on the TYPE of Entity.\nIf the Entity name contains a space it wont work.");
				this.logbook.log(2, "Command " + sl[0] + " failed to complete successfully. Required args not met.");
			} else {
				MasterControl.poni.printCl();
				Boolean doop = false, origin = false;
				int loc = -1;
				for (int i = 0; i < OC.length; i++) {
					if (sl[1].equalsIgnoreCase(OC[i].getName()) || sl[1].equalsIgnoreCase(OC[i].getAltName())) {
						doop = true;
						loc = i;
					}
				}
				for (int i = 0; i < show.length; i++) {
					if (sl[1].equalsIgnoreCase(show[i].getName()) || sl[1].equalsIgnoreCase(show[i].getAltName())) {
						doop = true;
						origin = true;
						loc = i;
					}
				}
				if (doop) {
					if (!origin) {
						MasterControl.poni.println(OC[loc].getInfo());
					} else {
						MasterControl.poni.println(OC[loc].getInfo());
					}
					this.logbook.log("Command " + sl[0] + " completed successfully.");
				} else {
					this.logbook.log(2, "Command " + sl[0] + " failed to complete successfully. " + sl[1] + " is not an entity.");
				}
			}
		}
		if (sl[0].equalsIgnoreCase("charset")) {
			MasterControl.poni.printCl();
			MasterControl.poni.println(name);
			this.logbook.log("Command " + sl[0] + " completed successfully.");
		}
		if (sl[0].equalsIgnoreCase("help")) {
			MasterControl.poni.printCl();
			for (int i = 0; i < commandsyntax.length; i++)
				MasterControl.poni.println(commandsyntax[i] + " : " + commandexpl[i]);
			this.logbook.log("Command " + sl[0] + " completed successfully.");
		}
		if (sl[0].equalsIgnoreCase("dump")) {
			String og = "Successfully Dumped Entity names and ID numbers to EntityList.txt";
			String out = "";
			for (int i = 0; i < OC.length; i++) {
				out += i + " : " + OC[i].getName() + "\n";
			}
			StringWriter sw = new StringWriter();
			try {
				sw.Write(out, "EntityList.txt", true);
			} catch (IOException e) {
				this.logbook.log(2, "Command " + sl[0] + " failed to complete successfully. IOException.");
				this.logbook.log(2, e.getMessage());
				for (StackTraceElement y : e.getStackTrace())
					this.logbook.log(2, y.toString());
				og = "Something went wrong while trying to save the file.";
			}
			MasterControl.poni.printCl();
			MasterControl.poni.println(og);
			if (!og.contains("wrong"))
				this.logbook.log("Command " + sl[0] + " completed successfully.");
		}
		if (sl[0].equalsIgnoreCase("WIP")) {
			String WIPEntitiis = "Entities that need work done:\n",
					noImage = "1 = No Alternate Image\n2 = No Main Image\n3 = No images\nEntitiis that have no image:\n",
					noDNA = "Entities without DNA:\n";
			for (int i = 0; i < OC.length; i++) {
				String temp = OC[i].toString(compDay);
				int g = 0;
				if (temp.contains("WIP"))
					WIPEntitiis = WIPEntitiis + i + " : " + OC[i].getAltName() + "\n";
				if (OC[i].imagepath.equalsIgnoreCase("null.png"))
					g += 2;
				if (OC[i].altimagepath.equalsIgnoreCase("null.png"))
					g += 1;
				if (g != 0)
					noImage = noImage + i + " : " + OC[i].getAltName() + " " + g + "\n";
				if (OC[i].getDNA().toString().equalsIgnoreCase("Empty"))
					noDNA = noDNA + i + " : " + OC[i].getAltName() + "\n";
			}
			WIPEntitiis = WIPEntitiis + "\n" + noImage + "\n" + noDNA;

			String og = "Success!";

			StringWriter sw = new StringWriter();
			try {
				sw.Write(WIPEntitiis, "WIPList.txt", true);
			} catch (IOException e) {
				this.logbook.log(2, "Command " + sl[0] + " failed to complete successfully. IOException.");
				this.logbook.log(2, e.getMessage());
				for (StackTraceElement y : e.getStackTrace())
					this.logbook.log(2, y.toString());
				og = "Failure.";
			}
			if (!og.equals("Failure."))
				this.logbook.log("Command " + sl[0] + " completed successfully.");
			MasterControl.poni.printCl();
			MasterControl.poni.println(og);
		}
	}

	/**
	 * Prints Information on the given entity.
	 * Gets Info from the OC or Non-OC List dependent on the Control Boolean.
	 * Gets Info on the entity from the list based on the input integer.
	 * 
	 * @param Boolean
	 *            Control Boolean
	 * @param int
	 *            Input Integer
	 */
	private void getInfo(Boolean b, int i) {
		MasterControl.poni.printCl();

		if (b)
			MasterControl.poni.println(OC[i].toString(compDay));
		if (!b)
			MasterControl.poni.println(show[i].toString(compDay));

		if (b)
			try {
				MasterControl.poni.lblPoniiPic.setIcon(MasterControl.poni.getImageIcn("/DevTSK/Entity/files/" + OC[i].getImagePath()));
				MasterControl.poni.lblCMPic.setIcon(MasterControl.poni.getImageIcn("/DevTSK/Entity/files/" + OC[i].getAltImagePath()));
			} catch (IOException e) {
				this.logbook.log(2, "An error occurred while trying to load an image.");
			}
		if (!b)
			try {
				MasterControl.poni.lblPoniiPic.setIcon(MasterControl.poni.getImageIcn("/DevTSK/Entity/files/" + show[i].getImagePath()));
				MasterControl.poni.lblCMPic.setIcon(MasterControl.poni.getImageIcn("/DevTSK/Entity/files/" + show[i].getAltImagePath()));
			} catch (IOException e) {
				this.logbook.log(2, "An error occurred while trying to load an image.");
			}
	}

	/**
	 * Returns a dynamic help string.
	 * Dynamic portion based on registered Entities.
	 * 
	 * @return The Help String.
	 */
	private String help() {
		String XD = "Acceptable Ponii names : " + OC.length + "\n";
		MasterControl.poni.printCl();
		for (int i = 0; i < OC.length; i++) {
			XD = XD + "\n" + OC[i].getName() + "\tAKA\t" + OC[i].getAltName();
		}
		XD = XD + "\n\nApplicable commands : " + commandexpl.length + "\n";
		for (int i = 0; i < commandexpl.length; i++)
			XD = XD + "\n" + commandsyntax[i] + " : " + commandexpl[i];
		return XD;
	}

	/**
	 * Handles Extracting of Images of a specific entity based on the input
	 * name.
	 * 
	 * @param String
	 *            Entity Name
	 * @throws IOException
	 */
	private void extract(String string, Boolean fromLoop) throws IOException {
		Boolean preformAction = false;
		String charToExtract = "null";
		for (int i = 0; i < OC.length; i++) {
			if (string.equalsIgnoreCase(OC[i].getAltName())) {
				preformAction = true;
				charToExtract = OC[i].getImagePath();
			}
			if (string.equalsIgnoreCase(OC[i].getName())) {
				preformAction = true;
				charToExtract = OC[i].getImagePath();
			}
		}
		if (preformAction) {
			this.logbook.log(1, "Extracting : " + string);
			File f = new File("./extracted/");
			f.mkdir();
			send = new FileOutputStream(f + "/" + charToExtract);
			byte[] out = new byte[EntityLoader.class.getResourceAsStream("/DevTSK/Entity/files/" + charToExtract).available()];
			EntityLoader.class.getResourceAsStream("/DevTSK/Entity/files/" + charToExtract).read(out);
			send.write(out);
			this.logbook.log(1, "Extracted : " + charToExtract + " to \"" + f.getAbsoluteFile() + "\"");
			send.close();
			if (!fromLoop)
				this.logbook.log("Command " + sl[0] + " completed successfully.");
		} else
			this.logbook.log(2, "Entity : " + string + " does not exist.");
	}

	/**
	 * Extracts all Entity images.
	 * Works by way of cycling through the OC entity name list and calling
	 * extract(String) with all names.
	 * 
	 * @throws IOException
	 */
	private void extract() throws IOException {
		for (int i = 0; i < OC.length; i++) {
			extract(OC[i].getName(), true);
		}
		this.logbook.log("Command " + sl[0] + " completed successfully.");
	}

	/**
	 * Extracts the default configuration.
	 * Only used when the original could not be found.
	 * 
	 * @throws IOException
	 */
	public void extractConfig() throws IOException {
		Boolean preformAction = true;
		if (preformAction) {
			System.out.println("Extracting : " + "Configuration File");
			File f = new File("./");
			f.mkdir();
			send = new FileOutputStream(f + "/" + "PoniiConfig.cfg");
			String charToExtract = "PoniiConfig.cfg";
			byte[] out = new byte[EntityLoader.class.getResourceAsStream("/DevTSK/Entity/files/" + charToExtract).available()];
			EntityLoader.class.getResourceAsStream("/DevTSK/Entity/files/" + charToExtract).read(out);
			send.write(out);
			System.out.println("Extracted : " + charToExtract + " to \"" + f.getAbsoluteFile() + "\"");
			send.close();
		}
	}
}
