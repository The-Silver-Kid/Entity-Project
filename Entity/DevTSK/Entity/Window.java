/* Window Class
 * (C) DevTSK Productions 2015 */

package DevTSK.Entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.TabSet;
import javax.swing.text.TabStop;
import DevTSK.Util.LoggerPro;
import DevTSK.Util.DAG.Config;
import DevTSK.Util.DAG.ConfigException;

/**
 * Draws the window and receives text to put in the output boxes
 * 
 * @author The_Silver_Kid
 *
 */
public class Window {
	public JFrame frmPoniiPic;
	public JFrame frmPoniiPicCont;

	public final Action action = new SwingAction();
	public JLabel lblPoniiPic;
	public JLabel lblCMPic;
	public JTextField lblTextArea;
	public JTextPane lblInfo;
	public JScrollPane loltest;
	public JButton in;
	public String handler = "";

	public EntityLoader el;

	private LoggerPro p;

	private String name;

	private File dir;

	private TabSet tabs;

	/**
	 * Sets up the windows based on various input variables and configuration.
	 * 
	 * @param p
	 * 
	 * @param String
	 *            Title
	 * @param int
	 *            close operation
	 * @param int
	 *            x cord of the window
	 * @param int
	 *            y cord of the window
	 * @param int
	 *            if the interaction window should be separate or not (0 or 1)
	 * @param EntityLoader
	 *            the constructed Entity Loader from the main class.
	 * @throws ConfigException
	 * @throws IOException
	 */
	public Window(String n, int close, int x, int y, Configuration config, EntityLoader h, LoggerPro p, File dir) throws ConfigException, IOException {

		TabStop[] tstops = new TabStop[] { new TabStop(220), new TabStop(280), /*new TabStop(150)*/ };
		tabs = new TabSet(tstops);

		el = h;

		this.p = p;

		name = n;

		this.dir = dir;

		frmPoniiPic = new JFrame();
		frmPoniiPic.getContentPane().setBackground(SystemColor.window);
		frmPoniiPic.setIconImage(Toolkit.getDefaultToolkit().getImage(Window.class.getResource("/DevTSK/Entity/files/ikon.png")));
		frmPoniiPic.setTitle(n + " Picture Window");
		frmPoniiPic.setBackground(SystemColor.window);
		frmPoniiPic.setResizable(false);
		frmPoniiPic.setBounds(x, y, 700, 700);
		p.log("Setting window size to " + (700 + config.getWinSize()));
		if (close == 0) {
			frmPoniiPic.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		} else if (close == 1) {
			frmPoniiPic.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} else {
			try {
				throw new Exception("Invalid Close Operation");
			} catch (Exception e) {
				e.printStackTrace();
				frmPoniiPic.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			}
		}
		frmPoniiPic.getContentPane().setLayout(null);

		frmPoniiPicCont = new JFrame();
		frmPoniiPicCont.getContentPane().setBackground(SystemColor.window);
		frmPoniiPicCont.setIconImage(Toolkit.getDefaultToolkit().getImage(Window.class.getResource("/DevTSK/Entity/files/ikon.png")));
		frmPoniiPicCont.setTitle(n + " Control Window");
		frmPoniiPicCont.setBackground(SystemColor.window);
		frmPoniiPicCont.setResizable(false);
		frmPoniiPicCont.setBounds(x + 700, y, 700 + config.getWinSize() * (config.getTextSize() - 11), 500);
		frmPoniiPicCont.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPoniiPicCont.getContentPane().setLayout(null);

		lblPoniiPic = new JLabel();
		lblPoniiPic.setBounds(0, 0, 700, 700);
		frmPoniiPic.getContentPane().add(lblPoniiPic);

		lblCMPic = new JLabel();
		lblCMPic.setBounds(350, 0, 350, 350);
		frmPoniiPic.getContentPane().add(lblCMPic);

		lblTextArea = new JTextField();
		Font txtarea = lblTextArea.getFont();
		lblTextArea.setToolTipText("Entity Name");
		lblTextArea.setText("");
		lblTextArea.setBounds(10, 10, 580, 20);
		lblTextArea.setFont(new Font(txtarea.getName(), Font.PLAIN, config.getTextSize()));
		frmPoniiPicCont.getContentPane().add(lblTextArea);

		lblInfo = new JTextPane();
		//lblInfo.setWrapStyleWord(true);
		lblInfo.setToolTipText("Information box");
		//lblInfo.setLineWrap(true);
		lblInfo.setText("");
		lblInfo.setBounds(10, 40, 670 + config.getWinSize() * (config.getTextSize() - 11), 420);
		lblInfo.setEditable(false);
		lblInfo.setFont(lblTextArea.getFont());
		frmPoniiPicCont.getContentPane().add(lblInfo);

		Style style = lblInfo.getLogicalStyle();
		StyleConstants.setTabSet(style, tabs);
		lblInfo.setLogicalStyle(style);

		loltest = new JScrollPane(lblInfo);
		loltest.setBounds(lblInfo.getBounds());
		loltest.setAutoscrolls(true);
		frmPoniiPicCont.getContentPane().add(loltest);

		in = new JButton();
		in.setBounds(600, 10, 80, 20);
		in.setAction(action);
		frmPoniiPicCont.getRootPane().setDefaultButton(in);
		frmPoniiPicCont.getContentPane().add(in);

		setupConfig(config);

		frmPoniiPic.setVisible(true);

		frmPoniiPicCont.setVisible(true);
		punch();
		this.p.log("Window Constructed.");
	}

