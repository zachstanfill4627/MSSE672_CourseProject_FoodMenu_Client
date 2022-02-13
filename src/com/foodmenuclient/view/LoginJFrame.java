package com.foodmenuclient.view;

import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

import com.foodmenuclient.controller.AuthenticationClient;
import com.foodmenu.model.domain.User;

public class LoginJFrame extends JFrame {
	
	private static Logger LOGGER = Logger.getLogger(LoginJFrame.class);
	
	private JTextField unameField;
	private JPasswordField passwordField;
	
	public LoginJFrame() throws IOException {
		super("Food Menu Login");
		LOGGER.trace("Instantiating LoginJFrame Class");
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(479, 350);
		getContentPane().setLayout(null);
		
		JLabel titleLablel = new JLabel("Food Menu Login");
		titleLablel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLablel.setFont(new Font("Calibri", Font.BOLD, 18));
		titleLablel.setBounds(10, 11, 443, 32);
		getContentPane().add(titleLablel);
		
		JButton createAccountButton = new JButton("Create Account");
		createAccountButton.addActionListener(new createAccountButtonListener());
		createAccountButton.setFont(new Font("Calibri", Font.BOLD, 12));
		createAccountButton.setBounds(325, 46, 128, 23);
		getContentPane().add(createAccountButton);
		
		JLabel unameLabel = new JLabel("Username");
		unameLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		unameLabel.setBounds(10, 94, 222, 24);
		getContentPane().add(unameLabel);
		
		unameField = new JTextField();
		unameField.setFont(new Font("Calibri", Font.BOLD, 16));
		unameField.setColumns(10);
		unameField.setBounds(10, 121, 443, 26);
		getContentPane().add(unameField);
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		passwordLabel.setBounds(10, 153, 222, 24);
		getContentPane().add(passwordLabel);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Calibri", Font.BOLD, 16));
		passwordField.setBounds(10, 180, 443, 26);
		getContentPane().add(passwordField);
		
		JButton rstPasswordButton = new JButton("Reset Password");
//		rstPasswordButton.addActionListener(new resetPWButtonListener());
		rstPasswordButton.setFont(new Font("Calibri", Font.BOLD, 16));
		rstPasswordButton.setBounds(10, 271, 178, 29);
		getContentPane().add(rstPasswordButton);
		
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new loginButtonListener());
		loginButton.setFont(new Font("Calibri", Font.BOLD, 16));
		loginButton.setBounds(335, 271, 118, 29);
		getContentPane().add(loginButton);
		
		LOGGER.info("LoginJFrame Class Instantiated");
	}
	
	class createAccountButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			LOGGER.trace("createAccountButtonListener Initiated");
			setVisible(false);
			LOGGER.trace("Set Window Visibility to False");
			CreateAccountJFrame createAccountJFrame;
			try {
				LOGGER.trace("Call for CreateAccountJFrame");
				createAccountJFrame = new CreateAccountJFrame();
				createAccountJFrame.setVisible(true);
				createAccountJFrame.addWindowListener(new java.awt.event.WindowAdapter() {
					public void windowClosed(java.awt.event.WindowEvent windowEvent) {
						setVisible(true);
						LOGGER.trace("Set Window Visibility to True");
					}
				});
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			LOGGER.trace("createAccountButtonListener Completed");
		}
	}
	
	class loginButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			LOGGER.trace("loginButtonListener Initiated");		
			String username = unameField.getText();
			String password = passwordField.getText();

			LOGGER.trace("Instantiating AuthenticationClient");
			AuthenticationClient authClient = new AuthenticationClient();
			LOGGER.trace("Completed Instantiating AuthenticationClient");
			
			try {
				LOGGER.info(String.format("Attempting to Authenticate User %s", username));
				String sessionKey = authClient.authenticateUser(username, password);
				if (!sessionKey.equals("")) {
					JOptionPane.showMessageDialog(null, "Authentication Succeeded!");
					LOGGER.info(String.format("Authentication Succeeded for User %s", username));
					setVisible(false);
					LOGGER.trace("Set Window Visibility to False");
					FoodMenuJFrame foodMenuJFrame = new FoodMenuJFrame();
					LOGGER.trace("FoodMenuJFrame Instantiated");
					foodMenuJFrame.setUser(username, sessionKey);
					LOGGER.info(String.format("FoodMenuJFrame User set for User %s", username));
					foodMenuJFrame.setVisible(true);
					LOGGER.trace("Set foodMenuJFrame Window Visibility to True");
				} else {
					JOptionPane.showMessageDialog(null, "Supplied Username or Password is incorrect!");
					LOGGER.error(String.format("Authentication Failed - The supplied Username or Password for Authentication Attempt %s was incorrect!", username));
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			LOGGER.trace("loginButtonListener Completed");
				
		}
	}
	
