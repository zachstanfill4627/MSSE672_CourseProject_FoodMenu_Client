package com.foodmenuclient.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;

import com.foodmenu.model.domain.*;
import com.foodmenuclient.controller.FoodMenuClient;
import com.foodmenuclient.view.tableModels.*;

public class FoodMenuJFrame extends JFrame {
	
	private static Logger LOGGER = Logger.getLogger(FoodMenuJFrame.class);

	private static final long serialVersionUID = 1L;
	
	private User activeUser = null;
	
	private User selectedUser = null;
	private FoodItem selectedFoodItem = null;
	private MenuItem selectedMenuItem = null;
	private DayMenu selectedDayMenu = null;
	
	private UsersTableModel usersModel = new UsersTableModel();
	private FoodItemsTableModel foodItemsModel = new FoodItemsTableModel();
	private FoodItemsTableModel foodItemsMenuItemModel = new FoodItemsTableModel();
	private FoodItemsTableModel foodItemsDayMenuModel = new FoodItemsTableModel();
	private IngredientsTableModel ingredientsFoodItemModel = new IngredientsTableModel();
	private IngredientsTableModel ingredientsMenuItemModel = new IngredientsTableModel();
	private IngredientsTableModel ingredientsDayMenuModel = new IngredientsTableModel();
	private MenuItemsTableModel menuItemsModel = new MenuItemsTableModel();
	private MenuItemsTableModel menuItemsDayMenuModel = new MenuItemsTableModel();
	private DayMenuTableModel dayMenuModel = new DayMenuTableModel();
	private RecipeTableModel recipeModel = new RecipeTableModel();
	
	private static FoodMenuClient foodMenuClient;
	
	private JTable usersTable;
	private JTable foodItemsTable;
	private JTable ingredientsTable;
	private JTable recipeTable;
	private JTable menuItemsTable;
	private JTable menuFoodItemsTable;
	private JTable menuIngredientsTable;
	private JTable dayMenusTable;
	private JTable dayMenuMenuItemsTable;
	private JTable dayMenuFoodItemsTable;
	private JTable dayMenuIngredientsTable;
	
    public void setUser(User user) throws ClassNotFoundException, IOException, Exception {
    	LOGGER.trace("Setting User for Session");
        this.activeUser = user;
        LOGGER.info(String.format("Set User %s for Session", user.getEmailAddress()));
        
        LOGGER.trace("Instantiate FoodMenuClient");
        foodMenuClient = new FoodMenuClient(user);
        
        foodMenuClient.openConnection();
        
        LOGGER.trace("Retrieve All Users for UsersTab_UsersTable");
        usersModel.setUsers(foodMenuClient.retrieveAllUsers());
		LOGGER.info(String.format("Successfully retrieved Users for User %s session", user.getEmailAddress()));
        
        LOGGER.trace("Retrieve All FoodItems for FoodItemsTables");
		foodItemsModel.setFoodItems(foodMenuClient.retrieveAllFoodItem());
		LOGGER.info(String.format("Successfully retrieved FoodItems for User %s session", user.getEmailAddress()));
        
        LOGGER.trace("Retrieve All MenuItems for MenuItemsTables");
		menuItemsModel.setMenuItems(foodMenuClient.retrieveAllMenuItem());
		LOGGER.info(String.format("Successfully retrieved MenuItems for User %s session", user.getEmailAddress()));
		
        LOGGER.trace("Retrieve All DayMenus for the DayMenuTable");
		dayMenuModel.setDayMenus(foodMenuClient.retrieveAllDayMenu());
		LOGGER.info(String.format("Successfully retrieved DayMenus for User %s session", user.getEmailAddress()));
		
        LOGGER.trace("Completed Set User for Session");
    }
	
