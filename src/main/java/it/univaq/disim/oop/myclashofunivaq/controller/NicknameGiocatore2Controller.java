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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
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
	private ViewDispatcher dispatcher;
	
	private Partita partita;
	
	public NicknameGiocatore2Controller() {
		giocatoreUtenteService = new GiocatoreUtenteServiceImpl();
		dispatcher = ViewDispatcher.getInstance();
		partitaService = new PartitaServiceImpl();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		avanti.disableProperty().bind(nickname.textProperty().isEmpty());
	}
	
	@Override
	public void inizializza(Partita partita) {
		this.partita = partita;
	}
	
	public void accettaNickname(Partita partita) {
		try {
			GiocatoreUtente giocatore2 = giocatoreUtenteService.convalidaNickName(nickname.getText(), partita);
			partitaService.aggiungiGiocatore(giocatore2, partita);
			
		} catch (NicknameNonValido e) {
			this.confermaNickname.setText(e.getMessage());  //gestione eccezione a livello utente
		}
	}
	
	@FXML
	public void avantiAction(ActionEvent event) {
		this.accettaNickname(partita);
		partitaService.giocatoriPartita(partita).stream().forEach((g) -> System.out.println(g.getNickname()));
		//dispatcher.caricaVista(...)
	}
}