//	class resetPWButtonListener implements ActionListener {
//		public void actionPerformed(ActionEvent e) {
//			LOGGER.trace("resetPWButtonListener Initiated");
//			
//			LOGGER.trace("Begin Prompting User for Required Information to Reset Password");
//			String email = JOptionPane.showInputDialog("Resetting Password!\n\nWhat is your email address?");
//			int age = 0;
//			try {	
//				age = Integer.parseInt(JOptionPane.showInputDialog("Resetting Password!\n\nHow old were you when this account was created?"));
//			} catch (NumberFormatException nfe) {
//				JOptionPane.showMessageDialog(null, "Invalid age input...\n\n Number Characters Only!\n\nPlease Try Again!");
//				LOGGER.error("Invalid Age Provided during password reset attempt. The value input was not a numeric character.");
//				return;
//			}
//				
//			String recPhrase = JOptionPane.showInputDialog("Resetting Password!\n\nRecovery Question\nWhat do you want to be when you grown up?");
//			
//			LOGGER.trace("ResetPassword Validate Input Fields Types (Not validating against user acount)");
//			if (email == null || age == 0 || recPhrase == null) {
//				JOptionPane.showMessageDialog(null, "User Identity could not be verified because the Username, Age, or Recovery Phrase were incorrect!\n\nPlease try again!");
//				return;
//			}
//			
//			String password1 = "";
//			String password2 = "";
//			
//			LOGGER.trace("Instantiating UserManager");
//			UserManager userManager = new UserManager();
//			LOGGER.trace("Completed Instantiating UserManager");
//			
//			LOGGER.info(String.format("Password reset attempt for Email:%s", email));
//			try {
//				if(userManager.resetUserPassword(email, age, recPhrase)) {
//					try {
//						do {
//							if(password1.equals(password2) && !password1.equals("")) {
//								JOptionPane.showMessageDialog(null, "Password doesn't meet Password Policy Standards!\n\n Please try again!");
//								LOGGER.error(String.format("User %s Password Reset - Password doesn't meet Password Policy Standards! Please try again!", email));
//							} else if (!password1.equals("")) {
//								JOptionPane.showMessageDialog(null, "Passwords didn't match!\n\n Please try again!");
//								LOGGER.error(String.format("User %s Password Reset - Passwords didn't match! Please try again!", email));
//							}
//							password1 = JOptionPane.showInputDialog("Resetting Password!\n\nIdentity Confirmed\nPlease enter your new password:");
//							password2 = JOptionPane.showInputDialog("Resetting Password!\n\nIdentity Confirmed\nPlease Confirm your new password:");
//						} while (!userManager.resetUserPassword(email, password1) || !password1.equals(password2));
//						JOptionPane.showMessageDialog(null, "User Password reset successfully");
//						LOGGER.info(String.format("User %s Password Reset - Password Successfully Reset", email));
//					} catch (HeadlessException | UserServiceException | ServiceLoadException | IOException e1) {
//						e1.printStackTrace();
//					} catch (NullPointerException e2) {
//						JOptionPane.showMessageDialog(null, "User Exited Password Reset!");
//						LOGGER.error(String.format("User %s Password Reset - User Exited Password Reset!", email));
//					}
//				} else {
//					JOptionPane.showMessageDialog(null, "User Identity could not be verified because the Username, Age, or Recovery Phrase were incorrect!\n\nPlease try again!");
//					LOGGER.error(String.format("User %s Password Reset - User Identity could not be verified because the Username, Age, or Recovery Phrase were incorrect! Please try again!", email));
//				}
//			} catch (HeadlessException | ServiceLoadException | UserServiceException e1) {
//				e1.printStackTrace();
//			}
//			
//			LOGGER.trace("resetPWButtonListener Compelted");
//			return;
//		}
//	}
}
