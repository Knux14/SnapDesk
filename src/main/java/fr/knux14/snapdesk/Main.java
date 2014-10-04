package fr.knux14.snapdesk;

import javax.swing.UIManager;

public class Main {

	/**
	 * Nothin' too fancy, just setting LnF & starting the frame
	 * @param args
	 */
	public static void main(String args[]) {
        SecurityUtil.removeCryptographyRestrictions();
        Resources.loadConfig();
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
