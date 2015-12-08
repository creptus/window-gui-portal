package tableModel;

import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;

import models.Project;

public class ProjectTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -494876418082084774L;

	LinkedList<Project> projects;

	public ProjectTableModel(LinkedList<Project> projects) {
		super();
		this.projects = projects;
	}

	public ProjectTableModel() {
		super();
		this.projects = new LinkedList<>();
	}

	public void setProjects(LinkedList<Project> projects) {
		this.projects = projects;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 6;
	}

	@Override
	public int getRowCount() {
		if (projects == null) {
			return 0;
		} else {
			return projects.size();
		}

	}

	@Override
	public Object getValueAt(int r, int c) {
		// TODO Auto-generated method stub
		switch (c) {
		case 0:
			return projects.get(r).id;
		case 1:
			return projects.get(r).domain.domain;
		case 2:
			return projects.get(r).create_date;
		case 3:
			return projects.get(r).thematic.name;
		case 4:
			return projects.get(r).client.company_name;
		case 5:

			if (projects.get(r).delete_date == null || projects.get(r).delete_date.equals("")) {
				return "work";
			} else {
				return "stop";
			}

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
			return "Domain";
		case 2:
			return "Date add";
		case 3:
			return "Them";
		case 4:
			return "Client";
		case 5:
			return "Status";
		case 6:
			return "Domain paid till";
		default:
			return "Other Column";
		}
	}

	@Override
	public Class<?> getColumnClass(int col) {
		Class<?> c;
		switch (col) {
		case 0:
			c = Integer.class;
			break;
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
			c = String.class;
			;
			break;
		default:
			c = super.getColumnClass(col);
		}
		return c;
	}
}
