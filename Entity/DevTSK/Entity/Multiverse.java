package DevTSK.Entity;

import java.util.ArrayList;

public class Multiverse {
	private String[] aList;

	public String[] getAreaList() {
		String[] aListt = new String[aList.length];
		for (int i = 0; i < aList.length; i++)
			aListt[i] = "/Area/" + aList[i] + ".json";
		return aListt;
	}

	public String[] getRawAreaList() {
		return aList;
	}

	public void addElement(String s) {
		ArrayList<String> e = new ArrayList<String>();
		String[] ss = new String[aList.length + 1];
		for (int i = 0; i < aList.length; i++)
			e.add(aList[i]);
		e.add(s);
		e.toArray(ss);
		aList = ss;
	}

	@Deprecated
	public void addList(String[] list) {
		aList = list;
	}
}
