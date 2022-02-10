package com.foodmenuclient.model.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.foodmenu.model.domain.DayMenu;
import com.foodmenu.model.domain.FoodItem;
import com.foodmenu.model.domain.MenuItem;
import com.foodmenu.model.domain.User;
import com.foodmenuclient.controller.FoodMenuClient;

public class FoodMenuClientTest {
	
	private static String testClass = "FoodMenuClient";
	
	private static FoodMenuClient foodMenuClient;
	
	private static User user = new User("Zach", "Stanfill", "zstanfill@regis.edu", "ghostbuster", 29, "admin");
	
	private static FoodItem foodItem;
	private static MenuItem menuItem;
	private static DayMenu dayMenu;
	private static User testUser;

	@SuppressWarnings("resource")
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ObjectInputStream foodItemObject = new ObjectInputStream(new FileInputStream("data/testObjectFiles/foodItem_Meatloaf.obj"));
		foodItem = (FoodItem) foodItemObject.readObject();
		
		ObjectInputStream menuItemObject = new ObjectInputStream(new FileInputStream("data/testObjectFiles/menuItem_BaconEggsToast.obj"));
		menuItem = (MenuItem)menuItemObject.readObject();
		
		ObjectInputStream dayMenuObject = new ObjectInputStream(new FileInputStream("data/testObjectFiles/dayMenu_20211106.obj"));
		dayMenu = (DayMenu)dayMenuObject.readObject();
		
		ObjectInputStream userObject = new ObjectInputStream(new FileInputStream("data/testObjectFiles/user_testUser.obj"));
		testUser = (User)userObject.readObject();
		
		testUser.setPassword("Password");

		
		foodMenuClient = new FoodMenuClient(user);
		
