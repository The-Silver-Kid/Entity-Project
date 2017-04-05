package DevTSK.Entity.Commands.Default;

import DevTSK.Entity.Entity;
import DevTSK.Entity.MasterControl;
import DevTSK.Entity.Commands.Command;
import DevTSK.Util.LoggerPro;

public class ListAll implements Command {

	public ListAll() {
	}

	@Override
	public String[] getTokens() {
		return new String[] { "ListAll", "ListAllDNA" };
	}

	@Override
	public String getSyntax() {
		return "ListAll";
	}

	@Override
	public String getHelp() {
		return "Returns a list of all registered Entities";
	}

	@Override
	public int run(Entity[] list, LoggerPro logbook, String[] sl) {
		MasterControl.poni.printCl();
		if (sl[0].equalsIgnoreCase("listall")) {
			MasterControl.poni.println("Acceptable OC/NonOC Entities Names: " + list.length + "\n");
			for (int i = 0; i < list.length; i++) {
				MasterControl.poni.println(list[i].getName() + "\tAKA\t" + list[i].getAltName());
			}
		} else if (sl[0].equalsIgnoreCase("listalldna")) {
			MasterControl.poni.println("OC/NonOC Entities with DNA: \n");
			for (int i = 0; i < list.length; i++) {
				try {
					if (list[i].getDNA() != null)
						MasterControl.poni.println(list[i].getName() + "\tAKA\t" + list[i].getAltName());
				} catch (Exception e) {
				}
			}

		}
		logbook.log("Command " + sl[0] + " completed successfully.");
		return 0;
	}

	@Override
	public boolean isHidden() {
		return false;
	}

}
