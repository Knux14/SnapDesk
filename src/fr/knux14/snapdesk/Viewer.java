package fr.knux14.snapdesk;

import java.io.File;

import javax.swing.JFrame;

public class Viewer extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * The SnapViewer
	 * @param files array of snaps and videos
	 */
	public Viewer (File[] files) {
		setSize(500, 300);
		setTitle("SnapViewer");
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}
	
}
