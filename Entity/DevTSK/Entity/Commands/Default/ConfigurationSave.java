package DevTSK.Entity.Commands.Default;

import java.io.FileOutputStream;
import java.io.IOException;
import DevTSK.Entity.Entity;
import DevTSK.Entity.MasterControl;
import DevTSK.Entity.Commands.Command;
import DevTSK.Util.LoggerPro;

public class ConfigurationSave implements Command {

	public ConfigurationSave() {
	}

	@Override
	public boolean isHidden() {
		return false;
	}

	@Override
	public String[] getTokens() {
		return new String[] { "CFG" };
	}

	@Override
	public String getSyntax() {
		return "CFG";
	}

	@Override
	public String getHelp() {
		return "Saves current configuration.";
	}

	@Override
	public int run(Entity[] list, LoggerPro logbook, String[] sl) {
		Boolean good = true;
		System.out.println("Saving Configuration...");

		byte[] tst = new byte[] {};
		String strnj = "version = 2.0;\n\n" + "bgr = "
				+ MasterControl.poni.frmPoniiPic.getContentPane().getBackground().getRed() + ";\n" + "bgg = "
				+ MasterControl.poni.frmPoniiPic.getContentPane().getBackground().getGreen() + ";\n" + "bgb = "
				+ MasterControl.poni.frmPoniiPic.getContentPane().getBackground().getBlue() + ";\n\n" + "inbr = "
				+ MasterControl.poni.lblTextArea.getBackground().getRed() + ";\n" + "inbg = "
				+ MasterControl.poni.lblTextArea.getBackground().getGreen() + ";\n" + "inbb = "
				+ MasterControl.poni.lblTextArea.getBackground().getBlue() + ";\n\n" + "infr = "
				+ MasterControl.poni.lblTextArea.getForeground().getRed() + ";\n" + "infg = "
				+ MasterControl.poni.lblTextArea.getForeground().getGreen() + ";\n" + "infb = "
				+ MasterControl.poni.lblTextArea.getForeground().getBlue() + ";\n\n" + "outbr = "
				+ MasterControl.poni.lblInfo.getBackground().getRed() + ";\n" + "outbg = "
				+ MasterControl.poni.lblInfo.getBackground().getGreen() + ";\n" + "outbb = "
				+ MasterControl.poni.lblInfo.getBackground().getBlue() + ";\n\n" + "outfr = "
				+ MasterControl.poni.lblInfo.getForeground().getRed() + ";\n" + "outfg = "
				+ MasterControl.poni.lblInfo.getForeground().getGreen() + ";\n" + "outfb = "
				+ MasterControl.poni.lblInfo.getForeground().getBlue() + ";\n\n" + "sep = " + "true;";
		// + "frame = " + framew + ";";
		tst = strnj.getBytes();

		try {
			FileOutputStream send = new FileOutputStream("./EntityConfig.cfg");
			send.write(tst);
			send.close();
		} catch (IOException e) {
			logbook.log(2, "Command " + sl[0] + " failed to complete successfully. IOException");
			logbook.log(2, e.getMessage());
			for (StackTraceElement sw : e.getStackTrace())
				logbook.log(2, sw.toString());
			good = false;
		}
		if (good) {
			logbook.log("Command " + sl[0] + " completed successfully.");
			return 0;
		} else
			return 1;
	}

}
