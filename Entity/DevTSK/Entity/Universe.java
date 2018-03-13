package DevTSK.Entity;

import java.awt.Color;
import java.util.ArrayList;

public class Universe {
	private String name, title;
	private String[] eList;
	private int x, y;
	private Kolor colour;
	private Location offset;

	public Universe(int x, int y, int N) {
		name = "test" + N;
		title = "TestRun : " + N;
		eList = new String[] {};
		this.x = x;
		this.y = y;
		colour = new Kolor(0xFF, 0xFF, 0xFF);
		offset = new Location(0, x, y);
	}

	public int[] getCoords() {
		return new int[] { x, y };
	}

	public Color getColor() {
		return colour.getColor();
	}

	public String toString() {
		return name + offset.toString();
	}

	public String getName() {
		return title;
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

	public Location getOffset() {
		return offset;
	}
}
