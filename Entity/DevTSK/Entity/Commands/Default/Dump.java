package DevTSK.Entity.Commands.Default;

import java.io.File;
import java.io.IOException;
import DevTSK.Entity.Entity;
import DevTSK.Entity.MasterControl;
import DevTSK.Entity.Commands.Command;
import DevTSK.Util.LoggerPro;
import DevTSK.Util.StringWriter;

public class Dump implements Command {

	public Dump() {
	}

	@Override
	public boolean isHidden() {
		return true;
	}

	@Override
	public String[] getTokens() {
		return new String[] { "Dump" };
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
		String og = "Successfully Dumped Entity names and ID numbers to EntityList.txt";
		String out = "";
		for (int i = 0; i < list.length; i++) {
			out += i + " : " + list[i].getName() + "\n";
		}
		StringWriter sw = new StringWriter();
		try {
			sw.Write(out, new File(MasterControl.poni.el.WORKING_DIR + "/EntityList.txt"), true);
		} catch (IOException e) {
			logbook.log(2, "Command " + sl[0] + " failed to complete successfully. IOException.");
			logbook.log(2, e.getMessage());
			for (StackTraceElement y : e.getStackTrace())
				logbook.log(2, y.toString());
			og = "Something went wrong while trying to save the file.";
		}
		MasterControl.poni.printCl();
		MasterControl.poni.println(og);
		if (!og.contains("wrong")) {
			logbook.log("Command " + sl[0] + " completed successfully.");
			return 0;
		}
		return 1;
	}

}
