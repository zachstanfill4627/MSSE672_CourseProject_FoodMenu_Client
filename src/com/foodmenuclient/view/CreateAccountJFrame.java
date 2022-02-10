package com.foodmenu.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

import com.foodmenu.model.business.exceptions.ServiceLoadException;
import com.foodmenu.model.business.managers.UserManager;
import com.foodmenu.model.domain.User;
import com.foodmenu.model.services.exceptions.UserServiceException;

public class CreateAccountJFrame extends JFrame {
	
	private static Logger LOGGER = Logger.getLogger(CreateAccountJFrame.class);
	
	private JTextField emailField;
	private JPasswordField passwordField;
	private JPasswordField confirmPassField;
	private JTextField fNameField;
	private JTextField lNameField;
	private JTextField recPhraseField;
	private JSpinner ageSpinner = new JSpinner();
	private JComboBox userLevelDropdown; 

	public CreateAccountJFrame() throws IOException {
		super("Food Menu Create Account");
		LOGGER.trace("Instantiating CreateAccountJFrame Class");
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(479, 569);
		getContentPane().setLayout(null);
		
		JLabel titleLabel = new JLabel("Create Food Menu Account");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Calibri", Font.BOLD, 18));
		titleLabel.setBounds(10, 11, 443, 32);
		getContentPane().add(titleLabel);
		
		JLabel emailLabel = new JLabel("Email");
		emailLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		emailLabel.setBounds(10, 148, 222, 26);
		getContentPane().add(emailLabel);
		
		emailField = new JTextField();
		emailField.setFont(new Font("Calibri", Font.BOLD, 16));
		emailField.setColumns(10);
		emailField.setBounds(10, 180, 443, 26);
		getContentPane().add(emailField);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Calibri", Font.BOLD, 16));
		passwordField.setBounds(10, 248, 222, 26);
		getContentPane().add(passwordField);
		
		JLabel confirmPasswordLabel = new JLabel("Confirm Password");
		confirmPasswordLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		confirmPasswordLabel.setBounds(238, 224, 215, 18);
		getContentPane().add(confirmPasswordLabel);
		
		confirmPassField = new JPasswordField();
		confirmPassField.setFont(new Font("Calibri", Font.BOLD, 16));
		confirmPassField.setBounds(238, 248, 215, 26);
		getContentPane().add(confirmPassField);
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		passwordLabel.setBounds(10, 224, 222, 18);
		getContentPane().add(passwordLabel);
		
		JButton createButton = new JButton("Create Account");
		createButton.addActionListener(new createButtonListener());
		createButton.setFont(new Font("Calibri", Font.BOLD, 16));
		createButton.setBounds(286, 490, 167, 29);
		getContentPane().add(createButton);
		
		JLabel firstNameLabel = new JLabel("First Name");
		firstNameLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		firstNameLabel.setBounds(10, 90, 222, 18);
		getContentPane().add(firstNameLabel);
		
		JLabel lastNameLabel = new JLabel("Last Name");
		lastNameLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		lastNameLabel.setBounds(238, 90, 215, 18);
		getContentPane().add(lastNameLabel);
		
		fNameField = new JTextField();
		fNameField.setFont(new Font("Calibri", Font.BOLD, 14));
		fNameField.setColumns(10);
		fNameField.setBounds(10, 113, 222, 24);
		getContentPane().add(fNameField);
		
		lNameField = new JTextField();
		lNameField.setFont(new Font("Calibri", Font.BOLD, 14));
		lNameField.setColumns(10);
		lNameField.setBounds(238, 113, 215, 24);
		getContentPane().add(lNameField);
		
		JLabel recoveryPhraseLabel = new JLabel("Password Recovery Question");
		recoveryPhraseLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		recoveryPhraseLabel.setBounds(10, 293, 443, 18);
		getContentPane().add(recoveryPhraseLabel);
		
		recPhraseField = new JTextField();
		recPhraseField.setFont(new Font("Calibri", Font.BOLD, 16));
		recPhraseField.setColumns(10);
		recPhraseField.setBounds(10, 338, 443, 26);
		getContentPane().add(recPhraseField);
		
		JLabel recoveryPhraseTextLabel = new JLabel("What do you want to be when you grow up?");
		recoveryPhraseTextLabel.setFont(new Font("Calibri", Font.BOLD, 12));
		recoveryPhraseTextLabel.setBounds(10, 317, 443, 15);
		getContentPane().add(recoveryPhraseTextLabel);
		
		userLevelDropdown = new JComboBox<Object>();
		userLevelDropdown.setModel(new DefaultComboBoxModel(new String[] {"user", "admin"}));
		userLevelDropdown.setSelectedIndex(0);
		userLevelDropdown.setFont(new Font("Calibri", Font.BOLD, 14));
		userLevelDropdown.setBackground(Color.WHITE);
		userLevelDropdown.setBounds(10, 492, 150, 24);
		getContentPane().add(userLevelDropdown);
		
		JLabel permLevelLabel = new JLabel("Permissions Level");
		permLevelLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		permLevelLabel.setBounds(10, 461, 222, 18);
		getContentPane().add(permLevelLabel);
		
		JLabel AgeLabel = new JLabel("Age");
		AgeLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		AgeLabel.setBounds(10, 389, 443, 18);
		getContentPane().add(AgeLabel);
		
		ageSpinner = new JSpinner();
		ageSpinner.setModel(new SpinnerNumberModel(29, 0, 100, 1));
		ageSpinner.setBounds(49, 386, 58, 20);
		getContentPane().add(ageSpinner);
		
		LOGGER.info("CreateAccountJFrame Class Instantiated");
	}
	
