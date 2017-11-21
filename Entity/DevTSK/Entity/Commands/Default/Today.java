package DevTSK.Entity.Commands.Default;

import DevTSK.Entity.Entity;
import DevTSK.Entity.MasterControl;
import DevTSK.Entity.Commands.Command;
import DevTSK.Util.LoggerPro;

public class Today implements Command {

	@Override
	public boolean isHidden() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String[] getTokens() {
		// TODO Auto-generated method stub
		return new String[] { "Today" };
	}

	@Override
	public String getSyntax() {
		// TODO Auto-generated method stub
		return "Today";
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return "Returns current date";
	}

	@Override
	public int run(Entity[] list, LoggerPro logbook, String[] sl) {
		MasterControl.poni.printCl();
		MasterControl.poni.println(MasterControl.u.getOffset().getDay().toString(true));
		return 0;
	}

}
