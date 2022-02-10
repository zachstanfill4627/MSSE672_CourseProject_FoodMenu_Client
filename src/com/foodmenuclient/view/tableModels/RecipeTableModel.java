package com.foodmenuclient.view.tableModels;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import com.foodmenu.model.domain.FoodItem;

public class RecipeTableModel extends AbstractTableModel {
	
	private static Logger  LOGGER = Logger.getLogger(RecipeTableModel.class);
	
	private static final long serialVersionUID = 1L;

	private String [] columnNames = {"Recipe"};
	
	private FoodItem foodItem = new FoodItem();
	private ArrayList<String> steps = new ArrayList<String>();
	
	public void setFoodItem(FoodItem foodItem) {
		LOGGER.trace("setFoodItem Called");
		this.foodItem = foodItem;
		this.steps = foodItem.getRecipe();
	}
	
	public int getRowCount() {
		LOGGER.trace("getRowCount Called");
		return steps.size();
	}
	
	public int getColumnCount() {
		LOGGER.trace("getColumnCount Called");
		return columnNames.length;
	}
	
	public Object getValueAt(int row, int column) {
		LOGGER.trace("getValueAt Called");
		String step = steps.get(row);
		switch (column) {
			case 0:
				return step;
		}
		return "";
	}
	
	public String getColumnName(int column) {
		LOGGER.trace("getColumnName Called");
		return columnNames[column];
	}

}
