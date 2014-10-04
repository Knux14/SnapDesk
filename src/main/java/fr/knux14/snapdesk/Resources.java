package fr.knux14.snapdesk;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class Resources {

	/**
	 * Kind of Pahimar's Reference class
	 */
	
	public static final String programName  = "jSnapDesk",
							   programVers  = "InDev 0.1",
							   programFName = programName + " " + programVers;

    private static File configFile = new File(getHomeDir(), "config.properties");
	public static BufferedImage loading, refresh;
	public static Color selectedColorBg, selectedColorFg, unselectedColorBg, unselectedColorFg;
	public static ResourceBundle text;
	public static Properties props;

	public static void load() {
			Locale langue = new Locale(props.getProperty("LanguageCode", "en"), props.getProperty("CountryCode", "US"));
			text = ResourceBundle.getBundle("TranslateDesk", langue);
            saveConfig();
	}
	
	public static File getHomeDir() {
		File userHome = new File(System.getProperty("user.home"));
		File home = new File(userHome, "SnapDesk");
		if(!home.exists()) home.mkdirs();
		return home;
	}

    public static void loadConfig() {
        try {
            props = new Properties();
            props.load(new FileInputStream(configFile));
        } catch (IOException e) {
            props = new Properties();
            props.put("LanguageCode", "en");
            props.put("CountryCode", "US");
            e.printStackTrace();
        }
    }

    public static void saveConfig() {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(configFile);
            props.store(fos, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

	public static File getDownloadDir() {
		return new File(getHomeDir(), "download");
	}
	
}
