package it.univaq.disim.oop.myclashofunivaq.controller;

import java.net.URL;

import java.util.ResourceBundle;

import it.univaq.disim.oop.myclashofunivaq.business.GiocatoreUtenteService;
import it.univaq.disim.oop.myclashofunivaq.business.PartitaService;
import it.univaq.disim.oop.myclashofunivaq.business.impl.GiocatoreUtenteServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.business.impl.NicknameNonValido;
import it.univaq.disim.oop.myclashofunivaq.business.impl.PartitaServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.domain.GiocatoreUtente;
import it.univaq.disim.oop.myclashofunivaq.domain.Partita;
import it.univaq.disim.oop.myclashofunivaq.view.InizializzaDati;
import it.univaq.disim.oop.myclashofunivaq.view.ViewDispatcher;
import it.univaq.disim.oop.myclashofunivaq.view.ViewException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class NicknameGiocatore2Controller implements Initializable, InizializzaDati<Partita>{
	
	@FXML
	private TextField nickname;
	
	@FXML
	private Label confermaNickname;
	
	@FXML
	private Button avanti;
	
	private final PartitaService partitaService;
	private final GiocatoreUtenteService giocatoreUtenteService;
	
	private final ViewDispatcher dispatcher;
	
	private Partita partita;
	
	public NicknameGiocatore2Controller() {
		this.giocatoreUtenteService = new GiocatoreUtenteServiceImpl();
		this.dispatcher = ViewDispatcher.getInstance();
		this.partitaService = new PartitaServiceImpl();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.avanti.disableProperty().bind(this.nickname.textProperty().isEmpty());
	}
	
	@Override
	public void inizializza(Partita partita) {
		this.partita = partita;
	}
	
	@FXML
	public void avantiAction(ActionEvent event) throws ViewException {
		try {
			GiocatoreUtente giocatore2 = this.giocatoreUtenteService.convalidaNickName(nickname.getText());
			if(this.partitaService.aggiungiGiocatore(giocatore2, this.partita))
				this.dispatcher.caricaVista("sceltaMazzo", this.partita);
		} catch (NicknameNonValido e) {
			this.confermaNickname.setText(e.getMessage());
		}
	}
}