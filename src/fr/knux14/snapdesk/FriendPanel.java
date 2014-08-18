package fr.knux14.snapdesk;

import java.awt.Dimension;
import javax.swing.JPanel;
import com.habosa.javasnap.Friend;

public class FriendPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Friend friend;

	/**
	 * Panel that displays in the friend list
	 * @param f friend
	 */
	public FriendPanel(Friend f) {
		this.friend = f;
		setMinimumSize(new Dimension(100, 40));
	}
	

}
