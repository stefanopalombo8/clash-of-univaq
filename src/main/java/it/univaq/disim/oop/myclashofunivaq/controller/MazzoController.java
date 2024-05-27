package it.univaq.disim.oop.myclashofunivaq.controller;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.myclashofunivaq.business.MazzoService;
import it.univaq.disim.oop.myclashofunivaq.business.PartitaService;
import it.univaq.disim.oop.myclashofunivaq.business.CartaService;
import it.univaq.disim.oop.myclashofunivaq.business.impl.Carte;
import it.univaq.disim.oop.myclashofunivaq.business.impl.MazzoException;
import it.univaq.disim.oop.myclashofunivaq.business.impl.MazzoServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.business.impl.PartitaServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.controller.utilities.GraphicEngine;
import it.univaq.disim.oop.myclashofunivaq.controller.utilities.Posizione;
import it.univaq.disim.oop.myclashofunivaq.domain.Carta;
import it.univaq.disim.oop.myclashofunivaq.domain.Giocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.GiocatoreComputer;
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

	@FXML
	private Label labelErrori;

	private final PartitaService partitaService;
	private final CartaService cartaService;
	private final MazzoService mazzoService;
	private final GraphicEngine engine;
	private final ViewDispatcher dispatcher;

	private Partita partita;
	private Giocatore giocatoreCorrente;
	private List<Carta> carteScelte;
	private List<GridPane> grids;
	private static int i = 0;
	private static final int dim_img = 80;

	public MazzoController() {
		this.dispatcher = ViewDispatcher.getInstance();
		this.cartaService = new Carte();
		this.mazzoService = new MazzoServiceImpl();
		this.partitaService = new PartitaServiceImpl();
		this.carteScelte = new ArrayList<>();
		this.engine = new GraphicEngine();
		this.grids = new ArrayList<>(8);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public void inizializza(Partita partita) {
		this.partita = partita;
		this.giocatoreCorrente = this.partitaService.alternaGiocatore(partita);
		this.nomeGiocatoreCorrente.setText(this.giocatoreCorrente.getNickname());

		if (this.giocatoreCorrente instanceof GiocatoreComputer) {
			this.costruisciMazzoComputer();
			this.action();
		}

		this.grids.add(this.mazzo);
		this.engine.mappingGriglie(this.grids);

		for (Carta carta : this.cartaService.trovaTutteCarte()) {
			ImageView imageView = this.engine.creaImpostaImageView(carta.getImmagineCarta(), dim_img, dim_img);

			this.roster.getChildren().add(imageView);

			this.engine.impostaTooltip(imageView, carta);

			imageView.setOnMouseClicked(event1 -> {
				ImageView imageViewScelta = this.engine.creaImpostaImageView(carta.getImmagineCarta(), dim_img,
						dim_img);
				try {

					this.mazzoService.checkCartaScelta(carta, this.carteScelte);

					this.carteScelte.add(carta);

					// non mi serve mappare la carta nell'engine, ma solo aggiungere l'immagine alla
					// griglia
					this.engine.aggiungiCartaImmagineGriglia(mazzo, null, imageViewScelta);

					this.labelErrori.setText("");

					imageViewScelta.setOnMouseClicked(event2 -> {
			
						Posizione posizione = new Posizione(GridPane.getColumnIndex(imageViewScelta),
								GridPane.getRowIndex(imageViewScelta));

						this.carteScelte.remove(carta);
						this.engine.eliminaImmagineCarta(this.mazzo, posizione, imageViewScelta);
					});

				} catch (MazzoException e) {
					this.labelErrori.setText(e.getMessage());
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

			this.mazzoService.checkSizeCarteScelte(this.carteScelte);

			Mazzo mazzo = this.mazzoService.creaMazzo(this.carteScelte);

			if (!(this.giocatoreCorrente instanceof GiocatoreComputer)) {
				this.mazzoService.controllaMazzo(mazzo);
			}

			this.mazzoService.aggiungiMazzo(mazzo, this.giocatoreCorrente);

			i++;

			if (i == 1) {
				this.dispatcher.caricaVista("sceltaMazzo", this.partita);
			} else {
				System.out.println("MAZZI IMPOSTATI");
				i = 0;
				this.dispatcher.caricaVista("gioco", this.partita);
			}

		} catch (ViewException e) {
			e.printStackTrace();
		} catch (MazzoException e) {
			this.labelErrori.setText(e.getMessage());
		}
	}

	// non metto incantesimi al computer per semplicità
	private void costruisciMazzoComputer() {
		for (Carta carta : this.cartaService.trovaTutteCarte()) {
			if (this.carteScelte.size() < 8 && carta instanceof Personaggio)
				this.carteScelte.add(carta);
		}
	}

}