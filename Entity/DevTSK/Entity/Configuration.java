package DevTSK.Entity;

public class Configuration {

	public static final float VER = 2.0f;

	private float version;
	private int textSize, winSize = -1;
	private int[] bgRGB, outFoRGB, outBgRGB, inFoRGB, inBgRGB;
	private boolean writeLogs, fancyNames;
	private String lastDir;

	public void setDefaults() {
		setWriteLogs(false);
		setTextSize(12);
		setWinSize(0);
		setBgRGB(new int[] { 255, 255, 255 });
		setOutFoRGB(new int[] { 0, 0, 0 });
		setOutBgRGB(new int[] { 255, 255, 255 });
		setInFoRGB(new int[] { 0, 0, 0 });
		setInBgRGB(new int[] { 255, 255, 255 });
		fancyNames = false;
		version = VER;
	}

	public float getVersion() {
		return version;
	}

	public int getTextSize() {
		return textSize;
	}

	public void setTextSize(int textSize) {
		this.textSize = textSize;
	}

	public int getWinSize() {
		return winSize;
	}

	public void setWinSize(int winSize) {
		this.winSize = winSize;
	}

	public int[] getBgRGB() {
		return bgRGB;
	}

	public void setBgRGB(int[] bgRGB) {
		this.bgRGB = bgRGB;
	}

	public int[] getOutBgRGB() {
		return outBgRGB;
	}

	public void setOutBgRGB(int[] outBgRGB) {
		this.outBgRGB = outBgRGB;
	}

	public int[] getInFoRGB() {
		return inFoRGB;
	}

	public void setInFoRGB(int[] inFoRGB) {
		this.inFoRGB = inFoRGB;
	}

	public int[] getOutFoRGB() {
		return outFoRGB;
	}

	public void setOutFoRGB(int[] outFoRGB) {
		this.outFoRGB = outFoRGB;
	}

	public int[] getInBgRGB() {
		return inBgRGB;
	}

	public void setInBgRGB(int[] inBgRGB) {
		this.inBgRGB = inBgRGB;
	}

	public boolean isWriteLogs() {
		return writeLogs;
	}

	public void setWriteLogs(boolean writeLogs) {
		this.writeLogs = writeLogs;
	}

	public String getLastDir() {
		return lastDir;
	}

	public void setLastDir(String lastDir) {
		this.lastDir = lastDir;
	}

	public boolean isUseTitles() {
		return fancyNames;
	}
}
