package DevTSK.Entity;

import DevTSK.Util.Day;

public abstract class Entity {
	protected Boolean Gender = false, /*false = Male, true = Female*/ otherBoolean = false, yaBoolean = false;
	protected int state = 0, kidammount = 0;
	protected String name = "Empty", altName = "Empty", description = "Empty", mother = "Empty", father = "Empty", flag = "Empty", additionalInfo = "Empty", imagepath = "null.png", altimagepath = "null.png", colour = "Empty", anotherColour = "Empty";
	protected DNA dna = new DNA();
	protected String[] kids = new String[0], with = new String[0], marriedTo = new String[0];
	protected Day birthday;

	public abstract String toString(Day offset);

	public String getGender() {
		if (Gender) {
			return "Female";
		}
		return "Male";
	}

	public Entity addDNA(DNA dna) {
		this.dna = dna;
		return this;
	}

	public String getFather() {
		return father;
	}

	public String getMother() {
		return mother;
	}

	public Boolean getRawGender() {
		return Gender;
	}

	public String[] getPartners() {
		return marriedTo;
	}

	public abstract String getInfo();

	public abstract String getName();

	public abstract String getAltName();

	public abstract String getImagePath();

	public abstract String getAltImagePath();

	public String getDNA() {
		return dna.getDNA();
	}
}
