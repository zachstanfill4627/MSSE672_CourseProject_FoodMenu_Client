package com.foodmenuclient.view.tableModels;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

import com.foodmenu.model.domain.FoodItem;

public class IngredientsTableModel extends AbstractTableModel {
	
	private static Logger  LOGGER = Logger.getLogger(IngredientsTableModel.class);
	
	private static final long serialVersionUID = 1L;

	private String [] columnNames = {"Ingredient"};
	
	private FoodItem foodItem = new FoodItem();
	private ArrayList<String> ingredients = new ArrayList<String>();
	
	public void setFoodItem(FoodItem foodItem) {
		LOGGER.trace("setFoodItem(FoodItem) Called");
		this.foodItem = foodItem;
		this.ingredients = foodItem.getIngredients();
	}
	
	public void setFoodItem(ArrayList<String> ingredients) {
		LOGGER.trace("setFoodItem(ArrayList) Called");
		this.ingredients = ingredients;
	}
	
	public int getRowCount() {
		LOGGER.trace("getRowCount Called");
		return ingredients.size();
	}
	
	public int getColumnCount() {
		LOGGER.trace("getColumnCount Called");
		return columnNames.length;
	}
	
	public Object getValueAt(int row, int column) {
		LOGGER.trace("getValueAt Called");
		String ingredient = ingredients.get(row);
		switch (column) {
			case 0:
				return ingredient;
		}
		return "";
	}
	
	public String getColumnName(int column) {
		LOGGER.trace("getColumnName Called");
		return columnNames[column];
	}
}
