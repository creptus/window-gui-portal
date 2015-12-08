
import java.awt.EventQueue;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;

import bissnes.Application;
import bissnes.Logger;
import models.Project;
import tableModel.ProjectTableModel;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.RowFilter;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JInternalFrame;



public class ProjectFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7197798983535638594L;
	private JPanel contentPane;

	private Application app = Application.getInstance();
	private Logger logger = Logger.getInstance();
	private JTable table;
	private JTextField txtFind;
	private JInternalFrame internalFrameNewProject;
	private JButton btnAdd ;
	private JLabel lblDomen;
	private JTextField textdomen;
	private JLabel lblClient;
	private JLabel lblThematic;
	private JTextField textFind;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProjectFrame frame = new ProjectFrame();
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
	public ProjectFrame() {
		setTitle("Utlab: Projects");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 760, 537);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		internalFrameNewProject = new JInternalFrame("Project: New");
		internalFrameNewProject.setBounds(241, 21, 380, 430);
		contentPane.add(internalFrameNewProject);
		internalFrameNewProject.getContentPane().setLayout(null);

		JButton btnClosenewproject = new JButton("Close");
		btnClosenewproject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAdd.setEnabled(true);
				internalFrameNewProject.setVisible(false);
			}
		});
		btnClosenewproject.setBounds(10, 367, 89, 23);
		internalFrameNewProject.getContentPane().add(btnClosenewproject);
		
		lblDomen = new JLabel("Domen");
		lblDomen.setBounds(10, 11, 69, 14);
		internalFrameNewProject.getContentPane().add(lblDomen);
		
		textdomen = new JTextField();
		textdomen.setBounds(89, 8, 265, 20);
		internalFrameNewProject.getContentPane().add(textdomen);
		textdomen.setColumns(10);
		
		lblClient = new JLabel("Client");
		lblClient.setBounds(10, 46, 75, 14);
		internalFrameNewProject.getContentPane().add(lblClient);
		
		lblThematic = new JLabel("Thematic");
		lblThematic.setBounds(10, 81, 75, 14);
		internalFrameNewProject.getContentPane().add(lblThematic);
		internalFrameNewProject.setVisible(false);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 82, 724, 395);
		contentPane.add(scrollPane);

		LinkedList<Project> projects = new LinkedList<>();//app.getProjects(true);
		ProjectTableModel tableModel = new ProjectTableModel(projects);
		table = new JTable(tableModel);
		table.setBounds(new Rectangle(0, 0, 100, 100));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		TableRowSorter<ProjectTableModel> sorter = new TableRowSorter<ProjectTableModel>(tableModel);
		table.setRowSorter(sorter);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// edit
				if (e.getClickCount() == 2) {
					int id = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
					logger.log("Open project #" + id);
					// System.out.println("ERROR 2");
					/*
					 * Client c = app.getClient(id); if (c != null && c.id != 0)
					 * { // System.out.println(c.toString());
					 * setIntoClientFrame(c); internalFrame.setBounds(218, 11,
					 * 290, 236); internalFrame.setVisible(true); }
					 */

				}
			}
		});
		scrollPane.setViewportView(table);

		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Runnable runnable = () -> {
					LinkedList<Project> projects = app.getProjects(true);
					tableModel.setProjects(projects);
					tableModel.fireTableDataChanged();
				};

				Thread thread = new Thread(runnable);
				thread.start();				
			}
		});
		btnRefresh.setBounds(10, 11, 89, 23);
		contentPane.add(btnRefresh);

		btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAdd.setEnabled(false);
				internalFrameNewProject.setBounds(210, 15, 380, 430);
				internalFrameNewProject.setVisible(true);
			}
		});
		btnAdd.setBounds(109, 11, 89, 23);
		contentPane.add(btnAdd);

		JLabel lblFind = new JLabel("Find");
		lblFind.setBounds(10, 57, 46, 14);
		contentPane.add(lblFind);
		
		textFind = new JTextField();
		textFind.setBounds(45, 54, 186, 20);
		textFind.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// search
				String text = textFind.getText();
				if (text.length() == 0) {
					sorter.setRowFilter(null);
				} else {
					sorter.setRowFilter(RowFilter.regexFilter(text));
				}
			}
		});
		contentPane.add(textFind);
		textFind.setColumns(10);

		txtFind = new JTextField();
		txtFind.setBounds(40, 54, 446, 20);
		
		Runnable runnable = () -> {
			tableModel.setProjects(app.getProjects(true));
			tableModel.fireTableDataChanged();
		};

		Thread thread = new Thread(runnable);
		thread.start();

		
		

	}
}
