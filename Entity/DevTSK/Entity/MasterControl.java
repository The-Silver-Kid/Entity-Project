package DevTSK.Entity;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import DevTSK.Entity.Commands.Default.BRSTMPLY;
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
import DevTSK.Util.FileDetect;
import DevTSK.Util.LoggerPro;
import DevTSK.Util.DAG.ConfigException;

/**
 * @author The_Silver_Kid
 * 
 *         Sets up the program
 */
public class MasterControl {

	private static EntityLoader h;
	private static String workdir;
	public static Window poni;

	private static LoggerPro p;

	private static Command[] cmands;

	private static JFrame empty;
	private static JComboBox<String> comboBox;

	public static Configuration config;

	static boolean selected = false;
	private static int sel = -1;

	public static Multiverse m;
	public static Universe u;

	private final static Action action = new Suniv();

	public static void main(String[] args) throws FileNotFoundException, InterruptedException {
		run();
	}

	private static void run() throws FileNotFoundException, InterruptedException {
		config = new Configuration();
		FileDetect fd = new FileDetect("./ecfg.json");

		if (fd.Detect()) {
			Gson g = new Gson();
			BufferedReader broke;
			try {
				broke = new BufferedReader(new FileReader(new File("./ecfg.json")));
				config = g.fromJson(broke, Configuration.class);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.exit(2);
			}

		} else {
			config.setDefaults();
			saveConf(config);
		}

		if (config.isWriteLogs()) {
			p = new LoggerPro(new String[] { "-", "#", "X" }, LoggerPro.FILE_AND_CONSOLE);
		} else {
			p = new LoggerPro(new String[] { "-", "#", "X" }, LoggerPro.CONSOLE_ONLY);
		}

		p.log("Load of configuration completed.");

		ArrayList<Command> coms = new ArrayList<>(), plugs = new ArrayList<>();

		File f = findDir();
		empty.dispose();
		empty.setVisible(false);

		workdir = f.getAbsolutePath();

		fd = new FileDetect(workdir + "/Multiverse.json");

		if (!fd.Detect())
			try {
				throw new FileNotFoundException();
			} catch (FileNotFoundException e) {
				p.log("Could not locate Multiverse.json");
				e.printStackTrace();
				System.exit(3);
			}

		p.log("Loading external Entity classes");
		try {
			findClasses(workdir + "/Classes/", true);
		} catch (NullPointerException e) {
			p.log("User cancelled Entity Pack choosing... System will exit.");
			System.exit(0);
		}
		p.log("Loading of external classes finished.");

		p.log("Beginning Initilization Phase.");

		GsonBuilder bldr = new GsonBuilder();
		bldr.registerTypeAdapter(Entity.class, new EntityAdapter());
		Gson g = bldr.create();

		p.log("Creating Multiverse.");
		BufferedReader br = new BufferedReader(new FileReader(new File(workdir + "/Multiverse.json")));
		m = g.fromJson(br, Multiverse.class);

		p.log("Picking Area.");
		empty.dispose();

		empty = new JFrame();
		empty.setSize(540, 80);
		empty.getContentPane().setLayout(null);
		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(m.getRawAreaList()));
		comboBox.setBounds(10, 5, 400, 22);
		empty.getContentPane().add(comboBox);
		JButton b = new JButton();
		b.setBounds(415, 5, 100, 22);
		b.setAction(MasterControl.action);
		empty.getContentPane().add(b);
		empty.setVisible(true);

		while (!selected) {
			Thread.sleep(100);
		}

		p.log("User has selected.");

		sel = comboBox.getSelectedIndex();

		empty.dispose();

		br = new BufferedReader(new FileReader(new File(workdir + m.getAreaList()[sel])));
		u = g.fromJson(br, Universe.class);

		Entity[] OC = new Entity[] {};
		try {
			OC = deserializeJSONFiles(u.getEntityList(), g);
		} catch (Exception e1) {
			p.log(2, "Something went wrong in loading entities! Cannot Continue");
			p.log(2, e1.getMessage());
			for (StackTraceElement s : e1.getStackTrace())
				p.log(2, s.toString());
			System.exit(1);
		}

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
		coms.add(new BRSTMPLY());

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
		p.log("Pluginable command injection finished.");

		cmands = new Command[coms.size()];
		coms.toArray(cmands);

		File ff = new File(workdir + "/Images/");
		if (!ff.exists())
			ff.mkdir();

		for (Entity check : OC) {
			File fff = new File(workdir + "/Images/" + check.getImagePath());
			if (!fff.exists() && !check.getImagePath().equalsIgnoreCase("null.png"))
				p.log(2, "Entity " + check.getAltName() + " has a specified image that doesn't exist!");
			fff = new File(workdir + "/Images/" + check.getAltImagePath());
			if (!fff.exists() && !check.getAltImagePath().equalsIgnoreCase("null.png"))
				p.log(2, "Entity " + check.getAltName() + " has a specified alternate image that doesn't exist!");

		}

		h = new EntityLoader(OC, u.getOffset().getDay(), p, f, cmands);

		try {
			poni = new Window(u.getTitle() + " - " + u.getName(), 1, 0, 0, config, h, p, f);
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

	private static Entity[] deserializeJSONFiles(String[] in, Gson g) throws Exception {
		Entity[] e = new Entity[in.length];

		BufferedReader br;

		for (int i = 0; i < in.length; i++) {
			br = new BufferedReader(new FileReader(new File(workdir + in[i])));
			e[i] = g.fromJson(br, Entity.class);
		}

		return e;
	}

	private static void findClasses(String ext, Boolean announce) {
		File dir = new File(ext);
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
		JFileChooser choose = new JFileChooser(config.getLastDir());
		choose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		choose.showDialog(null, "Open Directory");
		empty.add(choose);

		File f = choose.getSelectedFile();

		config.setLastDir(f.getAbsolutePath());

		saveConf(config);

		return f;
	}

	private static void saveConf(Configuration configuration) {
		GsonBuilder gb = new GsonBuilder();
		gb.setPrettyPrinting();
		Gson g = gb.create();
		String s = g.toJson(configuration);
		byte[] b = s.getBytes();
		try {
			FileOutputStream out = new FileOutputStream("./ecfg.json");
			out.write(b);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

	private static ArrayList<Command> plugins(File f) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		File master = new File(workdir + "/plugins");
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

			findClasses(workdir + "/plugins/", false);

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

class Suniv extends AbstractAction {
	private static final long serialVersionUID = 1L;

	/**
	 * makes the button look beautiful.
	 */
	public Suniv() {
		putValue(NAME, "Run");
		putValue(SHORT_DESCRIPTION, "Pushes string to internal system.");
	}

	/**
	 * Sends the input box text to the Entity Loader for processing.
	 */
	public void actionPerformed(ActionEvent arg0) {
		MasterControl.selected = true;
	}

}
