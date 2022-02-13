package com.foodmenuclient.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import com.foodmenu.model.domain.FoodItem;
import com.foodmenu.model.domain.MenuItem;
import com.foodmenuclient.controller.FoodMenuClient;
import com.foodmenuclient.view.tableModels.FoodItemsTableModel;

public class CreateMenuItemJFrame extends JFrame {
	
	private static Logger LOGGER = Logger.getLogger(CreateMenuItemJFrame.class);
	
	private FoodItemsTableModel allFoodItemsModel = new FoodItemsTableModel();
	private FoodItemsTableModel selectedFoodItemsModel = new FoodItemsTableModel();
	
	private FoodMenuClient foodMenuClient;
	
	private ArrayList<FoodItem> allFoodItems = new ArrayList<FoodItem>();
	private ArrayList<FoodItem> selectedFoodItems = new ArrayList<FoodItem>();
	
	private final DecimalFormat df = new DecimalFormat("#.##");
	
	private JTextField mealNameField;
	private JSpinner complexityValueSpinner;
	private JTable allFoodItemsTable;
	private JTable selectedFoodItemsTable;
	private JTextField healthValueField;
	
	private int[] allFoodItemsRows = null;
	private int[] selectedFoodItemsRows = null;
	private double healthValue = 0.0;
	
	public void setupCreateMenuItemJFrame(FoodMenuClient foodMenuClient) {
		this.foodMenuClient = foodMenuClient;
	}

