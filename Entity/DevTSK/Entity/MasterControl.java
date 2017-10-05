package DevTSK.Entity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import DevTSK.Entity.Commands.Command;
import DevTSK.Entity.Commands.Default.BGColor;
import DevTSK.Entity.Commands.Default.Build;
import DevTSK.Entity.Commands.Default.Charset;
import DevTSK.Entity.Commands.Default.ConfigurationSave;
import DevTSK.Entity.Commands.Default.Dir;
import DevTSK.Entity.Commands.Default.Dump;
import DevTSK.Entity.Commands.Default.Exit;
import DevTSK.Entity.Commands.Default.Help;
import DevTSK.Entity.Commands.Default.InColor;
import DevTSK.Entity.Commands.Default.InTxtColor;
import DevTSK.Entity.Commands.Default.Info;
import DevTSK.Entity.Commands.Default.LastRun;
import DevTSK.Entity.Commands.Default.ListAll;
import DevTSK.Entity.Commands.Default.OtColor;
import DevTSK.Entity.Commands.Default.OtTxtColor;
import DevTSK.Entity.Commands.Default.Today;
import DevTSK.Entity.Commands.Default.WIP;
import DevTSK.Util.Day;
import DevTSK.Util.LoggerPro;
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

	private static ArrayList<Entity> list = new ArrayList<Entity>();

	private static LoggerPro p = new LoggerPro(new String[] { "-", "#", "X" }, LoggerPro.FILE_AND_CONSOLE);

	private static Offset off = new Offset(0);

	private static Command[] cmands;

	private static JFrame empty;

	public static void main(String[] args) {
		ArrayList<Command> coms = new ArrayList<>(), plugs = new ArrayList<>();

		String title = "Entity Project";

		File f = findDir();
		empty.dispose();
		empty.setVisible(false);

		p.log("Loading external Entity classes");
		try {
			findClasses(f, "/Classes/", true);
		} catch (NullPointerException e) {
			p.log("User cancelled Entity Pack choosing... System will exit.");
			System.exit(0);
		}
		p.log("Loading of external classes finished.");

		p.log("Looking for packInfo.bin");
		File packinfo = new File(f.getAbsolutePath() + "/packInfo.bin");
		if (packinfo.exists() && !packinfo.isDirectory()) {
			p.log("Pack information found! Attempting to load.");
			try {
				FileInputStream in = new FileInputStream(f.getAbsolutePath() + "/packInfo.bin");
				byte[] result = new byte[in.available()];
				in.read(result);
				in.close();
				char[] ch = new char[result.length];
				for (int i = 0; i < ch.length; i++)
					ch[i] = (char) result[i];
				title = new StringBuilder().append(ch).toString();
			} catch (Exception e) {
				p.log(2, "Something went wrong whilst trying to read packInfo.bin");
				p.log(2, e.getMessage());
				for (StackTraceElement s : e.getStackTrace())
					p.log(2, s.toString());
			}
		} else {
			p.log(2, "No packInfo.bin found. This may be bad!");
		}

		p.log("Attempting to load files.");

		GsonBuilder bldr = new GsonBuilder();
		bldr.registerTypeAdapter(Entity.class, new EntityAdapter());
		Gson g = bldr.create();

		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(f.getAbsolutePath() + "/Offset.json")));
			off = g.fromJson(br, Offset.class);
			compDay = off.getDay();
		} catch (FileNotFoundException e) {
			p.log(2, "Something went wrong trying to load the offset date. Continuing without.");
			p.log(2, e.getMessage());
			for (StackTraceElement s : e.getStackTrace())
				p.log(2, s.toString());
		}

		Entity[] OC = new Entity[] {};
		try {
			OC = deserializeJSONFiles(f, g);
		} catch (Exception e1) {
			p.log(2, "Something went wrong in loading entities! Cannot Continue");
			p.log(2, e1.getMessage());
			for (StackTraceElement s : e1.getStackTrace())
				p.log(2, s.toString());
			System.exit(1);
		}

		//Adding default commands
		coms.add(new Help());
		coms.add(new BGColor());
		coms.add(new InColor());
		coms.add(new InTxtColor());
		coms.add(new OtColor());
		coms.add(new OtTxtColor());
		coms.add(new Charset());
		coms.add(new ConfigurationSave());
		coms.add(new Dir());
		coms.add(new Dump());
		coms.add(new Info());
		coms.add(new LastRun());
		coms.add(new ListAll());
		coms.add(new WIP());
		coms.add(new Exit());
		coms.add(new Build());
		coms.add(new Today());

		p.log("Attempting to load pluginable commands");
		try {
			plugs = plugins(f);
		} catch (Exception e) {
			p.log(2, "Something went wrong loading one or more plugins.");
			p.log(2, "Certain plugins may be unavailable...");
			p.log(2, e.getMessage());
			for (StackTraceElement s : e.getStackTrace())
				p.log(2, s.toString());
		}
		coms.addAll(plugs);
		p.log("Plugin-able command injection finished.");

		cmands = new Command[coms.size()];
		coms.toArray(cmands);

		File ff = new File(f.getAbsolutePath() + "/Images/");
		if (!ff.exists())
			ff.mkdir();

		for (Entity check : OC) {
			File fff = new File(f.getAbsolutePath() + "/Images/" + check.getImagePath());
			if (!fff.exists() && !check.getImagePath().equalsIgnoreCase("null.png"))
				p.log(2, "Entity " + check.getAltName() + " has a specified image that doesnt exist!");
			fff = new File(f.getAbsolutePath() + "/Images/" + check.getAltImagePath());
			if (!fff.exists() && !check.getAltImagePath().equalsIgnoreCase("null.png"))
				p.log(2, "Entity " + check.getAltName() + " has a specified alternate image that doesnt exist!");

		}

		h = new EntityLoader(OC, compDay, p, f, cmands);

		try {
			poni = new Window(title, 1, 0, 0, 0, h, p, f);
			poni.punch();
		} catch (ConfigException | IOException | NullPointerException e) {
			p.log(2, "Window Creation Failed. Cannot Continue!");
			p.log(2, e.getMessage());
			for (StackTraceElement s : e.getStackTrace())
				p.log(2, s.toString());
			System.exit(2);
		}
	}

	private static void addSoftwareLibrary(File file) throws Exception {
		Method method = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
		method.setAccessible(true);
		method.invoke(ClassLoader.getSystemClassLoader(), new Object[] { file.toURI().toURL() });
	}

	private static Entity[] deserializeJSONFiles(File f, Gson g) throws Exception {
		if (!f.exists())
			f.mkdirs();
		if (f.exists() && f.isDirectory()) {

			String[] dirs = f.list(new FilenameFilter() {
				public boolean accept(File current, String name) {
					return (new File(current, name).isDirectory() && !new File(current, name).isHidden());
				}
			});

			recurse(dirs, 3, f, g);

			Entity[] ret = new Entity[list.size()];
			for (int i = 0; i < list.size(); i++)
				ret[i] = list.get(i);
			return ret;
		} else if (f.exists() && !f.isDirectory()) {
			throw new MalformedURLException(f.getName() + " is a file not a folder");
		} else {
			throw new Exception("Unspecified error in " + f.getAbsolutePath());
		}
	}

	private static void recurse(String[] strs, int i, File f, Gson gs) throws FileNotFoundException {
		if (i < 0)
			return;
		for (String s : strs) {
			File g = new File(f.getAbsolutePath() + "/" + s);
			Entity[] es = findEntity(g, gs);
			for (Entity e : es)
				list.add(e);
			String[] dirs = g.list(new FilenameFilter() {
				public boolean accept(File current, String name) {
					return (new File(current, name).isDirectory() && !new File(current, name).isHidden());
				}
			});
			recurse(dirs, i - 1, g, gs);
		}
	}

	private static Entity[] findEntity(File f, Gson g) throws FileNotFoundException {
		FileFilter ff = new FileFilter(".json");
		File[] jsonFiles = f.listFiles(ff);
		Entity[] ret = new Entity[] {};
		if (jsonFiles.length >= 1)
			ret = new Entity[jsonFiles.length];
		if (!(ret.length == 0))
			for (int i = 0; i < jsonFiles.length; i++) {
				BufferedReader br = new BufferedReader(new FileReader(jsonFiles[i]));
				ret[i] = g.fromJson(br, Entity.class);
			}
		else
			ret = new Entity[] {};
		return ret;
	}

	private static void findClasses(File f, String ext, Boolean announce) {
		File dir = new File(f.getAbsolutePath() + ext);
		if (!dir.exists())
			dir.mkdirs();
		if (dir.exists() && dir.isDirectory()) {
			FileFilter ff = new FileFilter(".jar");
			File[] classes = dir.listFiles(ff);

			for (int i = 0; i < classes.length; i++) {
				try {
					if (announce)
						p.log("Attempting to load '" + classes[i].getName() + "'.");
					addSoftwareLibrary(classes[i]);
				} catch (Exception e) {
					if (announce)
						p.log(2, "Error in loading class file '" + classes[i].getName() + "'.");
					p.log(2, e.getMessage());
				}
			}
		} else if (dir.exists() && !dir.isDirectory()) {
			p.log(2, dir.getName() + " is a file not a folder");
		} else {
			p.log(2, "Unspecified error");
		}
	}

	private static File findDir() {
		empty = new JFrame();
		empty.setVisible(true);
		JFileChooser choose = new JFileChooser();
		choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		choose.showDialog(null, "Open Directory");
		empty.add(choose);

		return choose.getSelectedFile();
	}

	private static ArrayList<Command> plugins(File f) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		File master = new File(f.getAbsolutePath() + "/plugins");
		p.log("Looking in " + master.getAbsolutePath() + " for plugins...");
		ArrayList<Command> ret = new ArrayList<>();
		if (master.exists()) {

			FileFilter ff = new FileFilter(".jar");
			File[] fs = master.listFiles(ff);

			ArrayList<String> classNames = new ArrayList<String>();

			for (File fname : fs) {

				ZipInputStream zip = new ZipInputStream(new FileInputStream(fname));
				for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
					if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
						// This ZipEntry represents a class. Now, what class does it represent?
						String className = entry.getName().replace('/', '.'); // including ".class"
						classNames.add(className.substring(0, className.length() - ".class".length()));
					}
				}
				zip.close();
			}

			findClasses(f, "/plugins/", false);

			for (int i = 0; i < classNames.size(); i++) {
				Class<?> clazz = Class.forName(classNames.get(i));
				try {
					if (Command.class.isAssignableFrom(clazz)) {
						Constructor<?> ctor = clazz.getConstructor();
						Command obj = (Command) ctor.newInstance();
						ret.add(obj);
					}
				} catch (Exception e) {
					p.log(2, e.getMessage());
					for (StackTraceElement s : e.getStackTrace())
						p.log(2, s.toString());
				}

			}

			return ret;
		} else {
			p.log("Found 0 plugins in " + master.getAbsolutePath());
			return ret;
		}
	}
}

class FileFilter implements FilenameFilter {

	private String fileExtension;

	public FileFilter(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	@Override
	public boolean accept(File directory, String fileName) {
		return (fileName.endsWith(this.fileExtension));
	}
}

class EntityAdapter implements JsonSerializer<Entity>, JsonDeserializer<Entity> {
	@Override
	public JsonElement serialize(Entity src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject result = new JsonObject();
		result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
		result.add("properties", context.serialize(src, src.getClass()));

		return result;
	}

	@Override
	public Entity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();
		String type = jsonObject.get("type").getAsString();
		JsonElement element = jsonObject.get("properties");

		try {
			return context.deserialize(element, Class.forName("entity." + type));
		} catch (ClassNotFoundException cnfe) {
			throw new JsonParseException("Unknown element type: " + type, cnfe);
		}
	}
}