	@SuppressWarnings("serial")
	public FoodMenuJFrame() throws IOException {
		super("Food Menu");
		LOGGER.trace("Instantiating FoodMenuJFrame Class");
		setResizable(false);
		addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
            	try {
					foodMenuClient.closeConnection();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
                e.getWindow().dispose();
            }
        });
		
		setSize(850, 679);
		
		//create Box containers with BoxLayout
		Box dayMenus = Box.createHorizontalBox();
		Box menuItems = Box.createHorizontalBox();
		Box foodItems = Box.createHorizontalBox();
		Box userAccounts = Box.createHorizontalBox();
				
	   // create a JTabbedPane
	   JTabbedPane tabs = new JTabbedPane(
			   JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
	   tabs.setBounds(0, 0, 834, 640);
	   
	   //place each container on tabbed pane
	   tabs.addTab("Day Menus", dayMenus);
	   
	   JPanel dayMenusPanel = new JPanel();
	   dayMenus.add(dayMenusPanel);
	   dayMenusPanel.setLayout(null);
	   
	   JScrollPane dayMenuScrollPane = new JScrollPane();
	   dayMenuScrollPane.setBounds(10, 11, 809, 218);
	   dayMenusPanel.add(dayMenuScrollPane);
	   
	   dayMenusTable = new JTable();
	   dayMenusTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	   dayMenusTable.setFont(new Font("Calibri", Font.BOLD, 12));
	   dayMenusTable.setModel(new DefaultTableModel(
	   	new Object[][] {
	   		{null, null, null},
	   	},
	   	new String[] {
	   		"Date", "Complexity Value", "Health Value"
	   	}
	   ));
	   dayMenuScrollPane.setViewportView(dayMenusTable);
	   
	   dayMenusTable.setModel(dayMenuModel);
	   dayMenusTable.addMouseListener(new selectDayMenuMouseClickListener());
	   
	   JScrollPane dayMenuMenuItemsScrollPane = new JScrollPane();
	   dayMenuMenuItemsScrollPane.setBounds(10, 240, 405, 281);
	   dayMenusPanel.add(dayMenuMenuItemsScrollPane);
	   
	   dayMenuMenuItemsTable = new JTable();
	   dayMenuMenuItemsTable.setModel(new DefaultTableModel(
	   	new Object[][] {
	   		{null, null, null},
	   	},
	   	new String[] {
	   		"Meal Name", "Complexity Value", "Health Value"
	   	}
	   ));
	   dayMenuMenuItemsTable.setFont(new Font("Calibri", Font.BOLD, 12));
	   dayMenuMenuItemsScrollPane.setViewportView(dayMenuMenuItemsTable);
	   
	   JScrollPane dayMenuFoodItemsScrollPane = new JScrollPane();
	   dayMenuFoodItemsScrollPane.setBounds(426, 240, 393, 141);
	   dayMenusPanel.add(dayMenuFoodItemsScrollPane);
	   
	   dayMenuMenuItemsTable.setModel(menuItemsDayMenuModel);
	   
	   dayMenuFoodItemsTable = new JTable();
	   dayMenuFoodItemsTable.setModel(new DefaultTableModel(
	   	new Object[][] {
	   		{"", "", null, null},
	   	},
	   	new String[] {
	   		"Food Name", "Category", "Health Value", "Prep Time"
	   	}
	   ));
	   dayMenuFoodItemsTable.setFont(new Font("Calibri", Font.BOLD, 12));
	   dayMenuFoodItemsScrollPane.setViewportView(dayMenuFoodItemsTable);
	   
	   dayMenuFoodItemsTable.setModel(foodItemsDayMenuModel);
	   
	   JScrollPane dayMenuIngredientsScrollPane = new JScrollPane();
	   dayMenuIngredientsScrollPane.setBounds(425, 392, 394, 129);
	   dayMenusPanel.add(dayMenuIngredientsScrollPane);
	   
	   dayMenuIngredientsTable = new JTable();
	   dayMenuIngredientsTable.setModel(new DefaultTableModel(
	   	new Object[][] {
	   		{null},
	   	},
	   	new String[] {
	   		"Ingredients"
	   	}
	   ));
	   dayMenuIngredientsTable.setFont(new Font("Calibri", Font.BOLD, 12));
	   dayMenuIngredientsScrollPane.setViewportView(dayMenuIngredientsTable);
	   
	   dayMenuIngredientsTable.setModel(ingredientsDayMenuModel);
	   
	   JButton createDayMenuButton = new JButton("Create New Day Menu");
//	   createDayMenuButton.addActionListener(new createDayMenuButtonListener());
	   createDayMenuButton.setFont(new Font("Calibri", Font.BOLD, 14));
	   createDayMenuButton.setBounds(621, 578, 198, 23);
	   dayMenusPanel.add(createDayMenuButton);
	   
	   JButton deleteDayMenuButton = new JButton("Delete Day Menu");
	   deleteDayMenuButton.addActionListener(new deleteDayMenuButtonListener());
	   deleteDayMenuButton.setFont(new Font("Calibri", Font.BOLD, 14));
	   deleteDayMenuButton.setBounds(10, 576, 164, 23);
	   dayMenusPanel.add(deleteDayMenuButton);
	   
	   tabs.addTab("Menu Items", menuItems);
	   
	   JPanel menuItemsPanel = new JPanel();
	   menuItems.add(menuItemsPanel);
	   menuItemsPanel.setLayout(null);
	   
	   JScrollPane menuItemScrollPane = new JScrollPane();
	   menuItemScrollPane.setBounds(10, 5, 809, 177);
	   menuItemsPanel.add(menuItemScrollPane);
	   
	   menuItemsTable = new JTable();
	   menuItemsTable.setFont(new Font("Calibri", Font.BOLD, 12));
	   menuItemScrollPane.setViewportView(menuItemsTable);
	   menuItemsTable.setModel(new DefaultTableModel(
	   	new Object[][] {
	   		{null, null, null},
	   	},
	   	new String[] {
	   		"Meal Name", "Complexity Value", "HealthValue"
	   	}
	   ));
	   
	   JScrollPane menuFoodItemScrollPane = new JScrollPane();
	   menuFoodItemScrollPane.setBounds(10, 193, 522, 334);
	   menuItemsPanel.add(menuFoodItemScrollPane);
	   
	   menuItemsTable.setModel(menuItemsModel);
	   menuItemsTable.addMouseListener(new selectMenuItemMouseClickListener());
	   
	   menuFoodItemsTable = new JTable();
	   menuFoodItemsTable.setModel(new DefaultTableModel(
	   	new Object[][] {
	   		{null, null, null, null},
	   	},
	   	new String[] {
	   		"Food Name", "Category", "Health Value", "Prep Time"
	   	}
	   ));
	   menuFoodItemScrollPane.setViewportView(menuFoodItemsTable);
	   
	   menuFoodItemsTable.setModel(foodItemsMenuItemModel);
	   
	   JScrollPane menuIngredientsScrollPane = new JScrollPane();
	   menuIngredientsScrollPane.setBounds(539, 193, 280, 338);
	   menuItemsPanel.add(menuIngredientsScrollPane);
	   
	   menuIngredientsTable = new JTable();
	   menuIngredientsTable.setModel(new DefaultTableModel(
	   	new Object[][] {
	   		{null},
	   	},
	   	new String[] {
	   		"Ingredients"
	   	}
	   ));
	   menuIngredientsTable.setFont(new Font("Calibri", Font.BOLD, 12));
	   menuIngredientsScrollPane.setViewportView(menuIngredientsTable);
	   
	   menuIngredientsTable.setModel(ingredientsMenuItemModel);
	   
	   JButton createMenuItemButton = new JButton("Create New Menu Item");
//	   createMenuItemButton.addActionListener(new createMenuItemButtonListener());
	   createMenuItemButton.setFont(new Font("Calibri", Font.BOLD, 14));
	   createMenuItemButton.setBounds(621, 578, 198, 23);
	   menuItemsPanel.add(createMenuItemButton);
	   
	   JButton deleteMenuItemButton = new JButton("Delete Menu Item");
	   deleteMenuItemButton.addActionListener(new deleteMenuItemButtonListener());
	   deleteMenuItemButton.setFont(new Font("Calibri", Font.BOLD, 14));
	   deleteMenuItemButton.setBounds(10, 576, 164, 23);
	   menuItemsPanel.add(deleteMenuItemButton);
	   
	   tabs.addTab("Food Items", foodItems);
	   
	   JPanel foodItemsPanel = new JPanel();
	   foodItems.add(foodItemsPanel);
	   foodItemsPanel.setBounds(10, 0, 809, 548);
	   foodItemsPanel.setLayout(null);
	   
	   JScrollPane foodItemsScrollPane = new JScrollPane();
	   foodItemsScrollPane.setBounds(10, 5, 809, 262);
	   foodItemsPanel.add(foodItemsScrollPane);
	   
	   foodItemsTable = new JTable();
	   foodItemsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	   foodItemsTable.setFont(new Font("Calibri", Font.BOLD, 12));
	   foodItemsScrollPane.setViewportView(foodItemsTable);
	   foodItemsTable.setModel(new DefaultTableModel(
	   	new Object[][] {
	   		{null, null, null, null},
	   	},
	   	new String[] {
	   		"Food Name", "Category", "Health Value", "Prep Time"
	   	}
	   ));
	   
	   foodItemsTable.setModel(foodItemsModel);
	   foodItemsTable.addMouseListener(new selectFoodItemMouseClickListener());
	   
	   JScrollPane ingredientsScrollPane = new JScrollPane();
	   ingredientsScrollPane.setBounds(10, 274, 285, 262);
	   foodItemsPanel.add(ingredientsScrollPane);
	   
	   ingredientsTable = new JTable();
	   ingredientsTable.setRowSelectionAllowed(false);
	   ingredientsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	   ingredientsTable.setFont(new Font("Calibri", Font.BOLD, 12));
	   ingredientsTable.setModel(new DefaultTableModel(
	   	new Object[][] {
	   		{null},
	   	},
	   	new String[] {
	   		"Ingredients"
	   	}
	   ));
	   ingredientsScrollPane.setViewportView(ingredientsTable);
	   
	   ingredientsTable.setModel(ingredientsFoodItemModel);
	   
	   JScrollPane recipeScrollPane = new JScrollPane();
	   recipeScrollPane.setBounds(303, 274, 516, 262);
	   foodItemsPanel.add(recipeScrollPane);
	   
	   recipeTable = new JTable();
	   recipeTable.setRowSelectionAllowed(false);
	   recipeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	   recipeTable.setFont(new Font("Calibri", Font.BOLD, 12));
	   recipeTable.setModel(new DefaultTableModel(
	   	new Object[][] {
	   		{null},
	   	},
	   	new String[] {
	   		"Steps"
	   	}
	   ));
	   recipeScrollPane.setViewportView(recipeTable);
	   
	   recipeTable.setModel(recipeModel);
	   
	   JButton createFoodItemButton = new JButton("Create New Food Item");
	   createFoodItemButton.addActionListener(new createFoodItemButtonListener());
	   createFoodItemButton.setFont(new Font("Calibri", Font.BOLD, 14));
	   createFoodItemButton.setBounds(621, 578, 198, 23);
	   foodItemsPanel.add(createFoodItemButton);
	   
	   JButton deleteFoodItemButton = new JButton("Delete Food Item");
	   deleteFoodItemButton.addActionListener(new deleteFoodItemButtonListener());
	   deleteFoodItemButton.setFont(new Font("Calibri", Font.BOLD, 14));
	   deleteFoodItemButton.setBounds(10, 576, 164, 23);
	   foodItemsPanel.add(deleteFoodItemButton);
	   
	   tabs.addTab("User Accounts", userAccounts);
	   
	   JPanel usersPanel = new JPanel();
	   userAccounts.add(usersPanel);
	   usersPanel.setLayout(null);
	   
	   JScrollPane usersScrollPane = new JScrollPane();
	   usersScrollPane.setBounds(10, 0, 809, 548);
	   usersPanel.add(usersScrollPane);
	      
	   usersTable = new JTable();
	   usersTable.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
	   usersTable.setFont(new Font("Calibri", Font.BOLD, 12));
	   usersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	   usersTable.setModel(new DefaultTableModel(
	   	new Object[][] {
	   		{null, null, null, null, null},
	   	},
	   	new String[] {
	   		"First Name", "Last Name", "Email", "Age", "Role"
	   	}
	   ) {
	   	@SuppressWarnings("rawtypes")
		Class[] columnTypes = new Class[] {
	   		String.class, String.class, String.class, Integer.class, String.class
	   	};
	   	@SuppressWarnings({ "rawtypes", "unchecked" })
		public Class getColumnClass(int columnIndex) {
	   		return columnTypes[columnIndex];
	   	}
	   });
	   usersScrollPane.setViewportView(usersTable);
	   
	   usersTable.setModel(usersModel);  
       usersTable.addMouseListener(new selectUserMouseClickListener());
	   
	   JButton rstPasswordButton = new JButton("Reset Password");
//	   rstPasswordButton.addActionListener(new rstPasswordButtonListener());
	   rstPasswordButton.setFont(new Font("Calibri", Font.BOLD, 14));
	   rstPasswordButton.setBounds(678, 578, 141, 23);
	   usersPanel.add(rstPasswordButton);
	   
	   JButton deleteUserButton = new JButton("Delete User");
	   deleteUserButton.addActionListener(new deleteUserButtonListener());
	   getContentPane().setLayout(null);
	   deleteUserButton.setFont(new Font("Calibri", Font.BOLD, 14));
	   deleteUserButton.setBounds(10, 576, 143, 23);
	   usersPanel.add(deleteUserButton);
	   getContentPane().add(tabs);
	   
	   LOGGER.info("FoodMenuJFrame Instantiated");
	}
	
	
	
