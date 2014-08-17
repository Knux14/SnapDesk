package fr.knux14.snapdesk;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.habosa.javasnap.Snapchat;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public Snapchat scAccount;
	private JPanel insidePanel, btmPanel;
	private JLabel topLabel, snapLabel, storyLabel;
	private JButton takePicture, seeSnaps, seeStory, configuration;
	private RefreshButton bt;
	
	public MainFrame(Snapchat sc) {
		setSize(300, 420); // Weed :D
		setResizable(false);
		setLocationRelativeTo(null);
		setTitle (Resources.programFName);
		setDefaultCloseOperation(3);
		
		this.scAccount = sc;
		
		setLayout(new BorderLayout());
		
		topLabel = new JLabel("Connecté en tant que " + sc.username);
		topLabel.setHorizontalAlignment(JLabel.CENTER);
		
		insidePanel = new JPanel();
		insidePanel.setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		
		takePicture = new JButton("Envoyer un snap");
		g.fill = GridBagConstraints.HORIZONTAL;
		g.gridwidth = 3;
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
		g.gridx = 0;
		g.gridy = 5;
		g.anchor = GridBagConstraints.LAST_LINE_START;
		insidePanel.add(snapLabel, g);
		
		bt = new RefreshButton(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// @TODO
			}
		});
		g.gridx = 1;
		g.gridy = 6;
		g.anchor = GridBagConstraints.PAGE_END;
		insidePanel.add(bt, g);
		
		storyLabel = new JLabel("0 nouvelles histoires", JLabel.RIGHT);
		g.gridx = 2;
		g.gridy = 7;
		g.anchor = GridBagConstraints.LAST_LINE_END;
		insidePanel.add(storyLabel, g);		
		
//		btmPanel = new JPanel();
		/*btmPanel.setLayout(new BorderLayout());
		JPanel snapLabelPanel = new JPanel(new BorderLayout());
		snapLabelPanel.add(snapLabel, BorderLayout.SOUTH);
		btmPanel.add(snapLabelPanel, BorderLayout.WEST);
		JPanel lolpan = new JPanel();
		lolpan.add(bt);
		btmPanel.add(lolpan);
		JPanel storyLabelPanel = new JPanel(new BorderLayout());
		storyLabelPanel.add(storyLabel, BorderLayout.SOUTH);
		btmPanel.add(storyLabelPanel, BorderLayout.EAST);
		*/
		add(topLabel, BorderLayout.NORTH);
		add(insidePanel, BorderLayout.CENTER);
		//add(btmPanel, BorderLayout.SOUTH);
		
	}
	
}
