package DevTSK.Entity;

import java.io.IOException;
import javax.swing.JOptionPane;
import DevTSK.Entity.Classes.Carribou.UnMarriedCarribou;
import DevTSK.Entity.Classes.DragonPonii.UnMarriedDargonPonii;
import DevTSK.Entity.Classes.DragonPonii.UnMarriedPoniiDargon;
import DevTSK.Entity.Classes.FirePonii.UnMarriedFirePonii;
import DevTSK.Entity.Classes.MachinePonii.MarriedMachinePonii;
import DevTSK.Entity.Classes.MachinePonii.UnMarriedMachinePonii;
import DevTSK.Entity.Classes.PlanePonii.MarriedPlane;
import DevTSK.Entity.Classes.PlanePonii.MarriedPlaneWithOtherKids;
import DevTSK.Entity.Classes.PlanePonii.UnMarriedPlane;
import DevTSK.Entity.Classes.Ponii.MarriedPonii;
import DevTSK.Entity.Classes.Ponii.MarriedPoniiWithOtherKids;
import DevTSK.Entity.Classes.Ponii.UnMarriedPonii;
import DevTSK.Entity.Classes.Ponii.UnMarriedPoniiWithKids;
import DevTSK.Entity.Classes.Unknown.MarriedQuilava;
import DevTSK.Entity.Classes.Unknown.UnMarriedQuilava;
import DevTSK.Util.Day;
import DevTSK.Util.DAG.ConfigException;

/**
 * @author The_Silver_Kid
 * 
 *         Sets up the program
 */
public class MasterControl {

	private static EntityLoader h;
	public static String charsetname = "null";
	public static Window poni;
	public static Day compDay;