//	class rstPasswordButtonListener implements ActionListener {
//		public void actionPerformed(ActionEvent e) {
//			LOGGER.trace("rstPasswordButtonListener Initiated");
//			String newPassword = JOptionPane.showInputDialog("Resetting Password for user \n" + selectedUser.getEmailAddress());
//			LOGGER.info(String.format("Trusted password reset initiated for User %s", selectedUser.getEmailAddress()));
//			
//			LOGGER.trace(String.format("Begin User %s Password Reset", selectedUser.getEmailAddress()));
//			selectedUser.setPassword(newPassword);
//			try {
//				if(userManager.resetUserPassword(selectedUser)) {
//					JOptionPane.showMessageDialog(null, "User " + selectedUser.getEmailAddress() + " was successfully set!");
//					LOGGER.info(String.format("User %s password was successfully (re)set by %s", selectedUser.getEmailAddress(), user.getEmailAddress()));
//				} else {
//					JOptionPane.showMessageDialog(null, "Error in setting " + selectedUser.getEmailAddress() + " password!");
//					LOGGER.warn(String.format("Trusted password reset for User %s failed. Please try again.", selectedUser.getEmailAddress()));
//				}
//			} catch (ServiceLoadException | UserServiceException | HeadlessException | IOException e1) {
//				e1.printStackTrace();
//			}
//			
//			LOGGER.trace("rstPasswordButtonListener Completed");
//		}
//	}
//	
	class deleteUserButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			LOGGER.trace("deleteUserButtonListener  Initiated");
			
			int result = JOptionPane.showConfirmDialog(null, "Are you sure you would like to delete this User?\n" + selectedUser.getEmailAddress(), "Confirm Delete", JOptionPane.YES_NO_OPTION);
			
			if(result == JOptionPane.YES_OPTION) {
				try {
					if(foodMenuClient.deleteUser(selectedUser)) {
						JOptionPane.showMessageDialog(null, "User " + selectedUser.getEmailAddress() + " was successfully deleted!");
						LOGGER.info(String.format("User %s was successfully deleted by %s", selectedUser.getEmailAddress(), activeUser.getEmailAddress()));
						usersModel.setUsers(foodMenuClient.retrieveAllUsers());
						LOGGER.trace("Retrieving All Users for Users Table");
						usersModel.fireTableDataChanged();
						LOGGER.trace("Refreshing view for Users table");
					} else {
						JOptionPane.showMessageDialog(null, "System Failed to delete user \n" + selectedUser.getEmailAddress());
						LOGGER.warn(String.format("System Failed to delete user %s", selectedUser.getEmailAddress()));
					}
				} catch (ClassNotFoundException | IOException e1)  {
					e1.printStackTrace();
//				} catch (UserPrivilegesException e2) {
//					JOptionPane.showMessageDialog(null, "System failed to delete user \n" + selectedUser.getEmailAddress() + "\n\n" + e2.getMessage());
//					LOGGER.warn(String.format("System Failed to delete user %s, because %s lacks administrator permissions", selectedUser.getEmailAddress(), user.getEmailAddress()));
				}
				
			} else {
				JOptionPane.showMessageDialog(null, "User " + selectedUser.getEmailAddress() + " was not deleted!");
				LOGGER.trace(String.format("User %s declined to delete the specified user account", activeUser.getEmailAddress()));
			}
			
			LOGGER.trace("deleteUserButtonListener Completed");
		}
	}
	
	class createFoodItemButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			LOGGER.trace("createFoodItemButtonListener Initiated");
			CreateFoodItemJFrame createFoodItemJFrame;

			LOGGER.trace("Prompting user with CreateFoodItemJFrame");
			createFoodItemJFrame = new CreateFoodItemJFrame();
			createFoodItemJFrame.setupCreateFoodItemJFrame(foodMenuClient);
			createFoodItemJFrame.setVisible(true);
			
			createFoodItemJFrame.addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowClosed(java.awt.event.WindowEvent windowEvent) {
					LOGGER.trace("CreateFoodItemJFrame closed. Begin refreshing foodItemsTable");
					JOptionPane.showMessageDialog(null, "Refreshing Food Items Table!!");
			        try {
			        	LOGGER.trace("Retrieving All FoodItems from Database");
						foodItemsModel.setFoodItems(foodMenuClient.retrieveAllFoodItem());
						LOGGER.trace("Refereshing FoodItemsTable");
						foodItemsModel.fireTableDataChanged();
					} catch (ClassNotFoundException | IOException e1)  {
						e1.printStackTrace();
						LOGGER.fatal("Unable to retrieve FoodItems from Table");
					}
			        
			        LOGGER.trace("Selecting first FoodItem within FoodItemsTable");
					String foodName = foodItemsTable.getModel().getValueAt(0, 0).toString();

					LOGGER.trace("Populating Food Tab information fields with selected foodItem");
					try {
						selectedFoodItem = foodMenuClient.retrieveFoodItem(foodName);
						ingredientsFoodItemModel.setFoodItem(selectedFoodItem);
						ingredientsFoodItemModel.fireTableDataChanged();
						recipeModel.setFoodItem(selectedFoodItem);
						LOGGER.debug(String.format("Selected FoodItem: FoodName:%s", selectedFoodItem.getFoodName()));
						recipeModel.fireTableDataChanged();
					} catch (ClassNotFoundException | IOException e1)  {
						e1.printStackTrace();
					}
				}
			});
			
			LOGGER.trace("createFoodItemButtonListener Completed");
		}
	}
	
	class deleteFoodItemButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			LOGGER.trace("deleteFoodItemButtonListener Initiated");
			
			int result = JOptionPane.showConfirmDialog(null, "Are you sure you would like to delete this Food Item?\n" + selectedFoodItem.getFoodName(), "Confirm Delete", JOptionPane.YES_NO_OPTION);
			
			if(result == JOptionPane.YES_OPTION) {
				try {
					if(foodMenuClient.deleteFoodItem(selectedFoodItem)) {
						JOptionPane.showMessageDialog(null, "Food Item \"" + selectedFoodItem.getFoodName() + "\" was successfully deleted!");
						LOGGER.info(String.format("User %s successfully deleted FoodItem %s", activeUser.getEmailAddress(), selectedFoodItem.getFoodName()));

						LOGGER.trace("Retrieving All FoodItems for FoodItem Tables");
						ArrayList<FoodItem> foodItems = foodMenuClient.retrieveAllFoodItem(); 
						
						LOGGER.trace("Refreshing FoodItem Tables View");
						foodItemsModel.setFoodItems(foodItems);
						foodItemsModel.fireTableDataChanged();
						
						LOGGER.trace("Clear FoodItem Selection and the Ingredients & Recipe tables");
						if(foodItems.size() == 0) {
							ingredientsFoodItemModel.setFoodItem(new ArrayList<String>());
							ingredientsFoodItemModel.fireTableDataChanged();
							recipeModel.setFoodItem(new FoodItem());
							recipeModel.fireTableDataChanged();
							return;
						}
						
						String foodName = foodItemsTable.getModel().getValueAt(0, 0).toString();
						LOGGER.trace(String.format("Select First Item (%s) in FoodItem Table", foodName));

						LOGGER.trace(String.format("Populate Ingredients and Recipe Tables with Selected Food Item (%s)", foodName));
						try {
							selectedFoodItem = foodMenuClient.retrieveFoodItem(foodName);
							ingredientsFoodItemModel.setFoodItem(selectedFoodItem);
							ingredientsFoodItemModel.fireTableDataChanged();
							recipeModel.setFoodItem(selectedFoodItem);
							recipeModel.fireTableDataChanged();
						} catch (ClassNotFoundException | IOException e1)  {
							e1.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null, "System Failed to delete food item \"" + selectedFoodItem.getFoodName() + "\"");
						LOGGER.warn(String.format("System failed to delete food item %s", selectedFoodItem.getFoodName()));
					}
				} catch (ClassNotFoundException | IOException e1)  {
					e1.printStackTrace();
//				} catch (UserPrivilegesException e2) {
//					JOptionPane.showMessageDialog(null, "System Failed to delete food item \n" + selectedFoodItem.getFoodName() + "\n\n" + e2.getMessage());
//					LOGGER.warn(String.format("System failed to delete food item %s, because %s lacks administrator permissions", selectedFoodItem.getFoodName(), user.getEmailAddress()));
				}
			} else {
				JOptionPane.showMessageDialog(null, "Food Item \"" + selectedFoodItem.getFoodName() + "\" was not deleted!");
				LOGGER.trace(String.format("User %s declined to delete the specified food item", activeUser.getEmailAddress()));
			}
			
			LOGGER.trace("deleteFoodItemButtonListener Completed");
		}
	}
	
