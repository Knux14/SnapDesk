package fr.knux14.snapdesk;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.habosa.javasnap.Snap;
import com.habosa.javasnap.Story;

public class Viewer extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private Story[] stories;
	private Snap[] snaps;
	
	public int type = 0;
	public int total = -1;
	public int last = -1;
	
	public JPanel currentPanel;
	
	public Viewer (Snap[] snaps) {
		this();
		type = 1;
		this.total = snaps.length;
		this.snaps = snaps;
		goToNext();
	}
	
	public Viewer (Story[] stories) {
		this();
		type = 2;
		this.total = stories.length;
		this.stories = stories;
		goToNext();
	}
	
	private Viewer() {
		setSize(800, 600);
		setTitle("SnapViewer");
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.LINE_AXIS));
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}

	public void goToNext() {
		last++;
		if (last < (type == 1 ? snaps.length : (type == 2 ? stories.length : 0))) {
			if (currentPanel != null) remove(currentPanel);
			try {
				if (type == 1 && snaps[last].isImage()) {
					MainFrame.instance.scAccount.setSnapFlags(snaps[last], true, false, false);
				} else if (type == 2 && stories[last].isImage()) {
					MainFrame.instance.scAccount.setSnapFlags(stories[last], true, false, false);
				}
				add(currentPanel = (type == 1 ? (snaps[last].isImage() ? new PictureSnapPanel(this, snaps[last]) : new VideoSnapPanel(getSize(), snaps[last])) : type == 2 ? (stories[last].isImage() ? new PictureStoryPanel(this, stories[last]) : new VideoStoryPanel(getSize(), stories[last])) : null));
				
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			revalidate();
			repaint();
		} else {
			setVisible(false);
		}
	}
	
}

class PicturePanel extends JPanel {

	private Viewer view;
	private static final long serialVersionUID = 1L;
	protected Dimension bgSize;
	protected BufferedImage background;
	public int time = 0;
	int ratio = 0;
	
	public PicturePanel(Viewer v, BufferedImage image, int seconds) {
		this.view = v;
		this.background = image;
		this.bgSize = v.getSize();
		setBackground(Color.black);
		setMinimumSize(bgSize);
		
		ratio = image.getWidth() / image.getHeight();
		
		new ThreadTimer(this, v, seconds).start();
	}
	
	public void paintComponent(Graphics g) {
		int posY = 0, h = getHeight(), w = h * ratio, posX = getWidth() / 2 - w / 2;
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(background, posX, posY, w, h, null);
		g.setColor(Color.white);
		g.setFont(g.getFont().deriveFont(15f));
		g.drawString(time + " - " + view.last+1 + "/" + view.total, 20, 20);
	}
}

class PictureSnapPanel extends PicturePanel {

	private static final long serialVersionUID = 1L;
	
	public PictureSnapPanel(Viewer v, Snap snap) throws IOException {
		super(v, ImageIO.read(new File(Resources.getDownloadDir(), snap.getSender() + "-" + snap.getId() + ".jpg")), snap.getTime());
	}
	
}

class PictureStoryPanel extends PicturePanel {

	private static final long serialVersionUID = 1L;
	
	public PictureStoryPanel(Viewer v, Story story) throws IOException {
		super(v, ImageIO.read(new File(Resources.getDownloadDir(), story.getSender() + "-" + story.getId() + ".jpg")), story.getTime());
	}
	
}

class VideoSnapPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public VideoSnapPanel(Dimension winSize, Snap snap) {
		
	}
	
}

class VideoStoryPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public VideoStoryPanel(Dimension winSize, Story story) {

	}
	
}

class ThreadTimer extends Thread {
	
	Viewer view;
	int seconds;
	PicturePanel pan;
	
	public ThreadTimer(PicturePanel pp, Viewer v, int seconds) {
		this.pan = pp;
		this.view = v;
		this.seconds = seconds;
	}
	
	public void run() {
		for (int x = seconds; x != 0; x--){
			try {
				pan.time = x;
				pan.repaint();
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		view.goToNext();
	}
	
}
