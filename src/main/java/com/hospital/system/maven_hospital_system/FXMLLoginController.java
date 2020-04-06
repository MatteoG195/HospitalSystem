package com.hospital.system.maven_hospital_system;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.sql.*;



public class FXMLLoginController{
	private Stage stage;
	private Scene scene;
	
	@FXML
	private PasswordField passField;
	
	/**
	 * Constructor for Login Object
	 * @param stage
	 */
	public FXMLLoginController(Stage stage){
		this.stage = stage;
		System.out.println("Login Screen...");
	}
	/**
	 * Creation of Login Screen, contains enter key handler for login...
	 * @return Current scene to be displayed
	 * @throws IOException If FXML not found...
	 */
	public Scene LoginScreen() throws IOException {
		//stage.hide();
		FXMLLoader loader = new FXMLLoader();
		String path = System.getProperty("user.dir") + "\\fxml\\Login2.fxml";
		System.out.println(path);
		FileInputStream fxmlStream = new FileInputStream(path);
		//Load the FXML file
		Parent root = loader.load(fxmlStream);
		//Listener for Enter Key//
		root.setOnKeyPressed(new EventHandler<KeyEvent>()
				{
						public void handle(KeyEvent key) {
							if(key.getCode() == KeyCode.ENTER) {
								System.out.println("Enter Pressed");
								/*
								 * At this point, we point to Login Verification with DB...
								 * New Method to be created
								 */
								try {
									
									System.out.println(toHexString(getSHA(passField.getText())));
								}
								catch(NoSuchAlgorithmException e) {
									System.err.println("Encryption error!  Please check to make sure encryption type valid...");
								}
							}
						}
				});
		//Instantiate scene
		this.scene = new Scene(root);
		passField = (PasswordField) scene.lookup("#passField");  //Use the Current scene to lookup password ID for retrieval of password.

		return scene;
	}
	/**
	 * Hash Passwords for use within db matching...
	 * @param input password input
	 * @return SHA 256 in Byte format within array
	 * @throws NoSuchAlgorithmException If algorithm not found...
	 */
	private byte[] getSHA(String input) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		return md.digest(input.getBytes(StandardCharsets.UTF_8));
	}
	/**
	 * Allow for return of string format of Bytes.
	 * @param hash SHA 256 in array
	 * @return String format of SHA 256
	 */
	private String toHexString(byte[] hash) {
		BigInteger number = new BigInteger(1,hash);
		//Converts to a hex value...
		StringBuilder hexString = new StringBuilder(number.toString(16));
		
		//leading zeros to look pretty!
		while(hexString.length() < 32)
			hexString.insert(0, '0');
		
		return hexString.toString();
		
	}
	
	/**
	 * TO BE IMPLEMENTED!!!  Allow to look up if user correctly inputted credentials...
	 * @param userName The current user Input for name
	 * @param passHash The Hashed Password
	 * @return If credentials are correct.
	 */
	private Boolean isValidCredentials(String userName, String passHash) {
		return false;
	}
	
	

}
