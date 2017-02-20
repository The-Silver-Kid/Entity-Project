package DevTSK.Entity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import javax.swing.JFileChooser;
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

	public static void main(String[] args) {
		String title = "Entity Project";

		File f = findDir();

		p.log("Loading external classes");
		try {
			findClasses(f);
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
			}
		} else {
			p.log(2, "No packInfo.bin found. Issues may occur!");
		}

		p.log("Attempting to load files.");
		//TODO : Create Charset from files
		Entity[] OC = new Entity[] {};
		try {
			OC = deserializeJSONFiles(f);
		} catch (Exception e1) {
			p.log(2, "Something went wrong in loading entities! Cannot Continue");
			p.log(2, e1.getMessage());
			for (StackTraceElement s : e1.getStackTrace())
				p.log(2, s.toString());
			System.exit(1);
		}

		h = new EntityLoader(OC, new Day(), p, f);

		File ff = new File(f.getAbsolutePath() + "/Images/");
		if (!ff.exists())
			ff.mkdir();

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

	private static Entity[] deserializeJSONFiles(File f) throws Exception {
		if (!f.exists())
			f.mkdirs();
		if (f.exists() && f.isDirectory()) {

			String[] dirs = f.list(new FilenameFilter() {
				public boolean accept(File current, String name) {
					return (new File(current, name).isDirectory() && !new File(current, name).isHidden());
				}
			});

			recurse(dirs, 3, f);

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

	private static void recurse(String[] strs, int i, File f) throws FileNotFoundException {
		if (i < 0)
			return;
		for (String s : strs) {
			File g = new File(f.getAbsolutePath() + "/" + s);
			Entity[] es = findEntity(g);
			for (Entity e : es)
				list.add(e);
			String[] dirs = g.list(new FilenameFilter() {
				public boolean accept(File current, String name) {
					return (new File(current, name).isDirectory() && !new File(current, name).isHidden());
				}
			});
			recurse(dirs, i - 1, g);
		}
	}

	private static Entity[] findEntity(File f) throws FileNotFoundException {
		GsonBuilder bldr = new GsonBuilder();
		bldr.registerTypeAdapter(Entity.class, new EntityAdapter());
		Gson g = bldr.create();

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

	private static void findClasses(File f) {
		File dir = new File(f.getAbsolutePath() + "/classes/");
		if (!dir.exists())
			dir.mkdirs();
		if (dir.exists() && dir.isDirectory()) {
			FileFilter ff = new FileFilter(".jar");
			File[] classes = dir.listFiles(ff);

			for (int i = 0; i < classes.length; i++) {
				try {
					p.log("Attempting to load '" + classes[i].getName() + "'.");
					addSoftwareLibrary(classes[i]);
				} catch (Exception e) {
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
		JFileChooser choose = new JFileChooser();
		choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		choose.showDialog(null, "Open Directory");

		return choose.getSelectedFile();
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
