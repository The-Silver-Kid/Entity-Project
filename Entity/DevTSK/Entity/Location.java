package DevTSK.Entity;

import DevTSK.Util.Day;

public class Location {

	private int offset = 0, xC, yC;

	public Location(int offset, int x, int y) {
		this.offset = offset;
		xC = x;
		yC = y;
	}

	public Day getDay() {
		return new Day(offset);
	}

	public String toString() {
		return "[" + xC + ", " + yC + "]";
	}

	public int[] getCoords() {
		return new int[] { xC, yC };
	}
}
