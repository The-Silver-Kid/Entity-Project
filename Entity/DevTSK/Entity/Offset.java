package DevTSK.Entity;

import DevTSK.Util.Day;

public class Offset {

	private int offset = 0;

	public Offset(int offset) {
		this.offset = offset;
	}

	public Day getDay() {
		return new Day(offset);
	}
}
