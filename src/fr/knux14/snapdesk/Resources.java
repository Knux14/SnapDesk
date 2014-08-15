package fr.knux14.snapdesk;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Resources {

	public static final String programName  = "jSnapDesk",
							   programVers  = "InDev 0.1",
							   programFName = programName + " " + programVers;
	
	public static BufferedImage loading, refresh;
	
	public static void load() {
		try {
			loading = ImageIO.read(Resources.class.getResourceAsStream("res/loading.gif"));
			refresh = ImageIO.read(Resources.class.getResourceAsStream("res/refresh.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
