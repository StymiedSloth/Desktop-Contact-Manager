/*
 * Name: Harsha Deep Reddy V
 * 
 * Date: 13 Sep 2014 
 * Purpose of this module: This is the main class for the file, compilation should be done on this file.
 *                         This file defines the main pane for the program and loads the requried resources like the fxml
 *                         file, stylesheets. 
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Manager_Main extends Application {
    
    //Setting Primary Stage for the application
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Contacts Manager");
        primaryStage.setMinWidth(850);
        primaryStage.setMinHeight(800);
        //Loading the main fxml view
        Pane myPane = (Pane)FXMLLoader.load(getClass().getResource("ContactsManager/ContactsView.fxml"));
        
        Scene myScene = new Scene(myPane);        
        //Loading the main stylesheet for the app
        myScene.getStylesheets().add("ContactsManager/StyleSheet.css");
        primaryStage.setScene(myScene);
        
        primaryStage.show();
    }
 
    public static void main(String[] args) {
        launch(args);
    }
    
}