	/**
	 * Converts a normal image from classpath to a ImageIcon for display
	 * 
	 * @param String
	 *            image name
	 * @return Returns The Converted ImageIcon
	 * @throws IOException
	 */
	private ImageIcon getImIcn(String sr) throws IOException {
		Image img = ImageIO.read(Window.class.getResource(sr));
		ImageIcon icn = new ImageIcon(img);
		return icn;
	}

	/**
	 * draws the picture to the picture label
	 * 
	 * @param String
	 *            image name
	 * @throws IOException
	 */
	public void drawPic(String s) throws IOException {
		lblPoniiPic.setIcon(getImIcn(s));
	}

	/**
	 * prints the input string to the output box
	 * 
	 * @param String
	 *            input string
	 */
	public void println(String s) {
		lblInfo.setText(lblInfo.getText() + s + "\n");
	}

	/**
	 * prints a blank line to the output box
	 */
	public void println() {
		lblInfo.setText(lblInfo.getText() + "\n");
	}

	/**
	 * clears the output box
	 */
	public void printCl() {
		lblInfo.setText("");
	}

	/**
	 * Class that handles when you click the button or press enter.
	 * 
	 * @author The_Silver_Kid
	 *
	 */
	private class SwingAction extends AbstractAction {
		private static final long serialVersionUID = 3646194311743048047L;

		/**
		 * makes the button look beautiful.
		 */
		public SwingAction() {
			putValue(NAME, "Run");
			putValue(SHORT_DESCRIPTION, "Pushes string to internal system.");
		}

		/**
		 * Sends the input box text to the Entity Loader for processing.
		 */
		public void actionPerformed(ActionEvent arg0) {
			try {
				el.handle(lblTextArea.getText());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Converts an internal image to an icon.
	 * 
	 * @param String
	 *            image name
	 * @return Icon format of the image.
	 * @throws IOException
	 */
	public Icon getInternalImageIcn(String imagePath) throws IOException {
		Image img = ImageIO.read(Window.class.getResource(imagePath));
		ImageIcon icn = new ImageIcon(img);
		return icn;
	}

	/**
	 * Attempts to load an image from an external directory.
	 * 
	 * @param imagePath
	 * @return
	 * @throws IOException
	 */
	public Icon getExternalImageIcn(String imagePath) throws IOException {

		p.log("Attempting to load image " + imagePath + " from " + dir.toString());
		Image img;
		try {
			img = ImageIO.read(new File(dir.toString() + "/images/" + imagePath));
		} catch (IOException e) {
			p.log(1, "Image '" + imagePath + "' was not found. This error is on the pack creators hands.");
			img = ImageIO.read(Window.class.getResource("/DevTSK/Entity/files/null.png"));
			p.log(2, e.getMessage());
			for (StackTraceElement s : e.getStackTrace())
				p.log(2, s.toString());
		}
		ImageIcon icn = new ImageIcon(img);
		return icn;

	}

	/**
	 * DESTROY ALL THE WINDOW AND EVERYTHING RELATED TO IT
	 * *EVIL LAUGHING HERE*
	 */
	public void destroyWindows() {
		frmPoniiPic.dispose();
		frmPoniiPicCont.dispose();
	}

	/**
	 * Gets the System working... Unknown why need but it is...
	 * 
	 * @throws ConfigException
	 */
	public void punch() throws ConfigException {
		if (new Config("./EntityConfig.cfg").getDouble("version") < 2.0) {
			printCl();
			println("If you are seeing this something went wrong"
					+ "\nIt is probably my fault...");
		}
	}

	/**
	 * Sets up various things by loading them from the external configuration
	 * file.
	 * 
	 * @throws ConfigException
	 * @throws IOException
	 */
	public void setupConfig(Configuration config) throws IOException {
		p.log("Setting up window configuration.");

		int[] r = config.getBgRGB();
		frmPoniiPic.getContentPane().setBackground(new Color(r[0], r[1], r[2]));
		frmPoniiPicCont.getContentPane().setBackground(new Color(r[0], r[1], r[2]));

		r = config.getInBgRGB();
		lblTextArea.setBackground(new Color(r[0], r[1], r[2]));

		r = config.getInFoRGB();
		lblTextArea.setForeground(new Color(r[0], r[1], r[2]));

		r = config.getOutFoRGB();
		lblInfo.setForeground(new Color(r[0], r[1], r[2]));

		r = config.getOutBgRGB();
		lblInfo.setBackground(new Color(r[0], r[1], r[2]));
	}
}