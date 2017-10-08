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
		//Builds empty list
		ArrayList<Unit> lst = new ArrayList<>();
		if (sl.length > 1) {
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

		//TODO why is this cutting entry 0?
		//sorts kids
		try {
			for (int i = 0; i < lst.size(); i++) {
				Boolean doit = true;
				//check for partner first
				if (lst.get(i).g)
					if (!(lst.get(i).ptn == null)) {

						ArrayList<Integer> res = rcheck(lst, lst.get(i));
						if (res.size() > 0)
							doit = false;
					}

				//check for mother and add to her units
				if (doit) {
					ArrayList<Integer> result = rpcheck(lst, lst.get(i));
					if (result.size() > 0) {
						lst = radd(result, lst, lst.get(i));

						//System.err.println("WILL BE REMOVING " + lst.get(i).name.toUpperCase());
						lst.remove(i);
						i--;

					}
				}
			}

		} catch (

		Exception e) {
			logbook.log(2, "Command " + sl[0] + " failed to complete successfully.");
			logbook.log(2, e.getMessage());
			logbook.log(2, e.toString());
			for (StackTraceElement y : e.getStackTrace())
				logbook.log(2, y.toString());
			MasterControl.poni.printCl();
			MasterControl.poni.println("OH NOES!");
			return 1;
		}

		//	MasterControl.poni.printCl();
		//	MasterControl.poni.println(arrange(lst));

		//sorts partners
		try {
			for (int i = 0; i < lst.size(); i++) {
				System.err.println(lst.get(0).name.toLowerCase() + " : " + lst.get(i).name.toLowerCase() + " : " + i);
				//Check gender
				logbook.log("Checking Gender of " + lst.get(i).name);
				if (lst.get(i).g) {
					logbook.log(lst.get(i).name + " was female proceeding with sort.");
					//if female check if married...
					if (!(lst.get(i).ptn == null))
						logbook.log(lst.get(i).name + " has a total of " + lst.get(i).ptn.length + " partner(s).");
					else
						logbook.log(lst.get(i).name + " has a total of 0 partner(s).");
					if (!(lst.get(i).ptn == null)) {
						//if true check for partner and add her to his units
						ArrayList<Integer> result = rcheck(lst, lst.get(i));
						if (result.size() != 0) {
							lst = radd(result, lst, lst.get(i));
							//System.err.println("REMOVING " + lst.get(i).name.toUpperCase() + ":" + i);
							lst.remove(i);
							i--;

						}
					}
				}

			}
			MasterControl.poni.printCl();
			MasterControl.poni.println(arrange(lst));
			logbook.log("Command " + sl[0] + " completed successfully.");
			return 0;
		} catch (Exception e) {
			logbook.log(2, "Command " + sl[0] + " failed to complete successfully.");
			logbook.log(2, e.getMessage());
			logbook.log(2, e.toString());
			for (StackTraceElement y : e.getStackTrace())
				logbook.log(2, y.toString());
			MasterControl.poni.printCl();
			MasterControl.poni.println("OH NOES!");
			return 1;
		}
	}

	@SuppressWarnings("unchecked")
	private ArrayList<Unit> radd(ArrayList<Integer> result, ArrayList<Unit> lst, Unit unit) throws Exception {
		ArrayList<Unit> onions = (ArrayList<Unit>) lst.clone();
		ArrayList<Integer> ggm8 = (ArrayList<Integer>) result.clone();
		ggm8.remove(0);
		if (ggm8.size() > 0)
			onions = (ArrayList<Unit>) raddh(result, unit, lst).clone();
		else {
			onions.get(result.get(0)).units.add(unit);
			//onions.set(result.get(0), onions.get(result.get(0)));
		}
		return onions;
	}

	@SuppressWarnings("unchecked")
	private ArrayList<Unit> raddh(ArrayList<Integer> result, Unit unit, ArrayList<Unit> lst) throws Exception {
		ArrayList<Unit> lst2 = (ArrayList<Unit>) lst.clone();
		ArrayList<Integer> res = (ArrayList<Integer>) result.clone();
		if (result.size() > 1) {
			res.remove(0);
			lst2.get(result.get(0)).units = (ArrayList<Unit>) raddh(res, unit, lst2.get(result.get(0)).units).clone();
		} else if (result.size() == 1) {
			lst2.get(result.get(0)).units.add(unit);
			return lst2;
		} else {
			throw new Exception("Somehow the recursive function zeroed out.");
		}
		return lst2;
	}

	private ArrayList<Integer> rcheck(ArrayList<Unit> lst, Unit unit) {
		ArrayList<Integer> f = new ArrayList<>();
		for (int i = 0; i < lst.size(); i++) {
			if (lst.get(i).name.equalsIgnoreCase(unit.ptn[0]))
				f.add(i);
			if (lst.get(i).units.size() > 0) {
				ArrayList<Integer> x = rcheck(lst.get(i).units, unit);
				if (x.size() > 0) {
					f.add(i);
					f.addAll(x);
				}
			}
		}
		return f;
	}

	private ArrayList<Integer> rpcheck(ArrayList<Unit> lst, Unit unit) {
		ArrayList<Integer> f = new ArrayList<>();
		for (int i = 0; i < lst.size(); i++) {
			if (lst.get(i).name.equalsIgnoreCase(unit.m))
				f.add(i);
			if (lst.get(i).units.size() > 0) {
				ArrayList<Integer> x = rpcheck(lst.get(i).units, unit);
				if (x.size() > 0) {
					f.add(i);
					f.addAll(x);
				}
			}
		}
		return f;
	}

	private String arrange(ArrayList<Unit> lst) {
		String s = "Tree" + rarr("┣┳", lst, 1);

		return s;
	}

	private String rarr(String code, ArrayList<Unit> lst, int n) {
		String ret = "";
		for (int i = 0; i < lst.size(); i++) {
			//System.err.println(lst.get(i).name.toUpperCase());
			ret = ret + "\n" + code + lst.get(i).name;
			if (lst.get(i).units.size() > 0) {
				String space = "";
				for (int ii = 0; ii < code.length(); ii++)
					if (ii == n) {
						space += "┗┳";
						ii++;
					} else if (ii == 0)
						space += "┃";
					else
						space += "┆";
				ret += rarr(space, lst.get(i).units, n + 1);
			}
		}
		return ret;
	}

	private class Unit {
		@SuppressWarnings("unused")
		public String name = "null", f, m;
		public String[] ptn = new String[0];
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
