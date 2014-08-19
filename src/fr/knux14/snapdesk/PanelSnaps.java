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

import com.habosa.javasnap.Snap;

public class PanelSnaps extends JPanel {

	private static final long serialVersionUID = 1L;
	private MainFrame mf;
	public SnapPanel[] snaps;
	private JList<SnapPanel> snapList;
	private int lastSelected = -1;
	private JButton back, watch, save, resp;
	
	public PanelSnaps(final MainFrame mf) {
		this.mf = mf;
		snapList = new JList<>();
		/* Just in case that PanelFriend wasn't loaded*/
		Resources.selectedColorBg = snapList.getSelectionBackground();
		Resources.selectedColorFg = snapList.getSelectionForeground();
		Resources.unselectedColorBg = snapList.getBackground();
		Resources.unselectedColorFg = snapList.getForeground();
		snapList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				checkButtons();
				if (lastSelected != -1) {
					snaps[lastSelected].isSelected = false;
					snaps[lastSelected].repaint();
				}
				snapList.getSelectedValue().isSelected = true;
				lastSelected = snapList.getSelectedIndex();
			}
		});
		snapList.setCellRenderer(new SnapPanel());
		setLayout(new BorderLayout());
		
		back = new JButton(Resources.text.getString("Main.back"));		
		watch = new JButton(Resources.text.getString("Snaps.watch"));
		save = new JButton(Resources.text.getString("Snaps.save"));
		resp = new JButton(Resources.text.getString("Snaps.answer"));
		JScrollPane jsc = new JScrollPane(snapList);
		
		JPanel topPanel = new JPanel(new BorderLayout()), southPanel = new JPanel();
		
		jsc.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		topPanel.add(back, BorderLayout.CENTER);
		southPanel.add(watch);
		southPanel.add(save);
		southPanel.add(resp);
		
		add(topPanel, BorderLayout.NORTH);
		add(jsc, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);

		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mf.add(mf.home);
				mf.remove(PanelSnaps.this);
				mf.revalidate();
				mf.repaint();
			}
		});
		
		watch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Snap[] snaps = new Snap[1];
				snaps[0] = snapList.getSelectedValue().snap;
				new Downloader(snaps);
			}
		});
		
		updateSnapPanelList();
		checkButtons();
	}

	/**
	 * Rebuild the list of panels from the Snapchat.class' list
	 */
	public void updateSnapPanelList() {
		Snap[] snapList = mf.scAccount.getSnaps();
		ArrayList<SnapPanel> snapListTemp = new ArrayList<>();
		for (int x = 0; x < snapList.length; x++) {
			Snap s = snapList[x];
			if (s.getSender() != null) {
				snapListTemp.add(new SnapPanel(mf.scAccount.getFriend(s.getSender()), s));
			}
		}
		SnapPanel snap[] = {new SnapPanel(null, null)};
		snaps = snapListTemp.toArray(snap);//new SnapPanel[snapListTemp.size()];
		updateList();
	}
	
	/**
	 * Apply the array to the JList
	 */
	public void updateList() {
		DefaultListModel<SnapPanel> model = new DefaultListModel<>();
		for (SnapPanel sp : snaps) {
			model.addElement(sp);
		}
		snapList.setModel(model);
	}
	
	/**
	 * Check if there is a selection in the JList, and activate or desactivate the buttons
	 */
	public void checkButtons() {
		if (snapList.getSelectedIndex() == -1) {
			watch.setEnabled(false);
			save.setEnabled(false);
			resp.setEnabled(false);
		} else {
			if (snapList.getSelectedValue().snap.isViewed()) {
				watch.setEnabled(false);
				save.setEnabled(false);
				resp.setEnabled(false);				
			} else {
				watch.setEnabled(true);
				save.setEnabled(true);
				resp.setEnabled(true);				
			}
		}
	}
	
}
