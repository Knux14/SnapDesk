package fr.knux14.snapdesk;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.habosa.javasnap.Snap;
import com.habosa.javasnap.Snapchat;
import com.habosa.javasnap.Story;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

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
		setSize(420, 420); // Weed :D
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle (Resources.programFName);
		setDefaultCloseOperation(3);
		
		this.scAccount = sc;
		
		home = new JPanel();
		topPanel = new JPanel();
		insidePanel = new JPanel();
		btmPanel = new JPanel();
		
		home.add(topPanel);
		home.add(insidePanel);
		home.add(btmPanel);
		
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
		JLabel lspace = new JLabel("        ");
		JLabel rspace = new JLabel("           ");
		storyLabel = new JLabel();
		refreshbtn = new RefreshButton(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/**
				 * @TODO Télécharger les snaps, histoire & tout
				 */
			}
		});
	
		updateLabels();
		
		GroupLayout topLayout = new GroupLayout(topPanel);
		topPanel.setLayout(topLayout);
		topLayout.setAutoCreateGaps(true);
		topLayout.setAutoCreateContainerGaps(true);
		topLayout.setHorizontalGroup(
				topLayout.createSequentialGroup()
				.addComponent(topLabel));
		topLayout.setVerticalGroup(
				topLayout.createSequentialGroup()
				.addComponent(topLabel, 120, 120, 120));
		
		GroupLayout inLayout = new GroupLayout(insidePanel);
		insidePanel.setLayout(inLayout);
		inLayout.setAutoCreateGaps(true);
		inLayout.setAutoCreateContainerGaps(true);
		inLayout.setHorizontalGroup(
				inLayout.createSequentialGroup()
				.addGroup(inLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(takePicture, 300, 300, 300)
						.addComponent(seeSnaps, 300, 300, 300)
						.addComponent(seeStory, 300, 300, 300)
						.addComponent(configuration, 300, 300, 300))
				);
		inLayout.setVerticalGroup(
				inLayout.createSequentialGroup()
				.addGroup(inLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(takePicture))
				.addGroup(inLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(seeSnaps))
				.addGroup(inLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(seeStory))
				.addGroup(inLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(configuration))
				);
		
		GroupLayout btmLayout = new GroupLayout(btmPanel);
		btmPanel.setLayout(btmLayout);
		btmLayout.setAutoCreateGaps(true);
		btmLayout.setAutoCreateContainerGaps(true);
		btmLayout.setHorizontalGroup(
				btmLayout.createSequentialGroup()
				.addGroup(btmLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(snapLabel))
				.addGroup(btmLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(lspace))
				.addGroup(btmLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(refreshbtn, 32, 32, 32))
				.addGroup(btmLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(rspace))
				.addGroup(btmLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addComponent(storyLabel))
				);
		btmLayout.linkSize(SwingConstants.HORIZONTAL, snapLabel, storyLabel);
		btmLayout.setVerticalGroup(
				btmLayout.createSequentialGroup()
				.addGroup(btmLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(snapLabel)
						.addComponent(lspace)
						.addComponent(refreshbtn)
						.addComponent(rspace)
						.addComponent(storyLabel))
				);
		
		GroupLayout layout = new GroupLayout(home);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
						.addComponent(topPanel)
						.addComponent(insidePanel)
						.addComponent(btmPanel))
				);
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(topPanel))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(insidePanel))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(btmPanel))
				);
		
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
	
}