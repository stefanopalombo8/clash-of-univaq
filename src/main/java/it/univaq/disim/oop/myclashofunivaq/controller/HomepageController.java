package it.univaq.disim.oop.myclashofunivaq.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.myclashofunivaq.business.GiocatoreUtenteService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HomepageController implements Initializable {
	
	@FXML
	private TextField nickname;
	
	@FXML
	private Button accettaNickname;
	
	@FXML
	private Label confermaNickname;
	
	@FXML
	private Button recuperaPartiteSalvate;
	
	@FXML
	private Button giocaControGiocatore;
	
	@FXML
	private Button giocaControCPU;
	
	private GiocatoreUtenteService giocatoreUtenteService;
	
	private static final String stringaConferma = "nickname valido, scegli una modalità";

	
	public HomepageController() {
		//giocatoreUtenteService = implementazione
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		disableGamemodButton(recuperaPartiteSalvate);
		disableGamemodButton(giocaControGiocatore);
		disableGamemodButton(giocaControCPU);
		
		accettaNickname.disableProperty().bind(confermaNickname.textProperty().isEqualTo(stringaConferma));
	}
	
	@FXML
	public void accettaNicknameAction(ActionEvent event) {
		//uso del service
			
	}
	
	private void disableGamemodButton(Button bottone) {
		bottone.disableProperty().bind(confermaNickname.textProperty().isNotEqualTo(stringaConferma));
	}
	
	
}
