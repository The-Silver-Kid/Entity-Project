package DevTSK.Entity.Commands.Default;

import java.awt.Color;
import DevTSK.Entity.Entity;
import DevTSK.Entity.MasterControl;
import DevTSK.Entity.Commands.Command;
import DevTSK.Util.LoggerPro;

public class OtColor implements Command {

	public OtColor() {
	}

	@Override
	public String[] getTokens() {
		return new String[] { "OutputColor", "OutputColour" };
	}

	@Override
	public String getSyntax() {
		return "OutputColor <RRR> <GGG> <BBB>";
	}

	@Override
	public String getHelp() {
		return "Changes the Output Box Color.";
	}

	@Override
	public int run(Entity[] list, LoggerPro logbook, String[] sl) {
		if (sl.length == 4) {
			int r, g, b;
			r = Integer.parseInt(sl[1]);
			g = Integer.parseInt(sl[2]);
			b = Integer.parseInt(sl[3]);
			MasterControl.poni.lblInfo.setBackground(new Color(r, g, b));
			logbook.log("Command " + sl[0] + " completed successfully.");
		} else {
			logbook.log(2, "Command " + sl[0] + " failed to complete successfully. Required args not met.");
			return 1;
		}
		return 0;
	}

	@Override
	public boolean isHidden() {
		return false;
	}

}
