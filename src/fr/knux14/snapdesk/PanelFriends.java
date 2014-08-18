package fr.knux14.snapdesk;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.habosa.javasnap.Friend;

public class PanelFriends extends JPanel {

	private static final long serialVersionUID = 1L;
	private MainFrame mf;
	private FriendPanel[] friends;
	private JList<FriendPanel> friendList;
	private JButton back, talk, story, options;
	
	public PanelFriends(final MainFrame mf) {
		this.mf = mf;
		friendList = new JList<>();
		friendList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				checkButtons();
			}
		});
		
		back = new JButton("Retour");
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mf.add(mf.home);
				mf.remove(PanelFriends.this);
				mf.revalidate();
				mf.repaint();
			}
		});
		
		talk = new JButton("Messages");
		story = new JButton("Story");
		options = new JButton("Modifier");
		
		setLayout(new BorderLayout());
		JPanel topPanel = new JPanel(new BorderLayout()), southPanel = new JPanel();
		topPanel.add(back, BorderLayout.CENTER);

		JScrollPane jsc = new JScrollPane(friendList);
		jsc.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		southPanel.add(talk);
		southPanel.add(story);
		southPanel.add(options);
		
		add(topPanel, BorderLayout.NORTH);
		add(jsc, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
		
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
