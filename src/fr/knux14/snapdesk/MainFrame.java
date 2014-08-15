package fr.knux14.snapdesk;

import javax.swing.JFrame;

import com.habosa.javasnap.Snapchat;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public Snapchat scAccount;
	
	public MainFrame(Snapchat sc) {
		setSize(300, 420); // Weed :D
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle (References.programFName);
		
		this.scAccount = sc;
	}
	
}
