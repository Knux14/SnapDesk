package fr.knux14.snapdesk;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.habosa.javasnap.Friend;
import com.habosa.javasnap.Story;

public class PanelFriends extends JPanel {

	private static final long serialVersionUID = 1L;
	private MainFrame mf;
	public FriendPanel[] friends;
	private JList<FriendPanel> friendList;
	private int lastSelected = -1;
	private JButton back, talk, story, options;
	
	public PanelFriends(final MainFrame mf) {
		this.mf = mf;
		friendList = new JList<>();
		Resources.selectedColorBg = friendList.getSelectionBackground();
		Resources.selectedColorFg = friendList.getSelectionForeground();
		Resources.unselectedColorBg = friendList.getBackground();
		Resources.unselectedColorFg = friendList.getForeground();
		friendList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				checkButtons();
				if (lastSelected != -1) {
					friends[lastSelected].isSelected = false;
					friends[lastSelected].repaint();
				}
				if(friendList.getSelectedValue() != null)
					friendList.getSelectedValue().isSelected = true;
				lastSelected = friendList.getSelectedIndex();
			}
		});
		friendList.setCellRenderer(new FriendPanel());
		setLayout(new BorderLayout());
		
		back = new JButton(Resources.text.getString("Main.back"));		
		talk = new JButton(Resources.text.getString("Friends.talk"));
		story = new JButton(Resources.text.getString("Friends.story"));
		options = new JButton(Resources.text.getString("Friends.modify"));
		JScrollPane jsc = new JScrollPane(friendList);
		
		JPanel topPanel = new JPanel(new BorderLayout()), southPanel = new JPanel();
		
		jsc.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		topPanel.add(back, BorderLayout.CENTER);
		southPanel.add(talk);
		southPanel.add(story);
		southPanel.add(options);
		
		add(topPanel, BorderLayout.NORTH);
		add(jsc, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);

		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mf.add(mf.home);
				mf.remove(PanelFriends.this);
				mf.revalidate();
				mf.repaint();
			}
		});
		
		story.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<Story> stories = friendList.getSelectedValue().stories;
				new Downloader(stories.toArray(new Story[stories.size()]));
			}
		});
		
		updateFriendPanelList();
		checkButtons();
	}

	/**
	 * Rebuild the list of panels from the Snapchat.class' list
	 */
	public void updateFriendPanelList() {
		Friend[] friendList = mf.scAccount.getFriends();
		friends = new FriendPanel[friendList.length];
		for (int x = 0; x < friendList.length; x++){
			friends[x] = new FriendPanel(friendList[x]);
		}
		updateList();
	}
	
	/**
	 * Apply the array to the JList
	 */
	public void updateList() {
		DefaultListModel<FriendPanel> model = new DefaultListModel<>();
		for (FriendPanel fp : friends) {
			model.addElement(fp);
		}
		friendList.setModel(model);
	}
	
	/**
	 * Check if there is a selection in the JList, and activate or desactivate the buttons
	 */
	public void checkButtons() {
		if (friendList.getSelectedIndex() == -1) {
			talk.setEnabled(false);
			story.setEnabled(false);
			options.setEnabled(false);
		} else {
			talk.setEnabled(true);
			story.setEnabled(true);
			options.setEnabled(true);
		}
	}
	
}
