package fr.knux14.snapdesk;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import com.habosa.javasnap.Friend;
import com.habosa.javasnap.Story;

public class FriendPanel extends JPanel implements ListCellRenderer<FriendPanel> {

	private static final long serialVersionUID = 1L;
	private Friend friend;
	public boolean isSelected = false;
	public ArrayList<Story> stories;
	public boolean storySeen = true;
	
	/**
	 * Panel that displays in the friend list
	 * @param f friend
	 */
	public FriendPanel(Friend f) {
		this.friend = f;
		Dimension dim = new Dimension(100, 70);
		setMinimumSize(dim);
		setPreferredSize(dim);
		setSize(dim);
		stories = new ArrayList<Story>();
	}
	
	// So that we can make the list renderer as is w/out actually using a friend
	public FriendPanel() {}
	
	@Override
	public void paintComponent(Graphics g2) {
		Graphics2D g = (Graphics2D)g2;
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		if (isSelected) {
			g.setColor(Resources.selectedColorBg);
			g.fillRect(0, 0, getWidth(), getHeight());
		} else {
			g.setColor(Resources.unselectedColorBg);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		g.setColor(Color.black);
		g.setFont(g.getFont().deriveFont(15f));
		g.drawString(friend.getDisplayName().isEmpty() ? friend.getUsername() : friend.getDisplayName(), 5, 22);
		g.setFont(g.getFont().deriveFont(10f));
		g.drawString(stories.size() + " " + Resources.text.getString("Friends.story"), 9, 44);
		
		g.setColor(Color.gray);
		g.drawString(friend.getUsername(), 9, 34);
		
		if (!storySeen){
			g.setColor(Color.red);
			g.drawString(Resources.text.getString("Friends.newStory"), 9, 54);
		}
	}

	/**
	 * Update the list of stories
	 */
	public void updateStories() {
		stories.clear();
		storySeen = true;
		for (Story s : MainFrame.instance.scAccount.getStories()) {
			if (s.getSender().equals(friend.getUsername())) {
				if (!s.isViewed()) storySeen = false;
				stories.add(s);
			}
		}
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends FriendPanel> list, FriendPanel arg1, int arg2, boolean isSelected, boolean arg4) {
		return arg1;
	}
}
