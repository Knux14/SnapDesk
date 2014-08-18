package fr.knux14.snapdesk;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.habosa.javasnap.Snapchat;

public class AddAccountDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	int action = 0;
	private JLabel message;
	private JTextField usernameField, passField;
	private JButton connectBt;
	private String textNormal = Resources.text.getString("AddAccount.connect");
	private String textRetry = Resources.text.getString("AddAccount.connect2");
	private MainLoginPanel panel;

	/**
	 * Allow to use it so that when user's token has changed, he can reconnect
	 * @param pan MainLoginPanel
	 * @param lastUser Username
	 * @param action Choose between connection and retry
	 */
	public AddAccountDialog(MainLoginPanel pan, String lastUser, int action) {
		this(pan, action);
		usernameField.setText(lastUser);
	}
	
	public AddAccountDialog(MainLoginPanel pan, int action) {
		setSize(400, 150);
		setTitle(Resources.text.getString("AddAccount.title"));

		panel = pan;
		this.action = action;

		usernameField = new JTextField();
		passField = new JPasswordField();
		connectBt = new JButton(Resources.text.getString("AddAccount.connectBt"));
		connectBt.addActionListener(this);

		JPanel insidePanel = new JPanel(new BorderLayout());
		message = new JLabel(action == 2 ? textRetry : textNormal);
		insidePanel.add(message, BorderLayout.NORTH);

		JPanel fields = new JPanel();
		GridLayout gl = new GridLayout(0, 2);
		gl.setVgap(5);
		fields.setLayout(gl);
		JLabel userLab = new JLabel(Resources.text.getString("AddAccount.username") + ": ");
		JLabel passLab = new JLabel(Resources.text.getString("AddAccount.password") + ": ");
		fields.add(userLab);
		fields.add(usernameField);
		fields.add(passLab);
		fields.add(passField);

		fields.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		insidePanel.add(fields, BorderLayout.CENTER);
		insidePanel.add(connectBt, BorderLayout.SOUTH);
		insidePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		add(insidePanel);
		setResizable(false);
		//pack();
		validate();
		setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		connectBt.setEnabled(false);
		if (action == 1) {
			// Action 1: Adding user
			Snapchat sc = Snapchat.login(usernameField.getText(), passField.getText());
			if (sc != null && sc.authToken != null && !sc.authToken.isEmpty()) {
				/**
				 * @TODO Ask to overwrite or not
				 */
				SaveManager.addUser(usernameField.getText(), sc.getAuthToken(), true);
				SaveManager.saveUsernames();
				panel.updateList();
				setVisible(false);
			} else {
				passField.setText("");
				message.setText(textRetry);
				pack();
				revalidate();
				setLocationRelativeTo(null);
				connectBt.setEnabled(true);
			}
		} else if(action == 2) {
			// Action 2: Revalidating a token
			Snapchat sc = Snapchat.login(usernameField.getText(), passField.getText());
			if (sc != null && sc.authToken != null && !sc.authToken.isEmpty()) {
				SaveManager.addUser(usernameField.getText(), sc.getAuthToken(), true);
				SaveManager.saveUsernames();
				panel.updateList();
				setVisible(false);
				panel.loginFrame.setVisible(false);
				MainFrame mf = new MainFrame(sc);
				mf.setVisible(true);
			} else {
				passField.setText("");
				message.setText(textRetry);
				pack();
				revalidate();
				setLocationRelativeTo(null);
				connectBt.setEnabled(true);
			}
		}
	}

}