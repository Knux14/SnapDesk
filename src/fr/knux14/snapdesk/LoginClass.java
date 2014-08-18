package fr.knux14.snapdesk;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.habosa.javasnap.Snapchat;

public class LoginClass extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * First class to show up, allow user to choose between multiples accounts
	 */
	public LoginClass() {
		setSize(400, 450);
		setResizable(false);
		setTitle(Resources.programFName + " - Connexion");
		setDefaultCloseOperation(3);
		setLocationRelativeTo(null);
		add(new MainLoginPanel(this));
	}

}

class MainLoginPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public JFrame loginFrame;
	private JList<String> userList;
	private JButton connect, add, rem;

	public MainLoginPanel(final JFrame loginFrame) {
		this.loginFrame = loginFrame;
		userList = new JList<>();
		connect = new JButton("Connexion");
		add = new JButton("Ajouter");
		rem = new JButton("Supprimer");
		setLayout(new BorderLayout());

		JPanel panelBoutons = new JPanel();
		panelBoutons.add(add);
		panelBoutons.add(connect);
		panelBoutons.add(rem);

		add(new JLabel("Choissez un utilisateur pour se connecter"),
				BorderLayout.NORTH);
		add(new JScrollPane(userList), BorderLayout.CENTER);
		add(panelBoutons, BorderLayout.SOUTH);

		setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

		userList.setFixedCellHeight(44);
		DefaultListCellRenderer centerRenderer = new DefaultListCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		userList.setCellRenderer(centerRenderer);
		userList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				connect.setEnabled(true);
				rem.setEnabled(true);
			}
		});

		updateList();

		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddAccountDialog add = new AddAccountDialog(MainLoginPanel.this, 1);
				add.setVisible(true);
			}
		});

		rem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showConfirmDialog(
						MainLoginPanel.this.loginFrame,
						"Vous allez supprimer le compte "
								+ userList.getSelectedValue()
								+ " de SnapDesk.\n tes vous sur ?",
						"Suppression de compte", JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					SaveManager.userList.remove(userList.getSelectedValue());
					SaveManager.saveUsernames();
					updateList();
				}
			}
		});
		
		connect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				connect.setEnabled(false);
				String user = userList.getSelectedValue();
				Snapchat sc = Snapchat.loginByToken(user, SaveManager.getUser(user));
				if (sc != null && sc.authToken != null && !sc.authToken.isEmpty()) {
					loginFrame.setVisible(false);
					MainFrame mf = new MainFrame(sc);
					mf.setVisible(true);
				} else {
					AddAccountDialog aad = new AddAccountDialog(MainLoginPanel.this, user, 2);
					aad.setVisible(true);
				}
			}
		});

		connect.setEnabled(false);
		rem.setEnabled(false);
	}

	/**
	 * Refresh the users list based on the file
	 */
	public void updateList() {
		Object[] userListObject = SaveManager.userList.keySet().toArray();
		String[] userList = Arrays.copyOf(userListObject,
				userListObject.length, String[].class);
		Arrays.sort(userList);
		this.userList.setListData(userList);
		if (userList.length == 0) {
			connect.setEnabled(false);
			rem.setEnabled(false);
		}
	}
}