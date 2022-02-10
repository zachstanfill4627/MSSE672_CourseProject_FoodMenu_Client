package com.foodmenuclient.view.tableModels;

import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import com.foodmenu.model.domain.MenuItem;

public class MenuItemsTableModel extends AbstractTableModel {
	
	private static Logger  LOGGER = Logger.getLogger(MenuItemsTableModel.class);
	
	private static final long serialVersionUID = 1L;

	private String [] columnNames = {"Meal Name", "Complexity Value", "Health Value"};
	
	private ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
	
	private final DecimalFormat df = new DecimalFormat("#.##");
	
	public void setMenuItems(ArrayList<MenuItem> menuItems) {
		LOGGER.trace("setMenuItems Called");
		this.menuItems = menuItems;
	}
	
	public int getRowCount() {
		LOGGER.trace("getRowCount Called");
		return menuItems.size();
	}
	
	public int getColumnCount() {
		LOGGER.trace("getColumnCount Called");
		return columnNames.length;
	}
	
	public Object getValueAt(int row, int column) {
		LOGGER.trace("getValueAt Called");
		MenuItem menuItem = menuItems.get(row);
		switch (column) {
			case 0:
				return menuItem.getMealName();
			case 1:
				return menuItem.getComplexityValue();
			case 2:
				return df.format(menuItem.getHealthValue());
		}
		return "";
	}
	
	public String getColumnName(int column) {
		LOGGER.trace("getColumnName Called");
		return columnNames[column];
	}

}
