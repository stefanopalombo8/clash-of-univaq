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
	
	public void homepageView(Stage stage) throws IOException {
		this.stage = stage;
		caricaVista("applicationLayout");
		caricaVista("homepage");

		stage.show();
	}

	public void caricaVista(String nome) throws IOException {
		inizializzaLoader(nome);
		Scene scena = null;
		if(layout == null) {
			layout = loader.load();
			scena = new Scene(layout);
		}
		else {
			Parent parent = loader.load();
			layout.setCenter(parent);
		}

		if(scena != null)
			stage.setScene(scena);
		else
			stage.setScene(stage.getScene());

	}

	public <T> void caricaVista(String nome, T data) throws IOException {
		caricaVista(nome);
		InizializzaDati<T> inizializzatore = loader.getController();
		inizializzatore.inizializza(data);
	}

	private void inizializzaLoader(String nomeVista) throws IOException {
		loader = new FXMLLoader(getClass().getResource(cartellaViste + nomeVista + tipoFile));
	}

}
