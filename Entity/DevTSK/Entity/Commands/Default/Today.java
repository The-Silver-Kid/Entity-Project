package DevTSK.Entity.Commands.Default;

import DevTSK.Entity.Entity;
import DevTSK.Entity.MasterControl;
import DevTSK.Entity.Commands.Command;
import DevTSK.Util.LoggerPro;

public class Today implements Command {

	@Override
	public boolean isHidden() {
		return false;
	}

	@Override
	public String[] getTokens() {
		return new String[] { "Today", "day" };
	}

	@Override
	public String getSyntax() {
		return "Today";
	}

	@Override
	public String getHelp() {
		return "Returns current date";
	}

	@Override
	public int run(Entity[] list, LoggerPro logbook, String[] sl) {
		MasterControl.poni.printCl();
		MasterControl.poni.println(MasterControl.u.getOffset().getDay().toString(true));
		return 0;
	}

}
