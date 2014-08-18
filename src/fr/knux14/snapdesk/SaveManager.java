package fr.knux14.snapdesk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

public class SaveManager {

	public static HashMap<String, String> userList = new HashMap<>();

	// Where will be saved the username and the tokens
	private static final File usernameFile = new File(Resources.getHomeDir(), "lastlogin");
	private static boolean hasRead = false;
	
	public static String getUser(String username) {
		return userList.get(username);
	}
	
	public static void addUser(String username, String token, boolean overwrite) {
		if (!userList.containsKey(username))
			userList.put(username, token);
		if (userList.containsKey(username) && overwrite) {
			userList.remove(username);
			userList.put(username, token);
		}
	}
	
	public static void loadUsernames() {
		hasRead = true;
		if (!usernameFile.exists()) return;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(usernameFile));
			String ligne = "";
			while ((ligne = br.readLine()) != null) {
				String username = ligne.substring(0, ligne.lastIndexOf(":"));
				String token = ligne.substring(ligne.lastIndexOf(":") + 1, ligne.length());
				userList.put(username, token);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void saveUsernames() {
		if (hasRead) {
			BufferedWriter bw = null;
			try {
				bw = new BufferedWriter(new FileWriter(usernameFile));
				for (Entry<String, String> set : userList.entrySet()) {
					bw.write(set.getKey() + ":" + set.getValue() + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (bw != null)
					try {
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
		}
	}
	
}
