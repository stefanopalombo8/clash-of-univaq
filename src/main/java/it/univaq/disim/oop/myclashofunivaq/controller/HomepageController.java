package it.univaq.disim.oop.myclashofunivaq.controller;

import java.io.IOException;

import java.net.URL;


import java.util.ResourceBundle;

import it.univaq.disim.oop.myclashofunivaq.business.GiocatoreUtenteService;
import it.univaq.disim.oop.myclashofunivaq.business.PartitaService;
import it.univaq.disim.oop.myclashofunivaq.business.impl.GiocatoreUtenteServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.business.impl.NicknameNonValido;
import it.univaq.disim.oop.myclashofunivaq.business.impl.PartitaServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.domain.GiocatoreUtente;
import it.univaq.disim.oop.myclashofunivaq.domain.Partita;
import it.univaq.disim.oop.myclashofunivaq.view.ViewDispatcher;
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
	private Label confermaNickname;
	
	@FXML
	private Button recuperaPartiteSalvate;
	
	@FXML
	private Button giocaControGiocatore;
	
	@FXML
	private Button giocaControCPU;
	
	private final PartitaService partitaService;
	
	private final GiocatoreUtenteService giocatoreUtenteService;
	
	private ViewDispatcher dispatcher;

	public HomepageController() {
		giocatoreUtenteService = new GiocatoreUtenteServiceImpl();
		dispatcher = ViewDispatcher.getInstance();
		partitaService = new PartitaServiceImpl();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		disabilitaGamemod(recuperaPartiteSalvate);
		disabilitaGamemod(giocaControGiocatore);
		disabilitaGamemod(giocaControCPU);
	}
	
	private void disabilitaGamemod(Button bottone) {
		bottone.disableProperty().bind(nickname.textProperty().isEmpty());
	}
	
	/* qua mi serve il metodo perchè questo procedimento viene fatto per ogni bottone partita
	 * quindi per non riscriverlo 3 volte
	 */
	
	public boolean accettaNickname(Partita partita) {
		try {
			GiocatoreUtente giocatore = giocatoreUtenteService.convalidaNickName(nickname.getText());
			return partitaService.aggiungiGiocatore(giocatore, partita);
			
		} catch (NicknameNonValido e) {
			this.confermaNickname.setText(e.getMessage());  //gestione eccezione a livello utente
			return false;
		}
		
	}
	
	
	@FXML
	public void giocaControGiocatoreAction(ActionEvent event) throws IOException {
		Partita partita = partitaService.creaPartita();
		if(this.accettaNickname(partita))
			dispatcher.caricaVista("NicknameGiocatore2", partita);
	}
	
}
