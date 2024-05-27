package it.univaq.disim.oop.myclashofunivaq.controller;

import java.net.URL;

import java.util.ResourceBundle;

import it.univaq.disim.oop.myclashofunivaq.business.GiocatoreUtenteService;
import it.univaq.disim.oop.myclashofunivaq.business.PartitaService;
import it.univaq.disim.oop.myclashofunivaq.business.ResetStaticVariables;
import it.univaq.disim.oop.myclashofunivaq.business.impl.GiocatoreUtenteServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.business.impl.IncantesimoServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.business.impl.MazzoServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.business.impl.NicknameNonValido;
import it.univaq.disim.oop.myclashofunivaq.business.impl.PartitaServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.business.impl.TurnoServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.controller.utilities.GraphicEngine;
import it.univaq.disim.oop.myclashofunivaq.domain.GiocatoreComputer;
import it.univaq.disim.oop.myclashofunivaq.domain.GiocatoreUtente;
import it.univaq.disim.oop.myclashofunivaq.domain.Partita;
import it.univaq.disim.oop.myclashofunivaq.view.ViewDispatcher;
import it.univaq.disim.oop.myclashofunivaq.view.ViewException;

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
	
	private final ResetStaticVariables mazzoReset;
	private final ResetStaticVariables turniReset;
	private final ResetStaticVariables engineReset;
	private final ResetStaticVariables incantesimiReset;
	
	private final PartitaService partitaService;
	private final GiocatoreUtenteService giocatoreUtenteService;

	private final ViewDispatcher dispatcher;

	public HomepageController() {
		this.giocatoreUtenteService = new GiocatoreUtenteServiceImpl();
		this.dispatcher = ViewDispatcher.getInstance();
		this.partitaService = new PartitaServiceImpl();
		this.mazzoReset = new MazzoServiceImpl();
		this.turniReset = new TurnoServiceImpl();
		this.engineReset = new GraphicEngine();
		this.incantesimiReset = new IncantesimoServiceImpl();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		disabilitaGamemod(this.giocaControGiocatore);
		disabilitaGamemod(this.giocaControCPU);
		
		this.mazzoReset.reset();
		this.turniReset.reset();
		this.engineReset.reset();
		this.incantesimiReset.reset();
	}

	private void disabilitaGamemod(Button bottone) {
		bottone.disableProperty().bind(this.nickname.textProperty().isEmpty());
	}

	public boolean accettaNickname(Partita partita) {
		try {
			GiocatoreUtente giocatore = this.giocatoreUtenteService.convalidaNickName(this.nickname.getText());
			return this.partitaService.aggiungiGiocatore(giocatore, partita);

		} catch (NicknameNonValido e) {
			this.confermaNickname.setText(e.getMessage());
			return false;
		}

	}

	@FXML
	public void giocaControGiocatoreAction(ActionEvent event) {
		Partita partita = this.partitaService.creaPartita();
		
		if (this.accettaNickname(partita))
			try {
				this.dispatcher.caricaVista("NicknameGiocatore2", partita);
			} catch (ViewException e) {
				e.printStackTrace();
			}
	}
	
	@FXML
	public void giocaControCPUAction(ActionEvent event) {
		Partita partita = this.partitaService.creaPartita();
		GiocatoreComputer giocatoreComputer = new GiocatoreComputer("CPU");;
		
		if (this.accettaNickname(partita))
			try {
				this.partitaService.aggiungiGiocatore(giocatoreComputer, partita);
				this.dispatcher.caricaVista("sceltaMazzo", partita);
			} catch (ViewException e) {
				e.printStackTrace();
			} catch (NicknameNonValido e) {
				this.confermaNickname.setText(e.getMessage());
			}
	}

	@FXML
	public void recuperaPartiteSalvateAction(ActionEvent event) {
		try {
			this.dispatcher.caricaVista("listaPartiteSalvate");
		} catch (ViewException e) {
			e.printStackTrace();
		}
	}

}