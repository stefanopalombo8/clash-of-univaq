package it.univaq.disim.oop.myclashofunivaq.controller;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.myclashofunivaq.business.MazzoService;
import it.univaq.disim.oop.myclashofunivaq.business.PartitaService;
import it.univaq.disim.oop.myclashofunivaq.business.PersonaggioService;
import it.univaq.disim.oop.myclashofunivaq.business.impl.MazzoServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.business.impl.PartitaServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.business.impl.Personaggi;
import it.univaq.disim.oop.myclashofunivaq.domain.Carta;
import it.univaq.disim.oop.myclashofunivaq.domain.Giocatore;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class MazzoController implements Initializable, InizializzaDati<Partita> {

	@FXML
	private AnchorPane roster;

	@FXML
	private GridPane mazzo;

	@FXML
	private Button confermaMazzo;

	private int riga = 0;
	private int colonna = 0;
	private static final int dim_img_carta = 80; // quadrato

	private ViewDispatcher dispatcher;
	private Partita partita;

	private final PartitaService partitaService;
	private final PersonaggioService personaggioService;
	private final MazzoService mazzoService;

	private List<Carta> carteScelte;
	private static int i = 0;

	public MazzoController() {
		dispatcher = ViewDispatcher.getInstance();
		partitaService = new PartitaServiceImpl();
		personaggioService = new Personaggi();
		mazzoService = new MazzoServiceImpl();
		carteScelte = new ArrayList<>();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		for (Personaggio p : personaggioService.trovaTuttiPersonaggi()) {
			System.out.println(p.getNome());
			ImageView immagine_personaggio = new ImageView(p.getImmagineCarta());
			immagine_personaggio.setFitWidth(dim_img_carta);
			immagine_personaggio.setFitHeight(dim_img_carta);
			roster.getChildren().add(immagine_personaggio);

			immagine_personaggio.setOnDragDetected(event -> {
				Dragboard db = immagine_personaggio.startDragAndDrop(TransferMode.ANY);
				ClipboardContent content = new ClipboardContent();
				content.putImage(immagine_personaggio.getImage());
				db.setContent(content);
				event.consume();
			});

			mazzo.setOnDragOver(event -> {
				if (event.getGestureSource() != mazzo && event.getDragboard().hasImage()) {
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
				}
				event.consume();
			});

			mazzo.setOnDragDropped(event -> {
				Dragboard db = event.getDragboard();
				boolean success = false;

				if (db.hasImage()) {
					ImageView imageView = new ImageView(db.getImage());
					imageView.setFitHeight(dim_img_carta);
					imageView.setFitWidth(dim_img_carta);
					mazzo.add(imageView, colonna++, riga);

					if (colonna == 4) {
						colonna = 0;
						riga = 1;
					}

					carteScelte.add(p);

					success = (colonna == 4 && riga == 1) ? true : false;

				}
				event.setDropCompleted(success);
				event.consume();
			});

		}
	}

	@Override
	public void inizializza(Partita partita) {
		this.partita = partita;
	}

	@FXML
	private void confermaMazzoAction(ActionEvent event) {
		Giocatore giocatoreCorrente;
		
		try {
			if (i < 1) {
				giocatoreCorrente = partitaService.findAllGiocatori(partita)[i++];
				Mazzo mazzo = mazzoService.creaMazzo(carteScelte);
				// mazzoService.controllaMazzo(mazzo);
				mazzoService.aggiungiMazzo(mazzo, giocatoreCorrente);
				
				dispatcher.caricaVista("sceltaMazzo", partita);
			}
			else {
				System.out.println("\nMAZZI IMPOSTATI");
				dispatcher.caricaVista("gioco");
			}
				
		} catch (ViewException e) {
			e.printStackTrace();
		}

	}

}
