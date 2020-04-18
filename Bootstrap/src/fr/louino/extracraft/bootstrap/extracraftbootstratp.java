package fr.louino.extracraft.bootstrap;

import static fr.theshark34.swinger.Swinger.getTransparentWhite;
import static fr.theshark34.swinger.Swinger.setResourcePath;

import java.io.File;
import java.io.IOException;

import fr.theshark34.openlauncherlib.bootstrap.Bootstrap;
import fr.theshark34.openlauncherlib.bootstrap.LauncherClasspath;
import fr.theshark34.openlauncherlib.bootstrap.LauncherInfos;
import fr.theshark34.openlauncherlib.util.ErrorUtil;
import fr.theshark34.openlauncherlib.util.GameDir;
import fr.theshark34.openlauncherlib.util.SplashScreen;
import fr.theshark34.supdate.BarAPI;
import fr.theshark34.supdate.SUpdate;
import fr.theshark34.supdate.application.integrated.FileDeleter;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.colored.SColoredBar;

public class extracraftbootstratp {
	
	private static SplashScreen splash;
	private static SColoredBar bar;
	private static Thread barThread;
	
	private static final LauncherInfos CA_B_INFOS = new LauncherInfos("Topazia", "fr.whartokx.cyanite.LauncherFrame");
	private static final File CA_DIR = GameDir.createGameDir("Topazia");
	private static final LauncherClasspath CA_B_CP = new LauncherClasspath(new File(CA_DIR, "launcher/launcher.jar"), new File(CA_DIR, "Launcher/Libs"));
	
	private static ErrorUtil errorUtil = new ErrorUtil(new File(CA_DIR, "launcher/craches/"));
	
	public static void main(String[] args) {
		setResourcePath("/fr/louino/extracraft/bootstrap/resources");
		
		displaySplash();
		//barThread.start();
		
		try {
			doUpdate();
		} catch (Exception e) {
			errorUtil.catchError(e, "impossible de mettre a jour launcher !");
			barThread.interrupt();
		}
		
		
		try {
		launchlauncher();
		} catch (IOException e) {
			errorUtil.catchError(e, "impossible de lancer launcher !");

		}
	}
	
	private static void displaySplash() {
		splash = new SplashScreen("topazia", Swinger.getResource("splash.png"));
		splash.setLayout(null);
		bar = new SColoredBar(getTransparentWhite(100), getTransparentWhite(175));
		bar.setBounds(7, 363, 282 , 27);
		splash.add(bar);
		
		splash.setVisible(true);
	}
	
    private static void doUpdate() throws Exception {
		SUpdate su = new SUpdate("https://topazia.pw/download/bootstrap/", new File(CA_DIR, "Launcher"));
		su.getServerRequester().setRewriteEnabled(true);
		su.addApplication(new FileDeleter());
		barThread = new Thread() {
		 @Override
		 public void run() {
			 while(!this.isInterrupted()) {
				 bar.setValue((int) (BarAPI.getNumberOfTotalDownloadedBytes() / 1000));
				 bar.setMaximum((int) (BarAPI.getNumberOfFileToDownload() / 1000));
			 }
		 }
		};
		barThread.start();
		su.start();
		barThread.interrupt();
		
		
		
    }
	
    private static void launchlauncher() throws IOException {
	Bootstrap bootstrap = new Bootstrap(CA_B_CP, CA_B_INFOS);
	Process p = bootstrap.launch();
	
	splash.setVisible(false);
	
	try {
	p.waitFor();
	} catch (InterruptedException e) {
	}
	System.exit(0);
    }

}
