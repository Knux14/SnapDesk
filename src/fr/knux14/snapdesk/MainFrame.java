package fr.knux14.snapdesk;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.habosa.javasnap.Snap;
import com.habosa.javasnap.Snapchat;
import com.habosa.javasnap.Story;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public static MainFrame instance;
	public Snapchat scAccount;
	public boolean update = true;
	private ThreadAutoUpdate autoUpdate;
	public JPanel home;
	private JPanel topPanel, insidePanel;
	public PanelFriends panelFriends;
	private JLabel topLabel, snapLabel, storyLabel;
	private JButton takePicture, seeSnaps, seeStory, configuration,
			refreshButton;

	public MainFrame(Snapchat sc) {
		instance = this;
		setSize(350, 420); // Weed :D
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle(Resources.programFName);
		setDefaultCloseOperation(3);

		this.scAccount = sc;

		// Main Panels
		home = new JPanel(new BorderLayout());
		panelFriends = new PanelFriends(MainFrame.this);

		// Sub-panels for Main Menu
		topPanel = new JPanel(); // => "Connected as ... "
		insidePanel = new JPanel(); // Everything under topPanel

		topLabel = new JLabel("Connecté en tant que " + sc.username);
		takePicture = new JButton("Envoyer un snap");
		seeSnaps = new JButton("Mes snaps reçus");
		seeStory = new JButton("Amis et histoires");
		configuration = new JButton("Configuration");
		snapLabel = new JLabel("0 nouveaux snaps", JLabel.LEFT);
		refreshButton = new JButton("Actualiser");
		storyLabel = new JLabel("0 nouvelles histoires", JLabel.RIGHT);

		topPanel.add(topLabel);

		GridBagConstraints g = new GridBagConstraints();
		insidePanel.setLayout(new GridBagLayout());

		// Take a picture button
		g.fill = GridBagConstraints.HORIZONTAL;
		g.gridx = 1;
		g.gridy = 0;
		g.ipady = 40;
		insidePanel.add(takePicture, g);

		// Received Snaps button
		g.fill = GridBagConstraints.HORIZONTAL;
		g.gridx = 1;
		g.gridy = 2;
		g.ipady = 0;
		g.weighty = 1.0;
		insidePanel.add(seeSnaps, g);

		// Friend / Stories button
		g.fill = GridBagConstraints.HORIZONTAL;
		g.gridx = 1;
		g.gridy = 3;
		insidePanel.add(seeStory, g);

		// Configuration button
		g.fill = GridBagConstraints.HORIZONTAL;
		g.gridx = 1;
		g.gridy = 4;
		g.weighty = 1.0;
		g.anchor = GridBagConstraints.PAGE_END;
		insidePanel.add(configuration, g);

		// New snap label
		g.fill = GridBagConstraints.HORIZONTAL;
		g.weightx = 0.5;
		g.gridx = 0;
		g.gridy = 5;
		g.anchor = GridBagConstraints.LAST_LINE_START;
		insidePanel.add(snapLabel, g);

		// Refresh button
		g.gridx = 1;
		g.gridy = 5;
		g.fill = GridBagConstraints.NONE;
		g.weightx = 0.0;
		g.anchor = GridBagConstraints.PAGE_END;
		insidePanel.add(refreshButton, g);

		// Story label
		g.gridx = 2;
		g.gridy = 5;
		g.fill = GridBagConstraints.HORIZONTAL;
		g.weightx = 0.5;
		g.anchor = GridBagConstraints.LAST_LINE_END;
		insidePanel.add(storyLabel, g);

		seeStory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainFrame.this.add(panelFriends);
				MainFrame.this.remove(home);
				panelFriends.checkButtons();
				MainFrame.this.revalidate();
				MainFrame.this.repaint();
			}
		});

		refreshButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (scAccount.refresh()) {
					refresh();
				}
			}
		});

		home.add(topPanel, BorderLayout.NORTH);
		home.add(insidePanel, BorderLayout.CENTER);

		add(home);

		// This thread will every minutes check for new snaps, friends and
		// messages
		autoUpdate = new ThreadAutoUpdate(this);
		autoUpdate.start();
	}

	// Quick function that update the Snap number and Story number on the main
	// panel
	public void updateLabels() {
		int x = Snap.filterDownloadable(scAccount.getSnaps()).length;
		snapLabel.setText(x + " nouveaux snaps");
		x = 0;
		for (Story s : scAccount.getStories()) {
			if (!s.isViewed())
				x++;
		}
		storyLabel.setText(x + " nouvelles histoires");
	}

	/**
	 * Refresh the whole Snapchat stuff
	 */
	public void refresh() {
		// Update the bottom labels
		updateLabels();

		// Update friends
		panelFriends.updateFriendPanelList();

		// Update received snaps and messages

		// Update friends story
		for (FriendPanel f : panelFriends.friends) {
			f.updateStories();
		}
	}

}