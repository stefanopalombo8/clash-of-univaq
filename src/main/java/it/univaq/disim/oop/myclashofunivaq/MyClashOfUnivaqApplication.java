package it.univaq.disim.oop.myclashofunivaq;


import it.univaq.disim.oop.myclashofunivaq.view.ViewDispatcher;
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
		ViewDispatcher dispatcher = ViewDispatcher.getInstance();
		dispatcher.homepageView(stage);

	}

}
