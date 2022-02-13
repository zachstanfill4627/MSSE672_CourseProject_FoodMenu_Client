package com.foodmenuclient.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.foodmenu.model.domain.*;

public class FoodMenuClient {
	
	private String email = "";
	private String sessionKey = "";
	
	private Socket socket = null;
    private OutputStream outputStream;
    private InputStream inputStream;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
	
	// Default Constructor 
	public FoodMenuClient(String email, String sessionKey) {
		this.email = email;
		this.sessionKey = sessionKey;
	}
	
	public void openConnection() throws UnknownHostException, IOException, Exception {
		
		if(email.equals("") || sessionKey.equals("")) {
			throw new Exception ("FoodMenuClient.openConnection: Cannot open connection because no users has been set!");
		}
		
		// establish a connection by providing host and port number
        this.socket = new Socket("localhost", 40010);
        // get the input stream from the connected socket
        this.outputStream = socket.getOutputStream();
        this.inputStream = socket.getInputStream();
        // create a DataInputStream so we can read data from it.
        this.objectOutputStream =  new ObjectOutputStream(outputStream);
        this.objectInputStream = new ObjectInputStream(inputStream);

		UserWrapper userWrapper = new UserWrapper();
		userWrapper.setEmail(email);
		userWrapper.setAuthToken(sessionKey);
		
        objectOutputStream.writeObject(userWrapper);
	}
	
	public void closeConnection() throws IOException {
		socket.close();
	}
	
	public boolean createFoodItem(FoodItem foodItem) throws IOException, ClassNotFoundException {
		objectOutputStream.writeObject(new CreateWrapper(1, foodItem));
		Response response = (Response) objectInputStream.readObject();
		return response.getFlag();
	}
	
	public boolean createMenuItem(MenuItem menuItem) throws IOException, ClassNotFoundException {
		objectOutputStream.writeObject(new CreateWrapper(2, menuItem));
		Response response = (Response) objectInputStream.readObject();
		return response.getFlag();
	}
	
	public boolean createDayMenu(DayMenu dayMenu) throws IOException, ClassNotFoundException {
		objectOutputStream.writeObject(new CreateWrapper(3, dayMenu));
		Response response = (Response) objectInputStream.readObject();
		return response.getFlag();
	}
	
	public boolean createUser(User user) throws IOException, ClassNotFoundException {
		objectOutputStream.writeObject(new CreateWrapper(4, user));
		Response response = (Response) objectInputStream.readObject();
		return response.getFlag();
	}
	
	public FoodItem retrieveFoodItem(String foodName) throws IOException, ClassNotFoundException {
		objectOutputStream.writeObject(new RetrieveWrapper(1, true, foodName));
		Response response = (Response) objectInputStream.readObject();
		return response.getFoodItem();
	}
	
	public MenuItem retrieveMenuItem(String mealName) throws IOException, ClassNotFoundException {
		objectOutputStream.writeObject(new RetrieveWrapper(2, true, mealName));
		Response response = (Response) objectInputStream.readObject();
		return response.getMenuItem();
	}
	
	public DayMenu retrieveDayMenu(String date) throws IOException, ClassNotFoundException {
		objectOutputStream.writeObject(new RetrieveWrapper(3, true, date));
		Response response = (Response) objectInputStream.readObject();
		return response.getDayMenu();
	}
	
	public User retrieveUser(String email) throws IOException, ClassNotFoundException {
		objectOutputStream.writeObject(new RetrieveWrapper(4, true, email));
		Response response = (Response) objectInputStream.readObject();
		return response.getUser();
	}
	
	public ArrayList<FoodItem> retrieveAllFoodItem() throws IOException, ClassNotFoundException {
		objectOutputStream.writeObject(new RetrieveWrapper(1));
		Response response = (Response) objectInputStream.readObject();
		return response.getFoodItems();
	}
	
	public ArrayList<MenuItem> retrieveAllMenuItem() throws IOException, ClassNotFoundException {
		objectOutputStream.writeObject(new RetrieveWrapper(2));
		Response response = (Response) objectInputStream.readObject();
		return response.getMenuItems();
	}
	
	public ArrayList<DayMenu> retrieveAllDayMenu() throws IOException, ClassNotFoundException {
		objectOutputStream.writeObject(new RetrieveWrapper(3));
		Response response = (Response) objectInputStream.readObject();
		return response.getDayMenus();
	}
	
	public ArrayList<User> retrieveAllUsers() throws IOException, ClassNotFoundException {
		objectOutputStream.writeObject(new RetrieveWrapper(4));
		Response response = (Response) objectInputStream.readObject();
		return response.getUsers();
	}
	
	public boolean deleteFoodItem(FoodItem foodItem) throws IOException, ClassNotFoundException {
		objectOutputStream.writeObject(new DeleteWrapper(1, foodItem));
		Response response = (Response) objectInputStream.readObject();
		return response.getFlag();
	}
	
	public boolean deleteMenuItem(MenuItem menuItem) throws IOException, ClassNotFoundException {
		objectOutputStream.writeObject(new DeleteWrapper(2, menuItem));
		Response response = (Response) objectInputStream.readObject();
		return response.getFlag();
	}
	
	public boolean deleteDayMenu(DayMenu dayMenu) throws IOException, ClassNotFoundException {
		objectOutputStream.writeObject(new DeleteWrapper(3, dayMenu));
		Response response = (Response) objectInputStream.readObject();
		return response.getFlag();
	}
	
	public boolean deleteUser(User user) throws IOException, ClassNotFoundException {
		objectOutputStream.writeObject(new DeleteWrapper(4, user));
		Response response = (Response) objectInputStream.readObject();
		return response.getFlag();
	}
}