//	class createMenuItemButtonListener implements ActionListener {
//		public void actionPerformed(ActionEvent e) {
//			LOGGER.trace("createMenuItemButtonListener Initiated");
//			CreateMenuItemJFrame createMenuItemJFrame;
//			
//			LOGGER.trace("Prompting user with CreateMenuItemJFrame");
//			createMenuItemJFrame = new CreateMenuItemJFrame();
//			createMenuItemJFrame.setVisible(true);
//			
//			createMenuItemJFrame.addWindowListener(new java.awt.event.WindowAdapter() {
//				public void windowClosed(java.awt.event.WindowEvent windowEvent) {
//					LOGGER.trace("CreateMenuItemJFrame closed. Begin refreshing menuItemsTable");
//					JOptionPane.showMessageDialog(null, "Refreshing Menu Items Table!!");
//					try {
//						LOGGER.trace("Retrieving All MenuItems from Database");
//						menuItemsModel.setMenuItems(menuItemManager.retrieveAllMenuItems());
//						LOGGER.trace("Refreshing MenuItemsTable");
//						menuItemsModel.fireTableDataChanged();
//					} catch (ServiceLoadException | FoodItemServiceException | MenuItemServiceException e) {
//						e.printStackTrace();
//						LOGGER.fatal("Unable to retrieve MenuItems from database");
//					}
//			        
//					LOGGER.trace("Selecting first MealName within MealItemTable");
//					String mealName = menuItemsTable.getModel().getValueAt(0, 0).toString();
//					LOGGER.debug("MealName:" + mealName);
//					ArrayList<String> ingredients = new ArrayList<String>();
//
//					try {
//						selectedMenuItem = menuItemManager.retrieveMenuItem(mealName);
//						foodItemsMenuItemModel.setFoodItems(selectedMenuItem.getFoodList());
//						foodItemsMenuItemModel.fireTableDataChanged();
//						menuItemManager.retrieveMenuItem(mealName).getFoodList().forEach(food -> {{
//							ingredients.addAll(food.getIngredients());
//						}});
//						ingredientsMenuItemModel.setFoodItem(ingredients);
//						LOGGER.debug(String.format("Selected MealItemL MealName:%s", mealName));
//						ingredientsMenuItemModel.fireTableDataChanged();
//					} catch (ServiceLoadException | FoodItemServiceException | MenuItemServiceException e1) {
//						e1.printStackTrace();
//					}
//				}
//			});
//			
//			LOGGER.trace("createMenuItemButtonListener Completed");
//		}
//	}
//	
	class deleteMenuItemButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			LOGGER.trace("deleteMenuItemButtonListener Initiated");
			
			int result = JOptionPane.showConfirmDialog(null, "Are you sure you would like to delete this Menu Item?\n" + selectedMenuItem.getMealName(), "Confirm Delete", JOptionPane.YES_NO_OPTION);
			
			if(result == JOptionPane.YES_OPTION) {
				try {
					if(foodMenuClient.deleteMenuItem(selectedMenuItem)) {
						JOptionPane.showMessageDialog(null, "Menu Item \"" + selectedMenuItem.getMealName() + "\" was successfully deleted!");
						LOGGER.info(String.format("User %s successfully deleted MenuItem %s", activeUser.getEmailAddress(), selectedMenuItem.getMealName()));
						
						LOGGER.trace("Retrieving All MenuItems for MenuItem Tables");
						ArrayList<MenuItem> menuItems = foodMenuClient.retrieveAllMenuItem(); 
						
						menuItemsModel.setMenuItems(menuItems);
						menuItemsModel.fireTableDataChanged();
						
						LOGGER.trace("Retrieveing All Ingredients for MenuItem Ingredients Table");
						ArrayList<String> ingredients = new ArrayList<String>();
						
						LOGGER.trace("Clear MenuItem Selection and the Ingredients table");
						if(menuItems.size() == 0) {
							foodItemsMenuItemModel.setFoodItems(new ArrayList<FoodItem>());
							foodItemsMenuItemModel.fireTableDataChanged();
							ingredientsMenuItemModel.setFoodItem(ingredients);
							ingredientsMenuItemModel.fireTableDataChanged();														
							return;
						}
						
						String mealName = menuItemsTable.getModel().getValueAt(0, 0).toString();
						LOGGER.trace(String.format("Select First Item (%s) in MenuItem Table", mealName));
						
						LOGGER.trace(String.format("Populate Ingredients Table with Selected MenuItem (%s)", mealName));
						try {
							foodItemsMenuItemModel.setFoodItems(selectedMenuItem.getFoodList());
							foodItemsMenuItemModel.fireTableDataChanged();
							foodMenuClient.retrieveMenuItem(mealName).getFoodList().forEach(food -> {{
								ingredients.addAll(food.getIngredients());
							}});
							ingredientsMenuItemModel.setFoodItem(ingredients);
							ingredientsMenuItemModel.fireTableDataChanged();
						} catch (ClassNotFoundException | IOException e1)  {
							e1.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null, "System Failed to delete menu item \"" + selectedMenuItem.getMealName() + "\"");
						LOGGER.warn(String.format("System failed to delete menu item %s", selectedMenuItem.getMealName()));
					}
				} catch (ClassNotFoundException | IOException e1)  {
					e1.printStackTrace();;
//				} catch (UserPrivilegesException e2) {
//					JOptionPane.showMessageDialog(null, "System Failed to delete menu item \n" + selectedMenuItem.getMealName() + "\n\n" + e2.getMessage());
//					LOGGER.warn(String.format("System failed to delete menu item %s, because %s lacks administrator permissions", selectedMenuItem.getMealName(), user.getEmailAddress()));
				}
			} else {
				JOptionPane.showMessageDialog(null, "Menu Item \"" + selectedMenuItem.getMealName() + "\" was not deleted!");
				LOGGER.trace(String.format("User %s declined to delete the specified menu item", activeUser.getEmailAddress()));
			}
			
			LOGGER.trace("deleteMenuItemButtonListener Completed");
		}
	}
	
