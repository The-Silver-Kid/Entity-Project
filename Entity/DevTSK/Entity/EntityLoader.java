package DevTSK.Entity;

import java.io.File;
// import java.io.FileOutputStream;
import java.io.IOException;
import DevTSK.Entity.Commands.Command;
import DevTSK.Util.Day;
import DevTSK.Util.LoggerPro;
import DevTSK.Util.DAG.ConfigException;

/**
 * @author The_Silver_Kid
 *
 *         The Main Guts of the program.
 * 
 *         Handles all input and output.
 */
public class EntityLoader {

	public String lastCmd;
	public String name;
	private static String[] sl;

	public final Day compDay;

	//	private static FileOutputStream send;

	public final File WORKING_DIR;

	public final Command[] commands;
	public final int commandsHidden;

	private LoggerPro logbook;

	Entity[] OC;

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
	public EntityLoader(Entity[] o, Day d, LoggerPro logbook, File f, Command[] registeredCommands) {
		WORKING_DIR = f;
		OC = o;
		compDay = d;
		this.logbook = logbook;
		this.logbook.log("Constructed EntityLoader");
		this.logbook.log("Setting date to : " + compDay.toString(true));
		this.commands = registeredCommands;
		int t = 0;
		for (Command com : commands)
			if (com.isHidden())
				t++;
		commandsHidden = t;
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
		Boolean controlVar = false;
		for (int i = 0; i < OC.length; i++) {
			if (OC[i].getName().equalsIgnoreCase(s)) {
				handler = i;
			}
			if (s.equalsIgnoreCase(OC[i].getAltName())) {
				handler = i;
			}
		}

		if (handler == -1)
			this.logbook.log("Input : " + s + " Was not an Entity. Checking if command...");

		for (int o = 0; o < commands.length; o++)
			for (int i = 0; i < commands[o].getTokens().length; i++) {
				if (commands[o].getTokens()[i].equalsIgnoreCase(sl[0])) {
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
			MasterControl.poni.lblPoniiPic.setIcon(MasterControl.poni.getInternalImageIcn("/DevTSK/Entity/files/null.png"));
			MasterControl.poni.lblCMPic.setIcon(MasterControl.poni.getInternalImageIcn("/DevTSK/Entity/files/null.png"));
		} catch (IOException e) {
			this.logbook.log(2, "A picture wasn't found!");
			this.logbook.log(2, e.getMessage());
			for (StackTraceElement y : e.getStackTrace())
				this.logbook.log(2, y.toString());
		}
		if (handler >= 0) {
			getInfo(handler);
		} else if (!controlVar) {
			MasterControl.poni.println(help());
		} else if (controlVar) {
			try {
				control(s);
			} catch (Exception e) {
				this.logbook.log(2, "Something went wrong while attempting to process unknown command.");
				this.logbook.log(2, e.getLocalizedMessage());
				for (StackTraceElement sw : e.getStackTrace())
					this.logbook.log(2, sw.toString());
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
		for (Command com : commands)
			for (int i = 0; i < com.getTokens().length; i++)
				if (com.getTokens()[i].equalsIgnoreCase(sl[0]))
					com.run(OC, logbook, sl);
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
	private void getInfo(int i) {
		MasterControl.poni.printCl();

		MasterControl.poni.println(OC[i].toString(compDay));

		try {
			if (!OC[i].getImagePath().equalsIgnoreCase("null.png"))
				MasterControl.poni.lblPoniiPic.setIcon(MasterControl.poni.getExternalImageIcn(OC[i].getImagePath()));
			if (!OC[i].getAltImagePath().equalsIgnoreCase("null.png"))
				MasterControl.poni.lblCMPic.setIcon(MasterControl.poni.getExternalImageIcn(OC[i].getAltImagePath()));
		} catch (IOException e) {
			this.logbook.log(2, "An error occurred while trying to load an image.");
			this.logbook.log(2, e.getMessage());
			for (StackTraceElement sw : e.getStackTrace())
				this.logbook.log(2, sw.toString());
		}
	}

	/**
	 * Returns a dynamic help string.
	 * Dynamic portion based on registered Entities.
	 * 
	 * @return The Help String.
	 */
	private String help() {
		String XD = "Acceptable Entity names : " + OC.length + "\n";
		MasterControl.poni.printCl();
		for (int i = 0; i < OC.length; i++) {
			XD = XD + "\n" + OC[i].getName() + "\tAKA\t" + OC[i].getAltName();
		}
		XD = XD + "\n\nApplicable commands : " + (commands.length - commandsHidden) + "\nRGB Values are in decimal and range from 0-255\n";
		int t = 1;
		for (int i = 0; i < commands.length; i++)
			if (!commands[i].isHidden()) {
				XD = XD + "\n" + t + ". " + commands[i].getSyntax() + "\t" + commands[i].getHelp();
				t++;
			}
		return XD;
	}
}
