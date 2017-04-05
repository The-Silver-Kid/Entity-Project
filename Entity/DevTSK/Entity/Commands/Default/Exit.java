package DevTSK.Entity.Commands.Default;

import DevTSK.Entity.Entity;
import DevTSK.Entity.Commands.Command;
import DevTSK.Util.LoggerPro;

public class Exit implements Command {

	public Exit() {
	}

	@Override
	public boolean isHidden() {
		return false;
	}

	@Override
	public String[] getTokens() {
		return new String[] { "Exit" };
	}

	@Override
	public String getSyntax() {
		return "Exit";
	}

	@Override
	public String getHelp() {
		return "Closes the System";
	}

	@Override
	public int run(Entity[] list, LoggerPro logbook, String[] sl) {
		logbook.log(1, "System is shutting down!");
		System.exit(0);
		return 0;
	}

}