//	class createDayMenuButtonListener implements ActionListener {
//		public void actionPerformed(ActionEvent e) {
//			LOGGER.trace("createDayMenuButtonListener Initiated");
//			CreateDayMenuJFrame createDayMenuJFrame;
//			
//			LOGGER.trace("Prompting user with CreateDayMenuJFrame");
//			createDayMenuJFrame = new CreateDayMenuJFrame();
//			createDayMenuJFrame.setVisible(true);
//			
//			createDayMenuJFrame.addWindowListener(new java.awt.event.WindowAdapter() {
//				public void windowClosed(java.awt.event.WindowEvent windowEvent) {
//					LOGGER.trace("CreateDayMenuJFrame closed. Begin refreshing DayMenusTable");
//					JOptionPane.showMessageDialog(null, "Refreshing Day Menu Table!!");
//					try {
//						LOGGER.trace("Retrieving All DayMenus from Database");
//						dayMenuModel.setDayMenus(dayMenuManager.retrieveAllDayMenus());
//						dayMenuModel.fireTableDataChanged();
//					} catch (ServiceLoadException | DayMenuServiceException | MenuItemServiceException
//							| FoodItemServiceException e) {
//						e.printStackTrace();
//						LOGGER.fatal("Unable to retrieve DayMenus from database");
//					}
//					
//				}
//			});
//			
//			LOGGER.trace("createDayMenuButtonListener Completed");
//		}
//	}
//	
	class deleteDayMenuButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			LOGGER.trace("deleteDayMenuButtonListener Initiated");
			
			int result = JOptionPane.showConfirmDialog(null, "Are you sure you would like to delete this Day Menu?\n" + selectedDayMenu.getDateString(), "Confirm Delete", JOptionPane.YES_NO_OPTION);
			
			if(result == JOptionPane.YES_OPTION) {
				try {
					if(foodMenuClient.deleteDayMenu(selectedDayMenu)) {
						JOptionPane.showMessageDialog(null, "Day Menu \"" + selectedDayMenu.getDateString() + "\" was successfully deleted!");
						LOGGER.info(String.format("User %s  successfully deleted DayMenu %s", activeUser.getEmailAddress(), selectedDayMenu.getDateString()));
	
						LOGGER.trace("Retrieving All DayMenus for DayMenu Table");
						ArrayList<DayMenu> dayMenus = foodMenuClient.retrieveAllDayMenu();
						
						dayMenuModel.setDayMenus(dayMenus);
						dayMenuModel.fireTableDataChanged();
							
			    		ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
						ArrayList<FoodItem> foodItems = new ArrayList<FoodItem>();
						ArrayList<String> ingredients = new ArrayList<String>();
						
						LOGGER.trace("Clear DayMenu Selection and DayMenuTab Tables");
						if(dayMenus.size() == 0) {
							menuItemsDayMenuModel.setMenuItems(menuItems);
							menuItemsDayMenuModel.fireTableDataChanged();
							foodItemsDayMenuModel.setFoodItems(foodItems);
							foodItemsDayMenuModel.fireTableDataChanged();
							ingredientsDayMenuModel.setFoodItem(ingredients);
							ingredientsDayMenuModel.fireTableDataChanged();
							
							return;
						}						
												
						String dateString = dayMenusTable.getModel().getValueAt(0, 0).toString();
			    		
						LOGGER.trace(String.format("Populate DayMenuTab Tables and select DayMenu for First Date in the DayMenu Table"));
						try {
							selectedDayMenu = foodMenuClient.retrieveDayMenu(dateString);
							menuItemsDayMenuModel.setMenuItems(selectedDayMenu.getMenuList());
							menuItemsDayMenuModel.fireTableDataChanged();
							foodMenuClient.retrieveDayMenu(dateString).getMenuList().forEach(menu -> {{
								foodItems.addAll(menu.getFoodList());
							}});
							foodItemsDayMenuModel.setFoodItems(foodItems);
							foodItemsDayMenuModel.fireTableDataChanged();
							foodItems.forEach(item -> {{
								try {
									ingredients.addAll(foodMenuClient.retrieveFoodItem(item.getFoodName()).getIngredients());
								} catch (ClassNotFoundException | IOException e1)  {
									e1.printStackTrace();
								}
							}});
							ingredientsDayMenuModel.setFoodItem(ingredients);
							ingredientsDayMenuModel.fireTableDataChanged();
						} catch (ClassNotFoundException | IOException e1)  {
							e1.printStackTrace();
						} 
					} else {
						JOptionPane.showMessageDialog(null, "System Failed to delete day Menu " + selectedDayMenu.getDateString());
						LOGGER.warn(String.format("System failed to delete day menu %s", selectedDayMenu.getDateString()));
					}
				} catch (ClassNotFoundException | IOException e1)  {
					e1.printStackTrace();
//				} catch (UserPrivilegesException e2) {
//					JOptionPane.showMessageDialog(null, "System Failed to delete day menu \n" + selectedDayMenu.getDateString() + "\n\n" + e2.getMessage());
//					LOGGER.warn(String.format("System failed to delete day menu %s, because %s lacks administrator permissions", selectedDayMenu.getDateString(), user.getEmailAddress()));
				}
			} else {
				JOptionPane.showMessageDialog(null, "Day Menu " + selectedDayMenu.getDateString() + " was not deleted!");
				LOGGER.trace(String.format("User %s declined to delete the specified day menu", activeUser.getEmailAddress()));
			}
			
			LOGGER.trace("deleteDayMenuButtonListener Completed");
		}
	}
	
	class selectUserMouseClickListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			LOGGER.trace("selectUserMouseClickListener Initiated");

			LOGGER.trace("Read Selected User Account from UsersTab Users Table");
			int row = usersTable.getSelectedRow();
			String email = usersTable.getModel().getValueAt(row, 2).toString();
			
			LOGGER.trace("Retrieve User Account from Database");
			
			try {
				selectedUser = foodMenuClient.retrieveUser(email);
			} catch (ClassNotFoundException | IOException e1) {
				e1.printStackTrace();
			}
			
			
			LOGGER.trace("selectUserMouseClickListener Completed");
		}
		
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
	}
	
	class selectFoodItemMouseClickListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			LOGGER.trace("selectFoodItemMouseClickListener Initiated");
	
			LOGGER.trace("Read Selected FoodItem from FoodItemsTab FoodItem Table");
			int row = foodItemsTable.getSelectedRow();
			String foodName = foodItemsTable.getModel().getValueAt(row, 0).toString();

			LOGGER.trace("Retrieve Selected FoodItem, Ingredients, and Recipe from Database");

			try {
				selectedFoodItem = foodMenuClient.retrieveFoodItem(foodName);
				ingredientsFoodItemModel.setFoodItem(selectedFoodItem);
				ingredientsFoodItemModel.fireTableDataChanged();
				recipeModel.setFoodItem(selectedFoodItem);
				recipeModel.fireTableDataChanged();
			} catch (ClassNotFoundException | IOException e1) {
				e1.printStackTrace();
			}
			
			LOGGER.trace("selectFoodItemMouseClickListener Completed");
		}

		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
	}
	
	class selectMenuItemMouseClickListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			LOGGER.trace("selectMenuItemMouseClickListener Initiated");
			
			LOGGER.trace("Read Selected MenuItem from MenuItemTab MenuItem Table");			
			int row = menuItemsTable.getSelectedRow();
			String mealName = menuItemsTable.getModel().getValueAt(row, 0).toString();
			ArrayList<String> ingredients = new ArrayList<String>();

			LOGGER.trace("Retrieve Selected MenuItem, FoodItems, and Ingredients from Database");
			try {
				selectedMenuItem = foodMenuClient.retrieveMenuItem(mealName);
				foodItemsMenuItemModel.setFoodItems(selectedMenuItem.getFoodList());
				foodItemsMenuItemModel.fireTableDataChanged();
				foodMenuClient.retrieveMenuItem(mealName).getFoodList().forEach(food -> {{
					ingredients.addAll(food.getIngredients());
				}});
				ingredientsMenuItemModel.setFoodItem(ingredients);
				ingredientsMenuItemModel.fireTableDataChanged();
			} catch (ClassNotFoundException | IOException e1) {
				e1.printStackTrace();
			}
			
			LOGGER.trace("selectMenuItemMouseClickListener Completed");
		}

		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
	}
	
	class selectDayMenuMouseClickListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			LOGGER.trace("selectDayMenuMouseClickListener Initiated");
			
			LOGGER.trace("Read Selected DayMenu from DayMenusTab DayMenu Table");
			int row = dayMenusTable.getSelectedRow();
			
			String dateString = "";
			
			LOGGER.trace("Retrieve Selected DayMenu, MenuItems, FoodItems, and Ingredients from Database");

			dateString = dayMenusTable.getModel().getValueAt(row, 0).toString();

    		
    		ArrayList<FoodItem> foodItems = new ArrayList<FoodItem>();
			ArrayList<String> ingredients = new ArrayList<String>();
			
			try {
				selectedDayMenu = foodMenuClient.retrieveDayMenu(dateString);
				menuItemsDayMenuModel.setMenuItems(selectedDayMenu.getMenuList());
				menuItemsDayMenuModel.fireTableDataChanged();
				selectedDayMenu.getMenuList().forEach(menu -> {{
					foodItems.addAll(menu.getFoodList());
				}});
				foodItemsDayMenuModel.setFoodItems(foodItems);
				foodItemsDayMenuModel.fireTableDataChanged();
				foodItems.forEach(item -> {{
					try {
						ingredients.addAll(foodMenuClient.retrieveFoodItem(item.getFoodName()).getIngredients());
					} catch (ClassNotFoundException | IOException e1) {
						e1.printStackTrace();
					}
				}});
				ingredientsDayMenuModel.setFoodItem(ingredients);
				ingredientsDayMenuModel.fireTableDataChanged();
			} catch (ClassNotFoundException | IOException e1) {
				e1.printStackTrace();
			}
			
			LOGGER.trace("selectDayMenuMouseClickListener Completed");
		}
		
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
	}
}