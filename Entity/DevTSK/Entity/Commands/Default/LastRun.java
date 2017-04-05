package DevTSK.Entity.Commands.Default;

import DevTSK.Entity.Entity;
import DevTSK.Entity.MasterControl;
import DevTSK.Entity.Commands.Command;
import DevTSK.Util.LoggerPro;

public class LastRun implements Command {

	public LastRun() {
	}

	@Override
	public String[] getTokens() {
		return new String[] { "L", "Last" };
	}

	@Override
	public String getSyntax() {
		return "Last";
	}

	@Override
	public String getHelp() {
		return "Enters the last run command into the Input Box";
	}

	@Override
	public int run(Entity[] list, LoggerPro logbook, String[] sl) {
		MasterControl.poni.lblTextArea.setText(MasterControl.poni.el.lastCmd);
		logbook.log("Command " + sl[0] + " completed successfully.");
		return 0;
	}

	@Override
	public boolean isHidden() {
		return false;
	}

}
