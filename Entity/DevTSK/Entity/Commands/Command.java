package DevTSK.Entity.Commands;

import DevTSK.Entity.Entity;
import DevTSK.Util.LoggerPro;

public interface Command {

	public boolean isHidden();

	public String[] getTokens();

	public String getSyntax();

	public String getHelp();

	public int run(Entity[] list, LoggerPro logbook, String[] sl);
}