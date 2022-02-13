package com.foodmenuclient.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.foodmenuclient.model.services.logservice.LogServer;

public class MainUI {
	
	private static Logger LOGGER = Logger.getLogger(MainUI.class);
	
	static {		
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		LogServer logServer = (LogServer)context.getBean("logCfg");
		String log4j2PropFile = logServer.getPropFile("log");
		
		
        if(!log4j2PropFile.equals("")) {
         	System.setProperty("log4j.configurationFile", log4j2PropFile);
          	LOGGER.info("Loaded Log4J Properties from " + log4j2PropFile);
        } else {
         	System.setProperty("log4j.configurationFile", "config\\log4j2.properties");
         	LOGGER.error("System failed to load log4j2.properties file. Check configuration for more details");
        }
		
		LOGGER.trace(String.format("Logging Level: %s", LOGGER.getLevel()));
	}

	public static void main(String[] args) throws IOException {
		LOGGER.trace(String.format("Logging Level: %s", LOGGER.getLevel()));
		
		MainUI callingMainUI = new MainUI();
		MainUI.view();
	}

	public static void view() throws IOException {
		LoginJFrame loginJFrame = new LoginJFrame();
		loginJFrame.setVisible(true);
	}

}
