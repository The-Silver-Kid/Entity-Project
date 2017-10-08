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
		int wip = 0, im1 = 0, im2 = 0, imboth = 0;
		for (int i = 0; i < list.length; i++) {
			String temp = list[i].toString(MasterControl.poni.el.compDay);
			int g = 0;
			if (temp.contains("WIP")) {
				WIPEntitiis = WIPEntitiis + i + " : " + list[i].getAltName() + "\n";
				wip++;
			}
			if (list[i].getImagePath().equalsIgnoreCase("null.png")) {
				g += 2;
				im2++;
			}
			if (list[i].getAltImagePath().equalsIgnoreCase("null.png")) {
				g += 1;
				im1++;
			}
			if (g == 3)
				imboth++;
			if (g != 0)
				noImage = noImage + i + " : " + list[i].getAltName() + " " + g + "\n";
			if (list[i].getDNA().toString().equalsIgnoreCase("Empty"))
				noDNA = noDNA + i + " : " + list[i].getAltName() + "\n";
		}

		String totals = "Missing Info : " + wip + " : " + ((int) (((double) wip / list.length) * 100)) + "%\n" +
				"Missing Main image : " + im2 + " : " + ((int) (((double) im2 / list.length) * 100)) + "%\n" +
				"Missing Secondary image : " + im1 + " : " + ((int) (((double) im1 / list.length) * 100)) + "%\n" +
				"Missing Both Images : " + imboth + " : " + ((int) (((double) imboth / list.length) * 100)) + "%";

		WIPEntitiis = WIPEntitiis + "\n" + noImage + "\n" + noDNA + "\n" + totals;

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
