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

		for (Carta carta : mazzoService.mostraCarteMano(mazzo)) {
			ImageView imageView = utility.creaImpostaImageView(carta.getImmagineCarta(), dim_img, dim_img);

			Tooltip tooltip = new Tooltip(carta.getNome() + "\n" + carta.getCostoSchieramento());
			Tooltip.install(imageView, tooltip);

			utility.aggiungiImmagineGriglia(carteMano, imageView);
			utility.aggiungiCartaGriglia(carteMano, carta);

			utility.setImageDragProperty(carteMano, imageView);
			
			gridsList.remove(carteMano);
			
			for (GridPane grid : gridsList) {

				grid.setOnDragOver(event -> {
					if (event.getGestureSource() != grid && event.getDragboard().hasImage()) {
						event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
					}
					event.consume();
				});

				grid.setOnDragDropped(event -> {
					Dragboard db = event.getDragboard();
					boolean success = false;
					
					ImageView newImageView = null;
					Carta cartaSchierata = null;
					Posizione posizione = null;
					ImageView imageViewProssimaCarta = null;

					if (db.hasImage()) {
						newImageView = utility.creaImpostaImageView(db.getImage(), dim_img, dim_img);

						utility.aggiungiImmagineGriglia(grid, newImageView);
						utility.aggiungiCartaGriglia(grid, carta);

						imageViewProssimaCarta = utility.creaImpostaImageView(prossimaCarta.getImage(), 100,
								100);
						
						posizione = utility.getPosizioneCartaSelezionata()[0];
						
						try {
							cartaSchierata = (Carta) utility.ricercaCartaSelezionata(posizione).clone();
						} catch (CloneNotSupportedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						utility.aggiungiImmagineGriglia(carteMano, imageViewProssimaCarta, posizione);

						utility.setImageDragProperty(carteMano, imageViewProssimaCarta);

						prossimaCarta.setImage(mazzoService.mostraProssimaCarta(mazzo).getImmagineCarta());
						
						utility.resetArrayCopy();
						
					
						success = true;

					}

					event.setDropCompleted(success);
					event.consume();
					

				});

			}
			
		}

		prossimaCarta.setImage(mazzoService.mostraProssimaCarta(mazzo).getImmagineCarta());

	}

	@FXML
	public void passaTurnoAction(ActionEvent event) {
		try {
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

}
