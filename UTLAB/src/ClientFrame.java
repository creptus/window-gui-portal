
import java.awt.EventQueue;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.TableRowSorter;

import bissnes.Application;
import bissnes.Logger;
import models.Client;
import tableModel.ClientTableModel;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.RowFilter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JInternalFrame;

public class ClientFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5098657670133620436L;
	private JPanel contentPane;
	private JTable table;
	private JTextField textFind;
	private Application app = Application.getInstance();
	private Logger logger = Logger.getInstance();
	private JTextField textId;
	private JTextField txtName;
	private JTextField txtInn;
	private JTextField txtLogin;
	private JTextField txtPassword;
	private JTextField txtSellerid;
	private JTextField txtSellername;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientFrame frame = new ClientFrame();
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
	public ClientFrame() {
		setTitle("UTLAB:Клиенты");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 639, 511);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JInternalFrame internalFrame = new JInternalFrame("Client");
		internalFrame.setBounds(218, 11, 290, 236);
		internalFrame.setVisible(false);
		contentPane.add(internalFrame);
		internalFrame.getContentPane().setLayout(null);

		JButton btnClose = new JButton("Cansel");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				internalFrame.setBounds(428, 11, 216, 250);
				internalFrame.setVisible(false);
			}
		});
		btnClose.setBounds(69, 170, 89, 23);
		internalFrame.getContentPane().add(btnClose);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSave.setEnabled(false);
				Client c=getIntoClientFrame();
				if(app.saveClient(c)){
					btnSave.setEnabled(true);	
					internalFrame.setVisible(false);
				}else{
					btnSave.setEnabled(true);
				}

			}
		});
		btnSave.setBounds(175, 170, 89, 23);
		internalFrame.getContentPane().add(btnSave);

		JLabel lblId = new JLabel("ID");
		lblId.setBounds(10, 11, 46, 14);
		internalFrame.getContentPane().add(lblId);

		textId = new JTextField();
		textId.setEditable(false);
		textId.setBounds(69, 8, 195, 20);
		internalFrame.getContentPane().add(textId);
		textId.setColumns(10);

		JLabel lblName = new JLabel("Name");
		lblName.setBounds(10, 41, 46, 14);
		internalFrame.getContentPane().add(lblName);

		txtName = new JTextField();
		txtName.setText("Name");
		txtName.setBounds(69, 35, 195, 20);
		internalFrame.getContentPane().add(txtName);
		txtName.setColumns(10);

		JLabel lblInn = new JLabel("Inn");
		lblInn.setBounds(10, 67, 46, 14);
		internalFrame.getContentPane().add(lblInn);

		txtInn = new JTextField();
		txtInn.setText("Inn");
		txtInn.setBounds(69, 62, 195, 20);
		internalFrame.getContentPane().add(txtInn);
		txtInn.setColumns(10);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setBounds(10, 96, 46, 14);
		internalFrame.getContentPane().add(lblLogin);

		txtLogin = new JTextField();
		txtLogin.setText("Login");
		txtLogin.setBounds(69, 90, 195, 20);
		internalFrame.getContentPane().add(txtLogin);
		txtLogin.setColumns(10);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 121, 64, 14);
		internalFrame.getContentPane().add(lblPassword);

		txtPassword = new JTextField();
		txtPassword.setText("Password");
		txtPassword.setBounds(69, 116, 195, 20);
		internalFrame.getContentPane().add(txtPassword);
		txtPassword.setColumns(10);
		
		JLabel lblSeller = new JLabel("Seller");
		lblSeller.setBounds(10, 146, 46, 14);
		internalFrame.getContentPane().add(lblSeller);
		
		txtSellerid = new JTextField();
		txtSellerid.setEditable(false);
		txtSellerid.setText("SellerId");
		txtSellerid.setBounds(69, 140, 37, 20);
		internalFrame.getContentPane().add(txtSellerid);
		txtSellerid.setColumns(10);
		
		txtSellername = new JTextField();
		txtSellername.setEditable(false);
		txtSellername.setText("SellerName");
		txtSellername.setBounds(116, 140, 148, 20);
		internalFrame.getContentPane().add(txtSellername);
		txtSellername.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 72, 414, 390);
		contentPane.add(scrollPane);

		LinkedList<Client> clients = app.getClients();
		ClientTableModel tableModel = new ClientTableModel(clients);
		table = new JTable(tableModel);

		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// edit
				if (e.getClickCount() == 2) {
					logger.log("Open client #" + table.getValueAt(table.getSelectedRow(), 0));
					int id = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
					Client c = app.getClient(id);
					if (c != null && c.id != 0) {
						// System.out.println(c.toString());
						setIntoClientFrame(c);
						internalFrame.setBounds(218, 11, 290, 236);
						internalFrame.setVisible(true);
					}

				}
			}
		});

		TableRowSorter<ClientTableModel> sorter = new TableRowSorter<ClientTableModel>(tableModel);
		table.setRowSorter(sorter);

		scrollPane.setViewportView(table);

		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// load data

				LinkedList<Client> clients = app.getClients();
				tableModel.setClients(clients);
				tableModel.fireTableDataChanged();

			}
		});
		btnRefresh.setBounds(10, 11, 89, 23);
		contentPane.add(btnRefresh);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Client c = new Client();
				setIntoClientFrame(c);
				internalFrame.setBounds(218, 11, 290, 236);
				internalFrame.setVisible(true);
			}
		});
		btnAdd.setBounds(109, 11, 89, 23);
		contentPane.add(btnAdd);

		JLabel lblFind = new JLabel("Find");
		lblFind.setBounds(10, 45, 46, 14);
		contentPane.add(lblFind);

		textFind = new JTextField();
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
		textFind.setBounds(35, 42, 389, 20);
		contentPane.add(textFind);
		textFind.setColumns(10);

	}

	protected void setIntoClientFrame(Client c) {
		textId.setText(Integer.toString(c.id));
		txtName.setText(c.company_name);
		txtInn.setText(c.inn);
		txtLogin.setText(c.login);
		txtPassword.setText("");
		txtSellerid.setText(Integer.toString(c.seller.id));
		txtSellername.setText(c.seller.company_name);
	}

	protected Client getIntoClientFrame() {
		Client c = new Client();
		c.id = Integer.parseInt(textId.getText());
		c.company_name = txtName.getText();
		c.inn = txtInn.getText();
		c.login = txtLogin.getText();
		c.password = txtPassword.getText();
		
		c.seller.id=Integer.parseInt(txtSellerid.getText());
		c.seller_id=c.seller.id;
		c.seller.company_name=txtSellername.getText();
		
		
		return c;
	}
}
