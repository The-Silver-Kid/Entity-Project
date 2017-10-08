package DevTSK.Entity.Commands.Default;

import DevTSK.Entity.Entity;
import DevTSK.Entity.MasterControl;
import DevTSK.Entity.Commands.Command;
import DevTSK.Util.LoggerPro;

public class Info implements Command {

	public Info() {
	}

	@Override
	public boolean isHidden() {
		return false;
	}

	@Override
	public String[] getTokens() {
		return new String[] { "Info" };
	}

	@Override
	public String getSyntax() {
		return "Info <Entity>";
	}

	@Override
	public String getHelp() {
		return "Gives Info on the TYPE of Entity.";
	}

	@Override
	public int run(Entity[] list, LoggerPro logbook, String[] sl) {
		if (sl.length < 2 || sl.length > 2) {
			MasterControl.poni.printCl();
			MasterControl.poni.println(
					"Syntax is info <Entity>\nGives Info on the TYPE of Entity.\nIf the Entity name contains a space it wont work.");
			logbook.log(2, "Command " + sl[0] + " failed to complete successfully. Required args not met.");
			return 1;
		} else {
			MasterControl.poni.printCl();
			Boolean doop = false, origin = false;
			int loc = -1;
			for (int i = 0; i < list.length; i++) {
				if (sl[1].equalsIgnoreCase(list[i].getName()) || sl[1].equalsIgnoreCase(list[i].getAltName())) {
					doop = true;
					loc = i;
				}
			}
			if (doop) {
				if (!origin) {
					MasterControl.poni.println(list[loc].getInfo());
				} else {
					MasterControl.poni.println(list[loc].getInfo());
				}
				logbook.log("Command " + sl[0] + " completed successfully.");
				return 0;
			} else {
				logbook.log(2, "Command " + sl[0] + " failed to complete successfully. " + sl[1] + " is not an entity.");
				return 1;
			}
		}
	}

}
