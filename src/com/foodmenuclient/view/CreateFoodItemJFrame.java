package com.foodmenuclient.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

import com.foodmenu.model.domain.FoodItem;
import com.foodmenuclient.controller.FoodMenuClient;
import com.foodmenuclient.view.CreateDayMenuJFrame.selectSelectedMenuItemMouseClickListener;
import com.foodmenuclient.view.tableModels.IngredientsTableModel;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;

public class CreateFoodItemJFrame extends JFrame {
	
	private static Logger LOGGER = Logger.getLogger(CreateFoodItemJFrame.class);
	
	private FoodMenuClient foodMenuClient;
	
	private JTextField foodNameField;
	private JComboBox categoryComboBox;
	private JSpinner healthValueSpinner;
	private JSpinner prepTimeSpinner;
	private JScrollPane recipeScrollPane;
	private JTextArea recipeTextArea;
	private JScrollPane ingredientsScrollPane;
	private JLabel QuantityLabel;
	private JTextField quantityField;
	private JLabel MeasurementLabel;
	private JTextField measurementField;
	private JLabel IngredientLabel;
	private JTextField ingredientField;
	private JButton addButton;
	private JButton deleteButton;
	
	private ArrayList<String> ingredientsList = new ArrayList<String>();
	private JTable ingredientsTable;
	
	private IngredientsTableModel ingredientsModel = new IngredientsTableModel();
	@SuppressWarnings("unused")
	private int selectedIngredientRow = -1;
	
	public void setupCreateFoodItemJFrame(FoodMenuClient foodMenuClient) {
		this.foodMenuClient = foodMenuClient;
	}

