package it.univaq.disim.oop.myclashofunivaq.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.myclashofunivaq.business.GiocatoreUtenteService;
import it.univaq.disim.oop.myclashofunivaq.business.impl.GiocatoreUtenteServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.business.impl.NicknameNonValido;
import it.univaq.disim.oop.myclashofunivaq.domain.GiocatoreUtente;
import it.univaq.disim.oop.myclashofunivaq.view.InizializzaDati;
import it.univaq.disim.oop.myclashofunivaq.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class NicknameGiocatore2Controller implements Initializable, InizializzaDati<GiocatoreUtente> {
	
	@FXML
	private Label giocatore1Label;
	
	@FXML
	private TextField nicknameGiocatore2;
	
	@FXML
	private Button accettaNickname;
	
	@FXML
	private Label confermaNickname;
	
	@FXML
	private Button avanti;
	
	private GiocatoreUtenteService giocatoreUtenteService;
	private ViewDispatcher dispatcher;
	
	private GiocatoreUtente giocatore1;
	private GiocatoreUtente giocatore2;
	
	private static final String stringaConferma = "nickname valido vai avanti";
	
	public NicknameGiocatore2Controller() {
		this.giocatore1 = null;
		giocatoreUtenteService = new GiocatoreUtenteServiceImpl();
		dispatcher = ViewDispatcher.getInstance();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	@Override
	public void inizializza(GiocatoreUtente giocatore1) {
		this.giocatore1Label.setText(giocatore1.getNickname());
	}
	
	@FXML
	public void accettaNicknameAction(ActionEvent event) {
		try {
			giocatore2 = giocatoreUtenteService.convalidaNickName(nicknameGiocatore2.getText());
			this.confermaNickname.setText(stringaConferma);
		} catch (NicknameNonValido e) {
			this.confermaNickname.setText(e.getMessage());  //gestione eccezione a livello utente
		}
	}
}
