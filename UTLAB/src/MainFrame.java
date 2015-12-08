import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import bissnes.Application;
import bissnes.Logger;
import models.User;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import java.awt.ScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Rectangle;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea textAreaLog = new JTextArea();
	private Logger logger=Logger.getInstance();

	Timer timer = new Timer(1000, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			Logger l = Logger.getInstance();
			String msgs = textAreaLog.getText();
			String msg = l.getMessage();
			if (msg != null) {
				msg = "\r\n " + msg;
				textAreaLog.setText(msg+msgs);
			}
		}
	});

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
				Application app = Application.getInstance();
				User u = app.getAuthUser();
				if (u != null) {
					Logger l = Logger.getInstance();
					l.log("Hello "+u.login);

				}

			}
		});
		setResizable(false);
		setTitle("UTLAB:Главная");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 514, 300);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		mnFile.setBounds(new Rectangle(10, 0, 0, 0));
		mnFile.setSelectedIcon(new ImageIcon(MainFrame.class.getResource("/javax/swing/plaf/metal/icons/ocean/paletteClose.gif")));
		menuBar.add(mnFile);

		JMenuItem mntmClose = new JMenuItem("Close");
		mntmClose.setBounds(new Rectangle(20, 0, 0, 0));
		mntmClose.setSelectedIcon(new ImageIcon(MainFrame.class.getResource("/javax/swing/plaf/metal/icons/ocean/paletteClose.gif")));
		mnFile.add(mntmClose);
		mntmClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JMenu mnProjects = new JMenu("Projects");
		menuBar.add(mnProjects);

		JMenuItem mntmProjectsList = new JMenuItem("Projects list");
		mntmProjectsList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ProjectFrame projectFrame = new ProjectFrame();
				projectFrame.setVisible(true);
				logger.log("Open projects window.");
			}
		});
		mntmProjectsList.setIcon(new ImageIcon(
				MainFrame.class.getResource("/com/sun/javafx/scene/web/skin/AlignJustified_16x16_JFX.png")));
		mnProjects.add(mntmProjectsList);

		JMenuItem mntmClients = new JMenuItem("Clients");
		mntmClients.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ClientFrame clientsFrame = new ClientFrame();
				clientsFrame.setVisible(true);
				logger.log("Open clients window.");
			}
		});
		mnProjects.add(mntmClients);

		JMenuItem mntmSellers = new JMenuItem("Sellers");
		mnProjects.add(mntmSellers);

		JMenuItem mntmReports = new JMenuItem("Reports");
		mnProjects.add(mntmReports);

		JMenu mnTools = new JMenu("Tools");
		menuBar.add(mnTools);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmCheckforupdates = new JMenuItem("Check for updates");
		mnHelp.add(mntmCheckforupdates);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/*JOptionPane.showConfirmDialog((Component) null,"test",
				        "alert", JOptionPane.OK_CANCEL_OPTION);*/
				JOptionPane.showMessageDialog((Component) null,"Это приложение не находится в поддерже IT-отдела, оно как учебный проект.");
			}
		});
		mnHelp.add(mntmAbout);

		JMenuItem mntmComercialOffers = new JMenuItem("Comercial offers");
		mnTools.add(mntmComercialOffers);

		JMenuItem mntmPositions = new JMenuItem("Positions");
		mnTools.add(mntmPositions);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setBounds(10, 65, 391, 176);
		contentPane.add(scrollPane);

		textAreaLog.setEditable(false);
		textAreaLog.setBounds(5, 82, 414, 148);
		// contentPane.add(textAreaLog);
		scrollPane.add(textAreaLog);

		JButton btnClealLog = new JButton("Clear log");
		btnClealLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textAreaLog.setText("");
			}
		});
		btnClealLog.setBounds(407, 65, 91, 23);
		contentPane.add(btnClealLog);

		JLabel lblNewLabel = new JLabel("Log");
		lblNewLabel.setBounds(10, 45, 46, 14);
		contentPane.add(lblNewLabel);

		timer.start();
	}
}