	public CreateFoodItemJFrame() {
		super("Create Food Item");
		LOGGER.trace("Initializing CreateFoodItemJFrame Class");
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		setSize(479, 814);
		getContentPane().setLayout(null);
		
		JLabel titleLabel = new JLabel("Create Food Item");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Calibri", Font.BOLD, 18));
		titleLabel.setBounds(10, 11, 443, 32);
		getContentPane().add(titleLabel);
		
		JButton createFoodButton = new JButton("Create Item");
		createFoodButton.addActionListener(new createFoodButtonListener());
		createFoodButton.setFont(new Font("Calibri", Font.BOLD, 16));
		createFoodButton.setBounds(286, 735, 167, 29);
		getContentPane().add(createFoodButton);
		
		JLabel foodNameLabel = new JLabel("Food Name");
		foodNameLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		foodNameLabel.setBounds(10, 67, 126, 18);
		getContentPane().add(foodNameLabel);
		
		JLabel categoryLabel = new JLabel("Category");
		categoryLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		categoryLabel.setBounds(10, 96, 126, 18);
		getContentPane().add(categoryLabel);
		
		JLabel healthValueLabel = new JLabel("Health Value");
		healthValueLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		healthValueLabel.setBounds(10, 125, 126, 18);
		getContentPane().add(healthValueLabel);
		
		JLabel prepTimeLabel = new JLabel("PrepTime (Minutes)");
		prepTimeLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		prepTimeLabel.setBounds(10, 154, 126, 18);
		getContentPane().add(prepTimeLabel);
		
		JLabel recipeLabel = new JLabel("Recipe (One Step Per Line)");
		recipeLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		recipeLabel.setBounds(10, 183, 443, 18);
		getContentPane().add(recipeLabel);
		
		JLabel ingredientsLabel = new JLabel("Ingredients");
		ingredientsLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		ingredientsLabel.setBounds(10, 331, 443, 18);
		getContentPane().add(ingredientsLabel);
		
		foodNameField = new JTextField();
		foodNameField.setFont(new Font("Calibri", Font.BOLD, 12));
		foodNameField.setBounds(136, 64, 317, 20);
		getContentPane().add(foodNameField);
		foodNameField.setColumns(10);
		
		categoryComboBox = new JComboBox();
		categoryComboBox.setFont(new Font("Calibri", Font.BOLD, 12));
		categoryComboBox.setModel(new DefaultComboBoxModel(new String[] {"American", "Mexican", "Italian", "Japanese", "Chinese", "Other"}));
		categoryComboBox.setBackground(Color.WHITE);
		categoryComboBox.setBounds(136, 92, 317, 22);
		getContentPane().add(categoryComboBox);
		
		healthValueSpinner = new JSpinner();
		healthValueSpinner.setFont(new Font("Calibri", Font.BOLD, 12));
		healthValueSpinner.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		healthValueSpinner.setBounds(136, 122, 317, 20);
		getContentPane().add(healthValueSpinner);
		
		prepTimeSpinner = new JSpinner();
		prepTimeSpinner.setFont(new Font("Calibri", Font.BOLD, 12));
		prepTimeSpinner.setModel(new SpinnerNumberModel(1, 1, 1000, 1));
		prepTimeSpinner.setBounds(136, 151, 317, 20);
		getContentPane().add(prepTimeSpinner);
		
		recipeScrollPane = new JScrollPane();
		recipeScrollPane.setBounds(10, 200, 443, 116);
		getContentPane().add(recipeScrollPane);
		
		recipeTextArea = new JTextArea();
		recipeTextArea.setFont(new Font("Calibri", Font.PLAIN, 12));
		recipeScrollPane.setViewportView(recipeTextArea);
		
		ingredientsScrollPane = new JScrollPane();
		ingredientsScrollPane.setBounds(10, 458, 443, 223);
		getContentPane().add(ingredientsScrollPane);
		
		ingredientsTable = new JTable();
		ingredientsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ingredientsTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Ingredients"
			}
		));
		ingredientsScrollPane.setViewportView(ingredientsTable);
		
		ingredientsTable.setModel(ingredientsModel);
		ingredientsTable.addMouseListener(new selectIngredientMouseClickListener());
		
		QuantityLabel = new JLabel("Quantity");
		QuantityLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		QuantityLabel.setBounds(10, 360, 68, 18);
		getContentPane().add(QuantityLabel);
		
		quantityField = new JTextField();
		quantityField.setFont(new Font("Calibri", Font.BOLD, 14));
		quantityField.setColumns(10);
		quantityField.setBounds(10, 383, 68, 24);
		getContentPane().add(quantityField);
		
		MeasurementLabel = new JLabel("Measurement");
		MeasurementLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		MeasurementLabel.setBounds(88, 360, 89, 18);
		getContentPane().add(MeasurementLabel);
		
		measurementField = new JTextField();
		measurementField.setFont(new Font("Calibri", Font.BOLD, 14));
		measurementField.setColumns(10);
		measurementField.setBounds(88, 383, 82, 24);
		getContentPane().add(measurementField);
		
		IngredientLabel = new JLabel("Ingredient");
		IngredientLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		IngredientLabel.setBounds(180, 360, 273, 18);
		getContentPane().add(IngredientLabel);
		
		ingredientField = new JTextField();
		ingredientField.setFont(new Font("Calibri", Font.BOLD, 14));
		ingredientField.setColumns(10);
		ingredientField.setBounds(180, 383, 273, 24);
		getContentPane().add(ingredientField);
		
		addButton = new JButton("Add Ingredient");
		addButton.addActionListener(new addIngredientButtonListener());
		addButton.setFont(new Font("Calibri", Font.BOLD, 16));
		addButton.setBounds(10, 418, 167, 29);
		getContentPane().add(addButton);
		
		deleteButton = new JButton("Delete Ingredient");
		deleteButton.addActionListener(new deleteIngredientButtonListener());
		deleteButton.setFont(new Font("Calibri", Font.BOLD, 16));
		deleteButton.setBounds(10, 692, 167, 29);
		getContentPane().add(deleteButton);
		
		LOGGER.info("CreateFoodItemJFrame Class Instantiated");
	}
	
	class createFoodButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			LOGGER.trace("createFoodButtonListener Initiated");
			
			LOGGER.trace("Begin Reading CreateFoodItem Form");
			String foodName = foodNameField.getText();
			String category = categoryComboBox.getSelectedItem().toString();
			int healthValue = (int) healthValueSpinner.getValue();
			int prepTime = (int) prepTimeSpinner.getValue();
			ArrayList<String> recipe = new ArrayList<String>(Arrays.asList(recipeTextArea.getText().split("\\r?\\n")));
			//ArrayList<String> ingredients = new ArrayList<String>(Arrays.asList(ingredientsTextArea.getText().split("\\r?\\n")));
			LOGGER.info("Read CreateFoodItem Fields");
			LOGGER.debug(String.format("Create Food Item Form Values: FoodName:%s   "
					+ "Category:%s   HealthValue:%d   PrepTime:%d", foodName, category,  
					healthValue, prepTime));
			LOGGER.debug("Recipe Steps:");
			recipe.forEach(step -> LOGGER.debug("----Step----: " + step));
			LOGGER.debug("Ingredient Items");
			ingredientsList.forEach(item -> LOGGER.debug("----Item----: " + item));
			
			FoodItem foodItem = new FoodItem(foodName, category, healthValue, prepTime, recipe, ingredientsList);
			LOGGER.info(String.format("New FoodItem %s Object Instantiated", foodName));
			LOGGER.debug(foodItem.toSummaryString());
			
			LOGGER.trace("Begin Validating FoodItem Object");
			if(!foodItem.validate()) {
				JOptionPane.showMessageDialog(null, "Food Item did not validate, please ensure all fields have values!");
				LOGGER.error(String.format("New FoodItem %s failed to validate. Requesting more information from user.", foodName));
				return;
			}
			LOGGER.trace("Successfully Validated FoodItem Object");
			
			LOGGER.trace("Instantiating FoodItemManager");
			//FoodItemManager foodItemManager = new FoodItemManager();
			LOGGER.trace("Completed Instatiating FoodItemManager");
			
			
			try {
				LOGGER.trace(String.format("Attempting to add FoodItem %s to FoodItems Database", foodName));
				if(!foodMenuClient.createFoodItem(foodItem)) {
					JOptionPane.showMessageDialog(null, "System Failed to Create Food Item!");
					LOGGER.error(String.format("System Failed to add FoodItem %s to FoodItems Database", foodName)); 
				}
				LOGGER.info(String.format("Successfully Added FoodItem to the FoodItems Database"));
			} catch (ClassNotFoundException | IOException e1)  {
				e1.printStackTrace();
			}
			
			setVisible(false);
			LOGGER.trace("Set Window Visibility to False");
			dispose();
		}
	}
	
	class addIngredientButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			LOGGER.trace("addIngredientButtonListener Initiated");
			
			String quantity = quantityField.getText();
			String measurement = measurementField.getText();
			String ingredient = ingredientField.getText();
			
			ingredientsList.add(String.format("%s%s   %s", quantity, measurement, ingredient));
			
			quantityField.setText("");
			measurementField.setText("");
			ingredientField.setText("");
			
			ingredientsModel.setFoodItem(ingredientsList);
			
			ingredientsModel.fireTableDataChanged();
		}
	}
	
	class selectIngredientMouseClickListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			LOGGER.trace("selectedIngredientsMouseClickLister Initiated");
			
			selectedIngredientRow = ingredientsTable.getSelectedRow();
		}

		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
	}
	
	class deleteIngredientButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			LOGGER.trace("deleteIngredientButtonListener Initiated");
			
			if(selectedIngredientRow == -1) {
				LOGGER.warn("No Items Selected in ingredientsTable. No further action taken.");
				return;
			} else {
				ingredientsList.remove(ingredientsModel.getValueAt(selectedIngredientRow, 0));
				ingredientsModel.setFoodItem(ingredientsList);
				ingredientsModel.fireTableDataChanged();
				selectedIngredientRow = -1;
				return;
			}
		}
	}
}
