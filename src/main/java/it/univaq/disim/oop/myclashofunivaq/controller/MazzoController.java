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
import it.univaq.disim.oop.myclashofunivaq.controller.utilities.GraphicUtility;
import it.univaq.disim.oop.myclashofunivaq.domain.Carta;
import it.univaq.disim.oop.myclashofunivaq.domain.Giocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.GiocatoreComputer;
import it.univaq.disim.oop.myclashofunivaq.domain.Incantesimo;
import it.univaq.disim.oop.myclashofunivaq.domain.Mazzo;
import it.univaq.disim.oop.myclashofunivaq.domain.Partita;
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

	@FXML
	private Label labelErrori;

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
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void inizializza(Partita partita) {
		this.partita = partita;
		giocatoreCorrente = turnoService.alternaGiocatore(partita);
		this.nomeGiocatoreCorrente.setText(giocatoreCorrente.getNickname());

		if (giocatoreCorrente instanceof GiocatoreComputer) {
			this.costruisciMazzoComputer();
			this.action();
		}

		grids.add(mazzo);
		utility.mappingGriglie(grids);

		for (Carta carta : cartaService.trovaTutteCarte()) {
			ImageView imageView = utility.creaImpostaImageView(carta.getImmagineCarta(), dim_img, dim_img);

			roster.getChildren().add(imageView);

			utility.impostaTooltip(imageView, carta);

			imageView.setOnMouseClicked(event1 -> {
				ImageView imageViewScelta = utility.creaImpostaImageView(carta.getImmagineCarta(), dim_img, dim_img);
				try {
					if (carteScelte.contains(carta) || carteScelte.size() == 8) {
						throw new RuntimeException();
					}
					carteScelte.add(carta);

					utility.aggiungiCartaImmagineGriglia(mazzo, null, imageViewScelta);

				} catch (RuntimeException e) {
					this.labelErrori.setText("hai già scelto quella carta oppure è pieno");
				}
			});

		}

	}

	@FXML
	private void confermaMazzoAction(ActionEvent event) {
		this.action();
	}

	private void action() {
		try {
			i++;
			Mazzo mazzo = mazzoService.creaMazzo(carteScelte);
			/* metodo superfluo perché essendo il mazzo senza duplicati non si
			 * possono avere un numero di categorie < di 5, in ogni viene lasciata l'implementazione
			 * nel service
			 */
			//mazzoService.controllaMazzo(mazzo);

			mazzoService.aggiungiMazzo(mazzo, giocatoreCorrente);

			if (i == 1) {
				dispatcher.caricaVista("sceltaMazzo", partita);
			} else {
				System.out.println("MAZZI IMPOSTATI");
				i = 0;
				for(Carta c : carteScelte) {
					System.out.println("utente " + c.getNome());
				}
				dispatcher.caricaVista("gioco", partita);
			}

		} catch (ViewException e) {
			e.printStackTrace();
		}
	}

	private void costruisciMazzoComputer() {
		for(Carta carta : cartaService.trovaTutteCarte()) {
			if(!(carta instanceof Incantesimo) && carteScelte.size() < 8)
				this.carteScelte.add(carta);
		}
	}

}