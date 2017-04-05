package DevTSK.Entity.Commands.Default;

import java.io.File;
import java.io.IOException;
import DevTSK.Entity.Entity;
import DevTSK.Entity.MasterControl;
import DevTSK.Entity.Commands.Command;
import DevTSK.Util.LoggerPro;
import DevTSK.Util.StringWriter;

public class WIP implements Command {

	public WIP() {
	}

	@Override
	public boolean isHidden() {
		return true;
	}

	@Override
	public String[] getTokens() {
		return new String[] { "WIP" };
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
		String WIPEntitiis = "Entities that need work done:\n",
				noImage = "1 = No Alternate Image\n2 = No Main Image\n3 = No images\nEntitiis that have no image:\n",
				noDNA = "Entities without DNA:\n";
		for (int i = 0; i < list.length; i++) {
			String temp = list[i].toString(MasterControl.poni.el.compDay);
			int g = 0;
			if (temp.contains("WIP"))
				WIPEntitiis = WIPEntitiis + i + " : " + list[i].getAltName() + "\n";
			if (list[i].getImagePath().equalsIgnoreCase("null.png"))
				g += 2;
			if (list[i].getAltImagePath().equalsIgnoreCase("null.png"))
				g += 1;
			if (g != 0)
				noImage = noImage + i + " : " + list[i].getAltName() + " " + g + "\n";
			if (list[i].getDNA().toString().equalsIgnoreCase("Empty"))
				noDNA = noDNA + i + " : " + list[i].getAltName() + "\n";
		}
		WIPEntitiis = WIPEntitiis + "\n" + noImage + "\n" + noDNA;

		String og = "Success!";

		StringWriter sw = new StringWriter();
		try {
			sw.Write(WIPEntitiis, new File(MasterControl.poni.el.WORKING_DIR + "/WIPList.txt"), true);
		} catch (IOException e) {
			logbook.log(2, "Command " + sl[0] + " failed to complete successfully. IOException.");
			logbook.log(2, e.getMessage());
			for (StackTraceElement y : e.getStackTrace())
				logbook.log(2, y.toString());
			og = "Failure.";
		}
		if (!og.equals("Failure."))
			logbook.log("Command " + sl[0] + " completed successfully.");
		MasterControl.poni.printCl();
		MasterControl.poni.println(og);
		return 0;
	}

}
