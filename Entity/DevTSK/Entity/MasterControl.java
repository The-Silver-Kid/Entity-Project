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

	private static LoggerPro p = new LoggerPro(new String[] { "-", "#", "X" }, LoggerPro.FILE_AND_CONSOLE);

	public static void main(String[] args) {
		String title = "Entity Project";

		File f = findDir();

		Entity[] elist;

		p.log("Loading external classes");
		findClasses(f);
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

		p.log(2, "System failure. the system has not yet been coded.");

		try {
			poni = new Window(title, 1, 0, 0, 0, h, p);
			poni.punch();
		} catch (ConfigException | IOException | NullPointerException e) {
			p.log(2, "Window Creation Failed. Cannot Continue!");
			p.log(2, e.getMessage());
			for (StackTraceElement s : e.getStackTrace())
				p.log(2, s.toString());
			System.exit(1);
		}
	}

	private static void addSoftwareLibrary(File file) throws Exception {
		Method method = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
		method.setAccessible(true);
		method.invoke(ClassLoader.getSystemClassLoader(), new Object[] { file.toURI().toURL() });
	}

	private static Entity[] deserializeJSONFiles(File f, Class<Entity> c) throws Exception {
		if (f.exists() && f.isDirectory()) {
			GsonBuilder bldr = new GsonBuilder();
			bldr.registerTypeAdapter(Entity.class, new EntityAdapter());
			Gson g = bldr.create();
			FileFilter ff = new FileFilter(".json");
			File[] jsonFiles = f.listFiles(ff);
			Entity[] ret = new Entity[jsonFiles.length];
			for (int i = 0; i < jsonFiles.length; i++) {
				BufferedReader br = new BufferedReader(new FileReader(jsonFiles[i]));
				ret[i] = g.fromJson(br, Entity.class);
			}
			return ret;
		} else if (!f.exists() && f.isDirectory()) {
			throw new FileNotFoundException("Folder does not exist");
		} else if (f.exists() && !f.isDirectory()) {
			throw new MalformedURLException(f.getName() + " is a file not a folder");
		} else {
			throw new Exception("Unspecified error");
		}
	}

	private static void findClasses(File f) {
		File dir = new File(f.getAbsolutePath() + "/classes/");
		if (dir.exists() && dir.isDirectory()) {
			FileFilter ff = new FileFilter(".class");
			File[] classes = dir.listFiles(ff);

			for (int i = 0; i < classes.length; i++) {
				try {
					addSoftwareLibrary(classes[i]);
				} catch (Exception e) {
					p.log(2, "Error in loading class file '" + classes[i].getName() + "'.");
					p.log(2, e.getMessage());
				}
			}
		} else if (!dir.exists() && dir.isDirectory()) {
			p.log(2, "Folder does not exist");
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
			return context.deserialize(element, Class.forName("DevTSK.Entity." + type));
		} catch (ClassNotFoundException cnfe) {
			throw new JsonParseException("Unknown element type: " + type, cnfe);
		}
	}
}
