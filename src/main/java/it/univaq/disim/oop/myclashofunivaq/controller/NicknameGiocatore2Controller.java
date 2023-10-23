package it.univaq.disim.oop.myclashofunivaq.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.myclashofunivaq.business.GiocatoreUtenteService;
import it.univaq.disim.oop.myclashofunivaq.business.PartitaService;
import it.univaq.disim.oop.myclashofunivaq.business.impl.GiocatoreUtenteServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.business.impl.NicknameNonValido;
import it.univaq.disim.oop.myclashofunivaq.business.impl.PartitaServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.domain.GiocatoreUtente;
import it.univaq.disim.oop.myclashofunivaq.view.InizializzaDati;
import it.univaq.disim.oop.myclashofunivaq.view.ViewDispatcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
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
	
	private final PartitaService partitaService;
	private final GiocatoreUtenteService giocatoreUtenteService;
	private ViewDispatcher dispatcher;
	
	private static final String stringaConferma = "nickname valido vai avanti";
	
	public NicknameGiocatore2Controller() {
		giocatoreUtenteService = new GiocatoreUtenteServiceImpl();
		dispatcher = ViewDispatcher.getInstance();
		partitaService = new PartitaServiceImpl();
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
			GiocatoreUtente giocatore2 = giocatoreUtenteService.convalidaNickName(nicknameGiocatore2.getText());
			partitaService.aggiungiGiocatore(giocatore2);
			this.confermaNickname.setText(stringaConferma);
			this.confermaNickname.setAlignment(Pos.CENTER);
			this.accettaNickname.setDisable(true);
		} catch (NicknameNonValido e) {
			this.confermaNickname.setText(e.getMessage());  //gestione eccezione a livello utente
		}
	}
	
	@FXML
	public void avantiAction(ActionEvent event) {
		// prossima vista scelta deck
	}
}
