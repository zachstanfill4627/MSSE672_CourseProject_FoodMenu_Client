package com.foodmenuclient.view.tableModels;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import com.foodmenu.model.domain.User;

public class UsersTableModel extends AbstractTableModel {
	
	private static Logger  LOGGER = Logger.getLogger(UsersTableModel.class);
	
	private static final long serialVersionUID = 1L;

	private String [] columnNames = {"First Name", "Last Name", "Email", "Age",
			"Role"};
	
	private ArrayList<User> users = new ArrayList<User>();
	
	public void setUsers(ArrayList<User> users) {
		LOGGER.trace("setUsers Called");
		this.users = users;
	}
	
	public int getRowCount() {
		LOGGER.trace("getRowCount Called");
		return users.size();
	}
	
	public int getColumnCount() {
		LOGGER.trace("getColumnCount Called");
		return columnNames.length;
	}
	
	public Object getValueAt(int row, int column) {
		LOGGER.trace("getValueAt Called");
		User user = users.get(row);
		switch (column) {
			case 0:
				return user.getFirstName();
			case 1:
				return user.getLastName();
			case 2:
				return user.getEmailAddress();
			case 3:
				return user.getAge();
			case 4:
				return user.getRole();
		}
		return "";
	}
	
	public String getColumnName(int column) {
		LOGGER.trace("getColumnName Called");
		return columnNames[column];
	}

}