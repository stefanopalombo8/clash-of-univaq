package it.univaq.disim.oop.myclashofunivaq.view;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ViewDispatcher {

	private static final String cartellaViste = "/viste/";
	private static final String tipoFile = ".fxml";

	private static ViewDispatcher instance = new ViewDispatcher();
	private Stage stage; // centralizzazione dello stage

	private FXMLLoader loader;
	private BorderPane layout;
	
	public static ViewDispatcher getInstance() {
		return instance;
	}
	
	public void homepageView(Stage stage) {
		this.stage = stage;
		try {
			caricaVista("applicationLayout");
			caricaVista("homepage");
		} catch (ViewException e) {
			e.printStackTrace();
		}
		
		stage.show();
	}

	public void caricaVista(String nome) throws ViewException {
		inizializzaLoader(nome);
		Scene scena = null;
		Parent parent;
		
		try {
			parent = loader.load();
		} catch (IOException e) {
			throw new ViewException(e.getMessage());
		}
		
		if(layout == null) {
			layout = (BorderPane) parent;
			scena = new Scene(layout);
		}
		else 
			layout.setCenter(parent);
		
		if(scena != null)
			stage.setScene(scena);
		else
			stage.setScene(stage.getScene());

	}
	

	public <T> void caricaVista(String nome, T data) throws ViewException {
		caricaVista(nome);
		InizializzaDati<T> inizializzatore = loader.getController();
		inizializzatore.inizializza(data);
	}

	private void inizializzaLoader(String nomeVista) {
		loader = new FXMLLoader(getClass().getResource(cartellaViste + nomeVista + tipoFile));
		
	}

}
