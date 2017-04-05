package DevTSK.Entity.Commands.Default;

import DevTSK.Entity.Entity;
import DevTSK.Entity.MasterControl;
import DevTSK.Entity.Commands.Command;
import DevTSK.Util.LoggerPro;

public class Charset implements Command {

	public Charset() {
	}

	@Override
	public boolean isHidden() {
		return true;
	}

	@Override
	public String[] getTokens() {
		return new String[] { "Charset" };
	}

	@Override
	public String getSyntax() {
		return null;
	}

	@Override
	public String getHelp() {
		return null;
	}

	@Override
	public int run(Entity[] list, LoggerPro logbook, String[] sl) {
		MasterControl.poni.printCl();
		MasterControl.poni.println(MasterControl.poni.el.name);
		logbook.log("Command " + sl[0] + " completed successfully.");
		return 0;
	}

}