		foodMenuClient.openConnection();
		System.out.println("Connection Opened for Test"); 
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		foodMenuClient.closeConnection();
		System.out.println("Connection Closed for Test");
	}
	
	@Test
	public void inSequenceTest() throws ClassNotFoundException, IOException {
		// FoodItem Tests
		testCreateFoodItem();
		testRetrieveFoodItem();
		testRetrieveFoodItems();
		testDeleteFoodItem();
		
		System.out.println();
		
		// MenuItem Tests
		testCreateMenuItem();
		testRetrieveMenuItem();
		testRetrieveMenuItems();
		testDeleteMenuItem();
		
		System.out.println();
		
		// DayMenu Tests
		testCreateDayMenu();
		testRetrieveDayMenu();
		testRetrieveDayMenus();
		testDeleteDayMenu();
		
		
		System.out.println();
		
		// User Tests
		testCreateUser();
		testRetrieveUser();
		testRetrieveUsers();
		testDeleteUser();
	}
	
	public void testCreateFoodItem() throws ClassNotFoundException, IOException {
		String testMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
		String testTrace = testClass + "." + testMethod;
		
		assertTrue(testMethod, foodMenuClient.createFoodItem(foodItem));
		System.out.println("Passed  :  " + testTrace);
	}
	
	public void testCreateMenuItem() throws ClassNotFoundException, IOException {
		String testMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
		String testTrace = testClass + "." + testMethod;
		
		ArrayList<FoodItem> foodItems = menuItem.getFoodList();
		foodItems.forEach(item -> {
			try {
				foodMenuClient.createFoodItem(item);
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		});
		
		assertTrue(testMethod, foodMenuClient.createMenuItem(menuItem));
		System.out.println("Passed  :  " + testTrace);
	}
	
	public void testCreateDayMenu() throws ClassNotFoundException, IOException {
		String testMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
		String testTrace = testClass + "." + testMethod;
		
		ArrayList<MenuItem> menuList;
		ArrayList<FoodItem> foodList = new ArrayList<FoodItem>();
		
		
		menuList = dayMenu.getMenuList();
		
		menuList.forEach(menu -> {
			foodList.addAll(menu.getFoodList());
		});
		
		foodList.forEach(food -> {
			try {
				foodMenuClient.createFoodItem(food);
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		});
		menuList.forEach(menu -> {
			try {
				foodMenuClient.createMenuItem(menu);
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		});
		
		assertTrue(testMethod, foodMenuClient.createDayMenu(dayMenu));
		System.out.println("Passed  :  " + testTrace);
	}
	
	public void testCreateUser() throws ClassNotFoundException, IOException {
		String testMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
		String testTrace = testClass + "." + testMethod;
		
		assertTrue(testMethod, foodMenuClient.createUser(testUser));
		System.out.println("Passed  :  " + testTrace);
	}
	
	public void testRetrieveFoodItem() throws ClassNotFoundException, IOException {
		String testMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
		String testTrace = testClass + "." + testMethod;
		
		FoodItem retrievedItem = foodMenuClient.retrieveFoodItem(foodItem.getFoodName());
		
		assertTrue(testMethod, retrievedItem.validate());
		System.out.println("Passed  :  " + testTrace);
	}
	
	public void testRetrieveMenuItem() throws ClassNotFoundException, IOException {
		String testMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
		String testTrace = testClass + "." + testMethod;
		
		MenuItem retrievedItem = foodMenuClient.retrieveMenuItem(menuItem.getMealName());
		
		assertTrue(testMethod, retrievedItem.validate());
		System.out.println("Passed  :  " + testTrace);
	}
	
	public void testRetrieveDayMenu() throws ClassNotFoundException, IOException {
		String testMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
		String testTrace = testClass + "." + testMethod;
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("MMM-d-yyyy");
		
		DayMenu retrievedItem = foodMenuClient.retrieveDayMenu(sdf1.format(dayMenu.getDate().getTime()).toString());

		assertTrue(testMethod, retrievedItem.validate());
		System.out.println("Passed  :  " + testTrace);
	}
	
	public void testRetrieveUser() throws ClassNotFoundException, IOException {
		String testMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
		String testTrace = testClass + "." + testMethod;
		
		User retrievedItem = foodMenuClient.retrieveUser(testUser.getEmailAddress());
		
		assertTrue(testMethod, retrievedItem.validateNoPassword());
		System.out.println("Passed  :  " + testTrace);
	}

	public void testRetrieveFoodItems() throws ClassNotFoundException, IOException {
		String testMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
		String testTrace = testClass + "." + testMethod;
		ArrayList<FoodItem> foodItems = foodMenuClient.retrieveAllFoodItem();
		
		if (foodItems.size() > 0) {
			foodItems.forEach(item -> {				
				if(!item.validate()) {
					System.out.println(testTrace + ": FoodItem " + item.getFoodName() + " failed to Validate. Test Failed!"); 
					assertFalse(testMethod, true);
				} 
			});
			System.out.println("Passed  :  " + testTrace);
			assertTrue(testMethod, true);
		} else {
			System.out.println(testTrace + ": FoodItems ArrayList is Empty! Test Failed!");
			assertFalse(testMethod, true);
		}
	}
	
	public void testRetrieveMenuItems() throws ClassNotFoundException, IOException {
		String testMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
		String testTrace = testClass + "." + testMethod;
		ArrayList<MenuItem> menuItems = foodMenuClient.retrieveAllMenuItem();
		
		if (menuItems.size() > 0) {
			menuItems.forEach(item -> {				
				if(!item.validate()) {
					System.out.println(testTrace + ": MenuItem " + item.getMealName() + " failed to Validate. Test Failed!"); 
					assertFalse(testMethod, true);
				} 
			});
			System.out.println("Passed  :  " + testTrace);
			assertTrue(testMethod, true);
		} else {
			System.out.println(testTrace + ": MenuItems ArrayList is Empty! Test Failed!");
			assertFalse(testMethod, true);
		}
	}
	
	public void testRetrieveDayMenus() throws ClassNotFoundException, IOException {
		String testMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
		String testTrace = testClass + "." + testMethod;
		ArrayList<DayMenu> dayMenus = foodMenuClient.retrieveAllDayMenu();
		
		if (dayMenus.size() > 0) {
			dayMenus.forEach(item -> {				
				if(!item.validate()) {
					System.out.println(testTrace + ": DayMenu " + item.getDateString() + " failed to Validate. Test Failed!"); 
					assertFalse(testMethod, true);
				} 
			});
			System.out.println("Passed  :  " + testTrace);
			assertTrue(testMethod, true);
		} else {
			System.out.println(testTrace + ": DayMenu ArrayList is Empty! Test Failed!");
			assertFalse(testMethod, true);
		}
	}
	
	public void testRetrieveUsers() throws ClassNotFoundException, IOException {
		String testMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
		String testTrace = testClass + "." + testMethod;
		ArrayList<User> users = foodMenuClient.retrieveAllUsers();
		
		if (users.size() > 0) {
			users.forEach(item -> {				
				if(!item.validateNoPassword()) {
					System.out.println(testTrace + ": User " + item.getEmailAddress() + " failed to Validate. Test Failed!"); 
					assertFalse(testMethod, true);
				} 
			});
			System.out.println("Passed  :  " + testTrace);
			assertTrue(testMethod, true);
		} else {
			System.out.println(testTrace + ": Users ArrayList is Empty! Test Failed!");
			assertFalse(testMethod, true);
		}
	}
	
	public void testDeleteFoodItem() throws ClassNotFoundException, IOException {
		String testMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
		String testTrace = testClass + "." + testMethod;
		
		assertTrue(testMethod, foodMenuClient.deleteFoodItem(foodItem));
		System.out.println("Passed  :  " + testTrace);
	}
	
	public void testDeleteMenuItem() throws ClassNotFoundException, IOException {
		String testMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
		String testTrace = testClass + "." + testMethod;
		
		assertTrue(testMethod, foodMenuClient.deleteMenuItem(menuItem));
		System.out.println("Passed  :  " + testTrace);
		
		ArrayList<FoodItem> foodItems = menuItem.getFoodList();
		foodItems.forEach(item -> {
			try {
				foodMenuClient.deleteFoodItem(item);
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		});
	}
	
	public void testDeleteDayMenu() throws ClassNotFoundException, IOException {
		String testMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
		String testTrace = testClass + "." + testMethod;
		
		assertTrue(testMethod, foodMenuClient.deleteDayMenu(dayMenu));
		System.out.println("Passed  :  " + testTrace);
		
		ArrayList<MenuItem> menuList;
		ArrayList<FoodItem> foodList = new ArrayList<FoodItem>();
		
		
		menuList = dayMenu.getMenuList();
		
		menuList.forEach(menu -> {
			foodList.addAll(menu.getFoodList());
		});
		
		foodList.forEach(food -> {
			try {
				foodMenuClient.deleteFoodItem(food);
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		});
		menuList.forEach(menu -> {
			try {
				foodMenuClient.deleteMenuItem(menu);
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		});
	}
	
	public void testDeleteUser() throws ClassNotFoundException, IOException {
		String testMethod = Thread.currentThread().getStackTrace()[1].getMethodName();
		String testTrace = testClass + "." + testMethod;
		
		assertTrue(testMethod, foodMenuClient.deleteUser(testUser));
		System.out.println("Passed  :  " + testTrace);
	}
}
