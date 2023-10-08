package it.univaq.disim.oop.myclashofunivaq.controller;

import java.io.IOException;
import java.net.URL;


import java.util.ResourceBundle;

import it.univaq.disim.oop.myclashofunivaq.business.GiocatoreUtenteService;
import it.univaq.disim.oop.myclashofunivaq.business.impl.GiocatoreUtenteServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.business.impl.NicknameNonValido;
import it.univaq.disim.oop.myclashofunivaq.domain.GiocatoreUtente;
import it.univaq.disim.oop.myclashofunivaq.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class HomepageController implements Initializable {
	
	@FXML
	private AnchorPane anchorPane;
	
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
	
	private GiocatoreUtente giocatore;
	private GiocatoreUtenteService giocatoreUtenteService;
	
	private static final String stringaConferma = "nickname valido, scegli una modalità";
	
	private ViewDispatcher dispatcher;

	
	public HomepageController() {
		giocatoreUtenteService = new GiocatoreUtenteServiceImpl();
		dispatcher = ViewDispatcher.getInstance();
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
		try {
			giocatore = giocatoreUtenteService.convalidaNickName(nickname.getText());
			this.confermaNickname.setText(stringaConferma);
		} catch (NicknameNonValido e) {
			this.confermaNickname.setText(e.getMessage());  //gestione eccezione a livello utente
		}
	}
	
	private void disableGamemodButton(Button bottone) {
		bottone.disableProperty().bind(confermaNickname.textProperty().isNotEqualTo(stringaConferma));
	}
	
	@FXML
	public void giocaControGiocatoreAction(ActionEvent event) throws IOException {
		
		
	}
	
}
