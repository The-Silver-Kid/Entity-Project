package DevTSK.Entity;

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

	@Deprecated
	public void addList(String[] list) {
		aList = list;
	}
}
