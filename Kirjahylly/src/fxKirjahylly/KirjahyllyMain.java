package fxKirjahylly;
	
import Kirjahylly.Kirjahylly;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;

/**
 * Kirjahyllyn pääohjelma, käynnistää aloitusikkunan
 * @author Niko
 * @version 8.8.2022
 *
 */
public class KirjahyllyMain extends Application {
	@Override
	public void start(Stage primaryStage) {
        try {
            final FXMLLoader ldr = new FXMLLoader(getClass().getResource("KirjahyllyGUIView.fxml"));      
            final Pane root = (Pane)ldr.load();                                                     
            final KirjahyllyGUIController kirjahyllyCtrl = (KirjahyllyGUIController)ldr.getController();  
            final Scene scene = new Scene(root);                                                    
            scene.getStylesheets().add(getClass().getResource("kirjahylly.css").toExternalForm());
            primaryStage.setScene(scene);   
            primaryStage.setTitle("Kirjahylly");

            Kirjahylly kirjahylly = new Kirjahylly();
            kirjahyllyCtrl.setKirjahylly(kirjahylly);

            if (!kirjahyllyCtrl.avaa()) Platform.exit();
            primaryStage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
	}
	
	/** 
	 * Pääohjelma ohjelman käynnistämiseen.
	 * @param args ei kyätetä
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
