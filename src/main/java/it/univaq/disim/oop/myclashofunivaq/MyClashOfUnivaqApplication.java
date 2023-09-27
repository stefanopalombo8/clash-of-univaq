package it.univaq.disim.oop.myclashofunivaq;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MyClashOfUnivaqApplication extends Application {

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		//TEST
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/viste/homepage.fxml"));
		Parent login = loader.load(); //invoca il costruttore
		Scene scene = new Scene(login);
		stage.setScene(scene);
		stage.show();
		
	}

}
