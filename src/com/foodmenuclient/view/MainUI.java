package com.foodmenuclient.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.foodmenu.model.domain.User;

public class MainUI {
	
	private static Logger LOGGER = Logger.getLogger(MainUI.class);
	
	private static String propertiesFile = System.getProperty("user.dir") + "\\config\\application.properties";
	
	static {
		try (InputStream input = new FileInputStream(propertiesFile)) {
            Properties prop = new Properties();
            prop.load(input);
            if(new File(prop.getProperty("log4j2.properties")).exists()) {
            	System.setProperty("log4j.configurationFile", prop.getProperty("log4j2.properties"));
            	LOGGER.info("Loaded Log4J Properties from " + prop.getProperty("log4j2.properties"));
            } else {
            	System.setProperty("log4j.configurationFile", "config\\log4j2.properties");
            	LOGGER.error("System failed to load log4j2.properties file. Check configuration for more details");
            }
		} catch (Exception e) {
			System.setProperty("log4j.configurationFile", "config\\log4j2.properties");
			LOGGER.error("System failed to load log4j2.properties file. Check configuration for more details");
		}
		
		LOGGER.trace(String.format("Logging Level: %s", LOGGER.getLevel()));
	}

	public static void main(String[] args) throws IOException {
		LOGGER.info("Starting Application");
		MainUI callingMainUI = new MainUI();
		MainUI.view();
	}

	public static void view() throws IOException {
//		LoginJFrame loginJFrame = new LoginJFrame();
//		loginJFrame.setVisible(true);
//		
		User user = new User("Zach", "Stanfill", "zstanfill@regis.edu", "ghostbuster", 29, "admin");
		
		FoodMenuJFrame foodMenuJFrame = new FoodMenuJFrame();
		
		try {
			foodMenuJFrame.setUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		foodMenuJFrame.setVisible(true);
	}

}
