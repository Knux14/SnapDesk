package fr.knux14.snapdesk;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.JDialog;
import javax.swing.JPanel;

public class Popup extends JDialog {
	
	private static final long serialVersionUID = 1L;
	public static ArrayList<Popup> popupList = new ArrayList<>();
	public static Dimension screen;
	
	public Popup(String message) {
		setUndecorated(true);
		setSize(300, 50);
		setAlwaysOnTop(true);
		screen = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(screen.width - 305, 5 + 52 * popupList.size());
		setBackground(new Color(0, 0, 0, 0));
		add (new TextPanel(message));
		setVisible(true);
		ThreadKill tdkill = new ThreadKill(this);
		tdkill.start();
		popupList.add(this);
	}
	
}

class TextPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private String msg;
	
	public TextPanel(String msg) {
		setBackground(new Color(0, 0, 0, 0));
		this.msg = msg;
	}
	
	@Override
	public void paintComponent(Graphics g2) {
		Graphics2D g = (Graphics2D)g2;
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setColor(new Color(.7f, .7f, .7f, .8f));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.black);
		g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
		g.setFont(g.getFont().deriveFont(14f));
		g.setColor(Color.red);
		g.drawString("SnapDesk", 5, 15);
		g.setColor(Color.white);
		g.drawString(msg, 10, 32);
	}
}

class ThreadKill extends Thread {
	
	JDialog dial;
	
	public ThreadKill(JDialog dial) {
		this.dial = dial;
	}
	
	@Override
	public void run() {
		try {
			sleep(TimeUnit.SECONDS.toMillis(10));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		dial.setVisible(false);
		Popup.popupList.remove(dial);
		// When a popup is closed, the other are repositionned upper
		for (int x = 0; x < Popup.popupList.size(); x++) {
			Popup p = Popup.popupList.get(x);
			p.setLocation(Popup.screen.width - 305, 5 + 52 * x);
		}
	}
	
}
