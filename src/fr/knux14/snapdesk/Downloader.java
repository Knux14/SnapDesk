package fr.knux14.snapdesk;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import com.habosa.javasnap.Snap;
import com.habosa.javasnap.Snapchat;
import com.habosa.javasnap.Story;

public class Downloader extends JFrame {

	private static final long serialVersionUID = 1L;
	
	// Non-GUI Stuff
	Story[] dlListStory;
	Snap[] dlListSnap;
	int totalFiles = 0;
	int currFile = 0;
	
	// Gui Stuff
	JProgressBar progress;
	private JLabel label;
	private JButton cancel;
	
	public Downloader(Story[] stories) {
        dlListStory = Story.filterDownloadable(stories);
        totalFiles = dlListStory.length;
		label = new JLabel(Resources.text.getString("Downloader.text1") + " " + dlListStory[0].getSender() + Resources.text.getString("Downloader.text2"), JLabel.LEFT);
		setup();
		setVisible(true);
		ThreadDownloadStory tds = new ThreadDownloadStory(this);
		tds.start();
	}

	public Downloader(Snap[] snaps) {
        dlListSnap = Snap.filterDownloadable(snaps);
        totalFiles = dlListSnap.length;
		label = new JLabel(Resources.text.getString("Downloader.text1") + " " + dlListSnap[0].getSender() + Resources.text.getString("Downloader.text2"), JLabel.LEFT);
		setup();
	}
	
	private void setup() {
		setTitle("Downloader");
		setSize(350, 110);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLayout(new BorderLayout());
		
		progress = new JProgressBar();
		cancel = new JButton("Annuler");
		
		progress.setMaximum(totalFiles);
		progress.setBorder(BorderFactory.createEmptyBorder(20, 5, 20, 5));
		
		add(label, BorderLayout.NORTH);
		add(progress, BorderLayout.CENTER);
		add(cancel, BorderLayout.SOUTH);
		
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
	}
	
}

class ThreadDownloadStory extends Thread {
	
	Downloader dl;
	
	ThreadDownloadStory(Downloader dl) {
		this.dl = dl;
	}
	
	@Override
	public void run() {
		for (Story s : dl.dlListStory) {
			String extension = ".jpg";
            if(!s.isImage()){
              extension = ".mp4";
            }
            byte[] storyBytes = Snapchat.getStory(s); 
            File storyFile = new File(Resources.getDownloadDir(), s.getSender() + "-" + s.getId() + extension);
            try {
                FileOutputStream storyOs = new FileOutputStream(storyFile);
				storyOs.write(storyBytes);
	            storyOs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
            dl.currFile++;
            dl.progress.setValue(dl.currFile);
		}
		dl.setVisible(false);
		Viewer v = new Viewer(dl.dlListStory);
		v.setVisible(true);
	}
	
}


class ThreadDownloadSnap extends Thread {
	
	Downloader dl;
	
	ThreadDownloadSnap(Downloader dl) {
		this.dl = dl;
	}
	
	@Override
	public void run() {
		for (Snap s : dl.dlListSnap) {
			String extension = ".jpg";
            if(!s.isImage()){
              extension = ".mp4";
            }
            byte[] storyBytes = MainFrame.instance.scAccount.getSnap(s); 
            File storyFile = new File(Resources.getDownloadDir(), s.getSender() + "-" + s.getId() + extension);
            System.out.println(storyFile.getAbsolutePath());
            try {
                FileOutputStream storyOs = new FileOutputStream(storyFile);
				storyOs.write(storyBytes);
	            storyOs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
            dl.currFile++;
            dl.progress.setValue(dl.currFile);
            MainFrame.instance.scAccount.setSnapFlags(s, true, false, false);
		}
		dl.setVisible(false);
		Viewer v = new Viewer(dl.dlListSnap);
		v.setVisible(true);
	}
	
}