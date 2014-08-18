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
	private JPanel topPanel, insidePanel, btmPanel;
	private PanelFriends panelFriends;
	private JLabel topLabel, snapLabel, storyLabel;
	private JButton takePicture, seeSnaps, seeStory, configuration;
	private RefreshButton refreshbtn;
	
	public MainFrame(Snapchat sc) {
		instance = this;
		setSize(420, 420); // Weed :D
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle (Resources.programFName);
		setDefaultCloseOperation(3);
		
		this.scAccount = sc;
		
		home = new JPanel(new BorderLayout());
		topPanel = new JPanel();
		insidePanel = new JPanel();
		btmPanel = new JPanel();
		
		topLabel = new JLabel("Connecté en tant que " + sc.username);
		
		takePicture = new JButton("Envoyer un snap");
		seeSnaps = new JButton("Mes snaps reçus");
		seeStory = new JButton("Amis et histoires");
		seeStory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (panelFriends == null) panelFriends = new PanelFriends(MainFrame.this);
				MainFrame.this.add(panelFriends);
				MainFrame.this.remove(home);
				panelFriends.checkButtons();
				MainFrame.this.revalidate();
				MainFrame.this.repaint();
			}
		});
		configuration = new JButton("Configuration");
		
		snapLabel = new JLabel();
		storyLabel = new JLabel();
		refreshbtn = new RefreshButton(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (scAccount.refresh()) {
					refresh();
				}
			}
		});
	
		updateLabels();
		
		topPanel.add(topLabel);
		
		insidePanel.setLayout(new GridBagLayout());
		
		GridBagConstraints g = new GridBagConstraints();
		
		 takePicture = new JButton("Envoyer un snap");
	        g.fill = GridBagConstraints.HORIZONTAL;
	        g.gridx = 1;
	        g.gridy = 0;
	        g.ipady = 40;
	        insidePanel.add(takePicture, g);
	        
	        seeSnaps = new JButton("Mes snaps reçus");
	        g.fill = GridBagConstraints.HORIZONTAL;
	        g.gridx = 1;
	        g.gridy = 2;
	        g.ipady = 0;
	        g.weighty = 1.0;
	        insidePanel.add(seeSnaps, g);
	        
	        seeStory = new JButton("Amis et histoires");
	        g.fill = GridBagConstraints.HORIZONTAL;
	        g.gridx = 1;
	        g.gridy = 3;
	        insidePanel.add(seeStory, g);
	        
	        configuration = new JButton("Configuration");
	        g.fill = GridBagConstraints.HORIZONTAL;
	        g.gridx = 1;
	        g.gridy = 4;
	        g.weighty = 1.0;
	        g.anchor = GridBagConstraints.PAGE_END;
	        insidePanel.add(configuration, g);
		
		snapLabel = new JLabel("0 nouveaux snaps", JLabel.LEFT);
		g.fill = GridBagConstraints.HORIZONTAL;
		g.weightx = 0.5;
		g.gridx = 0;
		g.gridy = 5;
		g.anchor = GridBagConstraints.LAST_LINE_START;
		insidePanel.add(snapLabel, g);
		
		JButton bt = new JButton("R");
		g.gridx = 1;
		g.gridy = 5;
		g.fill = GridBagConstraints.NONE;
		g.weightx = 0.0;
		g.anchor = GridBagConstraints.PAGE_END;
		insidePanel.add(bt, g);
		
		storyLabel = new JLabel("0 nouvelles histoires", JLabel.RIGHT);
		g.gridx = 2;
		g.gridy = 5;
		g.fill = GridBagConstraints.HORIZONTAL;
		g.weightx = 0.5;
		g.anchor = GridBagConstraints.LAST_LINE_END;
		insidePanel.add(storyLabel, g);
		
		home.add(topPanel, BorderLayout.NORTH);
		home.add(insidePanel, BorderLayout.CENTER);
		
		add(home);
		
		// This thread will every minutes check for new snaps, friends and messages 
		autoUpdate = new ThreadAutoUpdate(this);
		autoUpdate.start();
	}

	// Quick function that update the Snap number and Story number on the main panel
	public void updateLabels() {
		int x = Snap.filterDownloadable(scAccount.getSnaps()).length;
		snapLabel.setText(x + " nouveaux snaps");
		x = 0;
		for (Story s : scAccount.getStories()) {
			if (!s.isViewed()) x++;
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
		if (panelFriends != null) panelFriends.updateFriendPanelList();
		
		// Update received snaps and messages
		
		// Update friends story
	}
	
}