	public CreateMenuItemJFrame() {
		super("Create Menu Item");
		LOGGER.trace("Instantiating CreateMenuItemJFrame Class");
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		setSize(850, 572);
		getContentPane().setLayout(null);
		
		try {
			LOGGER.trace("Retrieving AllFoodItems");
			allFoodItems = foodMenuClient.retrieveAllFoodItem();
			LOGGER.trace("Successfully Retrieved AllFoodItems");
			LOGGER.debug("Listing AllFoodItems:");
			allFoodItems.forEach(item -> LOGGER.debug("----Item----" + item.getFoodName()));
		} catch (ClassNotFoundException | IOException e)  {
			e.printStackTrace();
		};
		
		allFoodItemsModel.setFoodItems(allFoodItems);
		selectedFoodItemsModel.setFoodItems(selectedFoodItems);
		
		JLabel lblCreateMenuItem = new JLabel("Create Menu Item");
		lblCreateMenuItem.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreateMenuItem.setFont(new Font("Calibri", Font.BOLD, 18));
		lblCreateMenuItem.setBounds(10, 11, 814, 32);
		getContentPane().add(lblCreateMenuItem);
		
		JLabel mealNameLabel = new JLabel("Meal Name");
		mealNameLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		mealNameLabel.setBounds(10, 83, 126, 18);
		getContentPane().add(mealNameLabel);
		
		mealNameField = new JTextField();
		mealNameField.setFont(new Font("Calibri", Font.BOLD, 12));
		mealNameField.setColumns(10);
		mealNameField.setBounds(136, 80, 688, 20);
		getContentPane().add(mealNameField);
		
		JLabel complexityValueLabel = new JLabel("Complexity Value");
		complexityValueLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		complexityValueLabel.setBounds(10, 115, 126, 18);
		getContentPane().add(complexityValueLabel);
		
		complexityValueSpinner = new JSpinner();
		complexityValueSpinner.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		complexityValueSpinner.setFont(new Font("Calibri", Font.BOLD, 12));
		complexityValueSpinner.setBounds(136, 112, 688, 20);
		getContentPane().add(complexityValueSpinner);
		
		JScrollPane allFoodItemsScrollPane = new JScrollPane();
		allFoodItemsScrollPane.setBounds(10, 144, 364, 284);
		getContentPane().add(allFoodItemsScrollPane);
		
		allFoodItemsTable = new JTable();
		allFoodItemsTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		allFoodItemsTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null},
			},
			new String[] {
				"All Food Items"
			}
		));
		allFoodItemsTable.setFont(new Font("Calibri", Font.BOLD, 12));
		allFoodItemsScrollPane.setViewportView(allFoodItemsTable);
		
		allFoodItemsTable.setModel(allFoodItemsModel);
		allFoodItemsTable.addMouseListener(new selectAllFoodItemMouseClickListener());
		
		JScrollPane selectedFoodItemsScrollPane = new JScrollPane();
		selectedFoodItemsScrollPane.setBounds(461, 144, 363, 284);
		getContentPane().add(selectedFoodItemsScrollPane);
		
		selectedFoodItemsTable = new JTable();
		selectedFoodItemsTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		selectedFoodItemsTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null},
			},
			new String[] {
				"Selected Food Items"
			}
		));
		selectedFoodItemsTable.setFont(new Font("Calibri", Font.BOLD, 12));
		selectedFoodItemsScrollPane.setViewportView(selectedFoodItemsTable);
		
		selectedFoodItemsTable.setModel(selectedFoodItemsModel);
		selectedFoodItemsTable.addMouseListener(new selectSelectedFoodItemMouseClickListener());
		
		JLabel healthValueLabel = new JLabel("Calculated Health Value");
		healthValueLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		healthValueLabel.setBounds(10, 461, 182, 18);
		getContentPane().add(healthValueLabel);
		
		healthValueField = new JTextField();
		healthValueField.setHorizontalAlignment(SwingConstants.RIGHT);
		healthValueField.setText("0");
		healthValueField.setBackground(Color.WHITE);
		healthValueField.setEditable(false);
		healthValueField.setFont(new Font("Calibri", Font.BOLD, 12));
		healthValueField.setColumns(10);
		healthValueField.setBounds(202, 458, 622, 20);
		getContentPane().add(healthValueField);
		
		JButton createMenuButton = new JButton("Create Item");
		createMenuButton.addActionListener(new createMenuButtonListener());
		createMenuButton.setFont(new Font("Calibri", Font.BOLD, 16));
		createMenuButton.setBounds(657, 489, 167, 29);
		getContentPane().add(createMenuButton);
		
		JButton addFoodButton = new JButton(">>");
		addFoodButton.addActionListener(new addFoodButtonListener());
		addFoodButton.setBounds(384, 232, 67, 23);
		getContentPane().add(addFoodButton);
		
		JButton removeFoodButton = new JButton("<<");
		removeFoodButton.addActionListener(new removeFoodButtonListener());
		removeFoodButton.setBounds(384, 266, 67, 23);
		getContentPane().add(removeFoodButton);
		
		LOGGER.info("CreateAccountJFrame Class Instantiated");
	}
	
	class createMenuButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			LOGGER.trace("createMenuButtonListener Initiated");
			String mealName = mealNameField.getText();
			int complexityValue = (int) complexityValueSpinner.getValue();
			LOGGER.info("Read CreateMenuItems Fields");
			LOGGER.debug(String.format("Create MenuItem Form Values: MealName:%s   "
					+ "ComplexityValue:%d", mealName,  complexityValue));
			
			MenuItem menuItem = new MenuItem(mealName, selectedFoodItems, complexityValue);
			LOGGER.info(String.format("New MenuItem %s Object Instantiated", mealName));
			LOGGER.debug(menuItem.toSummaryString());
			
			LOGGER.trace("Begin Validating MenuItem Object");
			if(!menuItem.validate()) {
				JOptionPane.showMessageDialog(null, "Menu Item did not validate, please ensure all fields have values!");
				LOGGER.error(String.format("New MenuItem %s failed to validate. Requesting more information from user.", mealName));
				return;
			}
			LOGGER.trace("Successfully Validated MenuItem Object");
			
			LOGGER.trace("Instantiating MenuItemManager");
			LOGGER.trace("Completed Instantiating MenuItemManager");
			
			try {
				LOGGER.trace(String.format("Attempting to add MenuItem %s to MenuItems Database", mealName));
				if(!foodMenuClient.createMenuItem(menuItem)) {
					JOptionPane.showMessageDialog(null, "System Failed to Create Menu Item!");
					LOGGER.error(String.format("System Failed to add MenuItem %s to MenuItems Database", mealName));
				}
				LOGGER.trace(String.format("Successfully added MenuItem %s to MenuItems Database", mealName));
			} catch (ClassNotFoundException | IOException e1)  {
				e1.printStackTrace();
			}
			
			setVisible(false);
			LOGGER.trace("Set Window Visibility to False");
			dispose();
		}
	}
	
	class addFoodButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			LOGGER.trace("createFoodButtonListener Initiated");
			String foodName = "";
			FoodItem foodItem = new FoodItem();
			
			if(allFoodItemsRows == null) {
				LOGGER.warn("No Items Selected in AllFoodItems Table. No further action taken.");
				return;
			}
			
			LOGGER.trace("Begin Move FoodItems from AllFoodItems to SelectedFoodItems");
			for(int i : allFoodItemsRows ) {
				foodName = allFoodItemsTable.getModel().getValueAt(i, 0).toString();
				try {
					foodItem = foodMenuClient.retrieveFoodItem(foodName);
					selectedFoodItems.add(foodItem);
					
					selectedFoodItemsModel.setFoodItems(selectedFoodItems);
					selectedFoodItemsModel.fireTableDataChanged();
				} catch (ClassNotFoundException | IOException e1)  {
					e1.printStackTrace();
				}
			}

			for(int i = (allFoodItemsRows.length-1); i > -1 ; i--){
				allFoodItems.remove(allFoodItemsRows[i]);
				
				allFoodItemsModel.setFoodItems(allFoodItems);
				allFoodItemsModel.fireTableDataChanged();	
			}
			LOGGER.trace("Completed Move FoodItems from AllFoodItems to SelectedMenuItems");
			
			LOGGER.trace("Calculating MenuItem HealthValue");
			for(FoodItem item : selectedFoodItems) {
				healthValue+=item.getHealthValue();
			}
			healthValue = healthValue / selectedFoodItems.size();
			LOGGER.debug(String.format("Calculated Value Complete - HealthValue:%.2f", healthValue));
			
			LOGGER.trace("Setting MenuItem HealthValue");
			healthValueField.setText(String.valueOf(df.format(healthValue)));
			LOGGER.trace("Set Calculated MenuItem HealthValue");
			
			allFoodItemsRows = null;
			LOGGER.trace("addFoodButtonListener Completed");
		}
	}
	
	class removeFoodButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			LOGGER.trace("removeFoodButtonListener Initiated");
			String foodName = "";
			FoodItem foodItem = new FoodItem();
			
			if(selectedFoodItemsRows == null) {
				LOGGER.warn("No Items Selected in SelectedFoodItems Table. No further action taken.");
				return;
			}
						
			for(int i : selectedFoodItemsRows ) {
				foodName = selectedFoodItemsTable.getModel().getValueAt(i, 0).toString();
				try {
					foodItem = foodMenuClient.retrieveFoodItem(foodName);
					
					allFoodItems.add(foodItem);
					
					allFoodItemsModel.setFoodItems(allFoodItems);
					allFoodItemsModel.fireTableDataChanged();
				} catch (ClassNotFoundException | IOException e1)  {
					e1.printStackTrace();
				}
			}

			for(int i = (selectedFoodItemsRows.length-1); i > -1 ; i--){
				selectedFoodItems.remove(selectedFoodItemsRows[i]);
				
				selectedFoodItemsModel.setFoodItems(selectedFoodItems);
				selectedFoodItemsModel.fireTableDataChanged();	
			}
			LOGGER.trace("Completed Move FoodItems from SelectedFoodItems to AllFoodItems");
			
			LOGGER.trace("Calculating MenuItem HealthValue");			
			if(selectedFoodItems.size() > 0) {
				for(FoodItem item : selectedFoodItems) {
					healthValue+=item.getHealthValue();
				}
				healthValue = healthValue / selectedFoodItems.size();
			} else {
				healthValue = 0;
			}
			LOGGER.debug(String.format("Calculated Value Complete - HealthValue:%.2f", healthValue));
			
			LOGGER.trace("Setting MenuItem HealthValue:");
			healthValueField.setText(String.valueOf(df.format(healthValue)));
			LOGGER.trace("Set Calculated MenuItem HealthValue");
			
			selectedFoodItemsRows = null;
			LOGGER.trace("removeFoodButtonListener Completed");
		}
	}
	
	class selectAllFoodItemMouseClickListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			LOGGER.trace("selectAllFoodItemMouseClickListener Initiated");
			allFoodItemsRows = allFoodItemsTable.getSelectedRows();
		}

		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
	}
	
	class selectSelectedFoodItemMouseClickListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			LOGGER.trace("selectSelectedFoodItemMouseClickListener Initiated");
			selectedFoodItemsRows = selectedFoodItemsTable.getSelectedRows();
		}

		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
	}
}

