package DevTSK.Entity.Commands.Default;

import java.io.FileOutputStream;
import java.io.IOException;
import com.google.gson.Gson;
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
		return new String[] { "CFG", "Save-Config", "Save", "config" };
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
		Gson g = new Gson();

		MasterControl.config.setBgRGB(new int[] {
				MasterControl.poni.frmPoniiPic.getContentPane().getBackground().getRed(),
				MasterControl.poni.frmPoniiPic.getContentPane().getBackground().getGreen(),
				MasterControl.poni.frmPoniiPic.getContentPane().getBackground().getBlue()
		});
		MasterControl.config.setInBgRGB(new int[] {
				MasterControl.poni.lblInfo.getBackground().getRed(),
				MasterControl.poni.lblInfo.getBackground().getGreen(),
				MasterControl.poni.lblInfo.getBackground().getBlue()
		});
		MasterControl.config.setOutBgRGB(new int[] {
				MasterControl.poni.lblTextArea.getBackground().getRed(),
				MasterControl.poni.lblTextArea.getBackground().getGreen(),
				MasterControl.poni.lblTextArea.getBackground().getBlue()
		});
		MasterControl.config.setInFoRGB(new int[] {
				MasterControl.poni.lblInfo.getForeground().getRed(),
				MasterControl.poni.lblInfo.getForeground().getGreen(),
				MasterControl.poni.lblInfo.getForeground().getBlue()
		});
		MasterControl.config.setOutFoRGB(new int[] {
				MasterControl.poni.lblTextArea.getForeground().getRed(),
				MasterControl.poni.lblTextArea.getForeground().getGreen(),
				MasterControl.poni.lblTextArea.getForeground().getBlue()
		});

		tst = g.toJson(MasterControl.config).getBytes();

		try {
			FileOutputStream send = new FileOutputStream("./ecfg.json");
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
