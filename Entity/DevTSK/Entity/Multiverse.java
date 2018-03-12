package DevTSK.Entity;

import java.awt.Color;

public class Multiverse {
	private String setName, title;
	private Color background;
	private String[] aList;

	public Multiverse(int testVerses) {
		setName = "Private Test 1";
		background = new Color(0xFF000000);
		aList = new String[testVerses];
		int x = 10, y = 10;
		for (int i = 0; i < aList.length; i++) {
			aList[i] = new Universe(x, y, i).getName();
			x += 20;
			if (x > 690) {
				x = 10;
				y++;
			}
		}
	}

	public String getTitle() {
		return title;
	}

	public String getSetName() {
		return setName;
	}

	public Color getBackground() {
		return background;
	}

	public String[] getNames() {
		return aList;
	}

	public String[] getJsonNames() {
		String[] ret = new String[aList.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = "/Area/" + aList[i] + ".json";
		}
		return ret;
	}
}
