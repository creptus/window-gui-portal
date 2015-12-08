package tableModel;


import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;

import models.Client;


public class ClientTableModel extends AbstractTableModel {


	/**
	 * 
	 */
	private static final long serialVersionUID = 4870031910499915977L;
	LinkedList<Client> clients;

	public ClientTableModel(LinkedList<Client> clients) {
		super();
		this.clients = clients;
	}	

	public ClientTableModel() {
		super();
		this.clients = new LinkedList<>();
	}
	
	public void setClients(LinkedList<Client> clients){
		this.clients = clients;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return clients.size();
	}

	@Override
	public Object getValueAt(int r, int c) {
		// TODO Auto-generated method stub
		switch(c){
		case 0:
			return clients.get(r).id;
		case 1:
			return clients.get(r).company_name;
		case 2:
			return clients.get(r).inn;
		default:
			return "unknown";
		}
	}

	@Override
	public String getColumnName(int c) {
		switch (c) {
		case 0:
			return "Id";
		case 1:
			return "Company name";
		case 2:
			return "inn";
		default:
			return "Other Column";
		}
	}
	
	 @Override
	    public Class<?> getColumnClass(int col) {
	        Class<?> c;
	        switch(col){
	        case 0:
				c=Integer.class;
				break;
			case 1:
				c=String.class;
				break;
			case 2:
				c=String.class;
				break;
	        default:
	        	c = super.getColumnClass(col);
	        }
	       /* COLUMNS column = header[col];
	        if (column.equals(COLUMNS.IMAGE_COLUMN))
	            c = ImageIcon.class;
	        else if (column.equals(COLUMNS.CAR_TYPE_COLUMN))
	            c =  JComboBox.class;
	        // else if blabla....
	        else c = super.getColumnClass(col);*/
	        return c;
	    }

}
