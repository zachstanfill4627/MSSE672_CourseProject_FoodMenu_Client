package com.foodmenuclient.view.tableModels;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import com.foodmenu.model.domain.DayMenu;

public class DayMenuTableModel extends AbstractTableModel {
	
	private static Logger  LOGGER = Logger.getLogger(DayMenuTableModel.class);
			
	private static final long serialVersionUID = 1L;

	private String [] columnNames = {"Date", "Complexity Value", "Health Value"};
	
	private ArrayList<DayMenu> dayMenus = new ArrayList<DayMenu>();
	
	private final DecimalFormat df = new DecimalFormat("#.##");
	
	public void setDayMenus(ArrayList<DayMenu> dayMenus) {
		LOGGER.trace("setDayMenus Called");
		this.dayMenus = dayMenus;		
	}
	
	public int getRowCount() {
		LOGGER.trace("getRowCount Called");
		return dayMenus.size();
	}
	
	public int getColumnCount() {
		LOGGER.trace("getColumnCount Called");
		return columnNames.length;
	}
	
	public Object getValueAt(int row, int column) {
		LOGGER.trace("getValueAt Called");
		DayMenu dayMenu = dayMenus.get(row);
		
		Calendar date = dayMenu.getDate();
		SimpleDateFormat sdf = new SimpleDateFormat("MMM-d-yyyy");

		switch (column) {
			case 0:
				return sdf.format(date.getTime()).toString();
			case 1:
				return dayMenu.getComplexityValue();
			case 2:
				return df.format(dayMenu.getHealthValue());
		}
		return "";
	}
	
	public String getColumnName(int column) {
		LOGGER.trace("getColumnName Called");
		return columnNames[column];
	}

}
