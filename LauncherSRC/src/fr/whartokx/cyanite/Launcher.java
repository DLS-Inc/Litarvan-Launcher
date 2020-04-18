package fr.whartokx.cyanite;

import java.io.File;
import java.util.Arrays;
// CACA
import fr.litarvan.openauth.AuthPoints;
import fr.litarvan.openauth.AuthenticationException;
import fr.litarvan.openauth.Authenticator;
import fr.litarvan.openauth.model.AuthAgent;
import fr.litarvan.openauth.model.response.AuthResponse;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import fr.theshark34.openlauncherlib.minecraft.GameFolder;
import fr.theshark34.openlauncherlib.minecraft.GameInfos;
import fr.theshark34.openlauncherlib.minecraft.GameTweak;
import fr.theshark34.openlauncherlib.minecraft.GameType;
import fr.theshark34.openlauncherlib.minecraft.GameVersion;
import fr.theshark34.openlauncherlib.minecraft.MinecraftLauncher;
import fr.theshark34.supdate.BarAPI;
import fr.theshark34.supdate.SUpdate;
import fr.theshark34.supdate.application.integrated.FileDeleter;
import fr.theshark34.swinger.Swinger;

public class Launcher {
	public static final GameVersion CA_VERSION = new GameVersion("1.7.10", GameType.V1_7_10);
	public static final GameInfos CA_INFOS = new GameInfos("Topazia", CA_VERSION, new GameTweak[] { GameTweak.FORGE });
	public static final File CA_DIR = CA_INFOS.getGameDir();
	public static final File CA_CRASHES_FOLDER = new File(CA_DIR, "crashes");

	private static AuthInfos authInfos;
	private static Thread updateThread;


	public static void auth(String username, String password) throws AuthenticationException

	{
	  if (LauncherPanel.olinemode.getText()== "true") {
		Authenticator authenticator = new Authenticator(Authenticator.MOJANG_AUTH_URL, AuthPoints.NORMAL_AUTH_POINTS);
		AuthResponse response = authenticator.authenticate(AuthAgent.MINECRAFT, username, password, "");
		authInfos = new AuthInfos(response.getSelectedProfile().getName(), response.getAccessToken(),
				response.getSelectedProfile().getId());
	  }
	  
	  if (LauncherPanel.olinemode.getText()== "false") {
		authInfos = new AuthInfos(username, "sry", "nope");
	  }
	}
	
	public static void update() throws Exception {
		SUpdate su = new SUpdate("https://www.topazia.pw/download/launcher/", CA_DIR);
		su.getServerRequester().setRewriteEnabled(true);
		su.addApplication(new FileDeleter());

		Thread updateThread = new Thread() {
			private int val;
			private int max;

			@Override
			public void run() {
				while (!this.isInterrupted()) {
					if (BarAPI.getNumberOfFileToDownload() == 0) {
						LauncherFrame.getInstance().getLauncherPanel().setInfoText("Vérification...");
						continue;
					}
					val = (int) (BarAPI.getNumberOfTotalDownloadedBytes() / 1000);
					max = (int) (BarAPI.getNumberOfTotalBytesToDownload() / 1000);

					LauncherFrame.getInstance().getLauncherPanel().getProgressBar().setMaximum(max);
					LauncherFrame.getInstance().getLauncherPanel().getProgressBar().setValue(val);

					LauncherFrame.getInstance().getLauncherPanel()
							.setInfoText("Mise a jour en cours... Fichiers Restants :  " + BarAPI.getNumberOfDownloadedFiles() + "/"
									+ BarAPI.getNumberOfFileToDownload() + " " + Swinger.percentage(val, max) + "% ...");
				}
			}
		};

		updateThread.start();

		su.start();
		updateThread.interrupt();
	}

	public static void launch() throws LaunchException {
		ExternalLaunchProfile profile = MinecraftLauncher.createExternalProfile(CA_INFOS, GameFolder.BASIC, authInfos);
		profile.getVmArgs().addAll(Arrays.asList(LauncherFrame.getInstance().getLauncherPanel().getRamSelector().getRamArguments()));
		
		ExternalLauncher launcher = new ExternalLauncher(profile);

		Process p = launcher.launch();

		try {
			Thread.sleep(5000L);
			LauncherFrame.getInstance().setVisible(false);
			p.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.exit(0);
	}
	public static void interruptTherad() {
		if (updateThread != null)
		updateThread.interrupt();
	}
}
