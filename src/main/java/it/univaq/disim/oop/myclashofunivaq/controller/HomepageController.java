package it.univaq.disim.oop.myclashofunivaq.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.univaq.disim.oop.myclashofunivaq.business.GiocatoreUtenteService;
import it.univaq.disim.oop.myclashofunivaq.business.MazzoService;
import it.univaq.disim.oop.myclashofunivaq.business.PartitaService;
import it.univaq.disim.oop.myclashofunivaq.business.ResetStaticVariables;
import it.univaq.disim.oop.myclashofunivaq.business.impl.GiocatoreUtenteServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.business.impl.MazzoServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.business.impl.NicknameNonValido;
import it.univaq.disim.oop.myclashofunivaq.business.impl.PartitaServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.business.impl.TurnoServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.controller.utilitis.GraphicUtility;
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

	private final PartitaService partitaService;
	
	private final ResetStaticVariables mazzoReset;
	private final ResetStaticVariables turniReset;
	private final ResetStaticVariables utilityReset;
	
	

	private final GiocatoreUtenteService giocatoreUtenteService;

	private ViewDispatcher dispatcher;

	public HomepageController() {
		giocatoreUtenteService = new GiocatoreUtenteServiceImpl();
		dispatcher = ViewDispatcher.getInstance();
		partitaService = new PartitaServiceImpl();
		mazzoReset = new MazzoServiceImpl();
		turniReset = new TurnoServiceImpl();
		utilityReset = new GraphicUtility();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		disabilitaGamemod(giocaControGiocatore);
		disabilitaGamemod(giocaControCPU);
		
		this.mazzoReset.reset();
		this.turniReset.reset();
		this.utilityReset.reset();
	}

	private void disabilitaGamemod(Button bottone) {
		bottone.disableProperty().bind(nickname.textProperty().isEmpty());
	}

	/*
	 * qua mi serve il metodo perchè questo procedimento viene fatto per ogni
	 * bottone partita quindi per non riscriverlo 3 volte
	 */

	public boolean accettaNickname(Partita partita) {
		try {
			GiocatoreUtente giocatore = giocatoreUtenteService.convalidaNickName(nickname.getText());
			return partitaService.aggiungiGiocatore(giocatore, partita);

		} catch (NicknameNonValido e) {
			this.confermaNickname.setText(e.getMessage()); // gestione eccezione a livello utente
			return false;
		}

	}

	@FXML
	public void giocaControGiocatoreAction(ActionEvent event) {
		Partita partita = partitaService.creaPartita();
		
		if (this.accettaNickname(partita))
			try {
				dispatcher.caricaVista("NicknameGiocatore2", partita);
			} catch (ViewException e) {
				e.printStackTrace();
			}
	}

	@FXML
	public void recuperaPartiteSalvateAction(ActionEvent event) {
		try {
			dispatcher.caricaVista("listaPartiteSalvate");
		} catch (ViewException e) {
			e.printStackTrace();
		}
	}

}
