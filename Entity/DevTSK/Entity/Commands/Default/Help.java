package DevTSK.Entity.Commands.Default;

import DevTSK.Entity.Entity;
import DevTSK.Entity.MasterControl;
import DevTSK.Entity.Commands.Command;
import DevTSK.Util.LoggerPro;

public class Help implements Command {

	public Help() {
	}

	@Override
	public boolean isHidden() {
		return false;
	}

	@Override
	public String[] getTokens() {
		return new String[] { "Help", "cmds" };
	}

	@Override
	public String getSyntax() {
		return "Help";
	}

	@Override
	public String getHelp() {
		return "Returns a list of Commands and their functions.";
	}

	@Override
	public int run(Entity[] list, LoggerPro logbook, String[] sl) {
		int ignore = 1;
		if (sl.length > 1) {
			if (sl[1].equalsIgnoreCase("-all"))
				ignore = 0;
		}
		MasterControl.poni.printCl();
		int t = 1;
		String XD = "";
		for (int i = 0; i < MasterControl.poni.el.commands.length; i++)
			if (!MasterControl.poni.el.commands[i].isHidden() || ignore == 0) {
				XD = XD + "\n" + t + ". " + MasterControl.poni.el.commands[i].getSyntax() + "\t" + MasterControl.poni.el.commands[i].getHelp();
				t++;
			}
		MasterControl.poni.println("\n\nApplicable Commands : "
				+ (MasterControl.poni.el.commands.length - (ignore * MasterControl.poni.el.commandsHidden))
				+ "\nRGB Values are in decimal and range from 0-255\n"
				+ XD);
		logbook.log("Command " + sl[0] + " completed successfully.");
		return 0;
	}

}
