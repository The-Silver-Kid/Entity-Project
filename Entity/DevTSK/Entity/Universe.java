package DevTSK.Entity;

import java.util.ArrayList;

public class Universe {
	private String name, title;
	private String[] eList;
	private Offset offset;

	public String getTitle() {
		return title;
	}

	public String getName() {
		return name;
	}

	public void addElement(String s) {
		ArrayList<String> e = new ArrayList<String>();
		String[] ss = new String[eList.length + 1];
		for (int i = 0; i < eList.length; i++)
			e.add(eList[i]);
		e.add(s);
		e.toArray(ss);
		eList = ss;
	}

	public String[] getEntityList() {
		String[] eListt = new String[eList.length];
		for (int i = 0; i < eList.length; i++)
			eListt[i] = "/Entity/" + eList[i] + ".json";
		return eListt;
	}

	public String[] getRawEntityList() {
		return eList;
	}

	public Offset getOffset() {
		return offset;
	}
}