	class createButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			LOGGER.trace("createButtonListener Initiated");
			String propertiesFile = "config/application.properties";
			String adminpasscode;
			
			LOGGER.info("Reading Property Files for Admin Password");
			/** Read Configured Properties */
			try (InputStream input = new FileInputStream(propertiesFile)) {
	            Properties prop = new Properties();
	            prop.load(input);
	            adminpasscode = prop.getProperty("admin.passcode");
	            LOGGER.info("Admin Password read from " + propertiesFile + " successfully");
	            LOGGER.debug("Admin Passcode Set to: \"" + adminpasscode + "\"");
			} catch (Exception e1) {
	    		adminpasscode = "MenuAdmin";	
	    		LOGGER.warn("Error in reading property file password values, setting to default values!");
	    		LOGGER.debug("Admin Passcode Set to: \"" + adminpasscode + "\"");
	        }    
			
			String fName = fNameField.getText();
			String lName = lNameField.getText();
			String email = emailField.getText();
			String recPhrase = recPhraseField.getText();
			String password = "";
			int age = (int) ageSpinner.getValue();
			String role = "user";
			LOGGER.info("Read Create User Fields");
			LOGGER.debug(String.format("Create User Form Values: Firstname:%s   "
					+ "LastName:%s   Email:%s   RecoveryPhrase:%s   "
					+ "Age:%d   Role:%s.", fName,  lName, email, recPhrase, 
					age, role));
			
			
			if(passwordField.getText().equals(confirmPassField.getText())) {
				password = passwordField.getText();
				LOGGER.info("Passwords Match");
			} else {
				JOptionPane.showMessageDialog(null, "Passwords do not match!");
				LOGGER.error("Create User Form -- Passwords don't Match. Cannot Continue with User Creation.");
				LOGGER.debug(String.format("Create User Form -- Password:%s   ConfirmPassword:%s", passwordField.getText(), confirmPassField.getText()));
				return;
			}
			
			if(userLevelDropdown.getSelectedItem().toString().equals("admin")) {
				if(JOptionPane.showInputDialog("Input Pre-Defined Admin Passcode:").equals(adminpasscode)) {
					role = "admin";
					LOGGER.info(String.format("NewUser:%s   Set to Administrator Role", email));
				} else {
					JOptionPane.showMessageDialog(null, "Pre-Defined Admin Passcode incorrect, Downgrading to Role = User");
					role = "user";
					LOGGER.warn(String.format("NewUser:%s   Failed AdminPassword Check -- Set to User Role", email));
				}
			} 
			
			
			User user = new User(fName, lName, email, password, recPhrase, age, role);
			LOGGER.info(String.format("New User %s Object Instantiated", email));
			LOGGER.debug(user.toString());
			
			LOGGER.trace("Instantiating UserManager");
			UserManager userManager = new UserManager();
			LOGGER.trace("Completed Instantiating UserManager");
			
			try {
				LOGGER.trace(String.format("Attempting to add User %s to Users Database", email));
				if(userManager.addNewUser(user)) {
					try {
						JOptionPane.showMessageDialog(null, "User Account Created!\nUsername = " + email);
						LOGGER.info(String.format("User Account %s Added to Users Database Table", email));
					} catch (Exception e1) {
						LOGGER.fatal(String.format("User Account %s unable to be added to Users Database Table", email));
						e1.printStackTrace();
					}
					setVisible(false);
					LOGGER.trace("Set Window Visibility to False");
					dispose();
				} else {
					if(!userManager.isValidPassword(password)) {
						JOptionPane.showMessageDialog(null, "Password not Strong Enough!\n\nSee Administrator for password complexity requirements!");
						LOGGER.error(String.format("New User %s passwords are invalid passwords as defined by the password complexity requirements!" , email));
					} else {
						JOptionPane.showMessageDialog(null, "Problem with User Account Creation!");
						LOGGER.fatal(String.format("User %s Account unable to be added to Users Database Table", email));
					}
					return;
				}
			} catch (ServiceLoadException | UserServiceException | HeadlessException | IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
