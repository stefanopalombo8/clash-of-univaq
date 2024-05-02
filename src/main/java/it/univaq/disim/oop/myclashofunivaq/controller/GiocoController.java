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
import it.univaq.disim.oop.myclashofunivaq.controller.utilities.GraphicUtility;
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

	private GraphicUtility utility;
	private static final int dim_img = 60;
	// coordinata y per spostare gli elementi prossima immagine e elisir
	private static final int y_giocatore1 = 40;
	private static final int y_giocatore2 = 460;

	// Array per operarare dentro le lambda
	private Carta[] cartaSchierata = new Carta[1];
	private Carta[] prossimaCartaMazzo = new Carta[1];

	public GiocoController() {
		this.dispatcher = ViewDispatcher.getInstance();
		this.partitaService = new PartitaServiceImpl();
		this.turnoService = new TurnoServiceImpl();
		this.mazzoService = new MazzoServiceImpl();
		this.secondiTrascorsi = timerDurantion;
		this.utility = new GraphicUtility();
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

			partitaService.mappaPartitaSerializzata(partita);
			turnoService.ripopolaMappaTurni(partita);

			turnoCorrente = turnoService.getUltimoTurno(partita);

			giocatoreCorrente = turnoCorrente.getGiocatore();

			nomeGiocatore.setText(giocatoreCorrente.getNickname());

			faseCorrente.setText(turnoCorrente.getFase().toString());

			this.mostraElisir();

			Mazzo mazzo = mazzoService.trovaMazzo(giocatoreCorrente);
			cartaFactory.reimpostaImmagine(Arrays.asList(mazzo.getCarte()));

			if (turnoService.isTurnoPari(turnoCorrente)) {
				this.carteMano = carteManoG1;
				this.spostaElementiGiocatore2();
				this.utility.impostaRetroCarte(carteManoG2);
				this.gridsList = gridsListGiocatore;
				this.mostraVitaTorre(giocatoreCorrente, vitaTorre1, vitaTorre1Indicator);

				// questo perché al primo turno non ho ancora istanziato la torre avversaria
				if (turnoCorrente.getNumero() != 0) {
					this.mostraVitaTorre(this.turnoService.trovaAltroGiocatore(partita), vitaTorre2,
							vitaTorre2Indicator);
					this.vitaTorreAvversaria = vitaTorre2;
				}

			} else {
				this.carteMano = carteManoG2;
				this.spostaElementiGiocatore();
				this.utility.impostaRetroCarte(carteManoG1);
				this.gridsList = gridsListAvversario;
				this.mostraVitaTorre(giocatoreCorrente, vitaTorre2, vitaTorre2Indicator);
				this.mostraVitaTorre(this.turnoService.trovaAltroGiocatore(partita), vitaTorre1, vitaTorre1Indicator);
				this.vitaTorreAvversaria = vitaTorre1;
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

				this.dragAndDropIncantesimo(img);

				//MOSSE SPECIALI
				Posizione posizioneToSearch = new Posizione(GridPane.getColumnIndex(img), GridPane.getRowIndex(img));

				Personaggio personaggio = (Personaggio) utility.ricercaCartaStrada(idParent, posizioneToSearch);
				utility.impostaTooltip(img, personaggio);
				try {
					if(personaggio.getMossaSpeciale().getNome().equals("ricaricaEnergiaStrada")) {
						this.personaggioService.eseguiMossaSpeciale(personaggio, utility.cercaCarteStrada(idParent, personaggio));
					}
					else
						this.personaggioService.eseguiMossaSpeciale(personaggio);
				} catch (ManaException e) {
					String message = e.getMessage() + " per " + personaggio.getNome() + " " + idParent;
					System.err.println(message);
					this.labelErrori.setText(message);
				}
				utility.impostaTooltip(img, personaggio);

			}
			if (giocatoreCorrente instanceof GiocatoreUtente)
				this.trascinamentoImmagini(mazzo);
			else
				this.faiMosseComputer(mazzo);

		} else {

			giocatoreCorrente = turnoService.alternaGiocatore(partita);

			this.timerImpl();

			turnoCorrente = turnoService.avviaTurno(timeline, giocatoreCorrente);

			nomeGiocatore.setText(giocatoreCorrente.getNickname());

			faseCorrente.setText(turnoCorrente.getFase().toString());

			this.mostraElisir();

			Mazzo mazzo = mazzoService.trovaMazzo(giocatoreCorrente);

			if (turnoService.isTurnoPari(turnoCorrente)) {
				this.carteMano = carteManoG1;
				this.spostaElementiGiocatore2();
				this.utility.impostaRetroCarte(carteManoG2);
				this.gridsList = gridsListGiocatore;
				this.mostraVitaTorre(giocatoreCorrente, vitaTorre1, vitaTorre1Indicator);

				// questo perché al primo turno non ho ancora istanziato la torre avversaria
				if (turnoCorrente.getNumero() != 0) {
					this.mostraVitaTorre(this.turnoService.trovaAltroGiocatore(partita), vitaTorre2,
							vitaTorre2Indicator);
					this.vitaTorreAvversaria = vitaTorre2;
				}

			} else {
				this.carteMano = carteManoG2;
				this.spostaElementiGiocatore();
				this.utility.impostaRetroCarte(carteManoG1);
				this.gridsList = gridsListAvversario;
				this.mostraVitaTorre(giocatoreCorrente, vitaTorre2, vitaTorre2Indicator);
				this.mostraVitaTorre(this.turnoService.trovaAltroGiocatore(partita), vitaTorre1, vitaTorre1Indicator);
				this.vitaTorreAvversaria = vitaTorre1;
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
					
					//con questo capisco se devo associare l'impl del click al giocatore o all'avversario
					boolean presente = this.gridsList.stream().anyMatch(gridPane -> idParent.equals(gridPane.getId()));

					if (!presente)
						img.setOnMouseClicked(this::proprietaClickImageViewAvversario);
					else
						img.setOnMouseClicked(this::proprietaClickImageViewGiocatore);

					this.dragAndDropIncantesimo(img);

					//MOSSE SPECIALI
					Posizione posizioneToSearch = new Posizione(GridPane.getColumnIndex(img), GridPane.getRowIndex(img));

					Personaggio personaggio = (Personaggio) utility.ricercaCartaStrada(idParent, posizioneToSearch);
					utility.impostaTooltip(img, personaggio);
					try {
						if(personaggio.getMossaSpeciale().getNome().equals("ricaricaEnergiaStrada")) {
							this.personaggioService.eseguiMossaSpeciale(personaggio, utility.cercaCarteStrada(idParent, personaggio));
						}
						else
							this.personaggioService.eseguiMossaSpeciale(personaggio);
					} catch (ManaException e) {
						String message = e.getMessage() + " per " + personaggio.getNome() + " " + idParent;
						System.err.println(message);
						this.labelErrori.setText(message);
					}
					utility.impostaTooltip(img, personaggio);


				}
			}

			if (turnoService.isFirstTurno(turnoCorrente) || GraphicUtility.getStati().size() <= 1) {

				// RIPRISTINO DELLE IMMAGINI DELLE CARTE DOPO LA DESERIALIZZAZIONE
				if (Arrays.stream(mazzo.getCarte()).anyMatch(carta -> carta == null))
					this.cartaFactory.reimpostaImmagine(Arrays.asList(mazzo.getCarte()));

				for (Carta carta : mazzoService.mostraCarteMano(mazzo)) {
					ImageView imageView = utility.creaImpostaImageView(carta.getImmagineCarta(), dim_img, dim_img);
					utility.impostaTooltip(imageView, carta);
					// Mapping immagini e carte in mano
					utility.aggiungiCartaImmagineGriglia(carteMano, carta, imageView);
					utility.setImageDragProperty(carteMano, imageView);
				}
			} else
				utility.ripristinaCarteMano(carteMano);

			if (giocatoreCorrente instanceof GiocatoreUtente)
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

		gridsListCopy.remove(carteMano);
		gridsListAvversarioCopy.remove(carteMano);

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

		ImageView imageViewDaSchierare = utility.creaImpostaImageView(randomImageViewMano.getImage(), dim_img, dim_img);
		Carta cartaDaSchierare = utility.ricercaCartaStrada(carteMano.getId(), posizioneToSearch);
		try {
			turnoService.controllaSchieramento(turnoCorrente, cartaDaSchierare);
			cartaSchierata[0] = (Carta) cartaDaSchierare.clone();
			utility.impostaTooltip(imageViewDaSchierare, cartaSchierata[0]);

		} catch (ElisirException e) {
			System.err.println(e.getMessage());
			return;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		utility.aggiungiCartaImmagineGriglia(gridSchieramento, cartaSchierata[0], null); // aggiunta nella
		// strada

		utility.aggiungiCartaImmagineGriglia(gridSchieramento, null, imageViewDaSchierare);

		Carta[] prossimaCartaMazzo = new Carta[1]; // WRAPPER
		prossimaCartaMazzo[0] = mazzoService.mostraProssimaCarta(mazzo);
		prossimaCarta.setImage(prossimaCartaMazzo[0].getImmagineCarta());

		ImageView imageViewProssimaCarta = utility.creaImpostaImageView(prossimaCarta.getImage(), dim_img, dim_img);

		carteMano.getChildren().remove(utility.ricercaImmagineStrada(carteMano.getId(), posizioneToSearch));

		utility.aggiungiCartaImmagineGriglia(carteMano, prossimaCartaMazzo[0], imageViewProssimaCarta,
				posizioneToSearch);
		utility.impostaTooltip(imageViewProssimaCarta, prossimaCartaMazzo[0]);
		prossimaCartaMazzo[0] = mazzoService.mostraProssimaCarta(mazzo);
		prossimaCarta.setImage(prossimaCartaMazzo[0].getImmagineCarta());

		Personaggio personaggioSchierato = (Personaggio) cartaSchierata[0];

		MossaGiocatore mossaGiocatore = giocatoreService.effettuaSchieramentoPersonaggio(turnoCorrente,
				personaggioSchierato, utility.ricercaStradaSchieramento(gridSchieramento),
				PosizionamentoPersonaggio.ATTACCO);

		turnoService.salvaMossaGiocatore(partita, turnoCorrente, mossaGiocatore);

		this.mostraElisir();

		if (!turnoService.isFirstTurno(turnoCorrente)) {
			try {
				turnoService.cambiaFase(turnoCorrente);
			} catch (FasiTerminateException e) {
				System.err.println(e.getMessage());
				this.labelErrori.setText(e.getMessage());
			}
			faseCorrente.setText(turnoCorrente.getFase().toString());

			// DIFESA
			ImageView imageViewStrada = this.scegliImageViewRandomica(gridsListCopy);
			if (imageViewStrada == null) {
				this.labelErrori.setText("NON CI SONO CARTE IN QUELLA STRADA");
				return;
			}
			GridPane gridPaneParent = (GridPane) imageViewStrada.getParent();

			posizioneToSearch = new Posizione(GridPane.getColumnIndex(imageViewStrada),
					GridPane.getRowIndex(imageViewStrada));
			Personaggio personaggioStrada = (Personaggio) utility.ricercaCartaStrada(gridPaneParent.getId(),
					posizioneToSearch);

			try {
				MossaGiocatore mossaGiocatore2 = giocatoreService.cambiaPosizionePersonaggio(turnoCorrente,
						personaggioStrada, PosizionamentoPersonaggio.DIFESA);
				turnoService.salvaMossaGiocatore(partita, turnoCorrente, mossaGiocatore2);
				imageViewStrada.setRotate(270);
			} catch (PosizionamentoException e) {
				System.err.println(e.getMessage());
			}

			try {
				turnoService.cambiaFase(turnoCorrente);
			} catch (FasiTerminateException e) {
				System.err.println(e.getMessage());
				this.labelErrori.setText(e.getMessage());
			}
			faseCorrente.setText(turnoCorrente.getFase().toString());

			// ATTACCO
			// PERSONAGGIO ATTACCANTE
			imageViewStrada = this.scegliImageViewRandomica(gridsListCopy);
			if (imageViewStrada == null) {
				this.labelErrori.setText("NON CI SONO CARTE IN QUELLA STRADA");
				return;
			}

			gridPaneParent = (GridPane) imageViewStrada.getParent();
			GridPaneGioco gridPersonaggio = utility.ricercaStradaSchieramento(gridPaneParent);

			posizioneToSearch = new Posizione(GridPane.getColumnIndex(imageViewStrada),
					GridPane.getRowIndex(imageViewStrada));
			personaggioStrada = (Personaggio) utility.ricercaCartaStrada(gridPaneParent.getId(), posizioneToSearch);

			System.out.println("gridPaneParent " + gridPaneParent.getId());
			System.out.println("gridPersonaggio " + gridPersonaggio);
			try {

				if (utility.checkAttaccoTorre(gridPersonaggio.toString())) {

					Giocatore avversario = turnoService.trovaAltroGiocatore(partita);
					Torre torreAvversaria = turnoService.trovaTorreGiocatore(avversario);

					giocatoreService.attaccaTorre(turnoCorrente, personaggioStrada, gridPersonaggio, torreAvversaria);

					if (vitaTorreAvversaria.getId().equals("vitaTorre1"))
						this.mostraVitaTorre(avversario, vitaTorreAvversaria, vitaTorre1Indicator);
					else
						this.mostraVitaTorre(avversario, vitaTorreAvversaria, vitaTorre2Indicator);

				} else {
					giocatoreService.preparaAttacco(personaggioStrada, gridPersonaggio);
					// PERSONAGGIO ATTACCANTE
					ImageView imageViewStradaAvversaria = this.scegliImageViewRandomica(gridsListAvversarioCopy);
					if (imageViewStradaAvversaria == null) {
						this.labelErrori.setText("NON CI SONO CARTE IN QUELLA STRADA");
						return;
					}
					Posizione posizioneAvversaria = new Posizione(GridPane.getColumnIndex(imageViewStrada),
							GridPane.getRowIndex(imageViewStrada));
					GridPane gridPaneParentAvversaria = (GridPane) imageViewStradaAvversaria.getParent();
					Personaggio personaggioStradaAvversaria = (Personaggio) utility
							.ricercaCartaStrada(gridPaneParentAvversaria.getId(), posizioneAvversaria);

					Giocatore avversario = turnoService.trovaAltroGiocatore(partita);
					giocatoreService.effettuaAttacco(turnoCorrente, personaggioStradaAvversaria,
							utility.ricercaStradaSchieramento(gridPaneParentAvversaria),
							turnoService.trovaTorreGiocatore(avversario));

					if (personaggioStradaAvversaria.getVita() <= 0) {
						utility.eliminaImmagineCarta(gridPaneParent, posizioneToSearch, imageViewStradaAvversaria);

						if (vitaTorreAvversaria.getId().equals("vitaTorre1"))
							this.mostraVitaTorre(avversario, vitaTorreAvversaria, vitaTorre1Indicator);
						else
							this.mostraVitaTorre(avversario, vitaTorreAvversaria, vitaTorre2Indicator);

					} else
						utility.impostaTooltip(imageViewStradaAvversaria, personaggioStradaAvversaria);

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

	//metodo per trascinare le immagini dalla mano alle strade
	private void trascinamentoImmagini(Mazzo mazzo) {
		prossimaCartaMazzo[0] = mazzoService.mostraProssimaCarta(mazzo);
		prossimaCarta.setImage(prossimaCartaMazzo[0].getImmagineCarta());
		
		System.out.println("non mi vedooo " + prossimaCartaMazzo[0].getNome());

		for (GridPane grid : gridsList) {

			grid.setOnDragOver(event -> {
				if (event.getGestureSource() != grid && event.getDragboard().hasImage()) {
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
				}

				event.consume();
			});

			grid.setOnDragDropped(event -> {
				
				//uso le RuntimeException per uscire 
				if (grid.equals(carteMano)) {
					this.labelErrori.setText("NON PUOI AGGIUNGERE CARTE IN MANO");
					throw new RuntimeException();
				}

				/* 3 immagini + 1 nodo parent (strada piena verrà gestita dal drop dell'incantesimo o da questo)
				 *  nel senso che se si trascina in una strada piena probabilmente stai mettendo un personaggio
				 *  sopra l'altro (mossa non valida)
				 */
				if (grid.getChildren().size() == 4) {
					this.labelErrori.setText(grid.getId() + " PIENA");
					return;
				}

				Dragboard db = event.getDragboard();
				boolean success = false;

				ImageView newImageView = null;
				Posizione posizioneDaRimpiazzare = null;
				ImageView imageViewProssimaCarta = null;

				if (db.hasImage()) {
					newImageView = utility.creaImpostaImageView(db.getImage(), dim_img, dim_img);

					imageViewProssimaCarta = utility.creaImpostaImageView(prossimaCarta.getImage(), dim_img, dim_img);

					posizioneDaRimpiazzare = utility.getPosizioneCartaSelezionata()[0];

					try {
						// Mapping carta schierata
						Carta cartaDaSchierare = utility.ricercaCartaStrada(this.carteMano.getId(),
								posizioneDaRimpiazzare);
						turnoService.controllaSchieramento(turnoCorrente, cartaDaSchierare);
						cartaSchierata[0] = (Carta) cartaDaSchierare.clone();
						utility.impostaTooltip(newImageView, cartaSchierata[0]);

						if (cartaSchierata[0] instanceof Incantesimo) {
							this.labelErrori.setText("stai schierando un incatesimo in strada");
							throw new RuntimeException();
						}

						utility.aggiungiCartaImmagineGriglia(grid, cartaSchierata[0], null); // aggiunta carta nella
																								// strada
						
					} catch (ElisirException e) {
						this.labelErrori.setText(e.getMessage());
						throw new RuntimeException();
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
					//aggiunta dell'immagine nella strada
					utility.aggiungiCartaImmagineGriglia(grid, null, newImageView);
					
					//implementazione del click
					newImageView.setOnMouseClicked(this::proprietaClickImageViewGiocatore);
					
					//implementazione del drag and drop
					this.dragAndDropIncantesimo(newImageView);

					carteMano.getChildren()
							.remove(utility.ricercaImmagineStrada(carteMano.getId(), posizioneDaRimpiazzare));

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

						utility.impostaTooltip(newImageView, personaggioSchierato);

					}

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

			});

		}
	}
	
	//mappo le griglie che mi servono nell'utility
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
		try {
			turnoService.cambiaFase(turnoCorrente);
		} catch (FasiTerminateException e) {
			System.err.println(e.getMessage());
			this.labelErrori.setText(e.getMessage());
		}
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

			this.incantesimoService.checkAnnullaEffettoIncantesimi();
			this.personaggioService.resetMosseSpecialiAttive();

			dispatcher.caricaVista("gioco", partita);
		} catch (ViewException e) {
			e.printStackTrace();
		}
	}

	private void timerImpl() {
		//implementazione del timer
		timeline = new Timeline(new KeyFrame(Duration.seconds(1), (event) -> {
			secondiTrascorsi--;
			updateTimerLabel();
			if (secondiTrascorsi == 0) { //quando scade
				this.annullaMossa.setDisable(true);
				this.cambiaFase.setDisable(true);
				this.gridsList.stream().forEach(grid -> grid.setDisable(true));
				this.gridsListAvversario.stream().forEach(grid -> grid.setDisable(true));
			}
		}));
		timeline.setCycleCount(timerDurantion);
	}

	private void updateTimerLabel() {
		int seconds = secondiTrascorsi % 360;
		
		//3 cifre nella label
		String timeString = String.format("%03d", seconds);
		timer.setText(timeString);
	}

	private void mostraElisir() {
		/* essendo la scala in 20esimi e la rappresentazione della progress bar
		 * in % con un max di 100, il set progress dovrebbe essere dimezzato ma 
		 * si hanno sempre problemi con la rappresentazione numerica decimale
		 * quindi si lascia così
		 */
		double progress = turnoCorrente.getElisirGiocatore();
		System.out.println("elisir " + progress);
		elisir.setProgress(progress);
		elisirIndicator.setText(this.formatElisir(progress));
	}
	
	//formattiamo in casi di errori di precisione
	private String formatElisir(double value) {
		DecimalFormat df = new DecimalFormat("#.#");
		return df.format(value * 10);
	}

	private void mostraVitaTorre(Giocatore giocatore, ProgressBar torre, Label indicator) {
		double vita = turnoService.trovaTorreGiocatore(giocatore).getVita();
		torre.setProgress(vita);
		indicator.setText(this.formatVitaTorre(vita));

		if (vita == 0) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("VITTORIA!!!");
			alert.setHeaderText("HA VINTO IL GIOCATORE " + giocatoreCorrente.getNickname());
			alert.setContentText("PREMI ESCI per tornare alla HOMEPAGE");

			ButtonType esci = new ButtonType("ESCI");

			alert.getButtonTypes().setAll(esci);

			alert.showAndWait().ifPresent(response -> {
				if (response == esci) {
					try {
						dispatcher.caricaVista("applicationLayout");
						dispatcher.caricaVista("homepage");
					} catch (ViewException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
	
	//formattiamo in casi di errori di precisione
	private String formatVitaTorre(double value) {
		DecimalFormat df = new DecimalFormat("#.#");
		return df.format(value * 100);
	}

	private void proprietaClickImageViewGiocatore(MouseEvent event) {
		final ImageView imageViewCorrente = (ImageView) event.getSource();
		final GridPane gridPaneParent = (GridPane) imageViewCorrente.getParent();

		Posizione posizioneToSearch = new Posizione(GridPane.getColumnIndex(imageViewCorrente),
				GridPane.getRowIndex(imageViewCorrente));
		// riuso l'array ma si poteva creare anche una nuova Carta
		cartaSchierata[0] = utility.ricercaCartaStrada(gridPaneParent.getId(), posizioneToSearch);

		Personaggio personaggioCliccato = (Personaggio) cartaSchierata[0];

		try {
			if (turnoCorrente.getFase().equals(FaseTurno.Difesa)) {
				//se è la fase di difesa posiziono il personaggio in difesa
				MossaGiocatore mossaGiocatore = giocatoreService.cambiaPosizionePersonaggio(turnoCorrente,
						personaggioCliccato, PosizionamentoPersonaggio.DIFESA);
				turnoService.salvaMossaGiocatore(partita, turnoCorrente, mossaGiocatore);
				imageViewCorrente.setRotate(270);
				System.out.println("PERSONAGGIO POSIZIONATO IN DIFESA");
				
				this.utility.impostaTooltip(imageViewCorrente, personaggioCliccato);

			} else if (turnoCorrente.getFase().equals(FaseTurno.Attacco)) {
				//posiziono il personaggio in attacco
				MossaGiocatore mossaGiocatore = giocatoreService.cambiaPosizionePersonaggio(turnoCorrente,
						personaggioCliccato, PosizionamentoPersonaggio.ATTACCO);
				turnoService.salvaMossaGiocatore(partita, turnoCorrente, mossaGiocatore);
				imageViewCorrente.setRotate(360);
				
				System.out.println("SONO " + personaggioCliccato.getNome() + " E SONO STATO SCELTO PER L'ATTACCO");
				
				this.utility.impostaTooltip(imageViewCorrente, personaggioCliccato);

				System.out.println("VITA " + personaggioCliccato.getVita());
				try {
					if (turnoCorrente.getNumero() > 0) {
						GridPaneGioco gridPersonaggio = utility.ricercaStradaSchieramento(gridPaneParent);
						
						if(personaggioService.getPersonaggiConMosseAttive().contains(personaggioCliccato)) {
							MossaSpeciale mossaSpecialeAttaccante = personaggioCliccato.getMossaSpeciale();
							if(mossaSpecialeAttaccante.getNome().equals("attaccaDiretto")) {
								System.out.println("STO PER ATTACCARE LA TORRE DIRETTAMENTE");
								
								Giocatore avversario = turnoService.trovaAltroGiocatore(partita);
								Torre torreAvversaria = turnoService.trovaTorreGiocatore(avversario);

								giocatoreService.attaccaTorre(turnoCorrente, personaggioCliccato, gridPersonaggio,
										torreAvversaria);

								if (vitaTorreAvversaria.getId().equals("vitaTorre1"))
									this.mostraVitaTorre(avversario, vitaTorreAvversaria, vitaTorre1Indicator);
								else
									this.mostraVitaTorre(avversario, vitaTorreAvversaria, vitaTorre2Indicator);
								
								this.personaggioService.rimuoviPersonaggioConMossaAttivo(personaggioCliccato);
							}
							
						}
						if (utility.checkAttaccoTorre(gridPersonaggio.toString())) {

							Giocatore avversario = turnoService.trovaAltroGiocatore(partita);
							Torre torreAvversaria = turnoService.trovaTorreGiocatore(avversario);

							giocatoreService.attaccaTorre(turnoCorrente, personaggioCliccato, gridPersonaggio,
									torreAvversaria);

							if (vitaTorreAvversaria.getId().equals("vitaTorre1"))
								this.mostraVitaTorre(avversario, vitaTorreAvversaria, vitaTorre1Indicator);
							else
								this.mostraVitaTorre(avversario, vitaTorreAvversaria, vitaTorre2Indicator);

						} else //altrimenti hai un personaggio che davanti e non puoi attaccare la torre
							giocatoreService.preparaAttacco(personaggioCliccato, gridPersonaggio);
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
			this.labelErrori.setText(e.getMessage());
		}

	}

	// implementazione di quando si clicca un personaggio avversario
	private void proprietaClickImageViewAvversario(MouseEvent event) {
		/* interruzione bruta del click perché con il metodo presente nell'implementazione del timer
		 * le gridPane avversarie non si disattivano
		 */
		if (this.secondiTrascorsi == 0) {
			return;
		}

		final ImageView imageViewCorrente = (ImageView) event.getSource();
		final GridPane gridPaneParent = (GridPane) imageViewCorrente.getParent();

		try {
			if (turnoCorrente.getFase().equals(FaseTurno.Attacco)) {

				Posizione posizioneToSearch = new Posizione(GridPane.getColumnIndex(imageViewCorrente),
						GridPane.getRowIndex(imageViewCorrente));

				Carta cartaCliccata = utility.ricercaCartaStrada(gridPaneParent.getId(), posizioneToSearch);

				Personaggio personaggioAttaccato = (Personaggio) cartaCliccata;
				
				//reimposto il tooltip per sicurezza
				utility.impostaTooltip(imageViewCorrente, cartaCliccata);

				Giocatore avversario = turnoService.trovaAltroGiocatore(partita);

				giocatoreService.effettuaAttacco(turnoCorrente, personaggioAttaccato,
						utility.ricercaStradaSchieramento(gridPaneParent),
						turnoService.trovaTorreGiocatore(avversario));

				if (personaggioAttaccato.getVita() <= 0) {
					utility.eliminaImmagineCarta(gridPaneParent, posizioneToSearch, imageViewCorrente);
					
					System.out.println("PERSONAGGIO ATTACCO MORTO");
					
					if (vitaTorreAvversaria.getId().equals("vitaTorre1"))
						this.mostraVitaTorre(avversario, vitaTorreAvversaria, vitaTorre1Indicator);
					else
						this.mostraVitaTorre(avversario, vitaTorreAvversaria, vitaTorre2Indicator);

				} else
					utility.impostaTooltip(imageViewCorrente, cartaCliccata);

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

		//quando si passa l'incantesimo sopra un'immagine
		imageViewCorrente.setOnDragOver(event -> {
			if (event.getDragboard().hasImage()) {
				event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
			}
			event.consume();
		});

		//quando si rilascia l'incantesimo sopra un'immagine
		imageViewCorrente.setOnDragDropped(event -> {
			Dragboard db = event.getDragboard();
			boolean success = false;

			if (db.hasImage()) {

				Posizione posizioneCartaMano = utility.getPosizioneCartaSelezionata()[0];
				Carta cartaMano = utility.ricercaCartaStrada(this.carteMano.getId(),
						posizioneCartaMano);
				
				if(cartaMano instanceof Personaggio) {
					this.labelErrori.setText("STAI TRASCINADO UN PERSONAGGIO SOPRA UN ALTRO");
					return;
				}
				
				Incantesimo incantesimoDaSchierare = (Incantesimo) cartaMano;

				System.out.println("INCANTESIMO " + incantesimoDaSchierare);

				Posizione posizionePersonaggioTarget = new Posizione(GridPane.getColumnIndex(imageViewCorrente),
						GridPane.getRowIndex(imageViewCorrente));
			

				Personaggio personaggioTarget = (Personaggio) utility.ricercaCartaStrada(gridPaneParent.getId(),
						posizionePersonaggioTarget);
				
				//clono l'incantesimo della mano
				Incantesimo incancantesimoDaSchierareClone = null;
				try {
					incancantesimoDaSchierareClone = (Incantesimo) incantesimoDaSchierare.clone();
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
				
				//ricontrollo lo schieramento quando l'incantesimo è "droppato" sull'immagine
				try {
					turnoService.controllaSchieramento(turnoCorrente, incancantesimoDaSchierareClone);
				} catch (ElisirException e) {
					System.err.println(e.getMessage());
					return;
				}

				//metto la prossima carta del mazzo in mano
				ImageView imageViewProssimaCarta = utility.creaImpostaImageView(prossimaCarta.getImage(), dim_img,
						dim_img);
				Posizione posizioneDaRimpiazzare = utility.getPosizioneCartaSelezionata()[0];
				carteMano.getChildren()
						.remove(utility.ricercaImmagineStrada(carteMano.getId(), posizioneDaRimpiazzare));
				utility.aggiungiCartaImmagineGriglia(carteMano, prossimaCartaMazzo[0], imageViewProssimaCarta,
						posizioneDaRimpiazzare);
				utility.impostaTooltip(imageViewProssimaCarta, prossimaCartaMazzo[0]);
				utility.setImageDragProperty(carteMano, imageViewProssimaCarta);
				prossimaCartaMazzo[0] = mazzoService.mostraProssimaCarta(this.giocatoreCorrente.getMazzo());
				prossimaCarta.setImage(prossimaCartaMazzo[0].getImmagineCarta());

				//creo e salvo la mossa di schieramento dell'incantesimo
				MossaGiocatore mossa = this.giocatoreService.effettuaSchieramentoIncantesimo(turnoCorrente,
						incantesimoDaSchierare);

				turnoService.salvaMossaGiocatore(partita, turnoCorrente, mossa);

				//aggiorno l'elisir
				this.mostraElisir();

				//controllo che tipo di incantesimo è, se è di tipo attivo lo salvo altrimenti lo eseguo
				if (incantesimoDaSchierare.getNome().equals(IncantesimiNomi.RendiInvulnerabile.toString())
						|| incantesimoDaSchierare.getNome().equals(IncantesimiNomi.BloccaAttacco.toString()))
					this.incantesimoService.aggiungiIncantesimoAttivo(turnoCorrente, incancantesimoDaSchierareClone,
							personaggioTarget);
				else {
					this.incantesimoService.eseguiIncantesimo(incantesimoDaSchierare, personaggioTarget);
					
					if(personaggioTarget.getVita() <= 0) //nel caso il personaggio sia morto
						utility.eliminaImmagineCarta(gridPaneParent, posizionePersonaggioTarget, imageViewCorrente);
					else
						utility.impostaTooltip(imageViewCorrente, personaggioTarget);

				}

				success = true;
			}

			event.setDropCompleted(success);
			event.consume();
		});
	}

	@FXML
	public void salvaPartitaAction(ActionEvent event) {
		//per prima cosa salviamo il turno e lo stato delle carte corrente
		partitaService.salvaTurnoPartita(turnoCorrente, partita);
		utility.aggiungiStato(utility);

		//calcolo i criteri richiesti per l'ordinamento della partita
		int numeroMosse = partitaService.calcolaNumeroMossePartita(partita);
		int numeroCarteInCampo = utility.calcolaNumeroCarteTerreno();
		int valoreCarteInCampo = utility.calcolaValoreCarteTerreno();

		partitaService.impostaParamentriSalvataggio(partita, numeroMosse, numeroCarteInCampo, valoreCarteInCampo);

		partitaService.salvaPartita(partita);
		
		//mi preparo la mappa da serializzare
		utility.popolaMappaIncantesimi(incantesimoService.getIncantesimiAttivi());
		
		//serializzo
		try {
			GraphicUtility.serializeMappaGridpaneCarte(utility.getMappaGridpaneCarte(),
					String.valueOf(this.partita.getID()));

			GraphicUtility.serializeMappaGridpaneIncantesimi(utility.mappaGridpaneIncantesimi,
					String.valueOf(this.partita.getID()));

		} catch (IOException e) {
			e.printStackTrace();
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
		//esci senza salvare la partita
		try {
			dispatcher.caricaVista("applicationLayout");
			dispatcher.caricaVista("homepage");
		} catch (ViewException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void annullaMossaAction(ActionEvent event) {
		//questo if mi serve per capire in sostanza che sta giocando
		if (this.carteMano.getId().equals(GridPaneGioco.carteManoG2.toString()))
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
		Map<String, LinkedHashMap<Posizione, Carta>> mappaDeserializzata = null;
		Map<String, LinkedHashMap<Incantesimo, Posizione>> mappaIncantesimiDeserializzati = null;
		
		try {
			
			mappaDeserializzata = GraphicUtility.
					deserializeMappaGridpaneCarte(String.valueOf(this.partita.getID()));

			for (String key : mappaDeserializzata.keySet()) {
				LinkedHashMap<Posizione, Carta> innerMap = mappaDeserializzata.get(key);
				for (Posizione p : innerMap.keySet()) {
					Carta carta = innerMap.get(p);
					if (carta != null) {
						carteCampoDeserializzate.add(carta);
					}
				}
			}

			mappaIncantesimiDeserializzati = GraphicUtility
					.deserializeMappaGridpaneIncantesimi(String.valueOf(this.partita.getID()));

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.cartaFactory.reimpostaImmagine(carteCampoDeserializzate);
		List<GridPane> tutte = new ArrayList<>();
		tutte.addAll(gridsListGiocatore);
		tutte.addAll(gridsListAvversario);
		
		//ripristino delle carte sul terreno
		utility.ripristinaStato(tutte, mappaDeserializzata);

		//ripristino degli incantesimi attivi
		for (Map.Entry<String, LinkedHashMap<Incantesimo, Posizione>> entry : mappaIncantesimiDeserializzati
				.entrySet()) {
			String gridPaneKey = entry.getKey();
			LinkedHashMap<Incantesimo, Posizione> innerMap = entry.getValue();

			for (Incantesimo incantesimo : innerMap.keySet()) {
				Posizione p = innerMap.get(incantesimo);
				if (p != null) {
					Personaggio personaggio = (Personaggio) utility.ricercaCartaStrada(gridPaneKey, p);
					incantesimoService.aggiungiIncantesimoAttivo(turnoCorrente, incantesimo, personaggio);
				}
			}
		}

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