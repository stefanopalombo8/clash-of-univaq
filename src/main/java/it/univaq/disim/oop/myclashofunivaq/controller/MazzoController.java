package it.univaq.disim.oop.myclashofunivaq.controller;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.myclashofunivaq.business.MazzoService;
import it.univaq.disim.oop.myclashofunivaq.business.CartaService;
import it.univaq.disim.oop.myclashofunivaq.business.TurnoService;
import it.univaq.disim.oop.myclashofunivaq.business.impl.Carte;
import it.univaq.disim.oop.myclashofunivaq.business.impl.MazzoServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.business.impl.TurnoServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.controller.utilitis.GraphicUtility;
import it.univaq.disim.oop.myclashofunivaq.domain.Carta;
import it.univaq.disim.oop.myclashofunivaq.domain.Giocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.GiocatoreComputer;
import it.univaq.disim.oop.myclashofunivaq.domain.Incantesimo;
import it.univaq.disim.oop.myclashofunivaq.domain.Mazzo;
import it.univaq.disim.oop.myclashofunivaq.domain.Partita;
import it.univaq.disim.oop.myclashofunivaq.domain.Personaggio;
import it.univaq.disim.oop.myclashofunivaq.view.InizializzaDati;
import it.univaq.disim.oop.myclashofunivaq.view.ViewDispatcher;
import it.univaq.disim.oop.myclashofunivaq.view.ViewException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;

public class MazzoController implements Initializable, InizializzaDati<Partita> {

	@FXML
	private TilePane roster;

	@FXML
	private GridPane mazzo;

	@FXML
	private Button confermaMazzo;
	
	@FXML
	private Label nomeGiocatoreCorrente;

	private ViewDispatcher dispatcher;
	private Partita partita;

	private final TurnoService turnoService;
	private final CartaService cartaService;
	private final MazzoService mazzoService;
	private GraphicUtility utility;

	private Giocatore giocatoreCorrente;
	private List<Carta> carteScelte;
	private List<GridPane> grids;
	private static int i = 0;
	private static final int dim_img = 80;

	public MazzoController() {
		dispatcher = ViewDispatcher.getInstance();
		cartaService = new Carte();
		mazzoService = new MazzoServiceImpl();
		turnoService = new TurnoServiceImpl();
		carteScelte = new ArrayList<>();
		utility = new GraphicUtility();
		grids = new ArrayList<>();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {	}

	@Override
	public void inizializza(Partita partita) {
		this.partita = partita;
		giocatoreCorrente = turnoService.alternaGiocatore(partita);
		this.nomeGiocatoreCorrente.setText(giocatoreCorrente.getNickname());
		
		Personaggio personaggio = null;
		Incantesimo incantesimo = null;

		grids.add(mazzo);
		utility.mappingGriglie(grids);
		
		for (Carta carta : cartaService.trovaTutteCarte()) {
			ImageView imageView = utility.creaImpostaImageView(carta.getImmagineCarta(), dim_img, dim_img);
			
			roster.getChildren().add(imageView);
			
			// PER I MAZZI DI TESTI
			if(carta instanceof Personaggio)
				personaggio = (Personaggio) carta;
			else if(carta instanceof Incantesimo)  {
				if(carta.getNome().equals("RendiInvulnerabile"))
					incantesimo = (Incantesimo) carta;
			}
			
			imageView.setOnMouseClicked(event1 -> {
				ImageView imageViewScelta = utility.creaImpostaImageView(carta.getImmagineCarta(), dim_img, dim_img);
				carteScelte.add(carta);
				
				utility.aggiungiCartaImmagineGriglia(mazzo, null, imageViewScelta);
				
				imageViewScelta.setOnMouseClicked(event2 -> {
					carteScelte.remove(carta);
					mazzo.getChildren().remove(imageViewScelta);
				});
				
			});

		}
		
		//MAZZI DI TEST
		if(giocatoreCorrente instanceof GiocatoreComputer) {
			for(int i = 0; i <8; i++) {
				carteScelte.add(personaggio);
			}
		}
		else {
			for(int i = 0; i<4; i++) {
				carteScelte.add(personaggio);
			}
			for(int i = 4; i <8; i++) {
				carteScelte.add(incantesimo);
			}
		}
		
		
		if(giocatoreCorrente instanceof GiocatoreComputer) {
			this.action();
		}
		
		
	}

	@FXML
	private void confermaMazzoAction(ActionEvent event)  {
		this.action();
	}
	
	private void action() {
		try {
			i++;
			Mazzo mazzo = mazzoService.creaMazzo(carteScelte);
			
			System.out.println(mazzoService.controllaMazzo(mazzo));
			
			mazzoService.aggiungiMazzo(mazzo, giocatoreCorrente);
			
			if (i == 1) {
				dispatcher.caricaVista("sceltaMazzo", partita);
			} else {
				System.out.println("\nMAZZI IMPOSTATI");
				i = 0;
				dispatcher.caricaVista("gioco", partita);
			}

		} catch (ViewException e) {
			e.printStackTrace();
		}
	}

}
