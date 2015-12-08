import java.awt.EventQueue;

import javax.swing.JFrame;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JTextPane;

import bissnes.Application;
import bissnes.Logger;

public class Login {

	private JFrame frmUtlab;
	private JTextField textLogin;
	private JTextField textPassword;
	private JTextPane textPaneLoginError;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frmUtlab.setVisible(true);
					Application app = Application.getInstance();					
					if (app.isAuth()) {
						MainFrame frmMain = new MainFrame();
						frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						frmMain.setVisible(true);
						window.frmUtlab.setVisible(false);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmUtlab = new JFrame();
		frmUtlab.setResizable(false);
		frmUtlab.setTitle("UTLAB: Авторизация");
		frmUtlab.setBounds(100, 100, 450, 300);
		frmUtlab.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton btnLogin = new JButton("Войти");
		btnLogin.setBounds(166, 126, 89, 23);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textPaneLoginError.setVisible(false);
				if (textLogin.getText().equals("")) {
					textPaneLoginError.setText("Ошибка: Введите логин.");
					textPaneLoginError.setVisible(true);
				} else if (textPassword.getText().equals("")) {
					textPaneLoginError.setText("Ошибка: Введите пароль.");
					textPaneLoginError.setVisible(true);
				} else {
					btnLogin.setEnabled(false);
					Application app = Application.getInstance();					
					if (app.auth(textLogin.getText(), textPassword.getText())) {						
						MainFrame frmMain = new MainFrame();
						frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						frmMain.setVisible(true);
						frmUtlab.setVisible(false);
					} else {
						btnLogin.setEnabled(true);
						
						Logger l= Logger.getInstance();
						String msg=l.getMessage();
						textPaneLoginError.setText("Неверно. "+msg);
						textPaneLoginError.setVisible(true);
						
					}

				}

			}
		});
		frmUtlab.getContentPane().setLayout(null);
		frmUtlab.getContentPane().add(btnLogin);

		JLabel lblNewLabel = new JLabel("Логин:");
		lblNewLabel.setBounds(10, 11, 46, 14);
		frmUtlab.getContentPane().add(lblNewLabel);

		textLogin = new JTextField();
		textLogin.setText("");
		textLogin.setBounds(10, 28, 414, 20);
		frmUtlab.getContentPane().add(textLogin);
		textLogin.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Пароль:");
		lblNewLabel_1.setBounds(10, 59, 114, 14);
		frmUtlab.getContentPane().add(lblNewLabel_1);

		textPassword = new JTextField();
		textPassword.setText("");
		textPassword.setBounds(10, 77, 414, 20);
		frmUtlab.getContentPane().add(textPassword);
		textPassword.setColumns(10);

		textPaneLoginError = new JTextPane();
		textPaneLoginError.setForeground(Color.RED);
		textPaneLoginError.setEditable(false);
		textPaneLoginError.setText("Ошибка");
		textPaneLoginError.setBounds(10, 162, 414, 89);
		textPaneLoginError.setVisible(false);
		frmUtlab.getContentPane().add(textPaneLoginError);
	}
}
