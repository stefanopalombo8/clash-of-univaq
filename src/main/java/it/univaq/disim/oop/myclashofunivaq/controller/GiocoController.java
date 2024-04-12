package it.univaq.disim.oop.myclashofunivaq.controller;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.univaq.disim.oop.myclashofunivaq.business.GiocatoreService;
import it.univaq.disim.oop.myclashofunivaq.business.MazzoService;
import it.univaq.disim.oop.myclashofunivaq.business.PartitaService;
import it.univaq.disim.oop.myclashofunivaq.business.TurnoService;
import it.univaq.disim.oop.myclashofunivaq.business.impl.GiocatoreUtenteServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.business.impl.MazzoServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.business.impl.PartitaServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.business.impl.TurnoServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.controller.utilitis.GraphicUtility;
import it.univaq.disim.oop.myclashofunivaq.controller.utilitis.Posizione;
import it.univaq.disim.oop.myclashofunivaq.domain.Carta;
import it.univaq.disim.oop.myclashofunivaq.domain.Giocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.Mazzo;
import it.univaq.disim.oop.myclashofunivaq.domain.Partita;
import it.univaq.disim.oop.myclashofunivaq.domain.Personaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.PosizionamentoPersonaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.Stato;
import it.univaq.disim.oop.myclashofunivaq.domain.Turno;
import it.univaq.disim.oop.myclashofunivaq.view.InizializzaDati;
import it.univaq.disim.oop.myclashofunivaq.view.ViewDispatcher;
import it.univaq.disim.oop.myclashofunivaq.view.ViewException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class GiocoController implements Initializable, InizializzaDati<Partita> {

	@FXML
	private ProgressBar elisir;

	@FXML
	private Label elisirIndicator;

	@FXML
	private Label nomeGiocatore;

	@FXML
	private GridPane carteMano;

	@FXML
	private ImageView prossimaCarta;

	@FXML
	private Button passaTurno;

	@FXML
	private Label timer;

	@FXML
	private GridPane stradaSX;

	@FXML
	private GridPane stradaDX;

	@FXML
	private GridPane stradaC;

	private List<GridPane> gridsList;

	private int timerDurantion = 5; // seconds
	private int secondsElapsed;
	private Timeline timeline;

	private ViewDispatcher dispatcher;
	private Partita partita;
	private Giocatore giocatoreCorrente;
	private Turno turnoCorrente;

	private final PartitaService partitaService;
	private final TurnoService turnoService;
	private final MazzoService mazzoService;
	private final GiocatoreService giocatoreService;

	private GraphicUtility utility;
	private static final int dim_img = 80;

	public GiocoController() {
		this.dispatcher = ViewDispatcher.getInstance();
		this.partitaService = new PartitaServiceImpl();
		this.turnoService = new TurnoServiceImpl();
		this.mazzoService = new MazzoServiceImpl();
		this.secondsElapsed = timerDurantion;
		this.utility = new GraphicUtility();
		this.gridsList = new ArrayList<>();
		this.giocatoreService = new GiocatoreUtenteServiceImpl();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gridsList.add(stradaSX);
		gridsList.add(stradaDX);
		gridsList.add(stradaC);
		gridsList.add(carteMano);

		utility.mappingGriglie(gridsList);
	}

	@Override
	public void inizializza(Partita partita) {
		this.partita = partita;
		giocatoreCorrente = turnoService.alternaGiocatore(partita);

		timerImpl();
		turnoCorrente = turnoService.avviaTurno(timeline, giocatoreCorrente);

		nomeGiocatore.setText(giocatoreCorrente.getNickname());

		double progress = turnoCorrente.getElisirGiocatore();
		elisir.setProgress(progress);
		elisirIndicator.setText(this.formatElisir(progress));

		Mazzo mazzo = mazzoService.trovaMazzo(giocatoreCorrente);

		if (turnoService.isFirstTurno(turnoCorrente)) {
			
			for (Carta carta : mazzoService.mostraCarteMano(mazzo)) {
				ImageView imageView = utility.creaImpostaImageView(carta.getImmagineCarta(), dim_img, dim_img);

				this.impostaTooltip(imageView, carta);

				//Mapping immagini e carte in mano
				utility.aggiungiCartaImmagineGriglia(carteMano, carta, imageView);

				utility.setImageDragProperty(carteMano, imageView);

			}
			
		}
		else {
			utility.ripristinaStato(turnoCorrente.getStato(), gridsList);
		}
		
		Carta[] prossimaCartaMazzo = new Carta[1]; //WRAPPER
		prossimaCartaMazzo[0] = mazzoService.mostraProssimaCarta(mazzo);
		prossimaCarta.setImage(prossimaCartaMazzo[0].getImmagineCarta());
		
		// gridsList.remove(carteMano);
		for (GridPane grid : gridsList) {

			grid.setOnDragOver(event -> {
				if (event.getGestureSource() != grid && event.getDragboard().hasImage()) {
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
				}
		
				event.consume();
			});

			grid.setOnDragDropped(event -> {
				
				if(grid.equals(carteMano)) {
					System.err.println("NON PUOI AGGIUNGERE CARTE IN MANO");
					throw new RuntimeException();
					
				}
				
				if(grid.getChildren().size() == 4 && grid.getId().equals("stradaSX")) {
					System.err.println("STRADA PIENA");
					throw new RuntimeException();
				}
					
				
				Dragboard db = event.getDragboard();
				boolean success = false;

				ImageView newImageView = null;
				Posizione posizione = null;
				Carta cartaSchierata = null;
				ImageView imageViewProssimaCarta = null;

				if (db.hasImage()) {
					newImageView = utility.creaImpostaImageView(db.getImage(), dim_img, dim_img);
					
					//Mapping immagine posizionata
					utility.aggiungiCartaImmagineGriglia(grid, null, newImageView);
					
					imageViewProssimaCarta = utility.creaImpostaImageView(prossimaCarta.getImage(), 100, 100);
						
					posizione = utility.getPosizioneCartaSelezionata()[0];

					try {
						//Mapping carta schierata
						cartaSchierata = (Carta) utility.ricercaCartaSelezionataInMano(posizione).clone();
						utility.aggiungiCartaImmagineGriglia(grid, cartaSchierata, null); //aggiunta nella strada
						this.impostaTooltip(newImageView, cartaSchierata);
					} catch (CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//Mapping immagine/carta da prossima carta a mano
					utility.aggiungiCartaImmagineGriglia(carteMano, prossimaCartaMazzo[0], imageViewProssimaCarta, posizione);
					
					this.impostaTooltip(imageViewProssimaCarta, prossimaCartaMazzo[0]);

					utility.setImageDragProperty(carteMano, imageViewProssimaCarta);
					
					prossimaCartaMazzo[0] = mazzoService.mostraProssimaCarta(mazzo);
					prossimaCarta.setImage(prossimaCartaMazzo[0].getImmagineCarta());

					utility.resetArrayCopy();

					success = true;

				}

				event.setDropCompleted(success);
				event.consume();
			
				
				if (cartaSchierata instanceof Personaggio) {
					
					Personaggio personaggioSchierato = (Personaggio) cartaSchierata;

					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.setTitle("SCELTA POSIZIONE CARTA");
					alert.setHeaderText(null);
					alert.setContentText("ATTACCO o DIFESA");

					ButtonType bottoneSceltaAttacco = new ButtonType(
							PosizionamentoPersonaggio.ATTACCO.toString());
					ButtonType bottoneSceltaDifesa = new ButtonType(
							PosizionamentoPersonaggio.DIFESA.toString());

					alert.getButtonTypes().setAll(bottoneSceltaAttacco, bottoneSceltaDifesa);

					boolean[] flag = { false };
					PosizionamentoPersonaggio posizionamentoScelto = null;

					alert.showAndWait().ifPresent(response -> {
						if (response == bottoneSceltaDifesa)
							flag[0] = true;

					});

					if (flag[0]) {
						posizionamentoScelto = PosizionamentoPersonaggio.DIFESA;
						newImageView.setRotate(270);
					} else
						posizionamentoScelto = PosizionamentoPersonaggio.ATTACCO;

					giocatoreService.effettuaSchieramentoPersonaggio(personaggioSchierato,
							utility.ricercaStradaSchieramento(grid), posizionamentoScelto);
					
					
					//personaggioSchierato.getMossaSpeciale().esegui(personaggioSchierato);
				
				}

			});

		}

		

	}

	@FXML
	public void passaTurnoAction(ActionEvent event) {
		try {
			turnoService.creaSalvaStatoTurno(utility.getMappaGridpaneImmagini(), utility.getMappaGridpaneCarte(),
					turnoCorrente);

			partitaService.salvaTurnoPartita(turnoCorrente, partita);
			
			dispatcher.caricaVista("gioco", partita);
		} catch (ViewException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void timerImpl() {
		timeline = new Timeline(new KeyFrame(Duration.seconds(1), (event) -> {
			secondsElapsed--;
			updateTimerLabel();
			if (secondsElapsed == 0) {
				System.out.println("timer terminato");
			}
		}));
		timeline.setCycleCount(timerDurantion);
	}

	private void updateTimerLabel() {
		int seconds = secondsElapsed % 60;

		String timeString = String.format("%02d", seconds);
		timer.setText(timeString);
	}

	private String formatElisir(double value) {
		DecimalFormat df = new DecimalFormat("#.#");
		return df.format(value * 10);
	}
	
	private void impostaTooltip(ImageView img, Carta carta) {
		Tooltip tooltip = new Tooltip(carta.getNome() + "\n" + carta.getCostoSchieramento());
		Tooltip.install(img, tooltip);
	}

}
