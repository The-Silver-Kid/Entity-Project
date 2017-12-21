package DevTSK.Entity.Commands.Default;

import DevTSK.Entity.Entity;
import DevTSK.Entity.MasterControl;
import DevTSK.Entity.Commands.Command;
import DevTSK.Util.LoggerPro;

public class Dir implements Command {

	public Dir() {
	}

	@Override
	public boolean isHidden() {
		return true;
	}

	@Override
	public String[] getTokens() {
		return new String[] { "Dir" };
	}

	@Override
	public String getSyntax() {
		return "dir";
	}

	@Override
	public String getHelp() {
		return "Returns the working directory.";
	}

	@Override
	public int run(Entity[] list, LoggerPro logbook, String[] sl) {
		MasterControl.poni.printCl();
		MasterControl.poni.println(MasterControl.poni.el.WORKING_DIR.getAbsolutePath());
		logbook.log("Command " + sl[0] + " completed successfully.");
		return 0;
	}

}
