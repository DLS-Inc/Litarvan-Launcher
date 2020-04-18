package fr.whartokx.cyanite;

import fr.litarvan.openauth.AuthenticationException;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.openlauncherlib.util.ramselector.RamSelector;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.colored.SColoredBar;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.*;
 
// Referenced classes of package fr.whartokx.cyanite.Launcher:
//            Launcher, LauncherFrame
 
public class LauncherPanel extends JPanel implements SwingerEventListener
{
    private static final String Hover = null;
    private Image background;
    private Saver saver;
    private JTextField usernameField;
    private JTextField speudoField;//pour les crack
    private JTextField passwordField;
    private String boutonClicked;
    private STexturedButton quitButton;
    private STexturedButton playButton;
    private STexturedButton voletquiroule;
    private STexturedButton hideButton;
    private STexturedButton Discordbutton;
    private STexturedButton ramButton;
    private SColoredBar progressBar;
    private JLabel infoLabel;
    private JLabel versionlabel;
    private RamSelector ramSelector;
    public static JLabel olinemode; //olinemode == true = premium alors que olinemode == false = crack
   // private JEditorPane jep;
    
    public LauncherPanel()
    {
    	//JOptionPane.showMessageDialog(this,"ceci est une version de developement \n MERCI DE NE PAS PUBLIER", "ATENTION!!",JOptionPane.INFORMATION_MESSAGE);
    	
        background = Swinger.getResource("background.png");
        saver = new Saver(new File(Launcher.CA_DIR, "launcher.properties"));
        usernameField = new JTextField(saver.get("username"));
        speudoField = new JTextField(saver.get("speudo"));
        passwordField = new JPasswordField();
        quitButton = new STexturedButton(Swinger.getResource("croix-afk.png"), Swinger.getResource("croix-hover.png"), Swinger.getResource("croix-clicked.png"));
        playButton = new STexturedButton(Swinger.getResource("play-afk.png"), Swinger.getResource("play-hide.png"), Swinger.getResource("play-clicked.png"));
        voletquiroule = new STexturedButton(Swinger.getResource("volet-afk.png"), Swinger.getResource("volet-hide.png"), Swinger.getResource("volet-hide.png"));
        hideButton = new STexturedButton(Swinger.getResource("hide-afk.png"), Swinger.getResource("hide-hover.png"), Swinger.getResource("hide-clicked.png"));
        Discordbutton = new STexturedButton(Swinger.getResource("rien.png"), Swinger.getResource("rien.png"), Swinger.getResource("rien.png"));
        ramButton = new STexturedButton(Swinger.getResource("ram-afk.png"), Swinger.getResource("ram-hide.png"), Swinger.getResource("ram-clicked.png"));
        progressBar = new SColoredBar(new Color(255, 255, 255, 100), new Color(255, 255, 255, 175));
        infoLabel = new JLabel("Bienvenue ! Connectez-Vous ...", 0);
        versionlabel = new JLabel("3.0");
        olinemode = new JLabel("");
        ramSelector = new RamSelector(new File(Launcher.CA_DIR, "Launcher\\ram.txt"));
      //  jep = new JEditorPane();
        setLayout(null);
        setBackground(Swinger.TRANSPARENT);
        usernameField.setForeground(Color.WHITE);
        usernameField.setFont(usernameField.getFont().deriveFont(20F));
        usernameField.setCaretColor(Color.WHITE);
        usernameField.setOpaque(false);
        usernameField.setBorder(null);
        usernameField.setBounds(135, 299, 239, 38);
        add(usernameField); //email premium
        passwordField.setForeground(Color.WHITE);
        passwordField.setFont(passwordField.getFont().deriveFont(20F));
        passwordField.setCaretColor(Color.WHITE);
        passwordField.setOpaque(false);
        passwordField.setBorder(null);
        passwordField.setBounds(135, 403, 239, 38);
        add(passwordField);
        speudoField.setForeground(Color.WHITE);
        speudoField.setFont(usernameField.getFont().deriveFont(20F));
        speudoField.setCaretColor(Color.WHITE);
        speudoField.setOpaque(false);
        speudoField.setBorder(null);
        speudoField.setBounds(595, 298, 239, 38);
        add(speudoField); //speudo crack
        playButton.setBounds(397, 470, 180, 50);
        playButton.addEventListener(this);
        playButton.setOpaque(false);
        add(playButton);
        voletquiroule.setBounds(0, 50, 207, 100);
        voletquiroule.addEventListener(this);
        voletquiroule.setOpaque(false);
        add(voletquiroule);
        quitButton.setBounds(948, 7, 20, 20);
        quitButton.addEventListener(this);
        quitButton.setOpaque(false);
        add(quitButton);
        hideButton.setBounds(926, 7, 20, 20);
        hideButton.addEventListener(this);
        add(hideButton);
        Discordbutton.setBounds(8, 168, 191, 38);
        Discordbutton.addEventListener(this);
       // add(Discordbutton);
        progressBar.setStringPainted(true);
        progressBar.setString("");
        progressBar.setBounds(0, 610, 975, 15);
        add(progressBar);
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setFont(usernameField.getFont());
        infoLabel.setBounds(0, 570, 975, 35);
        add(infoLabel);
        versionlabel.setForeground(Color.WHITE);
        versionlabel.setFont(usernameField.getFont());
        versionlabel.setBounds(1, 1, 100, 15);
        add(versionlabel);
        ramButton.addEventListener(this);
        ramButton.setBounds(450, 530, 75, 31);
        add(ramButton);
    }
 
