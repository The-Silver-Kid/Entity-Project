package DevTSK.Entity;

import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class uSelector extends JFrame {
	private class BBox {
		private int x, y, w, h;

		public BBox(int xT, int yT, int wT, int hT) {
			x = xT;
			y = yT;
			w = wT;
			h = hT;
		}

		public boolean isInBounds(int pX, int pY) {
			return ((pX >= x && pX <= (x + w)) && (pY >= y && pY <= (y + h)));
		}
	}

	private static final long serialVersionUID = 1L;

	private BufferedImage stuff;
	private JLabel view;
	private ArrayList<BBox> boxes = new ArrayList<BBox>();

	private static final int BOX_SIZE = 100;

	public uSelector(int xSize, int ySize) {
		stuff = new BufferedImage(xSize, ySize, BufferedImage.TYPE_INT_RGB);
		view = new JLabel(new ImageIcon(stuff));
		this.setContentPane(view);
		this.setResizable(false);
		this.pack();
		this.setLocationByPlatform(true);
		Graphics2D g = (Graphics2D) stuff.getGraphics();
		for (int i = 0; i < MasterControl.verses.length; i++) {
			boxes.add(new BBox(MasterControl.verses[i].getCoords()[0], MasterControl.verses[i].getCoords()[1], BOX_SIZE, BOX_SIZE));
			g.setColor(MasterControl.verses[i].getColor());
			g.fillRect(MasterControl.verses[i].getCoords()[0], MasterControl.verses[i].getCoords()[1], BOX_SIZE, BOX_SIZE);
			//invert color?
			g.setColor(MasterControl.m.getBackground());
			g.drawString(MasterControl.verses[i].toString(),
					MasterControl.verses[i].getCoords()[0] - (g.getFontMetrics().stringWidth(MasterControl.verses[i].toString()) / 2) + (BOX_SIZE / 2),
					MasterControl.verses[i].getCoords()[1] + (BOX_SIZE / 2));
			g.drawString(MasterControl.verses[i].getOffset().getDay().getYear() + "",
					MasterControl.verses[i].getCoords()[0] - (g.getFontMetrics().stringWidth(MasterControl.verses[i].getOffset().getDay().getYear() + "") / 2) + (BOX_SIZE / 2),
					MasterControl.verses[i].getCoords()[1] + (BOX_SIZE / 2) + g.getFontMetrics().getHeight());
			this.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					for (int i = 0; i < boxes.size(); i++)
						if (boxes.get(i).isInBounds(e.getX(), e.getY())) {
							MasterControl.sel = i;
							MasterControl.selected = true;
						}
				}
			});
		}
	}

}
