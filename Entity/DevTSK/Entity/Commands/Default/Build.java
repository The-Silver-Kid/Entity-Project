package DevTSK.Entity.Commands.Default;

import java.util.ArrayList;
import DevTSK.Entity.Entity;
import DevTSK.Entity.MasterControl;
import DevTSK.Entity.Commands.Command;
import DevTSK.Util.LoggerPro;

public class Build implements Command {

	public Build() {
	}

	@Override
	public boolean isHidden() {
		return false;
	}

	@Override
	public String[] getTokens() {
		return new String[] { "Build" };
	}

	@Override
	public String getSyntax() {
		return "Build [Starting Entity]";
	}

	@Override
	public String getHelp() {
		return "Builds a Family tree (WIP)";
	}

	@Override
	public int run(Entity[] list, LoggerPro logbook, String[] sl) {
		ArrayList<Unit> lst = new ArrayList<>();
		if (sl.length > 1) {
			String s = sl[1];
			for (int i = 0; i < list.length; i++)
				if (list[i].getAltName().equalsIgnoreCase(sl[1]))
					if (!list[i].getRawGender())
						lst.add(new Unit(list[i].getName(), list[i].getFather(), list[i].getMother()));
					else
						lst.add(new Unit(list[i].getName(), list[i].getFather(), list[i].getMother(), list[i].getPartners()));
		}
		for (int i = 0; i < list.length; i++) {
			Boolean isListed = false;
			for (int o = 0; o < lst.size(); o++) {
				if (list[i].getName().equals(lst.get(o).name))
					isListed = true;
				if (!isListed)
					for (int ii = 0; ii < lst.get(o).units.size(); ii++)
						if (list[i].getName().equals(lst.get(o).name))
							isListed = true;
			}
			if (!isListed) {
				if (!list[i].getRawGender())
					lst.add(new Unit(list[i].getName(), list[i].getFather(), list[i].getMother()));
				else
					lst.add(new Unit(list[i].getName(), list[i].getFather(), list[i].getMother(), list[i].getPartners()));
			}
		}
		for (int i = 0; i < lst.size(); i++) {
			//Check gender
			if (lst.get(i).g)
				//if female check if married...
				if (lst.get(i).ptn.length > 0)
					//if true check for partner and add her to his units
					if (rcheck(0, lst, lst.get(i))) {

					}

		}
		MasterControl.poni.println(arrange(lst));
		logbook.log("Command " + sl[0] + " completed successfully.");
		return 0;
	}

	private int[] rcheck(int in, ArrayList<Unit> lst, Unit unit) {
		ArrayList<int> f = new ArrayList<>();
		for(int i = 0;i<lst.size();i++){
		
		}
		return ;
	}

	private String arrange(ArrayList<Unit> lst) {
		String s = "Tree\n";
		for (int i = 0; i < lst.size(); i++) {
			s = s + "+" + lst.get(i).name + "\n";
			for (int o = 0; o < lst.get(i).units.size(); o++)
				s = s + "+--" + lst.get(i).units.get(o).name + "\n";
		}

		return s;
	}

	private class Unit {
		public String name = "null", f, m;
		public String[] ptn = new String[] {};
		public Boolean g = false;
		public ArrayList<Unit> units = new ArrayList<>();

		public Unit(String n, String f, String m, String[] ptn) {
			name = n;
			this.f = f;
			this.m = m;
			g = true;
			this.ptn = ptn;
		}

		public Unit(String n, String f, String m) {
			name = n;
			this.f = f;
			this.m = m;
		}
	}
}
