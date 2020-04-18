package fr.whartokx.cyanite;

import javax.swing.JFrame;

import fr.theshark34.openlauncherlib.util.CrashReporter;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.util.WindowMover;


@SuppressWarnings("serial")
public class LauncherFrame extends JFrame {
	
	

	private static LauncherFrame instance;
	private LauncherPanel launcherPanel;
	private static CrashReporter crashReporter;

	public LauncherFrame() {
		
		
        setTitle("Topazia Launcher");
        setSize(975, 625);
        setDefaultCloseOperation(3);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setBackground(Swinger.TRANSPARENT);
        setIconImage(Swinger.getResource("icon.png"));
        setContentPane(launcherPanel = new LauncherPanel());
        WindowMover mover = new WindowMover(this);
        addMouseListener(mover);
        addMouseMotionListener(mover);
        setVisible(true);
		
		
	}

	public static void main(String[] args) {
		Swinger.setSystemLookNFeel();
		Swinger.setResourcePath("/fr/whartokx/cyanite/ressources/");
		Launcher.CA_CRASHES_FOLDER.mkdirs();
		crashReporter = new CrashReporter("Topazia Beta Launcher", Launcher.CA_CRASHES_FOLDER);

		instance = new LauncherFrame();
	}
	

	public static LauncherFrame getInstance() {
		return instance;
	}

	public static CrashReporter getCrashReporter() {
		return crashReporter;
	}

	public LauncherPanel getLauncherPanel() {
		return this.launcherPanel;

	}

}