    @Override
	public void onEvent(SwingerEvent e) {
		if (e.getSource() == playButton) {
			setFieldsEnabled(false);
			
			//on regarde si c'est un premium ou un crack//
			if(usernameField.getText().length() != 0 && speudoField.getText().length() != 0) { // il y a une email rentrée et un speudo donc on ne peux savoir si c'est un crack ou un premium
				JOptionPane.showMessageDialog(this, "Erreur, veuillez entrer un speudo ou un email mais pas les deux", "Erreur", JOptionPane.WARNING_MESSAGE);
				setFieldsEnabled(true);
				return;
			}
			if(usernameField.getText().length() != 0 && speudoField.getText().length() == 0) { //c'est un premium
				olinemode.setText("true");
			}
			if(usernameField.getText().length() == 0 && speudoField.getText().length() != 0) { //c'est un crack
				olinemode.setText("false");
			}
			if(usernameField.getText().length() == 0 && speudoField.getText().length() == 0) { // il y a aucune information rentrer
				JOptionPane.showMessageDialog(this, "Erreur, veuillez entrer un speudo ou un email valide", "Erreur", JOptionPane.WARNING_MESSAGE);
				setFieldsEnabled(true);
				return;
			}
			//////////////////////////////////////////////
			
			/*if (usernameField.getText().replaceAll(" ", "").length() == 0) {
				JOptionPane.showMessageDialog(this, "Erreur, veuillez entrer un email valide.",
						"Erreur", JOptionPane.ERROR_MESSAGE);
				setFieldsEnabled(true);
				return;
			}*/
			usernameField.getText().replaceAll(" ", "");
			speudoField.getText().replaceAll(" ", "");
			
			Thread t = new Thread() {
				@Override
				public void run() 
				{
					try 
					{
						if(olinemode.getText() == "true") {
							Launcher.auth(usernameField.getText(), passwordField.getText());
						}
						if(olinemode.getText() == "false") {
							Launcher.auth(speudoField.getText(), passwordField.getText());
						}
					} 
					catch (AuthenticationException e) 
					{
						JOptionPane.showMessageDialog(LauncherPanel.this,
								"Erreur, impossible de se connecter : " + e.getErrorModel().getErrorMessage(), "Erreur",
								JOptionPane.ERROR_MESSAGE);
						setFieldsEnabled(true);
						return;
					}
					saver.set("username", usernameField.getText());
					saver.set("speudo", speudoField.getText());
					try 
					{
						Launcher.update();
					} 
					catch (Exception e) 
					{
						Launcher.interruptTherad();
						LauncherFrame.getCrashReporter().catchError(e, "Erreur, problème du Serveur. Peut-être en maitnenance ?");
					}

					try 
					{
						Launcher.launch();
					} 
					catch (LaunchException e) 
					{
						LauncherFrame.getCrashReporter().catchError(e, "Erreur, impossible de lancer le Jeu. Venez sur Notre Discord Pour De L'aide.");
					}
				}
			};
			
			t.start();
		} 
        
	 if (e.getSource() == Discordbutton) {
		 try {
			Desktop.getDesktop().browse(new URI("https://discord.gg/uYabqA4"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	 }

		
		if (e.getSource() == quitButton)
			System.exit(0);
		else if (e.getSource() == hideButton)
			LauncherFrame.getInstance().setState(1);
		else if (e.getSource() == this.ramButton)
			ramSelector.display();
	}
 
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }
 
    private void setFieldsEnabled(boolean enabled)
    {
        usernameField.setEnabled(enabled);
        passwordField.setEnabled(enabled);
        playButton.setEnabled(enabled);
    }
 
    public SColoredBar getProgressBar()
    {
        return progressBar;
    }
 
    public void setInfoText(String text)
    {
        infoLabel.setText(text);
    }
 
    public RamSelector getRamSelector()
    {
        return ramSelector;
    }
}

