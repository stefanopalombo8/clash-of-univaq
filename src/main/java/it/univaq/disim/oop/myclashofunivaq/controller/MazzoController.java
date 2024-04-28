package it.univaq.disim.oop.myclashofunivaq.controller;

import java.net.URL;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.univaq.disim.oop.myclashofunivaq.business.MazzoService;
import it.univaq.disim.oop.myclashofunivaq.business.PartitaService;
import it.univaq.disim.oop.myclashofunivaq.business.CartaService;
import it.univaq.disim.oop.myclashofunivaq.business.TurnoService;
import it.univaq.disim.oop.myclashofunivaq.business.impl.Carte;
import it.univaq.disim.oop.myclashofunivaq.business.impl.MazzoServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.business.impl.PartitaServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.business.impl.TurnoServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.controller.utilitis.GraphicUtility;
import it.univaq.disim.oop.myclashofunivaq.domain.Carta;
import it.univaq.disim.oop.myclashofunivaq.domain.Giocatore;
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
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;

public class MazzoController implements Initializable, InizializzaDati<Partita> {

	@FXML
	private TilePane roster;

	@FXML
	private GridPane mazzo;

	@FXML
	private Button confermaMazzo;

	private ViewDispatcher dispatcher;
	private Partita partita;

	private final PartitaService partitaService;
	private final TurnoService turnoService;
	private final CartaService cartaService;
	private final MazzoService mazzoService;
	private GraphicUtility utility;

	private List<Carta> carteScelte;
	private List<GridPane> grids;
	private static int i = 0;
	private static final int dim_img = 80;

	public MazzoController() {
		dispatcher = ViewDispatcher.getInstance();
		partitaService = new PartitaServiceImpl();
		cartaService = new Carte();
		mazzoService = new MazzoServiceImpl();
		turnoService = new TurnoServiceImpl();
		carteScelte = new ArrayList<>();
		utility = new GraphicUtility();
		grids = new ArrayList<>();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Personaggio personaggio = null;
		Incantesimo incantesimo = null;
		grids.add(mazzo);
		
		utility.mappingGriglie(grids);
		
		for (Carta carta : cartaService.trovaTutteCarte()) {
			ImageView imageView = utility.creaImpostaImageView(carta.getImmagineCarta(), dim_img, dim_img);
			
			roster.getChildren().add(imageView);
			
			if(carta instanceof Personaggio)
				personaggio = (Personaggio) carta;
			else if(carta instanceof Incantesimo)  {
				if(carta.getNome().equals("RendiInvulnerabile"))
					incantesimo = (Incantesimo) carta;
			}
				
			imageView.setOnDragDetected(event -> {
				Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
				ClipboardContent content = new ClipboardContent();
				content.putImage(imageView.getImage());
				carteScelte.add(carta);
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
					ImageView newImageView = utility.creaImpostaImageView(db.getImage(), dim_img, dim_img);
					
					utility.aggiungiCartaImmagineGriglia(mazzo, null, newImageView);
					
					success = true;

				}
				event.setDropCompleted(success);
				event.consume();
			});

		}
		
		for(int i = 0; i<4; i++) {
			carteScelte.add(personaggio);
		}
		for(int i = 4; i <8; i++) {
			carteScelte.add(incantesimo);
		}
		
	}

	@Override
	public void inizializza(Partita partita) {
		this.partita = partita;
	}

	@FXML
	private void confermaMazzoAction(ActionEvent event) throws InterruptedException {
		Giocatore giocatoreCorrente;

		try {
			
			giocatoreCorrente = turnoService.alternaGiocatore(partita);
			i++;
			Mazzo mazzo = mazzoService.creaMazzo(carteScelte);
			
			// mazzoService.controllaMazzo(mazzo);
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