	/**
	 * Main Function
	 * 
	 * @param String[]
	 *            Constructor arguments
	 * @throws IOException
	 * @throws ConfigException
	 */
	public static void main(String[] args) throws IOException, ConfigException {
		if (args.length != 0)
			for (int i = 0; i < args.length; i++)
				System.out.println(args[i]);
		//Unmarried : Name, AltName, Flag, Gender, Description, ManeColour, TailColour, Mother, Father, Integer age, Integer day, Integer month, Integer year, ImageName, CutiimarkImage
		if (h != null) {
			poni.destroyWindows();
			h = null;
		}
		if (args.length < 1 || args[0].equalsIgnoreCase("actual") || args[0].equalsIgnoreCase("Default")) {
			charsetname = "RP";
			compDay = new Day(12);
			h = new EntityLoader(new Entity[] {
					//Try and keep families together.
					//Put a empty line to separate families.

					new MarriedPoniiWithOtherKids("Shadow Radon", "Radon", "", false, true, "\nUnicorn\nYellow Thunderbolt across underside\nYellow Thunderbolts around hooves.\nRules equestria with AJ", "\nBlack Thundercloud with a yellow thunderbolt coming from it", "Yellow and Black", "Yellow and Black", "Charrie", "Slanger", new Day(20, 7, 1993), "Radon.png", "kloud.png", new String[] { "Apple Jack", "Galecia Frostia" },
							new String[] { "Tree Lighting", "Shadow Jack", "Rhyne", "Aaron", "Nhyte", "Thorn", "Powder" },
							new String[] { "Apple Jack", "Apple Jack", "Apple Jack", "Apple Jack", "Apple Jack", "Apple Jack", "Galecia Frostia", "Galecia Frostia", "Galecia Frostia" })
									.addDNA(new DNA("3037000200000000FEFF0003001FFFF00TM183720202000141000000FFFF001B107F3FCC004CB2")),
					new UnMarriedPonii("Tree Lighting Radon", "Tree", "", true, true, "\nEarth\nCutest little filly ever sparkling with the same spark that her dad has\nShe lives with Blaze at SAA.", "\nApple tree with lightning bolt behind it", "Little more yellow then AJ's", "Slightly darker orange then AJ", "Apple Jack", "Shadow Radon", new Day(11, 9, 2011), "lightning.png", "null.png")
							.addDNA(new DNA("003H000000FDA00000000003B0188FF00UN1837002000000I1FAFE730000001B107F3FCC004CB2")),
					new UnMarriedPonii("Shadow Jack Radon", "Jack", "", false, false, "\nUnicorn\nSecond Ponii on the AJ-Radon Line.\nSpends most of his time with Rhyne.", "\nA cloud with snowflakes", "Pale White and yellow", "Pale White and yellow", "Apple Jack", "Shadow Radon", new Day(25, 12, 2015), "jack.png", "null.png"),
					new UnMarriedPonii("Rhyne Radon", "Rhyne", "", true, false, "\nUnicorn\nThird Ponii on the AJ-Radon Line.\nSpends most of his time with Jack.", "\nA cloud with Raindrops", "WIP", "WIP", "Apple Jack", "Shadow Radon", new Day(27, 10, 2016), "null.png", "null.png"),
					new UnMarriedPonii("Aaron Radon", "Aaron", "", false, false, "\nEarth\nFourth Ponii on the AJ-Radon Line.\n.WIP", "\nA gear cog", "WIP", "WIP", "Apple Jack", "Shadow Radon", new Day(20, 01, 2017), "null.png", "null.png"),
					new UnMarriedPonii("Nhyte Radon", "Nhyte", "", true, false, "\nPegasus\nFifth Ponii on the AJ-Radon Line.\n.WIP", "\nWIP", "WIP", "WIP", "Apple Jack", "Shadow Radon", new Day(16, 11, 2017), "null.png", "null.png"),
					new UnMarriedPonii("Thorn Radon", "Thorn", "", true, false, "\nUnicorn\nSixth Ponii on the AJ-Radon Line.\n.WIP", "\nWIP", "WIP", "WIP", "Apple Jack", "Shadow Radon", new Day(20, 01, 2018), "null.png", "null.png"),

					new MarriedPlane("Blitz Radon Shuttleknight", "Blitz", "", true, true, "\nStandard\nChanged to be a plane ponii by her brother Radon.", "\n\u2708", "Copper Colour", "Copper Colour", "Charrie", "Slanger", new Day(12, 12, 1997), "blitz.png", "null.png", "Steven Shuttleknight",
							new String[] { "Morgan" })
									.addDNA(new DNA("2S2S0000204F4F4F800000000019F4F4FUN1837000020001417F2F2F9F4F4F1B107F3FCC004CB2")),
					new MarriedPonii("Steven Shuttleknight", "Steven", "", false, false, "\nPegasus\nDelivers Pizza", "\nPizza Box", "WIP", "WIP", "Speedometere", "Plizzia", new Day(28, 2, 1998), "steven.png", "null.png", "Blitz Radon Shuttleknight",
							new String[] { "Morgan" }),
					new UnMarriedPonii("Morgan Shuttleknight", "Emi", "", true, false, "\nPegasus\nWIP", "\nWIP", "WIP", "WIP", "Blitz", "Steven", new Day(5, 3, 2017), "null.png", "null.png"),

					//TODO : Krystla Frostia
					//new UnMarriedPonii("Thorn Radon", "Thorn", "", true, false, "\nUnicorn\nSixth Ponii on the AJ-Radon Line.\n.WIP", "\nWIP", "WIP", "WIP", "Apple Jack", "Shadow Radon", new Day(20, 01, 2018), "null.png", "null.png"),

					new MarriedPonii("Galecia Frostia", "Galecia", "", true, false, "\nUnicorn\nIt's Complicated.\nLikes the cold", "\nIcicle", "Light blue", "Light blue", "Krystla", "Starliner", new Day(4, 2, 1993), "Galecia.png", "null.png", "Shadow Radon",
							new String[] { "Powder", "Icicle", "Glacier" })
									.addDNA(new DNA("2W2O0001000095B900CCCC030010080FFUN1837000000000N10080FF00C0FF0N107F3FCC004CB2")),
					new UnMarriedPonii("Powder Frostia", "Powder", "", true, false, "\nEarth\nAccedental Child of Radon and Galecia when he went to visit.", "\nCloud with snowflakes", "Light blue", "Light blue", "Galecia", "Shadow Radon", new Day(1, 1, 2016), "PowderBlue.png", "null.png"),
					new UnMarriedPonii("Icicle Frostia", "Icicle", "", true, false, "\nUnicorn\nSecond Ponii on the Galecia-Radon Line.\n.WIP", "\nWIP", "WIP", "WIP", "Galecia Frostia", "Shadow Radon", new Day(28, 10, 2016), "null.png", "null.png"),
					new UnMarriedPonii("Glacier Frostia", "Glacier", "", true, false, "\nUnicorn\nThird Ponii on the Galecia-Radon Line.\n.WIP", "\nWIP", "WIP", "WIP", "Galecia Frostia", "Shadow Radon", new Day(14, 8, 2017), "null.png", "null.png"),

					new UnMarriedPonii("Snowfall Frostia", "Snowy", "", true, false, "\nPegasus\nHalf sister of Galecia\nRecently came back from living with the griffons for a while", "\nUnknown", "Blue", "Blue", "Sierra", "Starliner", new Day(13, 6, 1994), "null.png", "null.png"),

					new MarriedQuilava("Lavé", "Lave", true, false, "\nNo Information Available", "Unknown", "Unknown", new Day(24, 8, 1991), "null.png", "null.png", "Unknown", new String[] { "Unknown", "Blaze" }),
					new UnMarriedFirePonii("Blaze Oxydation", "Blaze", "", true, false, false, "\nUnicorn\nCross Breed of a Ponii and Quilava\nWas originally a stallion but due to some events he is now a mare\nMane acts a little like fire.\nLives with Tree Radon at SAA.", "\nVolcanic storm type thing", "Deep Red", "Deep Red", "Lavé", "UNKNOWN", new Day(19, 11, 2001), "blaze.png", "null.png"),

					new MarriedMachinePonii("C418", "C4", "", false, "\nMachine ponii built by radon that Looks almost exactly like him...", "\nC418", "Yellow and Black", "Yellow and Black", "N/A", "N/A", new Day(3, 12, 2010), "C4.png", "null.png", "C523",
							new String[] { "D105", "D115" })
									.addDNA(new DNA("3037000200000000FEFF0003001FFFF00TM183720202000141000000FFFF001B107F3FCC004CB2")),
					new MarriedMachinePonii("C523", "C5", "", true, "\nAnother machine ponii built by Radon however she was built for C4 to have a friend", "\nC523", "Pale Yellow", "Pale Yellow", "N/A", "N/A", new Day(12, 3, 2012), "C5.png", "null.png", "C418",
							new String[] { "D105", "D113" }),
					new UnMarriedMachinePonii("D105", "Bit", "", false, "\nKid of C4 and C5\nUsually found playing with Emi", "\nD105", "Blue and red", "Blue and red", "C523", "C418", new Day(4, 6, 2015), "null.png", "null.png"),
					new UnMarriedMachinePonii("D113", "Byte", "", true, "\nSecond Kid of C4 and C5\nWIP", "\nWIP", "WIP", "WIP", "C5", "C4", new Day(2, 29, 2016), "null.png", "null.png"),

					//TODO : Slanger Radon
					//new UnMarriedPonii("Thorn Radon", "Thorn", "", true, false, "\nUnicorn\nSixth Ponii on the AJ-Radon Line.\n.WIP", "\nWIP", "WIP", "WIP", "Apple Jack", "Shadow Radon", new Day(20, 01, 2018), "null.png", "null.png"),
					new MarriedPonii("Charrie (Firefly) Sychace Radon", "Charrie", "", true, false, "\nPegasus\nOld but cares allot about every ponii", "\nBlack Outlined White Thunderbolt", "Pinkish brown", "Pinkish brown", "Keri", "Craider", new Day(13, 7, 1948), "charrie.png", "null.png", "Slanger",
							new String[] { "Shadow", "Shyne", "Steve", "Blitz" }),

					new UnMarriedPonii("Maven RedHeart", "Maven", "", false, false, "\nUnicorn\nKnows almost as much as his mother about taking care of poniis", "\nRed cross", "Red", "Red", "Nurse RedHeart", "Raygle", new Day(1, 1, 2010), "Maven.png", "null.png")
							.addDNA(new DNA("2S2S000100FFFFFEFFC49D000014080FFUN1837001200000N1800000FF00000L01800000FF0000")),

					new MarriedPlane("Belle Carbone Stratoliner", "Belle", "", true, false, "\nAir Bus Plone\n", "\nAn 'A' with Aeroplane in it", "n/a", "n/a", "unknown", "unknown", new Day(12, 8, 1994), "null.png", "null.png", "Boewing",
							new String[] { "Morgan" }),
					new MarriedPlane("Boewing Stratoliner", "Boewing", "", false, false, "\nF16 fighter jet plone\nUsually has a bandana around mouth when flying that has the shark face design thing on it", "Missile", "N/A", "N/A", "Unknown", "Unknown", new Day(13, 7, 1994), "null.png", "null.png", "Belle",
							new String[] { "Morgan" }),
					new UnMarriedPlane("Morgan Stratoliner", "Morgan", "✈", true, true, "\n...bff\nWIP", "\nMissile and a aeroplane", "N/A", "N/A", "Belle", "Boewing", new Day(), "null.png", "null.png"),

					new UnMarriedPonii("Cosmos Darkstar", "Cosmos", "", false, true, "\nZebra pegasus\nIs half demon\nLives in the old castle in Everfree", "\nPentagram", "Purple with gray stripes", "Gray", "Nightmare Moon", "King of Hell", new Day(21, 8, 1164), "null.png", "null.png")
							.addDNA(new DNA("3B240J41004E4E4E6B6B6B02000B49600UN1837205030001Q05F6366FF7FFF00107F3FCC004CB2")),

					new UnMarriedDargonPonii("Miiryanth", "Miir", "", false, "\nDragon ponii\nStill young but takes care of his younger brother Gorlanth.", "Fiery orange-red colour", "Very dark gray and scale covered like the body, then the fiery orange-red for the tuft at the tip", "Unknown", "Unknown", new Day(4, 8, 1996), "null.png"),
					new UnMarriedPoniiDargon("Gorlanth", "Lance", "", false, "\nPonii dragon\nExists", "Dark blue", "Dark blue", "\nHasn't gotten it yet...Triangle beaker thing", "Unknown", "Unknown", new Day(12, 7, 2010), "null.png", "null.png"),

					new UnMarriedCarribou("Hrodmar Thorhalson", "Hrodmar", "", false, false, "\nViking Carribou\nHe's a Viking, what more exists need?\nCame through a time rift while hunting Cosmos", "\nThree white swirls", "Red with white highlights", "Red with white highlights", "Nina Faralddottir", "Thoral Kiotvason", new Day(12, 1, 1992), "null.png", "null.png" /*"39230B4000705000FFC49D0010000BB96QN1D3A00800000191BA3000FFFFEE0001705000FFFFEE"*/ ),

					new UnMarriedPlane("Banshee Nighthawk", "Nighthawk", "", false, true, "\nMilitary plone\nHead of the NLRAF and the LAF", "Targeting reticle", "Black", "Black", "REDACTED", "REDACTED", new Day(12, 9, 1989), "null.png", "null.png"),

					/* You suddenly realize that no one can see 'Nova.png' as it is not the entity image, and is compiled into the JAR witch no one will ever open with anything other then Java.*/
					new UnMarriedPonii("Agent V42666J", "Octave", "", true, false, "\nRefer to Nova.png for outfit and weapon\nPegasus\nMember of group of elite assassins known as Ghosts\nOctave is pronounced with a hard A sound", "Targeting reticle", "Silver", "Silver", "REDACTED", "REDACTED", new Day(5, 1, 1995), "null.png", "null.png"),

					// ALT versions here
					new MarriedPonii("Shade Radon", "Shade", "", false, true, "\nUnicorn\nJust like Radon but without the yellow markings", "\nBlack Thundercloud with a yellow thunderbolt coming from it", "Yellow and Black", "Yellow and Black", "Karrie", "Slaider", new Day(20, 7, 1993), "null.png", "kloud.png", "Apple Jack (Earth 2)" /*for lack of better name*/,
							new String[] { "Lighting Apple", "Shade Jack", "Sno", "Xander", "Dai", "Roze", "Powder" })
									.addDNA(new DNA("2S2S000200000000FFFF0000000FFFF00UN1837204020000N1000000FFFF000L107F3FCC004CB2")),

			}, new Entity[] {
					new MarriedPonii("Apple Jack Radon", "AJ", "", true, false, "\nEarth\nIts Apple Jack... y'all know her", "\nThree red apples", "Pale Yellow", "Pale Yellow", "?", "?", new Day(3, 4, 1993), "AJ.png", "AJMark.png", "Shadow Radon",
							new String[] { "Tree Lighting", "Shadow Jack", "Rhyne", "Aaron", "Nhyte", "Thorn", "Powder" })
									.addDNA(new DNA("2S2S000000FEA100FEB19E0040110BF00UN1837000100000I0FAFE73FF7FFF0H107F3FCC004CB2")),

					new UnMarriedPonii("Nurse RedHeart", "RedHeart", "", true, false, "\nEarth\nShe's a nurse.", "\nRed cross with hearts", "Light pink", "Light pink", "?", "?", new Day(9, 8, 1982), "null.png", "null.png"),

					new UnMarriedPonii("Princess Cadence", "Cadence", "", true, false, "\nAlicorn\nPretty pink ponii princess", "\nCrystal Heart", "Pink as can be", "Yellow, purple, and pink", "Queen Galaxia", "Unknown", new Day(16, 6, -9474), "null.png", "null.png"),
			}, compDay);

		}
		/*
		 * use for your own set of poniis that aren't included or planed
		 * to be used in the RP thing
		 */
		if (args.length >= 1) {
			if (args[0].equalsIgnoreCase("Hill")) {
				charsetname = "Hill";
				compDay = new Day();
				h = new EntityLoader(new Entity[] {
						//oc
				}, new Entity[] {
						//non-oc
				}, compDay);
			}
			/* CODES
			 * Snowfall: 3K24006010CCCCCCFFFFFF022010092BBUN1837000200000800040FFFF7FFF0B107F3FCC004CB2
			 *
			 * Cosmos: 3B240J41004E4E4E6B6B6B02000B49600UN1837205030001Q05F6366FF7FFF00107F3FCC004CB2
			 * Cadence: 3547006110FFC7DAFFC49D00101740096UI1G3C002000000817F3FA2DF44A50Q107F3FCC004CB2
			 * Cosmos/cadence daughter who needs name (Korinux?): 112A08711118181864646400101513247MN1C35008300001U12F005C00377221107F3FCC004CB2
			 * cosmos son (winged unicorn not alicon; because there is so much difference...): 112A08711118181864646400101513247MN1C35008300001U12F005C00377221107F3FCC004CB2
			 * cosmos+pinkamena daughter1: 112A086090DDDD000070CD02101006600MN1C35008310001D112345645A9471A107F3FCC004CB2
			 * cosmos+pinkamena daughter2: 112A08711118181864646400101513247MN1C35008300001U12F005C00377221107F3FCC004CB2 
			 */
			if (args[0].equalsIgnoreCase("Reggii")) {
				charsetname = "Reggii";
				compDay = new Day();
				h = new EntityLoader(new Entity[] {
						new MarriedPoniiWithOtherKids("Shadow Radon", "Radon", "", false, true, "\nUnicorn\nYellow Thunderbolt across underside\nYellow Thunderbolts around hooves\nLight Industries Admin", "\nBlack Thundercloud with a yellow thunderbolt coming from it", "Yellow and Black", "Yellow and Black", "Charrie", "Slanger", new Day(20, 7, 1993), "Radon.png", "kloud.png", new String[] { "Apple Jack", "Galecia Frostia", "Night Glider" }, new String[] { "Tree Lightning", "Shadow Jack", "Rhyne", "Aaron", "Powder", "Thorn", "Nhyte", "?", "Blue Powder", "Icicle", "Morgan", "Drone", "Lineair", "Anna", "Blaze", "Copper", "Alloy", "Drop Forge", "Blast Flame", "Night Strider" }, new String[] { "Apple Jack", "Apple Jack", "Apple Jack", "Apple Jack", "Apple Jack", "Apple Jack", "Apple Jack", "Apple Jack", "Galecia Frostia", "Galecia Frostia", "Blitz Radon", "Blitz Radon", "Blitz Radon", "Lavé", "Lavé", "Lavé", "Lavé", "Lavé", "Lavé", "Night Glider" }),

						new MarriedPonii("Tree Lighting Radon", "Tree", "", true, false, "\nEarth\nCutest little filly ever sparkling with the same spark that her dad has.", "\nApple tree with lightning bolt behind it", "Little more yellow then AJ's", "Slightly darker orange then AJ", "Apple Jack", "Shadow Radon", new Day(11, 9, 2011), "lightning.png", "null.png", "Blaze Oxydation", new String[] { "Flame Oxydation" }),
						new UnMarriedPonii("Shadow Jack Radon", "Jack", "", false, true, "\nUnicorn\nSecond Ponii on the AJ-Radon Line.\nJerk\nMostly spends time with Rhyne", "\nA cloud with snowflakes", "Pale White and yellow", "Pale White and yellow", "Apple Jack", "Shadow Radon", new Day(25, 12, 2012), "jack.png", "null.png"),
						new UnMarriedPonii("Rhyne Radon", "Rhyne", "", true, true, "\nUnicorn\nThird Ponii on the AJ-Radon Line.\nKeeps Jack in line (usually)\nMostly spends time with Jack", "\nA cloud with raindrops", "WIP", "WIP", "Apple Jack", "Shadow Radon", new Day(5, 6, 2013), "null.png", "null.png"),

						new MarriedPonii("Galecia Frostia", "Galecia", "", true, false, "\nUnicorn\nWas Radon's Marefriend for a while.... er, well still his marefriend.\nLikes the cold", "\nIcicle", "Light blue", "Light blue", "Krystla", "Starliner", new Day(20, 7, 1993), "Galecia.png", "null.png", "Shadow Radon", new String[] { "Powder" }),
						new UnMarriedPonii("Powder Frostia", "Powder", "", true, false, "\nEarth\nA Pony that looks and acts exactly like Powder from the Radon-AJ group except she is blue.", "\nCloud with snowflakes", "Light blue", "Light blue", "Galecia", "Shadow Radon", new Day(25, 12, 2013), "PowderBlue.png", "null.png"),

						new UnMarriedQuilava("Lavé", "Lave", true, false, "\nRadon's Pet (more or less)", "Reggii", "Radon", new Day(24, 8, 1991), "null.png", "null.png"),

						new MarriedPlaneWithOtherKids("Blitz Radon", "Blitz", "", true, true, "\nStandard\nWas genetically modified by her brother, Shadow Radon, to be a plane", "\n\u2708", "Copper Colour", "Copper Colour", "Charrie", "Slanger", new Day(12, 12, 1997), "blitz.png", "null.png", "Steve ShuttleKnight", new String[] { "Morgan", "Drone", "Lineair", "Lieutenant", "Captain" }, new String[] { "Shadow Radon", "Shadow Radon", "Shadow Radon", "Steve", "Steve" }),
						new MarriedPonii("Steven Shuttleknight", "Steven", "", false, false, "\nPegasus\nDelivers Pizza and occasionally works the night shift", "\nPizza Box", "", "", "Speedometere", "Plizzia", new Day(28, 2, 1998), "steven.png", "null.png", "Blitz Radon", new String[] { "Lieutenant", "Captain" }),

						//new UnMarriedFirePonii("Blaze Oxydation", "Blaze", "", false, false, false, "\nUnicorn\nCross Breed of a Ponii and Quilava\nMane acts a little like fire.\nUsually not seen as his dad prefers to keep him hidden\nHe likes the Tree Radon.", "\nDosent have it yet...\nVolcanic storm type thing", "Deep Red", "Deep Red", "Lavï¿½", "?", 4, 19, 11, 2001, "blaze.png", "null.png"),
						//new UnMarriedMachinePonii("C418", "C4", "", false, "\nMachine ponii built by radon that Looks almost exactly like him...", "\nC418", "Yellow and Black", "Yellow and Black", "N/A", "N/A", 5, 3, 12, 2010, "C4.png", "null.png"),
						//new UnMarriedMachinePonii("C523", "C5", "", true, "\nAnother machine ponii built by radon however she was built for C4 to have a friend", "\nC523", "Pale Yellow", "Pale Yellow", "N/A", "N/A", 3, 12, 3, 2012, "C5.png", "null.png"),
						//new MarriedPonii("Charrie (Firefly) Sychace Radon", "Charrie", "", true, false, "\nPegasus\nOld but cares allot about everyponii", "\nBlack Outlined White Thunderbolt", "Pinkish brown", "Pinkish brown", "Keri", "Craider", 67, 13, 7, 1948, "charrie.png", "null.png", "Slanger", 4, new String[] { "Shadow", "Shyne", "Steve", "Blitz" }),
						//new UnMarriedPoniiWithKids("Cosmos Darkstar", "Cosmos", "\u26e7", false, true, "\nZebra pegasus\nIs half demon\nLives in the old castle in Everfree", "\nPentagram", "Purple with gray stripes", "Gray", "Nighmare Moon", "King of Hell", 851, 21, 8, 1164, "null.png", "null.png", 1, new String[] { "" }, new String[] { "Cadence" }),
				}, new Entity[] {
						new MarriedPonii("Apple Jack Radon", "AJ", "", true, false, "\nEarth\nIts Apple Jack... y'all know her", "\nThree red apples", "Pale Yellow", "Pale Yellow", "?", "?", new Day(3, 4, 1993), "AJ.png", "AJMark.png", "Shadow Radon", new String[] { "Tree Lightning", "Shadow Jack", "Rhyne", "Aaron", "Powder", "Thorn", "Nhyte", "?", }),
						new UnMarriedPoniiWithKids("Nurse RedHeart", "RedHeart", "", true, false, "\nEarth\nShe's a nurse.", "\nRed cross with hearts", "Light pink", "Light pink", "?", "?", new Day(9, 8, 1982), "null.png", "null.png", new String[] { "Maven" }, new String[] { "Unknown" }),
				}, compDay);
			}
			if (args[0].equalsIgnoreCase("Equestria3")) {
				charsetname = "Equestria3";
				compDay = new Day();
				h = new EntityLoader(new Entity[] {
						new UnMarriedPoniiWithKids("Shadow Radium", "Radium", "", false, false, "\nUnicorn\nWIP", "", "Yellow and Black", "Yellow and Black", "Carrie", "Captain Slayder", new Day(20, 7, 1993), "Radon.png", "null.png", new String[] { "Lightning Bolt", "Shadow Jack", "Ray", "Scorcher" }, new String[] { "Apple Jack", "Apple Jack", "Apple Jack", "Volcani" }),

						new UnMarriedPonii("Lighting Bolt Radium", "Lightning", "", true, false, "\nEarth\nWIP", "\nWIP", "Little more yellow then AJ's", "Slightly darker orange then AJ", "Apple Jack", "Shadow Radium", new Day(11, 9, 2011), "lightning.png", "null.png"),
						new UnMarriedPonii("Shadow Jack Radium", "Jack", "", false, false, "\nUnicorn\nSecond Ponii on the AJ-Radium Line.\nWIP", "\nWIP", "Pale White and yellow", "Pale White and yellow", "Apple Jack", "Shadow Radon", new Day(25, 12, 2012), "jack.png", "null.png"),
						new UnMarriedPonii("Ray Radium", "Ray", "", true, false, "\nUnicorn\nThird Ponii on the AJ-Radium Line.\nWIP", "\nWIP", "WIP", "WIP", "Apple Jack", "Shadow Radium", new Day(25, 12, 2012), "null.png", "null.png"),

						new UnMarriedQuilava("Valcani", "Valcani", true, false, "\nWIP", "Reggii", "Radon", new Day(24, 8, 1991), "null.png", "null.png"),

						new MarriedPlane("Jet Radium", "Jet", "", true, false, "\nStandard\nWas mechanically modified by her brother, Shadow Radium, to be a plane and possess certain powers", "\n\u2708", "Copper Colour", "Copper Colour", "Carrie", "Captain Slayder", new Day(12, 12, 1997), "blitz.png", "null.png", "lieutenant ShuttleKnight", new String[] {}),
						new MarriedPonii("Lieutenant Shuttleknight", "Steven", "", false, false, "\nPegasus\nLieutenant for the Royal Guard", "\nWIP", "", "", "Speedometere", "Altaspecter", new Day(28, 2, 1998), "steven.png", "null.png", "Jet Radium", new String[] {}),
				}, new Entity[] {

				}, compDay);
			}
		}
		if (h == null) {
			JOptionPane.showMessageDialog(null, "OH NOES!\nThe charset '" + args[0] + "' has not been setup yet!\n\nSystem will now exit.");
			System.exit(1);
		}
		System.out.println("Setting date to : " + compDay.toString());
		poni = new Window("Ponii Program 4.0", 1, 0, 0, 0, h);
		poni.punch();
		//poni.setupConfig();
		//poni.punch();
	}
}
