package DevTSK.Entity;

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
