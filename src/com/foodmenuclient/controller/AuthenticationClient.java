package com.foodmenuclient.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.foodmenu.model.domain.User;
import com.foodmenu.model.domain.UserWrapper;
import com.foodmenuclient.model.services.networkservice.NetworkClient;

public class AuthenticationClient {
	
	private Socket socket = null;
    private OutputStream outputStream;
    private InputStream inputStream;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    
    // Default Constructor
    public AuthenticationClient() {
    	
    }
    
    public String authenticateUser (String email, String password) 
    	throws UnknownHostException, IOException, ClassNotFoundException, Exception {
        
    	
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		NetworkClient authSvrNetClient = (NetworkClient)context.getBean("authSvrNetCfg");
		int destPort = Integer.parseInt(authSvrNetClient.getPort("port"));
		String destHost = authSvrNetClient.getIP("IP");
		
		// establish a connection by providing host and port number
		this.socket = new Socket(destHost, destPort);
        // get the input stream from the connected socket
        this.outputStream = socket.getOutputStream();
        this.inputStream = socket.getInputStream();
        // create a DataInputStream so we can read data from it.
        this.objectOutputStream =  new ObjectOutputStream(outputStream);
        this.objectInputStream = new ObjectInputStream(inputStream);
        
        objectOutputStream.writeObject(new UserWrapper(3, email, password));
        
        UserWrapper response = (UserWrapper) objectInputStream.readObject();
        
        if (response.getResponse()) {
        	return response.getAuthToken();
        } else {
        	throw new Exception("Failed to authenticate user");
        }
    }
    
    public boolean createUser (User user, String adminPasscode)
    	throws UnknownHostException, IOException, ClassNotFoundException, Exception {
        this.socket = new Socket("localhost", 40008); // Needs to be changed to be read from a prop file
        // get the input stream from the connected socket
        this.outputStream = socket.getOutputStream();
        this.inputStream = socket.getInputStream();
        // create a DataInputStream so we can read data from it.
        this.objectOutputStream =  new ObjectOutputStream(outputStream);
        this.objectInputStream = new ObjectInputStream(inputStream);
        
        UserWrapper userWrapper = new UserWrapper();
        userWrapper.setRequestType(1);
        userWrapper.setUser(user);
        System.out.println(user.getRole());
        userWrapper.setMiscString(adminPasscode);
        
        objectOutputStream.writeObject(userWrapper);
        
        UserWrapper response = (UserWrapper) objectInputStream.readObject();
        
        return response.getResponse();
    }

}
