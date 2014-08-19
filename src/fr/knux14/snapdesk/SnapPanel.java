package fr.knux14.snapdesk;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import com.habosa.javasnap.Friend;
import com.habosa.javasnap.Snap;

public class SnapPanel extends JPanel implements ListCellRenderer<SnapPanel> {

	private static final long serialVersionUID = 1L;
	private Friend friend;
	public Snap snap;
	public boolean isSelected = false;
	public boolean snapSeen = true;
	
	/**
	 * Panel that displays in the friend list
	 * @param f friend
	 */
	public SnapPanel(Friend f, Snap s) {
		this.friend = f;
		Dimension dim = new Dimension(100, 70);
		setMinimumSize(dim);
		setPreferredSize(dim);
		setSize(dim);
		this.snap = s;
	}
	
	// So that we can make the list renderer as is w/out actually using a friend
	public SnapPanel() {}
	
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
		if (friend != null)
			g.drawString(friend.getDisplayName().isEmpty() ? friend.getUsername() : friend.getDisplayName(), 5, 22);
		else 
			g.drawString(snap.getSender(), 5, 22);
		g.setFont(g.getFont().deriveFont(10f));
		String type = snap.isImage() ? Resources.text.getString("Snap.image") : snap.isVideo() ? Resources.text.getString("Snap.video") : Resources.text.getString("Snap.other");
		g.drawString(type, 9, 34);
		
		if (!snapSeen){
			g.setColor(Color.red);
			g.drawString(Resources.text.getString("Snaps.newSnap"), 9, 44);
		}
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends SnapPanel> list, SnapPanel arg1, int arg2, boolean isSelected, boolean arg4) {
		return arg1;
	}
}
