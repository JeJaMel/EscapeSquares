package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.Node;



public class MainController {

	@FXML
	TextField nameTextField;
	
	private Stage stage;
	private Scene scene;
	private Parent root;

	@FXML
	private Button exitButton;
	@FXML
	private AnchorPane scenePane;
	 
	 public void switchToEasy(ActionEvent event) throws IOException {
	  root = FXMLLoader.load(getClass().getResource("EasyLevel.fxml"));
	  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	  scene = new Scene(root);
	  scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	  stage.setScene(scene);
	  stage.show();
	 }
	 
	 public void switchToNormal(ActionEvent event) throws IOException {
	  Parent root = FXMLLoader.load(getClass().getResource("NormalLevel.fxml"));
	  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	  scene = new Scene(root);
	  scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	  stage.setScene(scene);
	  stage.show();
	 }
	
	public void logout(ActionEvent event) {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		
		Image icono = new Image("porosad.png");
		Image icon = new Image("Icon.png");
		
		 ImageView iconLeave = new ImageView(icono);
		iconLeave.setFitHeight(48); 
		iconLeave.setFitWidth(48);
		
		alert.setGraphic(iconLeave);
		alert.setTitle("Logout");
		alert.setHeaderText("you're about to logout");
		alert.setContentText("do you really want to leave?");
		
		Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
		alertStage.getIcons().add(icon);
		
		alert.getDialogPane().getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		    
		if(alert.showAndWait().get() == ButtonType.OK) {
		stage = (Stage) scenePane.getScene().getWindow();
		System.out.println("You succesfully logout!");
		stage.close();
		}
		
		
	}
	
}
