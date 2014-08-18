package fr.knux14.snapdesk;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Resources {

	/**
	 * Kind of Pahimar's Reference class
	 */
	
	public static final String programName  = "jSnapDesk",
							   programVers  = "InDev 0.1",
							   programFName = programName + " " + programVers;
	
	public static BufferedImage loading, refresh;
	public static Color selectedColorBg, selectedColorFg, unselectedColorBg, unselectedColorFg;
	
	public static void load() {
		try {
			loading = ImageIO.read(Resources.class.getResourceAsStream("res/loading.gif"));
			refresh = ImageIO.read(Resources.class.getResourceAsStream("res/refresh.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static File getHomeDir() {
		File userHome = new File(System.getProperty("user.home"));
		File home = new File(userHome, "SnapDesk");
		if(!home.exists()) home.mkdirs();
		return home;
	}
	
}
