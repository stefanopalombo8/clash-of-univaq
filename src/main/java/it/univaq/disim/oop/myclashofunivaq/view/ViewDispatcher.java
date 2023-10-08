package it.univaq.disim.oop.myclashofunivaq.view;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewDispatcher {
	private static final String cartellaViste = "/viste/";
	private static final String tipoFile = ".fxml";
	
	private static ViewDispatcher instance = new ViewDispatcher();
	private Stage stage; //centralizzazione dello stage
	
	private FXMLLoader loader;
	
	public static ViewDispatcher getInstance() {
		return instance;
	}
	
	public void homepageView(Stage stage) throws IOException {
		this.stage = stage;
		caricaVista("homepage");
		stage.show();
	}
	
	private void caricaVista(String nome) throws IOException {
		inizializzaLoader(nome);
		Parent parent = loader.load();
		Scene scena = new Scene(parent);
		stage.setScene(scena);
	}
	
	private void inizializzaLoader(String nomeVista) throws IOException {
		loader = new FXMLLoader(getClass().getResource(cartellaViste + nomeVista + tipoFile));
	}

}
