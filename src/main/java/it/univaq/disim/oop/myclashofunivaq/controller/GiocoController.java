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
import java.util.Random;

import it.univaq.disim.oop.myclashofunivaq.business.GiocatoreService;
import it.univaq.disim.oop.myclashofunivaq.business.IncantesimoService;
import it.univaq.disim.oop.myclashofunivaq.business.MazzoService;
import it.univaq.disim.oop.myclashofunivaq.business.PartitaService;
import it.univaq.disim.oop.myclashofunivaq.business.PersonaggioService;
import it.univaq.disim.oop.myclashofunivaq.business.TurnoService;
import it.univaq.disim.oop.myclashofunivaq.business.impl.AttaccoException;
import it.univaq.disim.oop.myclashofunivaq.business.impl.ElisirException;
import it.univaq.disim.oop.myclashofunivaq.business.impl.GiocatoreServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.business.impl.GiocatoreUtenteServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.business.impl.IncantesimoServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.business.impl.ManaException;
import it.univaq.disim.oop.myclashofunivaq.business.impl.MazzoServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.business.impl.PartitaServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.business.impl.PersonaggioServiceImpl;
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
import it.univaq.disim.oop.myclashofunivaq.domain.Incantesimo;
import it.univaq.disim.oop.myclashofunivaq.domain.Mazzo;
import it.univaq.disim.oop.myclashofunivaq.domain.MossaGiocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.Partita;
import it.univaq.disim.oop.myclashofunivaq.domain.Personaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.PosizionamentoPersonaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.Tank;
import it.univaq.disim.oop.myclashofunivaq.domain.Torre;
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
import javafx.stage.WindowEvent;
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
	private final IncantesimoService incantesimoService;
	private final PersonaggioService personaggioService;

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
		this.giocatoreService = new GiocatoreServiceImpl();
		this.cartaFactory = Factory.getInstance();
		this.incantesimoService = new IncantesimoServiceImpl();
		this.personaggioService = new PersonaggioServiceImpl();
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

				if (turnoCorrente.getNumero() != 0) {
					this.mostraVitaTorre(this.turnoService.trovaAltroGiocatore(partita), vitaTorre2,
							vitaTorre2Indicator);
					this.vitaTorreAvversaria = vitaTorre2;
				}

			} else {
				this.carteMano = carteManoG2;
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

				if (!presente) {
					img.setOnMouseClicked(this::proprietaClickImageViewAvversario);
					this.dragAndDropIncantesimo(img);
				} else
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

				if (turnoCorrente.getNumero() != 0) {
					this.mostraVitaTorre(this.turnoService.trovaAltroGiocatore(partita), vitaTorre2,
							vitaTorre2Indicator);
					this.vitaTorreAvversaria = vitaTorre2;
				}

			} else {
				this.carteMano = carteManoG2;
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

					boolean presente = this.gridsList.stream().anyMatch(gridPane -> idParent.equals(gridPane.getId()));

					if (!presente) {
						img.setOnMouseClicked(this::proprietaClickImageViewAvversario);
						this.dragAndDropIncantesimo(img);
					} else
						img.setOnMouseClicked(this::proprietaClickImageViewGiocatore);
					
					Posizione posizioneToSearch = new Posizione(GridPane.getColumnIndex(img),
							GridPane.getRowIndex(img));
					
					Personaggio personaggio = (Personaggio) utility.ricercaCartaStrada(idParent, posizioneToSearch);
					utility.impostaTooltip(img, personaggio);
					try {
						this.personaggioService.eseguiMossaSpeciale(personaggio);
					} catch (ManaException e) {
						System.err.println(e.getMessage());
					}
					
				}
			}

			if (turnoService.isFirstTurno(turnoCorrente) || GraphicUtility.getStati().size() <= 1) {

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
							System.err.println("stai schierando un incatesimo in strada");
							throw new RuntimeException();
						}

						utility.aggiungiCartaImmagineGriglia(grid, cartaSchierata[0], null); // aggiunta nella
																								// strada

					} catch (ElisirException e) {
						System.out.println(e.getMessage());
						throw new RuntimeException();
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}

					utility.aggiungiCartaImmagineGriglia(grid, null, newImageView);

					newImageView.setOnMouseClicked(this::proprietaClickImageViewGiocatore);

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

						// personaggioSchierato.getMossaSpeciale().esegui(personaggioSchierato);

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

			this.incantesimoService.checkAnnullaEffettoIncantesimi();
			this.personaggioService.resetMosseSpecialiAttive();

			dispatcher.caricaVista("gioco", partita);
		} catch (ViewException e) {
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

	private String formatVitaTorre(double value) {
		DecimalFormat df = new DecimalFormat("#.#");
		return df.format(value * 100);
	}

	private void proprietaClickImageViewGiocatore(MouseEvent event) {

		final ImageView imageViewCorrente = (ImageView) event.getSource();
		final GridPane gridPaneParent = (GridPane) imageViewCorrente.getParent();

		Posizione posizioneToSearch = new Posizione(GridPane.getColumnIndex(imageViewCorrente),
				GridPane.getRowIndex(imageViewCorrente));
		cartaSchierata[0] = utility.ricercaCartaStrada(gridPaneParent.getId(), posizioneToSearch);

		Personaggio personaggioCliccato = (Personaggio) cartaSchierata[0];

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
				try {
					if (turnoCorrente.getNumero() > 0) {
						GridPaneGioco gridPersonaggio = utility.ricercaStradaSchieramento(gridPaneParent);
						if (utility.checkAttaccoTorre(gridPersonaggio.toString())) {

							Giocatore avversario = turnoService.trovaAltroGiocatore(partita);
							Torre torreAvversaria = turnoService.trovaTorreGiocatore(avversario);

							giocatoreService.attaccaTorre(turnoCorrente, personaggioCliccato, gridPersonaggio,
									torreAvversaria);

							if (vitaTorreAvversaria.getId().equals("vitaTorre1"))
								this.mostraVitaTorre(avversario, vitaTorreAvversaria, vitaTorre1Indicator);
							else
								this.mostraVitaTorre(avversario, vitaTorreAvversaria, vitaTorre2Indicator);

						} else
							giocatoreService.preparaAttacco(personaggioCliccato, gridPersonaggio);
					} else
						throw new AttaccoException("NON SI può ATTACCARE è IL PRIMO TURNO");
				} catch (AttaccoException e) {
					System.err.println(e.getMessage());
				}
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

				Personaggio personaggioAttaccato = (Personaggio) cartaCliccata;

				utility.impostaTooltip(imageViewCorrente, cartaCliccata);

				Giocatore avversario = turnoService.trovaAltroGiocatore(partita);

				giocatoreService.effettuaAttacco(turnoCorrente, personaggioAttaccato,
						utility.ricercaStradaSchieramento(gridPaneParent),
						turnoService.trovaTorreGiocatore(avversario));

				if (personaggioAttaccato.getVita() <= 0) {
					utility.eliminaImmagineCarta(gridPaneParent, posizioneToSearch, imageViewCorrente);

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
		}

	}

	private void dragAndDropIncantesimo(ImageView imageViewTarget) {
		final ImageView imageViewCorrente = imageViewTarget;
		final GridPane gridPaneParent = (GridPane) imageViewCorrente.getParent();

		imageViewCorrente.setOnDragOver(event2 -> {
			if (event2.getDragboard().hasImage()) {
				event2.acceptTransferModes(TransferMode.COPY_OR_MOVE);
			}
			event2.consume();
		});

		imageViewCorrente.setOnDragDropped(event2 -> {
			Dragboard db2 = event2.getDragboard();
			boolean success2 = false;

			if (db2.hasImage()) {

				Posizione posizioneCartaMano = utility.getPosizioneCartaSelezionata()[0];
				Incantesimo incantesimoDaSchierare = (Incantesimo) utility.ricercaCartaStrada(this.carteMano.getId(),
						posizioneCartaMano);

				System.out.println("INCANTESIMO " + incantesimoDaSchierare);

				Posizione posizionePersonaggioTarget = new Posizione(GridPane.getColumnIndex(imageViewCorrente),
						GridPane.getRowIndex(imageViewCorrente));

				Personaggio personaggioCliccato = (Personaggio) utility.ricercaCartaStrada(gridPaneParent.getId(),
						posizionePersonaggioTarget);

				Incantesimo incancantesimoDaSchierareClone = null;
				try {
					incancantesimoDaSchierareClone = (Incantesimo) incantesimoDaSchierare.clone();
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				MossaGiocatore mossa = this.giocatoreService.effettuaSchieramentoIncantesimo
						(turnoCorrente, incantesimoDaSchierare);
				
				turnoService.salvaMossaGiocatore(partita, turnoCorrente, mossa);
				
				this.mostraElisir();
				
				if (incantesimoDaSchierare.getNome().equals("RendiInvulnerabile"))
					this.incantesimoService.aggiungiIncantesimoAttivo(turnoCorrente, incancantesimoDaSchierareClone,
							personaggioCliccato);
				else {
					this.incantesimoService.eseguiIncantesimo(incantesimoDaSchierare, personaggioCliccato);
					utility.impostaTooltip(imageViewCorrente, personaggioCliccato);
				}

				success2 = true;
			}

			event2.setDropCompleted(success2);
			event2.consume();
		});

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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.cartaFactory.reimpostaImmagine(carteCampoDeserializzate);
		List<GridPane> tutte = new ArrayList<>();
		tutte.addAll(gridsListGiocatore);
		tutte.addAll(gridsListAvversario);

		utility.ripristinaStato(tutte, mappa);
	}
	
}