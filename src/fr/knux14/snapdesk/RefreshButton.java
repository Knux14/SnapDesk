package fr.knux14.snapdesk;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class RefreshButton extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;
	private ActionListener act;
	private boolean loading;
	private Dimension size = new Dimension(32, 32);
	
	public RefreshButton(ActionListener act){
		this.act = act;
		this.loading = false;
		setSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
	}

	public void setLoading(boolean loading) {
		this.loading = loading;
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		g.drawImage(loading ? Resources.loading : Resources.refresh, 0, 0, 32, 32, null);
	}

	@Override
	public void mouseClicked(MouseEvent e) { }

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }

	@Override
	public void mousePressed(MouseEvent e) { }

	@Override
	public void mouseReleased(MouseEvent e) {
		act.actionPerformed(null);
		setLoading(!loading);				
	}
}
