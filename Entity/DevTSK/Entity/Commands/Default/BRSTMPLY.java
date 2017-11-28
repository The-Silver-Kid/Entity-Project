package DevTSK.Entity.Commands.Default;

import org.hackyourlife.dsp.Player;
import DevTSK.Entity.Entity;
import DevTSK.Entity.Commands.Command;
import DevTSK.Util.FileDetect;
import DevTSK.Util.LoggerPro;

public class BRSTMPLY implements Command {

	private Player p = new Player();
	private boolean isPlay = false;

	@Override
	public boolean isHidden() {
		return true;
	}

	@Override
	public String[] getTokens() {
		return new String[] { "BRSTM" };
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
		if (sl.length > 1)
			if (sl[1].equalsIgnoreCase("Stop") || sl[1].equalsIgnoreCase("s")) {
				p.end();
				isPlay = false;
				return 0;
			} else if (!isPlay) {
				FileDetect f = new FileDetect(sl[1]);
				if (f.Detect()) {
					p = new Player();
					p.start(sl[1]);
					isPlay = true;
					return 0;
				} else {
					return 1;
				}
			}
		return 1;
	}

}
