package it.univaq.disim.oop.myclashofunivaq.controller;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import it.univaq.disim.oop.myclashofunivaq.business.GiocatoreService;
import it.univaq.disim.oop.myclashofunivaq.business.MazzoService;
import it.univaq.disim.oop.myclashofunivaq.business.PartitaService;
import it.univaq.disim.oop.myclashofunivaq.business.TurnoService;
import it.univaq.disim.oop.myclashofunivaq.business.impl.AttaccoException;
import it.univaq.disim.oop.myclashofunivaq.business.impl.ElisirException;
import it.univaq.disim.oop.myclashofunivaq.business.impl.GiocatoreUtenteServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.business.impl.MazzoServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.business.impl.PartitaServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.business.impl.PosizionamentoException;
import it.univaq.disim.oop.myclashofunivaq.business.impl.TurnoServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.configuration.CartaFactory;
import it.univaq.disim.oop.myclashofunivaq.configuration.Factory;
import it.univaq.disim.oop.myclashofunivaq.controller.utilitis.GraphicUtility;
import it.univaq.disim.oop.myclashofunivaq.controller.utilitis.GridPaneGioco;
import it.univaq.disim.oop.myclashofunivaq.controller.utilitis.Posizione;
import it.univaq.disim.oop.myclashofunivaq.domain.Carta;
import it.univaq.disim.oop.myclashofunivaq.domain.FaseTurno;
import it.univaq.disim.oop.myclashofunivaq.domain.Giocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.Mazzo;
import it.univaq.disim.oop.myclashofunivaq.domain.MossaGiocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.Partita;
import it.univaq.disim.oop.myclashofunivaq.domain.Personaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.PosizionamentoPersonaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.Tank;
import it.univaq.disim.oop.myclashofunivaq.domain.Turno;
import it.univaq.disim.oop.myclashofunivaq.view.InizializzaDati;
import it.univaq.disim.oop.myclashofunivaq.view.ViewDispatcher;
import it.univaq.disim.oop.myclashofunivaq.view.ViewException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class GiocoController implements Initializable, InizializzaDati<Partita> {

	@FXML
	private ProgressBar vitaTorre1;

	@FXML
	private Label vitaTorre1Indicator;

	@FXML
	private ProgressBar vitaTorre2;

	@FXML
	private Label vitaTorre2Indicator;

	@FXML
	private ProgressBar elisir;

	@FXML
	private Label elisirIndicator;

	@FXML
	private Label nomeGiocatore;

	@FXML
	private Label faseCorrente;

	@FXML
	private Button cambiaFase;

	@FXML
	private GridPane carteManoG1;

	@FXML
	private GridPane carteManoG2;

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
	private GridPane stradaSXavversario;

	@FXML
	private GridPane stradaDX;

	@FXML
	private GridPane stradaDXavversario;

	@FXML
	private GridPane stradaC;

	@FXML
	private GridPane stradaCavversario;

	@FXML
	private Button salvaPartita;

	@FXML
	private Button esciPartita;
	
	@FXML
	private Button annullaMossa;

	private List<GridPane> gridsListGiocatore;
	private List<GridPane> gridsListAvversario;
	private List<GridPane> gridsList;

	private int timerDurantion = 60; // seconds
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
	private final CartaFactory cartaFactory;

	private GraphicUtility utility;
	private static final int dim_img = 60;

	private Carta[] cartaSchierata = new Carta[1];

	public GiocoController() {
		this.dispatcher = ViewDispatcher.getInstance();
		this.partitaService = new PartitaServiceImpl();
		this.turnoService = new TurnoServiceImpl();
		this.mazzoService = new MazzoServiceImpl();
		this.secondsElapsed = timerDurantion;
		this.utility = new GraphicUtility();
		this.gridsListGiocatore = new ArrayList<>();
		this.gridsListAvversario = new ArrayList<>();
		this.gridsList = new ArrayList<>();
		this.giocatoreService = new GiocatoreUtenteServiceImpl();
		this.cartaFactory = Factory.getInstance();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@Override
	public void inizializza(Partita partita) {
		this.partita = partita;

		if (partita.isRecuperata()) {
			System.out.println("PARTITA DESERIALIZZATA");
			partitaService.mappaPartitaSerializzata(partita);
			this.turnoService.ripopolaMappaTurni(partita);

			giocatoreCorrente = partita.getTurni().get(partita.getTurni().size() - 1).getGiocatore();

			turnoCorrente = partita.getTurni().get(partita.getTurni().size() - 1);

			nomeGiocatore.setText(giocatoreCorrente.getNickname());

			faseCorrente.setText(turnoCorrente.getFase().toString());

			this.mostraElisir();

			Mazzo mazzo = mazzoService.trovaMazzo(giocatoreCorrente);
			this.cartaFactory.reimpostaImmagine(Arrays.asList(mazzo.getCarte()));

			if (turnoCorrente.getNumero() % 2 == 0) {
				this.carteMano = carteManoG1;
				this.gridsList = gridsListGiocatore;
				this.mostraVitaTorre(giocatoreCorrente, vitaTorre1, vitaTorre1Indicator);
				if (turnoCorrente.getNumero() != 0)
					this.mostraVitaTorre(this.turnoService.trovaAltroGiocatore(partita), vitaTorre2,
							vitaTorre2Indicator);
			} else {
				this.carteMano = carteManoG2;
				this.gridsList = gridsListAvversario;
				this.mostraVitaTorre(giocatoreCorrente, vitaTorre2, vitaTorre2Indicator);
				this.mostraVitaTorre(this.turnoService.trovaAltroGiocatore(partita), vitaTorre1, vitaTorre1Indicator);
			}

			this.mappaGriglie();

			List<GridPane> tutte = new ArrayList<>();
			tutte.addAll(gridsListGiocatore);
			tutte.addAll(gridsListAvversario);

			this.recuperaCarte();

			for (ImageView img : utility.getNuoveImmagini()) {
				GridPane parent = (GridPane) img.getParent();
				String idParent = parent.getId();

				boolean presente = this.gridsList.stream().anyMatch(gridPane -> idParent.equals(gridPane.getId()));

				if (!presente)
					img.setOnMouseClicked(this::proprietaClickImageViewAvversario);
				else
					img.setOnMouseClicked(this::proprietaClickImageViewGiocatore);

			}

			this.trascinamentoImmagini(mazzo);

		} else {
			System.out.println(
					"size else " + GraphicUtility.getStati().size() + " " + GraphicUtility.getStati().toString());
			
			giocatoreCorrente = turnoService.alternaGiocatore(partita);
			timerImpl();
			turnoCorrente = turnoService.avviaTurno(timeline, giocatoreCorrente);
			
			nomeGiocatore.setText(giocatoreCorrente.getNickname());

			faseCorrente.setText(turnoCorrente.getFase().toString());

			this.mostraElisir();

			Mazzo mazzo = mazzoService.trovaMazzo(giocatoreCorrente);

			if (turnoCorrente.getNumero() % 2 == 0) {
				this.carteMano = carteManoG1;
				this.gridsList = gridsListGiocatore;
				this.mostraVitaTorre(giocatoreCorrente, vitaTorre1, vitaTorre1Indicator);
				if (turnoCorrente.getNumero() != 0)
					this.mostraVitaTorre(this.turnoService.trovaAltroGiocatore(partita), vitaTorre2,
							vitaTorre2Indicator);
			} else {
				this.carteMano = carteManoG2;
				this.gridsList = gridsListAvversario;
				this.mostraVitaTorre(giocatoreCorrente, vitaTorre2, vitaTorre2Indicator);
				this.mostraVitaTorre(this.turnoService.trovaAltroGiocatore(partita), vitaTorre1, vitaTorre1Indicator);
			}

			this.mappaGriglie();

			List<GridPane> tutte = new ArrayList<>();
			tutte.addAll(gridsListGiocatore);
			tutte.addAll(gridsListAvversario);

			if (turnoCorrente.getNumero() > 0) {
				utility.ripristinaStato(tutte);
				for (ImageView img : utility.getNuoveImmagini()) {
					GridPane parent = (GridPane) img.getParent();
					String idParent = parent.getId();

					boolean presente = this.gridsList.stream().anyMatch(gridPane -> idParent.equals(gridPane.getId()));

					if (!presente)
						img.setOnMouseClicked(this::proprietaClickImageViewAvversario);
					else
						img.setOnMouseClicked(this::proprietaClickImageViewGiocatore);
					
				}
			}

			if (turnoService.isFirstTurno(turnoCorrente) || GraphicUtility.getStati().size() <= 1) {
				for (Carta carta : mazzoService.mostraCarteMano(mazzo)) {
					if (carta.getImmagineCarta() == null) {
						this.cartaFactory.reimpostaImmagine(Arrays.asList(mazzo.getCarte()));
					}
					ImageView imageView = utility.creaImpostaImageView(carta.getImmagineCarta(), dim_img, dim_img);
					utility.impostaTooltip(imageView, carta);
					// Mapping immagini e carte in mano
					utility.aggiungiCartaImmagineGriglia(carteMano, carta, imageView);
					utility.setImageDragProperty(carteMano, imageView);
				}
			} else
				utility.ripristinaCarteMano(carteMano);

			this.trascinamentoImmagini(mazzo);

		}

	}
	
	private void trascinamentoImmagini(Mazzo mazzo) {
		Carta[] prossimaCartaMazzo = new Carta[1]; // WRAPPER
		prossimaCartaMazzo[0] = mazzoService.mostraProssimaCarta(mazzo);
		prossimaCarta.setImage(prossimaCartaMazzo[0].getImmagineCarta());

		for (GridPane grid : gridsList) {

			grid.setOnDragOver(event -> {
				if (event.getGestureSource() != grid && event.getDragboard().hasImage()) {
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
				}

				event.consume();
			});

			grid.setOnDragDropped(event -> {

				if (grid.equals(carteMano)) {
					System.err.println("NON PUOI AGGIUNGERE CARTE IN MANO");
					throw new RuntimeException();

				}
				
				// 3 immagini + 1 nodo parent
				if (grid.getChildren().size() == 4) {
					System.err.println("STRADA PIENA");
					throw new RuntimeException();
				}

				Dragboard db = event.getDragboard();
				boolean success = false;

				ImageView newImageView = null;
				Posizione posizioneDaRimpiazzare = null;
				ImageView imageViewProssimaCarta = null;

				if (db.hasImage()) {
					newImageView = utility.creaImpostaImageView(db.getImage(), dim_img, dim_img);

					imageViewProssimaCarta = utility.creaImpostaImageView(prossimaCarta.getImage(), 70, 70);

					posizioneDaRimpiazzare = utility.getPosizioneCartaSelezionata()[0];

					try {
						// Mapping carta schierata
						Carta cartaDaSchierare = utility.ricercaCartaStrada(this.carteMano.getId(), posizioneDaRimpiazzare);
						turnoService.controllaSchieramento(turnoCorrente, cartaDaSchierare);
						cartaSchierata[0] = (Carta) cartaDaSchierare.clone();
						utility.aggiungiCartaImmagineGriglia(grid, cartaSchierata[0], null); // aggiunta nella
																								// strada
						utility.impostaTooltip(newImageView, cartaSchierata[0]);

					} catch (ElisirException e) {
						System.out.println(e.getMessage());
						throw new RuntimeException();
					} catch (CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					utility.aggiungiCartaImmagineGriglia(grid, null, newImageView);

					newImageView.setOnMouseClicked(this::proprietaClickImageViewGiocatore);
					
					// Mapping immagine/carta da prossima carta a mano
					utility.aggiungiCartaImmagineGriglia(carteMano, prossimaCartaMazzo[0], imageViewProssimaCarta,
							posizioneDaRimpiazzare);
					utility.impostaTooltip(imageViewProssimaCarta, prossimaCartaMazzo[0]);
					utility.setImageDragProperty(carteMano, imageViewProssimaCarta);
					prossimaCartaMazzo[0] = mazzoService.mostraProssimaCarta(mazzo);
					prossimaCarta.setImage(prossimaCartaMazzo[0].getImmagineCarta());

					success = true;

				}

				event.setDropCompleted(success);
				event.consume();

				Personaggio personaggioSchierato = null;

				if (cartaSchierata[0] instanceof Personaggio) {

					personaggioSchierato = (Personaggio) cartaSchierata[0];

					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.setTitle("SCELTA POSIZIONE CARTA");
					alert.setHeaderText(null);
					alert.setContentText("ATTACCO o DIFESA");

					ButtonType bottoneSceltaAttacco = new ButtonType(PosizionamentoPersonaggio.ATTACCO.toString());
					ButtonType bottoneSceltaDifesa = new ButtonType(PosizionamentoPersonaggio.DIFESA.toString());

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

					MossaGiocatore mossaGiocatore = giocatoreService.effettuaSchieramentoPersonaggio(turnoCorrente,
							personaggioSchierato, utility.ricercaStradaSchieramento(grid), posizionamentoScelto);

					turnoService.salvaMossaGiocatore(partita, turnoCorrente, mossaGiocatore);

					this.mostraElisir();

					// personaggioSchierato.getMossaSpeciale().esegui(personaggioSchierato);

				}

			});

		}
	}

	private void mappaGriglie() {
		gridsListGiocatore.add(stradaSX);
		gridsListGiocatore.add(stradaDX);
		gridsListGiocatore.add(stradaC);
		gridsListGiocatore.add(carteMano);
		utility.mappingGriglie(gridsListGiocatore);

		gridsListAvversario.add(stradaSXavversario);
		gridsListAvversario.add(stradaDXavversario);
		gridsListAvversario.add(stradaCavversario);
		gridsListAvversario.add(carteMano);
		utility.mappingGriglie(gridsListAvversario);
	}

	@FXML
	public void cambiaFaseAction(ActionEvent event) {
		turnoService.cambiaFase(turnoCorrente);
		faseCorrente.setText(turnoCorrente.getFase().toString());

		if (turnoCorrente.getFase().equals(FaseTurno.Attacco) || turnoCorrente.getFase().equals(FaseTurno.Difesa)) {
			this.carteMano.setDisable(true); // Non si può schierare
		}
	}

	@FXML
	public void passaTurnoAction(ActionEvent event) {
		try {
			partitaService.salvaTurnoPartita(turnoCorrente, partita);

			if (partita.isRecuperata() && !GraphicUtility.getStati().isEmpty()) {
				utility.sovrascriviUltimoStato(utility);
			} else {
				utility.aggiungiStato(utility);
			}
			
			partita.setRecuperata(false);
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

	private void mostraElisir() {
		double progress = turnoCorrente.getElisirGiocatore();
		elisir.setProgress(progress);
		elisirIndicator.setText(this.formatElisir(progress));
	}

	private String formatElisir(double value) {
		DecimalFormat df = new DecimalFormat("#.#");
		return df.format(value * 10);
	}

	private void mostraVitaTorre(Giocatore giocatore, ProgressBar torre, Label indicator) {
		double vita = turnoService.trovaTorreGiocatore(giocatore).getVita();
		torre.setProgress(vita);
		indicator.setText(this.formatVitaTorre(vita));
	}

	private String formatVitaTorre(double value) {
		DecimalFormat df = new DecimalFormat("#.#");
		return df.format(value * 100);
	}

	private void proprietaClickImageViewGiocatore(MouseEvent event) {

		final ImageView imageViewCorrente = (ImageView) event.getSource();
		final GridPane gridPaneParent = (GridPane) imageViewCorrente.getParent();

		if (cartaSchierata[0] == null) {
			Posizione posizioneToSearch = new Posizione(GridPane.getColumnIndex(imageViewCorrente),
					GridPane.getRowIndex(imageViewCorrente));
			cartaSchierata[0] = utility.ricercaCartaStrada(gridPaneParent.getId(), posizioneToSearch);
		}

		final Personaggio personaggioCliccato = (Personaggio) cartaSchierata[0];

		try {
			if (turnoCorrente.getFase().equals(FaseTurno.Difesa)) {
				MossaGiocatore mossaGiocatore = giocatoreService.cambiaPosizionePersonaggio(turnoCorrente,
						personaggioCliccato, PosizionamentoPersonaggio.DIFESA);
				turnoService.salvaMossaGiocatore(partita, turnoCorrente, mossaGiocatore);
				imageViewCorrente.setRotate(270);
			} else if (turnoCorrente.getFase().equals(FaseTurno.Attacco)) {
				System.out.println("sono " + personaggioCliccato.getNome() + " stato scelto per l'attacco");

				MossaGiocatore mossaGiocatore = giocatoreService.cambiaPosizionePersonaggio(turnoCorrente,
						personaggioCliccato, PosizionamentoPersonaggio.ATTACCO);
				turnoService.salvaMossaGiocatore(partita, turnoCorrente, mossaGiocatore);
				imageViewCorrente.setRotate(360);

				System.out.println("VITA " + personaggioCliccato.getVita());
				System.out.println("MANA " + personaggioCliccato.getMana());
				giocatoreService.preparaAttacco(personaggioCliccato, utility.ricercaStradaSchieramento(gridPaneParent));
			} else
				throw new PosizionamentoException("è la fase di schieramento");

		} catch (PosizionamentoException e) {
			System.err.println(e.getMessage());
		}

	}

	private void proprietaClickImageViewAvversario(MouseEvent event) {

		final ImageView imageViewCorrente = (ImageView) event.getSource();
		final GridPane gridPaneParent = (GridPane) imageViewCorrente.getParent();

		try {
			if (turnoCorrente.getFase().equals(FaseTurno.Attacco)) {

				Posizione posizioneToSearch = new Posizione(GridPane.getColumnIndex(imageViewCorrente),
						GridPane.getRowIndex(imageViewCorrente));

				Carta cartaCliccata = utility.ricercaCartaStrada(gridPaneParent.getId(), posizioneToSearch);

				Personaggio personaggio = (Personaggio) cartaCliccata;

				utility.impostaTooltip(imageViewCorrente, cartaCliccata);

				giocatoreService.effettuaAttacco(turnoCorrente, personaggio,
						utility.ricercaStradaSchieramento(gridPaneParent));

				utility.impostaTooltip(imageViewCorrente, cartaCliccata);

			} else
				throw new AttaccoException("Non puoi selezionarla non è la fase di attacco");

		} catch (AttaccoException e) {
			System.err.println(e.getMessage());
		}

	}

	@FXML
	public void salvaPartitaAction(ActionEvent event) {
		partitaService.salvaTurnoPartita(turnoCorrente, partita);
		utility.aggiungiStato(utility);

		int numeroMosse = partitaService.calcolaNumeroMossePartita(partita);
		int numeroCarteInCampo = utility.calcolaNumeroCarteTerreno();
		int valoreCarteInCampo = utility.calcolaValoreCarteTerreno();

		partitaService.impostaParamentriSalvataggio(partita, numeroMosse, numeroCarteInCampo, valoreCarteInCampo);

		partitaService.salvaPartita(partita);

		for (String key : utility.getMappaGridpaneCarte().keySet()) {
			LinkedHashMap<Posizione, Carta> innerMap = utility.getMappaGridpaneCarte().get(key);
			for (Posizione p : innerMap.keySet()) {
				Carta carta = innerMap.get(p);
				if (carta != null) {
					System.out.println(key + " " + p + " " + carta);
				}

			}
		}

		try {
			GraphicUtility.serializeMappaGridpaneCarte(utility.getMappaGridpaneCarte(),
					String.valueOf(this.partita.getID()));
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		try {
			dispatcher.caricaVista("applicationLayout");
			dispatcher.caricaVista("homepage");
		} catch (ViewException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void esciAction(ActionEvent event) {
		try {
			dispatcher.caricaVista("applicationLayout");
			dispatcher.caricaVista("homepage");
		} catch (ViewException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void annullaMossaAction(ActionEvent event) {		
		if(this.carteMano.getId().equals("carteManoG2"))
			utility.ripristinaDopoAnnullamento(gridsList, gridsListGiocatore);
		else
			utility.ripristinaDopoAnnullamento(gridsList, gridsListAvversario);
		
		turnoService.annullaUltimoTurno(turnoCorrente);
		try {
			dispatcher.caricaVista("gioco", partita);
		} catch (ViewException e) {
			e.printStackTrace();
		}
	}

	public void recuperaCarte() {
		List<Carta> carteCampoDeserializzate = new ArrayList<>();
		Map<String, LinkedHashMap<Posizione, Carta>> mappa = null;

		try {
			mappa = GraphicUtility.deserializeMappaGridpaneCarte(String.valueOf(this.partita.getID()));
			for (String key : mappa.keySet()) {
				LinkedHashMap<Posizione, Carta> innerMap = mappa.get(key);
				for (Posizione p : innerMap.keySet()) {
					Carta carta = innerMap.get(p);
					if (carta != null) {
						carteCampoDeserializzate.add(carta);
						// System.out.println(key + " " + p + " " + carta);
					}
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.cartaFactory.reimpostaImmagine(carteCampoDeserializzate);
		List<GridPane> tutte = new ArrayList<>();
		tutte.addAll(gridsListGiocatore);
		tutte.addAll(gridsListAvversario);

		utility.ripristinaStato(tutte, mappa);
	}

}