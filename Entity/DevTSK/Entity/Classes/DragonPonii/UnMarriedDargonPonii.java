/**
 * @author CosmosDarkstar
 */

package DevTSK.Entity.Classes.DragonPonii;

import DevTSK.Util.Day;

public class UnMarriedDargonPonii extends DargonPonii {

	public UnMarriedDargonPonii(String Name, String AlternateName, String Flag, Boolean Gender, String Description, String ManeColour, String TailColour, String Mother, String Father, Day bday, String Imagename) {
		this.name = Name;
		this.Gender = Gender;
		this.description = "\nDargon" + Description;
		this.colour = ManeColour;
		this.anotherColour = TailColour;
		this.mother = Mother;
		this.father = Father;
		this.birthday = bday;

		this.imagepath = Imagename;
		this.altName = AlternateName;

		this.flag = Flag;
	}

	@Override
	public String toString(Day offset) {
		String vpni = "";
		if (otherBoolean)
			vpni = "\nIs a Vamponii";
		String s = "Name : " + name + " AKA " + altName +
				"\nAge : " + offset.yearsFrom(birthday) +
				"\n\n" + super.getGender() +
				"\n\n" + description + vpni +
				"\nMane : " + colour + "\nTail : " + anotherColour +
				"\nBirthday : " + birthday.getMonth() + " " + birthday.getDay() + " " + birthday.getYear() +
				"\n\nCutii Mark : " + additionalInfo +
				"\n\nMother : " + mother + "\nFather : " + father +
				"\nFlag : '" + flag + "'" +
				"\n::End Of Ponii::";
		return s;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getAltName() {
		return altName;
	}

	@Override
	public String getImagePath() {
		return imagepath;
	}

	@Override
	public String getAltImagePath() {
		return altimagepath;
	}
}
