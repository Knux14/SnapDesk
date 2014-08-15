package fr.knux14.snapdesk;

import javax.swing.UIManager;

public class Main {

	public static void main(String args[]) {
		Resources.load();
		SaveManager.loadUsernames();
		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		new LoginClass().setVisible(true);
	}

}