package DevTSK.Entity;

import java.awt.Color;

public class Kolor {

	int red, green, blue;

	public Kolor(int r, int g, int b) {
		red = r;
		green = g;
		blue = b;
	}

	public Color getColor() {
		return new Color(red, green, blue);
	}
}
