package it.univaq.disim.oop.myclashofunivaq.controller;

import java.io.IOException;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.univaq.disim.oop.myclashofunivaq.business.GiocatoreComputerService;
import it.univaq.disim.oop.myclashofunivaq.business.GiocatoreService;
import it.univaq.disim.oop.myclashofunivaq.business.IncantesimoService;
import it.univaq.disim.oop.myclashofunivaq.business.MazzoService;
import it.univaq.disim.oop.myclashofunivaq.business.PartitaService;
import it.univaq.disim.oop.myclashofunivaq.business.PersonaggioService;
import it.univaq.disim.oop.myclashofunivaq.business.TurnoService;
import it.univaq.disim.oop.myclashofunivaq.business.impl.AttaccoException;
import it.univaq.disim.oop.myclashofunivaq.business.impl.ElisirException;
import it.univaq.disim.oop.myclashofunivaq.business.impl.FasiTerminateException;
import it.univaq.disim.oop.myclashofunivaq.business.impl.GiocatoreComputerServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.business.impl.GiocatoreServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.business.impl.IncantesimoServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.business.impl.ManaException;
import it.univaq.disim.oop.myclashofunivaq.business.impl.MazzoServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.business.impl.PartitaServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.business.impl.PersonaggioServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.business.impl.PosizionamentoException;
import it.univaq.disim.oop.myclashofunivaq.business.impl.TurnoServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.configuration.CartaFactory;
import it.univaq.disim.oop.myclashofunivaq.configuration.Factory;
import it.univaq.disim.oop.myclashofunivaq.controller.utilities.GraphicEngine;
import it.univaq.disim.oop.myclashofunivaq.controller.utilities.GridPaneGioco;
import it.univaq.disim.oop.myclashofunivaq.controller.utilities.Posizione;
import it.univaq.disim.oop.myclashofunivaq.domain.Carta;
import it.univaq.disim.oop.myclashofunivaq.domain.FaseTurno;
import it.univaq.disim.oop.myclashofunivaq.domain.Giocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.GiocatoreUtente;
import it.univaq.disim.oop.myclashofunivaq.domain.Incantesimo;
import it.univaq.disim.oop.myclashofunivaq.domain.Mazzo;
import it.univaq.disim.oop.myclashofunivaq.domain.MossaGiocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.MossaSpeciale;
import it.univaq.disim.oop.myclashofunivaq.domain.Partita;
import it.univaq.disim.oop.myclashofunivaq.domain.Personaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.PosizionamentoPersonaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.Torre;
import it.univaq.disim.oop.myclashofunivaq.domain.Turno;
import it.univaq.disim.oop.myclashofunivaq.domain.nomicarte.IncantesimiNomi;
import it.univaq.disim.oop.myclashofunivaq.view.InizializzaDati;
import it.univaq.disim.oop.myclashofunivaq.view.ViewDispatcher;
import it.univaq.disim.oop.myclashofunivaq.view.ViewException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
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

	private ProgressBar vitaTorreAvversaria;

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

	@FXML
	private Label labelErrori;

	private List<GridPane> gridsListGiocatore;
	private List<GridPane> gridsListAvversario;
	// rappresenta la lista corrente che sia giocatore o avversario
	private List<GridPane> gridsList;

	private int timerDurantion = 120; // secondi
	private int secondiTrascorsi;
	private Timeline timeline;

	private ViewDispatcher dispatcher;
	private Partita partita;
	private Giocatore giocatoreCorrente;
	private Turno turnoCorrente;

	private final PartitaService partitaService;
	private final TurnoService turnoService;
	private final MazzoService mazzoService;
	private final GiocatoreService giocatoreService;
	private final GiocatoreComputerService giocatoreComputerService;
	private final CartaFactory cartaFactory;
	private final IncantesimoService incantesimoService;
	private final PersonaggioService personaggioService;

	private GraphicEngine engine;
	private static final int dim_img = 60;
	// coordinata y per spostare gli elementi prossima immagine e elisir
	private static final int y_giocatore1 = 40;
	private static final int y_giocatore2 = 460;

	// Array per operare dentro le lambda
	private Carta[] cartaSchierata = new Carta[1];
	private Carta[] prossimaCartaMazzo = new Carta[1];

	public GiocoController() {
		this.dispatcher = ViewDispatcher.getInstance();
		this.partitaService = new PartitaServiceImpl();
		this.turnoService = new TurnoServiceImpl();
		this.mazzoService = new MazzoServiceImpl();
		this.secondiTrascorsi = this.timerDurantion;
		this.engine = new GraphicEngine();
		this.gridsListGiocatore = new ArrayList<>();
		this.gridsListAvversario = new ArrayList<>();
		this.gridsList = new ArrayList<>();
		this.giocatoreService = new GiocatoreServiceImpl();
		this.cartaFactory = Factory.getInstance();
		this.incantesimoService = new IncantesimoServiceImpl();
		this.personaggioService = new PersonaggioServiceImpl();
		this.giocatoreComputerService = new GiocatoreComputerServiceImpl();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@Override
	public void inizializza(Partita partita) {
		this.partita = partita;

		if (partita.isRecuperata()) {
			System.out.println("PARTITA DESERIALIZZATA");
			this.annullaMossa.setDisable(true);

			this.partitaService.mappaPartitaSerializzata(partita);
			this.turnoService.ripopolaMappaTurni(partita);

			this.turnoCorrente = this.turnoService.getUltimoTurno(partita);

			this.giocatoreCorrente = this.turnoCorrente.getGiocatore();

			this.nomeGiocatore.setText(this.giocatoreCorrente.getNickname());

			this.faseCorrente.setText(this.turnoCorrente.getFase().toString());

			this.mostraElisir();

			Mazzo mazzo = this.mazzoService.trovaMazzo(this.giocatoreCorrente);

			this.mappaElementiTurno();

			this.mappaGriglie();

			this.recuperaCarte();

			this.setProprietaNuoveImmagini();

			if (this.giocatoreCorrente instanceof GiocatoreUtente)
				this.trascinamentoImmagini(mazzo);
			else
				this.faiMosseComputer(mazzo);

		} else {

			Turno ultimoTurno = this.turnoService.getUltimoTurno(partita);

			if (ultimoTurno == null)
				this.giocatoreCorrente = this.partitaService.trovaAltroGiocatore(partita, null);
			else
				this.giocatoreCorrente = this.partitaService.trovaAltroGiocatore(partita,
						this.turnoService.getUltimoTurno(partita).getGiocatore());

			this.timerImpl();

			this.turnoCorrente = this.turnoService.avviaTurno(timeline, this.giocatoreCorrente);

			this.nomeGiocatore.setText(this.giocatoreCorrente.getNickname());

			this.faseCorrente.setText(this.turnoCorrente.getFase().toString());

			this.mostraElisir();

			Mazzo mazzo = this.mazzoService.trovaMazzo(this.giocatoreCorrente);

			this.mappaElementiTurno();

			this.mappaGriglie();

			List<GridPane> tutte = this.getAllGrids();

			if (this.turnoCorrente.getNumero() > 0) {

				this.engine.ripristinaStato(tutte);

				this.setProprietaNuoveImmagini();
			}

			if (this.turnoService.isFirstTurno(this.turnoCorrente) || GraphicEngine.getStati().size() <= 1) {

				for (Carta carta : this.mazzoService.mostraCarteMano(mazzo)) {
					ImageView imageView = this.engine.creaImpostaImageView(carta.getImmagineCarta(), dim_img, dim_img);
					this.engine.impostaTooltip(imageView, carta);
					// Mapping immagini e carte in mano
					this.engine.aggiungiCartaImmagineGriglia(this.carteMano, carta, imageView);
					this.engine.setImageDragProperty(imageView);
				}
			} else
				this.engine.ripristinaCarteMano(this.carteMano);

			if (this.giocatoreCorrente instanceof GiocatoreUtente)
				this.trascinamentoImmagini(mazzo);
			else
				this.faiMosseComputer(mazzo);

		}

	}

	/*
	 * Metodo ogni volta che tocca giocare al computer per ogni fase effettua una
	 * mossa randomica al primo turno effettua solo uno schieramento
	 */
	private void faiMosseComputer(Mazzo mazzo) {
		this.annullaMossa.setDisable(true);
		this.cambiaFase.setDisable(true);

		List<GridPane> gridsListCopy = new ArrayList<>(this.gridsList);
		List<GridPane> gridsListAvversarioCopy = new ArrayList<>(this.gridsListAvversario);

		gridsListCopy.remove(this.carteMano);
		gridsListAvversarioCopy.remove(this.carteMano);

		// FASE SCHIERAMENTO
		ObservableList<Node> carteInMano = this.carteMano.getChildren();

		int posizioneImmagineMano = this.giocatoreComputerService.getPosizioneRandom(carteInMano.size());
		Node randomNode = carteInMano.get(posizioneImmagineMano);

		while (!(randomNode instanceof ImageView)) {
			posizioneImmagineMano = this.giocatoreComputerService.getPosizioneRandom(carteInMano.size());
			randomNode = carteInMano.get(posizioneImmagineMano);

			if (randomNode instanceof ImageView)
				break;
		}

		int stradaSchieramento = this.giocatoreComputerService.getPosizioneRandom(gridsListCopy.size());
		GridPane gridSchieramento = gridsListCopy.get(stradaSchieramento);

		ImageView randomImageViewMano = (ImageView) randomNode;
		Posizione posizioneToSearch = new Posizione(GridPane.getColumnIndex(randomImageViewMano),
				GridPane.getRowIndex(randomImageViewMano));

		ImageView imageViewDaSchierare = this.engine.creaImpostaImageView(randomImageViewMano.getImage(), dim_img,
				dim_img);
		Carta cartaDaSchierare = this.engine.ricercaCartaStrada(this.carteMano.getId(), posizioneToSearch);
		
		try {
			this.turnoService.controllaSchieramento(this.turnoCorrente, cartaDaSchierare);
			this.cartaSchierata[0] = (Carta) cartaDaSchierare.clone();
			this.engine.impostaTooltip(imageViewDaSchierare, this.cartaSchierata[0]);

		} catch (ElisirException e) {
			System.err.println(e.getMessage());
			return;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		this.engine.aggiungiCartaImmagineGriglia(gridSchieramento, this.cartaSchierata[0], imageViewDaSchierare); // aggiunta nella
		// strada

		Carta[] prossimaCartaMazzo = new Carta[1]; // WRAPPER
		prossimaCartaMazzo[0] = this.mazzoService.mostraProssimaCarta(mazzo,
				this.engine.ricercaCarteMano(this.carteMano.getId()));
		this.prossimaCarta.setImage(prossimaCartaMazzo[0].getImmagineCarta());

		ImageView imageViewProssimaCarta = this.engine.creaImpostaImageView(prossimaCarta.getImage(), dim_img, dim_img);

		this.carteMano.getChildren()
				.remove(this.engine.ricercaImmagineStrada(this.carteMano.getId(), posizioneToSearch));

		this.engine.aggiungiCartaImmagineGriglia(this.carteMano, prossimaCartaMazzo[0], imageViewProssimaCarta,
				posizioneToSearch);
		this.engine.impostaTooltip(imageViewProssimaCarta, prossimaCartaMazzo[0]);
		prossimaCartaMazzo[0] = this.mazzoService.mostraProssimaCarta(mazzo,
				this.engine.ricercaCarteMano(this.carteMano.getId()));
		this.prossimaCarta.setImage(prossimaCartaMazzo[0].getImmagineCarta());

		Personaggio personaggioSchierato = (Personaggio) this.cartaSchierata[0];

		MossaGiocatore mossaGiocatore = this.giocatoreService.effettuaSchieramento(this.turnoCorrente,
				personaggioSchierato, this.engine.ricercaStradaSchieramento(gridSchieramento),
				PosizionamentoPersonaggio.ATTACCO);

		this.turnoService.salvaMossaGiocatore(this.partita, this.turnoCorrente, mossaGiocatore);

		this.mostraElisir();

		if (!this.turnoService.isFirstTurno(this.turnoCorrente)) {
			try {
				this.turnoService.cambiaFase(this.turnoCorrente);
			} catch (FasiTerminateException e) {
				System.err.println(e.getMessage());
				this.labelErrori.setText(e.getMessage());
			}
			this.faseCorrente.setText(this.turnoCorrente.getFase().toString());

			// DIFESA
			ImageView imageViewStrada = this.scegliImageViewRandomica(gridsListCopy);
			if (imageViewStrada == null) {
				this.labelErrori.setText("NON CI SONO CARTE IN QUELLA STRADA");
				return;
			}
			GridPane gridPaneParent = (GridPane) imageViewStrada.getParent();

			posizioneToSearch = new Posizione(GridPane.getColumnIndex(imageViewStrada),
					GridPane.getRowIndex(imageViewStrada));
			Personaggio personaggioStrada = (Personaggio) this.engine.ricercaCartaStrada(gridPaneParent.getId(),
					posizioneToSearch);

			try {
				MossaGiocatore mossaGiocatore2 = giocatoreService.cambiaPosizionePersonaggio(this.turnoCorrente,
						personaggioStrada, PosizionamentoPersonaggio.DIFESA);
				this.turnoService.salvaMossaGiocatore(this.partita, this.turnoCorrente, mossaGiocatore2);
				imageViewStrada.setRotate(270);
			} catch (PosizionamentoException e) {
				System.err.println(e.getMessage());
			}

			try {
				this.turnoService.cambiaFase(this.turnoCorrente);
			} catch (FasiTerminateException e) {
				System.err.println(e.getMessage());
				this.labelErrori.setText(e.getMessage());
			}
			this.faseCorrente.setText(this.turnoCorrente.getFase().toString());

			// ATTACCO
			// PERSONAGGIO ATTACCANTE
			imageViewStrada = this.scegliImageViewRandomica(gridsListCopy);
			if (imageViewStrada == null) {
				this.labelErrori.setText("NON CI SONO CARTE IN QUELLA STRADA");
				return;
			}

			gridPaneParent = (GridPane) imageViewStrada.getParent();
			GridPaneGioco gridPersonaggio = this.engine.ricercaStradaSchieramento(gridPaneParent);

			posizioneToSearch = new Posizione(GridPane.getColumnIndex(imageViewStrada),
					GridPane.getRowIndex(imageViewStrada));
			personaggioStrada = (Personaggio) this.engine.ricercaCartaStrada(gridPaneParent.getId(), posizioneToSearch);

			try {

				if (this.engine.checkAttaccoTorre(gridPersonaggio.toString())) {

					Giocatore avversario = this.partitaService.trovaAltroGiocatore(partita, this.giocatoreCorrente);
					Torre torreAvversaria = this.turnoService.trovaTorreGiocatore(avversario);

					this.giocatoreService.attaccaTorre(this.turnoCorrente, personaggioStrada, gridPersonaggio,
							torreAvversaria);

					if (this.vitaTorreAvversaria.getId().equals("vitaTorre1"))
						this.mostraVitaTorre(avversario, this.vitaTorreAvversaria, this.vitaTorre1Indicator);
					else
						this.mostraVitaTorre(avversario, this.vitaTorreAvversaria, this.vitaTorre2Indicator);

				} else {
					this.giocatoreService.preparaAttacco(personaggioStrada, gridPersonaggio);
					// PERSONAGGIO ATTACCANTE
					ImageView imageViewStradaAvversaria = this.scegliImageViewRandomica(gridsListAvversarioCopy);
					if (imageViewStradaAvversaria == null) {
						this.labelErrori.setText("NON CI SONO CARTE IN QUELLA STRADA");
						return;
					}
					Posizione posizioneAvversaria = new Posizione(GridPane.getColumnIndex(imageViewStrada),
							GridPane.getRowIndex(imageViewStrada));
					GridPane gridPaneParentAvversaria = (GridPane) imageViewStradaAvversaria.getParent();
					Personaggio personaggioStradaAvversaria = (Personaggio) this.engine
							.ricercaCartaStrada(gridPaneParentAvversaria.getId(), posizioneAvversaria);

					Giocatore avversario = this.partitaService.trovaAltroGiocatore(this.partita,
							this.giocatoreCorrente);

					this.giocatoreService.effettuaAttacco(this.turnoCorrente, personaggioStradaAvversaria,
							this.engine.ricercaStradaSchieramento(gridPaneParentAvversaria),
							this.turnoService.trovaTorreGiocatore(avversario));

					if (personaggioStradaAvversaria.getVita() <= 0) {
						this.engine.eliminaImmagineCarta(gridPaneParent, posizioneToSearch, imageViewStradaAvversaria);

						if (this.vitaTorreAvversaria.getId().equals("vitaTorre1"))
							this.mostraVitaTorre(avversario, this.vitaTorreAvversaria, this.vitaTorre1Indicator);
						else
							this.mostraVitaTorre(avversario, this.vitaTorreAvversaria, this.vitaTorre2Indicator);

					} else
						this.engine.impostaTooltip(imageViewStradaAvversaria, personaggioStradaAvversaria);

				}

			} catch (AttaccoException e) {
				System.err.println(e.getMessage());
			}

		}

	}

	/*
	 * Metodo per scegliere un immagine randomica dal terreno
	 */
	private ImageView scegliImageViewRandomica(List<GridPane> gridsList) {
		int stradaCampo = this.giocatoreComputerService.getPosizioneRandom(gridsList.size());
		GridPane gridCampo = gridsList.get(stradaCampo);

		ObservableList<Node> immaginiStrada = gridCampo.getChildren();

		if (!immaginiStrada.stream().anyMatch(node -> node instanceof ImageView))
			return null;

		int posizioneCartaStrada = this.giocatoreComputerService.getPosizioneRandom(immaginiStrada.size());
		Node randomNode = immaginiStrada.get(posizioneCartaStrada);

		while (!(randomNode instanceof ImageView)) {
			posizioneCartaStrada = this.giocatoreComputerService.getPosizioneRandom(immaginiStrada.size());
			randomNode = immaginiStrada.get(posizioneCartaStrada);

			if (randomNode instanceof ImageView)
				break;
		}

		return (ImageView) randomNode;
	}

	// metodo per trascinare le immagini dalla mano alle strade
	private void trascinamentoImmagini(Mazzo mazzo) {
		this.prossimaCartaMazzo[0] = this.mazzoService.mostraProssimaCarta(mazzo,
				this.engine.ricercaCarteMano(this.carteMano.getId()));
		this.prossimaCarta.setImage(this.prossimaCartaMazzo[0].getImmagineCarta());

		for (GridPane grid : this.gridsList) {

			grid.setOnDragOver(event -> {
				if (event.getGestureSource() != grid && event.getDragboard().hasImage()) {
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
				}

				event.consume();
			});

			grid.setOnDragDropped(event -> {

				// uso le RuntimeException per uscire
				if (grid.equals(this.carteMano)) {
					labelErrori.setText("NON PUOI AGGIUNGERE CARTE IN MANO");
					throw new RuntimeException();
				}

				/*
				 * 3 immagini + 1 nodo parent (strada piena verrà gestita dal drop
				 * dell'incantesimo o da questo) nel senso che se si trascina in una strada
				 * piena probabilmente stai mettendo un personaggio sopra l'altro (mossa non
				 * valida)
				 */
				if (grid.getChildren().size() == 4) {
					labelErrori.setText(grid.getId() + " PIENA");
					return;
				}

				Dragboard db = event.getDragboard();
				boolean success = false;

				ImageView newImageView = null;
				Posizione posizioneDaRimpiazzare = null;
				ImageView imageViewProssimaCarta = null;

				if (db.hasImage()) {
					newImageView = this.engine.creaImpostaImageView(db.getImage(), dim_img, dim_img);

					imageViewProssimaCarta = this.engine.creaImpostaImageView(prossimaCarta.getImage(), dim_img,
							dim_img);

					posizioneDaRimpiazzare = this.engine.getPosizioneCartaSelezionata()[0];

					try {
						// Mapping carta schierata
						Carta cartaDaSchierare = this.engine.ricercaCartaStrada(this.carteMano.getId(),
								posizioneDaRimpiazzare);
						this.turnoService.controllaSchieramento(this.turnoCorrente, cartaDaSchierare);
						cartaSchierata[0] = (Carta) cartaDaSchierare.clone();
						this.engine.impostaTooltip(newImageView, cartaSchierata[0]);

						if (cartaSchierata[0] instanceof Incantesimo) {
							labelErrori.setText("stai schierando un incatesimo in strada");
							throw new RuntimeException();
						}

						this.engine.aggiungiCartaImmagineGriglia(grid, cartaSchierata[0], null); // aggiunta carta nella
						// strada

					} catch (ElisirException e) {
						labelErrori.setText(e.getMessage());
						throw new RuntimeException();
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
					// aggiunta dell'immagine nella strada
					this.engine.aggiungiCartaImmagineGriglia(grid, null, newImageView);

					// implementazione del click
					newImageView.setOnMouseClicked(this::proprietaClickImageViewGiocatore);
					labelErrori.setText("");

					// implementazione del drag and drop
					this.dragAndDropIncantesimo(newImageView);

					this.carteMano.getChildren()
							.remove(this.engine.ricercaImmagineStrada(this.carteMano.getId(), posizioneDaRimpiazzare));

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

						MossaGiocatore mossaGiocatore = giocatoreService.effettuaSchieramento(this.turnoCorrente,
								personaggioSchierato, this.engine.ricercaStradaSchieramento(grid),
								posizionamentoScelto);

						this.turnoService.salvaMossaGiocatore(this.partita, this.turnoCorrente, mossaGiocatore);

						this.mostraElisir();

						this.engine.impostaTooltip(newImageView, personaggioSchierato);

					}

					// Mapping immagine/carta da prossima carta a mano
					this.engine.aggiungiCartaImmagineGriglia(this.carteMano, prossimaCartaMazzo[0],
							imageViewProssimaCarta, posizioneDaRimpiazzare);
					this.engine.impostaTooltip(imageViewProssimaCarta, prossimaCartaMazzo[0]);
					this.engine.setImageDragProperty(imageViewProssimaCarta);
					prossimaCartaMazzo[0] = this.mazzoService.mostraProssimaCarta(mazzo,
							this.engine.ricercaCarteMano(this.carteMano.getId()));
					prossimaCarta.setImage(prossimaCartaMazzo[0].getImmagineCarta());

					success = true;

				}

				event.setDropCompleted(success);
				event.consume();

			});

		}
	}

	// per ogni turno si impostano i vari elementi al giocatore corrente
	private void mappaElementiTurno() {
		if (this.turnoService.isTurnoPari(this.turnoCorrente)) {
			this.carteMano = this.carteManoG1;
			this.spostaElementiGiocatore2();
			this.engine.impostaRetroCarte(this.carteManoG2);
			this.gridsList = this.gridsListGiocatore;
			this.mostraVitaTorre(this.giocatoreCorrente, this.vitaTorre1, this.vitaTorre1Indicator);

			// questo perché al primo turno non ho ancora istanziato la torre avversaria
			if (this.turnoCorrente.getNumero() != 0) {
				this.mostraVitaTorre(this.partitaService.trovaAltroGiocatore(this.partita, this.giocatoreCorrente),
						this.vitaTorre2, this.vitaTorre2Indicator);
				this.vitaTorreAvversaria = this.vitaTorre2;
			}

		} else {
			this.carteMano = this.carteManoG2;
			this.spostaElementiGiocatore();
			this.engine.impostaRetroCarte(this.carteManoG1);
			gridsList = this.gridsListAvversario;
			this.mostraVitaTorre(this.giocatoreCorrente, this.vitaTorre2, this.vitaTorre2Indicator);
			this.mostraVitaTorre(this.partitaService.trovaAltroGiocatore(this.partita, this.giocatoreCorrente),
					this.vitaTorre1, this.vitaTorre1Indicator);
			this.vitaTorreAvversaria = this.vitaTorre1;
		}

	}

	// mappo le griglie che mi servono nell'this.engine
	private void mappaGriglie() {
		this.gridsListGiocatore.add(stradaSX);
		this.gridsListGiocatore.add(stradaDX);
		this.gridsListGiocatore.add(stradaC);
		this.gridsListGiocatore.add(carteMano);
		this.engine.mappingGriglie(this.gridsListGiocatore);

		this.gridsListAvversario.add(stradaSXavversario);
		this.gridsListAvversario.add(stradaDXavversario);
		this.gridsListAvversario.add(stradaCavversario);
		this.gridsListAvversario.add(carteMano);
		this.engine.mappingGriglie(this.gridsListAvversario);
	}

	@FXML
	public void cambiaFaseAction(ActionEvent event) {
		try {
			this.turnoService.cambiaFase(this.turnoCorrente);
		} catch (FasiTerminateException e) {
			System.err.println(e.getMessage());
			labelErrori.setText(e.getMessage());
		}
		this.faseCorrente.setText(this.turnoCorrente.getFase().toString());

		if (this.turnoCorrente.getFase().equals(FaseTurno.Attacco)
				|| this.turnoCorrente.getFase().equals(FaseTurno.Difesa)) {
			carteMano.setDisable(true); // Non si può schierare
		}
	}

	@FXML
	public void passaTurnoAction(ActionEvent event) {
		try {
			this.partitaService.salvaTurnoPartita(this.turnoCorrente, this.partita);

			if (this.partita.isRecuperata() && !GraphicEngine.getStati().isEmpty()) {
				this.engine.sovrascriviUltimoStato(this.engine);
			} else {
				this.engine.aggiungiStato(this.engine);
			}

			this.partita.setRecuperata(false);

			this.incantesimoService.checkAnnullaEffettoIncantesimi();
			this.personaggioService.resetMosseSpecialiAttive();

			this.dispatcher.caricaVista("gioco", this.partita);
		} catch (ViewException e) {
			e.printStackTrace();
		}
	}

	private void timerImpl() {
		// implementazione del timer
		timeline = new Timeline(new KeyFrame(Duration.seconds(1), (event) -> {
			secondiTrascorsi--;
			this.updateTimerLabel();
			if (secondiTrascorsi == 0) { // quando scade
				annullaMossa.setDisable(true);
				this.cambiaFase.setDisable(true);
				gridsList.stream().forEach(grid -> grid.setDisable(true));
				this.gridsListAvversario.stream().forEach(grid -> grid.setDisable(true));
			}
		}));
		timeline.setCycleCount(timerDurantion);
	}

	private void updateTimerLabel() {
		int seconds = secondiTrascorsi % 360;

		// 3 cifre nella label
		String timeString = String.format("%03d", seconds);
		timer.setText(timeString);
	}

	private void mostraElisir() {
		/*
		 * essendo la scala in 20esimi e la rappresentazione della progress bar in % con
		 * un max di 100, il set progress dovrebbe essere dimezzato ma si hanno sempre
		 * problemi con la rappresentazione numerica decimale quindi si lascia così
		 */
		double progress = this.turnoCorrente.getElisirGiocatore();
		this.elisir.setProgress((double) progress / 2);
		this.elisirIndicator.setText(this.formatElisir(progress));
	}

	// formattiamo in casi di errori di precisione
	private String formatElisir(double value) {
		DecimalFormat df = new DecimalFormat("#.#");
		return df.format(value * 10);
	}

	private void mostraVitaTorre(Giocatore giocatore, ProgressBar torre, Label indicator) {
		double vita = this.turnoService.trovaTorreGiocatore(giocatore).getVita();
		torre.setProgress(vita);
		indicator.setText(this.formatVitaTorre(vita));

		if (vita == 0) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("VITTORIA!!!");
			alert.setHeaderText("HA VINTO IL GIOCATORE " + this.giocatoreCorrente.getNickname());
			alert.setContentText("PREMI ESCI per tornare alla HOMEPAGE");

			ButtonType esci = new ButtonType("ESCI");

			alert.getButtonTypes().setAll(esci);

			alert.showAndWait().ifPresent(response -> {
				if (response == esci) {
					try {
						this.dispatcher.caricaVista("applicationLayout");
						this.dispatcher.caricaVista("homepage");
					} catch (ViewException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	// formattiamo in casi di errori di precisione
	private String formatVitaTorre(double value) {
		DecimalFormat df = new DecimalFormat("#.#");
		return df.format(value * 100);
	}

	private void proprietaClickImageViewGiocatore(MouseEvent event) {
		final ImageView imageViewCorrente = (ImageView) event.getSource();
		final GridPane gridPaneParent = (GridPane) imageViewCorrente.getParent();

		Posizione posizioneToSearch = new Posizione(GridPane.getColumnIndex(imageViewCorrente),
				GridPane.getRowIndex(imageViewCorrente));

		Personaggio personaggioCliccato = (Personaggio) this.engine.ricercaCartaStrada(gridPaneParent.getId(),
				posizioneToSearch);

		try {
			if (this.turnoCorrente.getFase().equals(FaseTurno.Difesa)) {
				// se è la fase di difesa posiziono il personaggio in difesa
				MossaGiocatore mossaGiocatore = giocatoreService.cambiaPosizionePersonaggio(this.turnoCorrente,
						personaggioCliccato, PosizionamentoPersonaggio.DIFESA);
				this.turnoService.salvaMossaGiocatore(this.partita, turnoCorrente, mossaGiocatore);
				imageViewCorrente.setRotate(270);
				System.out.println("PERSONAGGIO POSIZIONATO IN DIFESA");

				this.engine.impostaTooltip(imageViewCorrente, personaggioCliccato);

			} else if (turnoCorrente.getFase().equals(FaseTurno.Attacco)) {
				// posiziono il personaggio in attacco
				MossaGiocatore mossaGiocatore = giocatoreService.cambiaPosizionePersonaggio(this.turnoCorrente,
						personaggioCliccato, PosizionamentoPersonaggio.ATTACCO);
				this.turnoService.salvaMossaGiocatore(this.partita, this.turnoCorrente, mossaGiocatore);
				imageViewCorrente.setRotate(360);

				System.out.println("SONO " + personaggioCliccato.getNome() + " E SONO STATO SCELTO PER L'ATTACCO");

				this.engine.impostaTooltip(imageViewCorrente, personaggioCliccato);

				System.out.println("VITA " + personaggioCliccato.getVita());
				try {
					if (this.turnoCorrente.getNumero() > 0) {
						GridPaneGioco gridPersonaggio = this.engine.ricercaStradaSchieramento(gridPaneParent);
						MossaSpeciale mossaSpecialeAttaccante = personaggioCliccato.getMossaSpeciale();

						if (this.personaggioService.getPersonaggiConMosseAttive().contains(personaggioCliccato)
								&& mossaSpecialeAttaccante.getNome().equals("attaccaDiretto")) {

							System.out.println("STO PER ATTACCARE LA TORRE DIRETTAMENTE");

							Giocatore avversario = this.partitaService.trovaAltroGiocatore(this.partita,
									this.giocatoreCorrente);
							Torre torreAvversaria = this.turnoService.trovaTorreGiocatore(avversario);

							this.giocatoreService.attaccaTorre(this.turnoCorrente, personaggioCliccato, gridPersonaggio,
									torreAvversaria);

							if (this.vitaTorreAvversaria.getId().equals("vitaTorre1"))
								this.mostraVitaTorre(avversario, this.vitaTorreAvversaria, this.vitaTorre1Indicator);
							else
								this.mostraVitaTorre(avversario, this.vitaTorreAvversaria, this.vitaTorre2Indicator);

							this.personaggioService.rimuoviPersonaggioConMossaAttivo(personaggioCliccato);

						} else {
							if (this.engine.checkAttaccoTorre(gridPersonaggio.toString())) {

								Giocatore avversario = this.partitaService.trovaAltroGiocatore(this.partita,
										this.giocatoreCorrente);
								Torre torreAvversaria = this.turnoService.trovaTorreGiocatore(avversario);

								this.giocatoreService.attaccaTorre(this.turnoCorrente, personaggioCliccato,
										gridPersonaggio, torreAvversaria);

								if (this.vitaTorreAvversaria.getId().equals("vitaTorre1"))
									this.mostraVitaTorre(avversario, this.vitaTorreAvversaria,
											this.vitaTorre1Indicator);
								else
									this.mostraVitaTorre(avversario, this.vitaTorreAvversaria,
											this.vitaTorre2Indicator);

							} else // altrimenti hai un personaggio che davanti e non puoi attaccare la torre
								giocatoreService.preparaAttacco(personaggioCliccato, gridPersonaggio);
						}
					} else
						throw new AttaccoException("NON SI può ATTACCARE è IL PRIMO TURNO");
				} catch (AttaccoException e) {
					System.err.println(e.getMessage());
					this.labelErrori.setText(e.getMessage());
				}
			} else
				throw new PosizionamentoException("è la fase di schieramento");

		} catch (PosizionamentoException e) {
			System.err.println(e.getMessage());
			labelErrori.setText(e.getMessage());
		}

	}

	// implementazione di quando si clicca un personaggio avversario
	private void proprietaClickImageViewAvversario(MouseEvent event) {
		/*
		 * interruzione bruta del click perché con il metodo presente
		 * nell'implementazione del timer le gridPane avversarie non si disattivano
		 */
		if (secondiTrascorsi == 0) {
			return;
		}

		final ImageView imageViewCorrente = (ImageView) event.getSource();
		final GridPane gridPaneParent = (GridPane) imageViewCorrente.getParent();

		try {
			if (this.turnoCorrente.getFase().equals(FaseTurno.Attacco)) {

				Posizione posizioneToSearch = new Posizione(GridPane.getColumnIndex(imageViewCorrente),
						GridPane.getRowIndex(imageViewCorrente));

				Carta cartaCliccata = this.engine.ricercaCartaStrada(gridPaneParent.getId(), posizioneToSearch);

				Personaggio personaggioAttaccato = (Personaggio) cartaCliccata;

				// reimposto il tooltip per sicurezza
				this.engine.impostaTooltip(imageViewCorrente, cartaCliccata);

				Giocatore avversario = this.partitaService.trovaAltroGiocatore(this.partita, this.giocatoreCorrente);

				giocatoreService.effettuaAttacco(this.turnoCorrente, personaggioAttaccato,
						this.engine.ricercaStradaSchieramento(gridPaneParent),
						this.turnoService.trovaTorreGiocatore(avversario));

				if (personaggioAttaccato.getVita() <= 0) {
					this.engine.eliminaImmagineCarta(gridPaneParent, posizioneToSearch, imageViewCorrente);

					System.out.println("PERSONAGGIO ATTACCO MORTO");

					if (this.vitaTorreAvversaria.getId().equals("vitaTorre1"))
						this.mostraVitaTorre(avversario, this.vitaTorreAvversaria, this.vitaTorre1Indicator);
					else
						this.mostraVitaTorre(avversario, this.vitaTorreAvversaria, this.vitaTorre2Indicator);

				} else
					this.engine.impostaTooltip(imageViewCorrente, cartaCliccata);

			} else
				throw new AttaccoException("Non puoi selezionarla non è la fase di attacco");

		} catch (AttaccoException e) {
			System.err.println(e.getMessage());
			this.labelErrori.setText(e.getMessage());
		}

	}

	private void dragAndDropIncantesimo(ImageView imageViewTarget) {

		final ImageView imageViewCorrente = imageViewTarget;
		final GridPane gridPaneParent = (GridPane) imageViewCorrente.getParent();

		// quando si passa l'incantesimo sopra un'immagine
		imageViewCorrente.setOnDragOver(event -> {
			if (event.getDragboard().hasImage()) {
				event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
			}
			event.consume();
		});

		// quando si rilascia l'incantesimo sopra un'immagine
		imageViewCorrente.setOnDragDropped(event -> {
			Dragboard db = event.getDragboard();
			boolean success = false;

			if (db.hasImage()) {

				Posizione posizioneCartaMano = this.engine.getPosizioneCartaSelezionata()[0];
				Carta cartaMano = this.engine.ricercaCartaStrada(this.carteMano.getId(), posizioneCartaMano);

				if (cartaMano instanceof Personaggio) {
					this.labelErrori.setText("STAI TRASCINADO UN PERSONAGGIO SOPRA UN ALTRO");
					return;
				}

				Incantesimo incantesimoDaSchierare = (Incantesimo) cartaMano;

				System.out.println("INCANTESIMO " + incantesimoDaSchierare);

				Posizione posizionePersonaggioTarget = new Posizione(GridPane.getColumnIndex(imageViewCorrente),
						GridPane.getRowIndex(imageViewCorrente));

				Personaggio personaggioTarget = (Personaggio) this.engine.ricercaCartaStrada(gridPaneParent.getId(),
						posizionePersonaggioTarget);

				// clono l'incantesimo della mano
				Incantesimo incancantesimoDaSchierareClone = null;
				try {
					incancantesimoDaSchierareClone = (Incantesimo) incantesimoDaSchierare.clone();
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}

				// ricontrollo lo schieramento quando l'incantesimo è "droppato" sull'immagine
				try {
					this.turnoService.controllaSchieramento(this.turnoCorrente, incancantesimoDaSchierareClone);
				} catch (ElisirException e) {
					System.err.println(e.getMessage());
					return;
				}

				// metto la prossima carta del mazzo in mano
				ImageView imageViewProssimaCarta = this.engine.creaImpostaImageView(this.prossimaCarta.getImage(),
						dim_img, dim_img);
				Posizione posizioneDaRimpiazzare = this.engine.getPosizioneCartaSelezionata()[0];
				this.carteMano.getChildren()
						.remove(this.engine.ricercaImmagineStrada(this.carteMano.getId(), posizioneDaRimpiazzare));
				this.engine.aggiungiCartaImmagineGriglia(this.carteMano, this.prossimaCartaMazzo[0],
						imageViewProssimaCarta, posizioneDaRimpiazzare);
				this.engine.impostaTooltip(imageViewProssimaCarta, this.prossimaCartaMazzo[0]);
				this.engine.setImageDragProperty(imageViewProssimaCarta);
//				this.prossimaCartaMazzo[0] = this.mazzoService.mostraProssimaCarta(this.giocatoreCorrente.getMazzo(),
//						this.engine.ricercaCarteMano(this.carteMano.getId()));
				this.prossimaCarta.setImage(this.prossimaCartaMazzo[0].getImmagineCarta());

				// creo e salvo la mossa di schieramento dell'incantesimo
				MossaGiocatore mossa = this.giocatoreService.effettuaSchieramento(this.turnoCorrente,
						incantesimoDaSchierare);

				this.turnoService.salvaMossaGiocatore(this.partita, this.turnoCorrente, mossa);

				// aggiorno l'elisir
				this.mostraElisir();

				// controllo che tipo di incantesimo è, se è di tipo attivo lo salvo altrimenti
				// lo eseguo
				if (incantesimoDaSchierare.getNome().equals(IncantesimiNomi.RendiInvulnerabile.toString())
						|| incantesimoDaSchierare.getNome().equals(IncantesimiNomi.BloccaAttacco.toString()))
					this.incantesimoService.aggiungiIncantesimoAttivo(this.turnoCorrente,
							incancantesimoDaSchierareClone, personaggioTarget);
				else {
					this.incantesimoService.eseguiIncantesimo(incantesimoDaSchierare, personaggioTarget);

					if (personaggioTarget.getVita() <= 0) // nel caso il personaggio sia morto
						this.engine.eliminaImmagineCarta(gridPaneParent, posizionePersonaggioTarget, imageViewCorrente);
					else
						this.engine.impostaTooltip(imageViewCorrente, personaggioTarget);

				}

				success = true;

				this.labelErrori.setText("");
			}

			event.setDropCompleted(success);
			event.consume();
		});
	}

	private void esecuzioneMosseSpeciali(String idParent, ImageView img) {
		// MOSSE SPECIALI
		Posizione posizioneToSearch = new Posizione(GridPane.getColumnIndex(img), GridPane.getRowIndex(img));

		Personaggio personaggio = (Personaggio) this.engine.ricercaCartaStrada(idParent, posizioneToSearch);
		this.engine.impostaTooltip(img, personaggio);
		try {
			if (personaggio.getMossaSpeciale().getNome().equals("ricaricaEnergiaStrada")) {
				this.personaggioService.eseguiMossaSpeciale(personaggio,
						this.engine.cercaPersonaggiStrada(idParent, personaggio));
			} else
				this.personaggioService.eseguiMossaSpeciale(personaggio);
		} catch (ManaException e) {
			String message = e.getMessage() + " per " + personaggio.getNome() + " " + idParent;
			System.err.println(message);
			this.labelErrori.setText(message);
		}
		this.engine.impostaTooltip(img, personaggio);

	}

	@FXML
	public void salvaPartitaAction(ActionEvent event) {
		// per prima cosa salviamo il turno e lo stato delle carte corrente
		this.partitaService.salvaTurnoPartita(this.turnoCorrente, this.partita);
		this.engine.aggiungiStato(this.engine);

		// calcolo i criteri richiesti per l'ordinamento della partita
		int numeroMosse = this.partitaService.calcolaNumeroMossePartita(this.partita);
		int numeroCarteInCampo = this.engine.calcolaNumeroCarteTerreno();
		int valoreCarteInCampo = this.engine.calcolaValoreCarteTerreno();

		this.partitaService.impostaParamentriSalvataggio(this.partita, numeroMosse, numeroCarteInCampo,
				valoreCarteInCampo);

		this.partitaService.salvaPartita(this.partita);

		// mi preparo la mappa da serializzare
		this.engine.popolaMappaIncantesimi(this.incantesimoService.getIncantesimiAttivi());

		// serializzo
		try {
			GraphicEngine.serializeMappaGridpaneCarte(this.engine.getMappaGridpaneCarte(),
					String.valueOf(this.partita.getID()));

			GraphicEngine.serializeMappaGridpaneIncantesimi(this.engine.getMappaGridpaneIncantesimi(),
					String.valueOf(this.partita.getID()));

		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			this.dispatcher.caricaVista("applicationLayout");
			this.dispatcher.caricaVista("homepage");
		} catch (ViewException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void esciAction(ActionEvent event) {
		// esci senza salvare la partita
		try {
			this.dispatcher.caricaVista("applicationLayout");
			this.dispatcher.caricaVista("homepage");
		} catch (ViewException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void annullaMossaAction(ActionEvent event) {

		this.engine.ripristinaDopoAnnullamento(this.getAllGrids());

		this.incantesimoService.annullamentoIncantesimi();

		this.turnoService.annullaUltimoTurno(this.turnoCorrente);
		try {
			this.dispatcher.caricaVista("gioco", this.partita);
		} catch (ViewException e) {
			e.printStackTrace();
		}
	}

	private void setProprietaNuoveImmagini() {
		for (ImageView img : this.engine.getNuoveImmagini()) {
			GridPane parent = (GridPane) img.getParent();
			String idParent = parent.getId();

			boolean presente = this.gridsList.stream().anyMatch(gridPane -> idParent.equals(gridPane.getId()));

			if (!presente)
				img.setOnMouseClicked(this::proprietaClickImageViewAvversario);
			else
				img.setOnMouseClicked(this::proprietaClickImageViewGiocatore);

			this.labelErrori.setText("");

			this.dragAndDropIncantesimo(img);

			// MOSSE SPECIALI
			this.esecuzioneMosseSpeciali(idParent, img);

		}
	}

	public void recuperaCarte() {
		List<Carta> carteCampoDeserializzate = new ArrayList<>();
		Map<String, LinkedHashMap<Posizione, Carta>> mappaDeserializzata = null;
		Map<String, LinkedHashMap<Incantesimo, Posizione>> mappaIncantesimiDeserializzati = null;

		try {

			mappaDeserializzata = GraphicEngine.deserializeMappaGridpaneCarte(String.valueOf(this.partita.getID()));

			for (String key : mappaDeserializzata.keySet()) {
				LinkedHashMap<Posizione, Carta> innerMap = mappaDeserializzata.get(key);
				for (Posizione p : innerMap.keySet()) {
					Carta carta = innerMap.get(p);
					if (carta != null) {
						carteCampoDeserializzate.add(carta);
					}
				}
			}

			mappaIncantesimiDeserializzati = GraphicEngine
					.deserializeMappaGridpaneIncantesimi(String.valueOf(this.partita.getID()));

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.cartaFactory.reimpostaImmagine(carteCampoDeserializzate);

		for (Giocatore g : this.partita.getGiocatori()) {
			Mazzo mazzo = this.mazzoService.trovaMazzo(g);
			this.cartaFactory.reimpostaImmagine(Arrays.asList(mazzo.getCarte()));
		}

		List<GridPane> tutte = this.getAllGrids();

		// ripristino delle carte sul terreno
		this.engine.ripristinaStato(tutte, mappaDeserializzata);

		// ripristino degli incantesimi attivi
		for (Map.Entry<String, LinkedHashMap<Incantesimo, Posizione>> entry : mappaIncantesimiDeserializzati
				.entrySet()) {
			String gridPaneKey = entry.getKey();
			LinkedHashMap<Incantesimo, Posizione> innerMap = entry.getValue();

			for (Incantesimo incantesimo : innerMap.keySet()) {
				Posizione p = innerMap.get(incantesimo);
				if (p != null) {
					Personaggio personaggio = (Personaggio) this.engine.ricercaCartaStrada(gridPaneKey, p);
					this.incantesimoService.aggiungiIncantesimoAttivo(this.turnoCorrente, incantesimo, personaggio);
				}
			}
		}

	}

	private List<GridPane> getAllGrids() {
		List<GridPane> allGrids = new ArrayList<>();
		allGrids.addAll(this.gridsListGiocatore);
		allGrids.addAll(this.gridsListAvversario);
		return allGrids;
	}

	private void spostaElementiGiocatore() {
		this.prossimaCarta.setLayoutY(y_giocatore1);
		this.elisir.setLayoutY(y_giocatore1);
		this.elisirIndicator.setLayoutY(y_giocatore1);

	}

	private void spostaElementiGiocatore2() {
		this.prossimaCarta.setLayoutY(y_giocatore2);
		this.elisir.setLayoutY(y_giocatore2);
		this.elisirIndicator.setLayoutY(y_giocatore2);

	}

}