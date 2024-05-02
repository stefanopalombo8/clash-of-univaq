package it.univaq.disim.oop.myclashofunivaq.controller.utilities;

import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import it.univaq.disim.oop.myclashofunivaq.business.ResetStaticVariables;
import it.univaq.disim.oop.myclashofunivaq.domain.Carta;
import it.univaq.disim.oop.myclashofunivaq.domain.CuraPersonaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.Fulmine;
import it.univaq.disim.oop.myclashofunivaq.domain.Furia;
import it.univaq.disim.oop.myclashofunivaq.domain.Incantesimo;
import it.univaq.disim.oop.myclashofunivaq.domain.Personaggio;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;

public class GraphicUtility implements ResetStaticVariables, Serializable {
	
	// mappe che mi aiutano nel avere sotto controllo tutte le immagini e le carte
	private Map<String, LinkedHashMap<Posizione, ImageView>> mappaGridpaneImmagini = new HashMap<>();
	private Map<String, LinkedHashMap<Posizione, Carta>> mappaGridpaneCarte = new HashMap<>();
	private Map<String, LinkedHashMap<Posizione, Carta>> mappaGridpaneCarteBackup = new HashMap<>();
	
	public Map<String, LinkedHashMap<Incantesimo, Posizione>> mappaGridpaneIncantesimi = new HashMap<>();

	private Posizione[] posizioneCartaSelezionata = new Posizione[1];

	private static List<GraphicUtility> stati = new ArrayList<>();
	private static int i = 0;

	private List<ImageView> nuoveImmagini = new ArrayList<>();
	
	/* questi 2 path mi servono per serializzare le carte (personaggi) 
	 * uso un file per ogni partita a cui associo due file (una mappa personaggi e una mappa incantesimi)
	 * perché con un unico file ho problemi a recuperare le informazioni
	 */
	private static String path = "src/main/resourses/files/partiteSalvate/carte";
	private static String path2 = "src/main/resourses/files/partiteSalvate/incantesimi";
	
	public Map<String, LinkedHashMap<Posizione, ImageView>> getMappaGridpaneImmagini() {
		return mappaGridpaneImmagini;
	}

	public Map<String, LinkedHashMap<Posizione, Carta>> getMappaGridpaneCarte() {
		return mappaGridpaneCarte;
	}

	public Posizione[] getPosizioneCartaSelezionata() {
		return posizioneCartaSelezionata;
	}

	public void setMappaGridpaneImmagini(Map<String, LinkedHashMap<Posizione, ImageView>> mappaGridpaneImmagini) {
		this.mappaGridpaneImmagini = mappaGridpaneImmagini;
	}

	public void setMappaGridpaneCarte(Map<String, LinkedHashMap<Posizione, Carta>> mappaGridpaneCarte) {
		this.mappaGridpaneCarte = mappaGridpaneCarte;
	}

	public List<ImageView> getNuoveImmagini() {
		return nuoveImmagini;
	}

	public static List<GraphicUtility> getStati() {
		return stati;
	}

	public static void serializeMappaGridpaneCarte(Map<String, LinkedHashMap<Posizione, Carta>> mappaGridpaneCarte,
			String index) throws IOException {
		String path = GraphicUtility.path + index + ".txt";
		try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(path))) {
			outputStream.writeObject(mappaGridpaneCarte);
		}
	}

	public static Map<String, LinkedHashMap<Posizione, Carta>> deserializeMappaGridpaneCarte(String index)
			throws IOException, ClassNotFoundException {
		String path = GraphicUtility.path + index + ".txt";
		try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(path))) {
			return (Map<String, LinkedHashMap<Posizione, Carta>>) inputStream.readObject();
		}
	}
	
	public static void serializeMappaGridpaneIncantesimi(Map<String, LinkedHashMap<Incantesimo, Posizione>> mappaGridpaneIncantesimi,
			String index) throws IOException {
		String path = GraphicUtility.path2 + index + ".txt";
		try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(path))) {
			outputStream.writeObject(mappaGridpaneIncantesimi);
		}
	}

	public static Map<String, LinkedHashMap<Incantesimo, Posizione>> deserializeMappaGridpaneIncantesimi(String index)
			throws IOException, ClassNotFoundException {
		String path = GraphicUtility.path2 + index + ".txt";
		try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(path))) {
			return (Map<String, LinkedHashMap<Incantesimo, Posizione>>) inputStream.readObject();
		}
	}
	
	public void impostaRetroCarte(GridPane grid) {
		Image image = new Image("/viste/images/retroCarte.jpg");
		for(int i = 0; i < 4; i++) {
			ImageView imageView = this.creaImpostaImageView(image, 60, 50);
			grid.add(imageView, i, 0);
			GridPane.setHalignment(imageView, HPos.CENTER); // Allineamento orizzontale al centro
	        GridPane.setValignment(imageView, VPos.CENTER); // Allineamento verticale al centro
		}
	}


	public void aggiungiStato(GraphicUtility stato) {
		stati.add(stato);
		i++;
	}

	public GraphicUtility getUltimoStatoGiocatore() {
		return stati.get(i - 2);
	}

	public GraphicUtility getUltimoStato() {
		return stati.get(i - 1);
	}

	public void sovrascriviUltimoStato(GraphicUtility stato) {
		int index = stati.size() - 1;
		stati.remove(index);
		stati.add(index, stato);
	}

	public ImageView creaImpostaImageView(Image image, double height, double width) {
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(height);
		imageView.setFitWidth(width);

		return imageView;
	}

	public void impostaTooltip(ImageView img, Carta carta) {
		Tooltip tooltip = new Tooltip("NOME: " + carta.getNome() + "\n" + "COSTO: " + carta.getCostoSchieramento() + "\n");
		if (carta instanceof Personaggio) {
			Personaggio p = (Personaggio) carta;
			tooltip.setText(tooltip.getText() + "VITA " + p.getVita() + "\n" + "MANA: " + p.getMana() + "\n" + "DANNO: " + p.getDanno()
					+ "\n" + "ARMOR: " + p.getArmatura() + "\n" + "POSIZIONE: " + p.getPosizionamento());
		}
		else {
			Incantesimo i = (Incantesimo) carta;
			switch(i.getNome()) {
			case "Furia": 
				Furia f = (Furia) i;
				tooltip.setText(tooltip.getText() + "AUMENTO ATTACCO: " + f.getAumento());
				break;
			case "Fulmine": 
				Fulmine fu = (Fulmine) i;
				tooltip.setText(tooltip.getText() + "DANNO: " + fu.getDanno());
				break;
			case "CuraPersonaggio":
				CuraPersonaggio cp = (CuraPersonaggio) i;
				tooltip.setText(tooltip.getText() + "CURA: " + cp.getCura());
				break;
			}
		}

		Tooltip.install(img, tooltip);
	}

	public void setImageDragProperty(GridPane source, ImageView imageView) {
		imageView.setOnDragDetected(event -> {
			Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
			ClipboardContent content = new ClipboardContent();
			content.putImage(imageView.getImage());
			db.setContent(content);
			event.consume();

			Posizione posizione = null;
			Parent parent = imageView.getParent();
			if (parent instanceof GridPane)
				posizione = new Posizione(GridPane.getColumnIndex(imageView), GridPane.getRowIndex(imageView));

			posizioneCartaSelezionata[0] = posizione;
			
		});
	}

	/*
	 * predisposizione per ogni griglia di un numero di posizioni congruo al numero
	 * di righe e colonne
	 */
	public void mappingGriglie(List<GridPane> grids) {

		for (GridPane grid : grids) {
			LinkedHashMap<Posizione, ImageView> mappaImmagini = new LinkedHashMap<>();
			LinkedHashMap<Posizione, Carta> mappaCarte = new LinkedHashMap<>();
			LinkedHashMap<Posizione, Carta> mappaCarteBackup = new LinkedHashMap<>();

			int num_colonne = grid.getColumnConstraints().size();
			int num_righe = grid.getRowConstraints().size();

			for (int i = 0; i < num_righe; i++) {
				for (int j = 0; j < num_colonne; j++) {
					Posizione p = new Posizione(j, i);
					mappaImmagini.put(p, null);
					mappaCarte.put(p, null);
					mappaCarteBackup.put(p, null);
				}

			}

			mappaGridpaneImmagini.put(grid.getId(), mappaImmagini);
			mappaGridpaneCarte.put(grid.getId(), mappaCarte);
			mappaGridpaneCarteBackup.put(grid.getId(), mappaCarteBackup);
		}

	}

	/*
	 * inserimento di un immagine in una particolare griglia nella prima posizione
	 * libera
	 */

	public void aggiungiCartaImmagineGriglia(GridPane grid, Carta carta, ImageView imageView) {

		if (carta != null) {
			for (Map.Entry<String, LinkedHashMap<Posizione, Carta>> entry : mappaGridpaneCarte.entrySet()) {
				String gridPaneKey = entry.getKey();

				if (gridPaneKey.equals(grid.getId())) {
					LinkedHashMap<Posizione, Carta> innerMap = entry.getValue();

					for (Posizione p : innerMap.keySet()) {
						if (innerMap.get(p) == null) {
							innerMap.replace(p, carta);
							break;
						}
					}

					break;
				}

			}
		}

		if (imageView != null) {
			for (Map.Entry<String, LinkedHashMap<Posizione, ImageView>> entry : mappaGridpaneImmagini.entrySet()) {
				String gridPaneKey = entry.getKey();

				if (gridPaneKey.equals(grid.getId())) {
					LinkedHashMap<Posizione, ImageView> innerMap = entry.getValue();

					for (Posizione p : innerMap.keySet()) {
						if (innerMap.get(p) == null) {
							innerMap.replace(p, imageView);
							grid.add(imageView, p.getColonna(), p.getRiga());
							break;
						}
					}

					break;
				}

			}
		}

	}
	// metodo per ricercare una carta data la strada e la sua posizione
	public Carta ricercaCartaStrada(String gridPaneSource, Posizione posizione) {
		Carta carta = null;
		
		for (Map.Entry<String, LinkedHashMap<Posizione, Carta>> entry : mappaGridpaneCarte.entrySet()) {
			String gridPaneKey = entry.getKey();

			if (gridPaneKey.equals(gridPaneSource)) {

				LinkedHashMap<Posizione, Carta> innerMap = entry.getValue();

				for (Posizione p : innerMap.keySet()) {
					if (p.getRiga() == posizione.getRiga() && p.getColonna() == posizione.getColonna()) {
						carta = innerMap.get(p);
						break;
					}
				}

				break;
			}

		}

		return carta;
	}
	
	// metodo per ricercare un'immagine data la strada e la sua posizione
	public ImageView ricercaImmagineStrada(String gridPaneSource, Posizione posizione) {
		ImageView imageView = null;
		
		for (Map.Entry<String, LinkedHashMap<Posizione, ImageView>> entry : mappaGridpaneImmagini.entrySet()) {
			String gridPaneKey = entry.getKey();

			if (gridPaneKey.equals(gridPaneSource)) {

				LinkedHashMap<Posizione, ImageView> innerMap = entry.getValue();

				for (Posizione p : innerMap.keySet()) {
					if (p.getRiga() == posizione.getRiga() && p.getColonna() == posizione.getColonna()) {
						imageView = innerMap.get(p);
						break;
					}
				}

				break;
			}

		}

		return imageView;
	}

	/*
	 * inserimento di un immagine in una particolare griglia e in una particolare
	 * posizione (quando si schiera una carta dalla mano si libera una posizione)
	 */

	public void aggiungiCartaImmagineGriglia(GridPane grid, Carta carta, ImageView imageView, Posizione posizione) {
		if (carta != null) {
			for (Map.Entry<String, LinkedHashMap<Posizione, Carta>> entry : mappaGridpaneCarte.entrySet()) {
				String gridPaneKey = entry.getKey();

				if (gridPaneKey.equals(grid.getId())) {
					LinkedHashMap<Posizione, Carta> innerMap = entry.getValue();

					for (Posizione p : innerMap.keySet()) {
						if (p.getColonna() == posizione.getColonna()) {
							innerMap.replace(p, carta);
							break;
						}
					}

					break;
				}

			}
		}

		if (imageView != null) {
			for (Map.Entry<String, LinkedHashMap<Posizione, ImageView>> entry : mappaGridpaneImmagini.entrySet()) {
				String gridPaneKey = entry.getKey();

				if (gridPaneKey.equals(grid.getId())) {
					LinkedHashMap<Posizione, ImageView> innerMap = entry.getValue();

					for (Posizione p : innerMap.keySet()) {
						if (p.getColonna() == posizione.getColonna()) {
							innerMap.replace(p, imageView);
							grid.add(imageView, posizione.getColonna(), 0);
							break;
						}
					}

					break;
				}

			}
		}

	}
	// metodo per prendermi la enum associata alla grid per uno schieramento
	public GridPaneGioco ricercaStradaSchieramento(GridPane grid) {
		Optional<GridPaneGioco> optionalName = Arrays.stream(GridPaneGioco.values())
				.filter(n -> n.toString().equals(grid.getId())).findAny();

		return optionalName.get();
	}
	
	/* questo è uno dei metodi più importanti perché mi consente di mi portarmi dietro le carte
	 *  ad ogni nuova istanza del controller (cioè ad ogni nuovo turno)
	 */
	public void ripristinaStato(List<GridPane> grids) {

		for (Map.Entry<String, LinkedHashMap<Posizione, Carta>> entry : this.getUltimoStato().mappaGridpaneCarte
				.entrySet()) {

			String gridPaneKey = entry.getKey();
			LinkedHashMap<Posizione, Carta> innerMap = entry.getValue();

			if (gridPaneKey.equals(GridPaneGioco.carteManoG1.toString())
					|| gridPaneKey.equals(GridPaneGioco.carteManoG2.toString()))
				continue;

			for (GridPane grid : grids) {
				if (gridPaneKey.equals(grid.getId())) {
					for (Posizione p : innerMap.keySet()) {
						Carta carta = innerMap.get(p);
						if (carta != null) {
							LinkedHashMap<Posizione, Carta> currentInnerMap = this.mappaGridpaneCarte.get(gridPaneKey);
							currentInnerMap.replace(this.ricercaNewPosizione(p, currentInnerMap), carta);

							if (carta instanceof Personaggio) {
								Personaggio personaggio = (Personaggio) carta;
								personaggio.setMana(personaggio.getMana() + 1);
							}
						}
					}
				}
			}
		}

		this.copyMappaCarta(grids);

		// RIPRISTINO DELLE IMMAGINI SUL TERRENO

		for (Map.Entry<String, LinkedHashMap<Posizione, ImageView>> entry : getUltimoStato().getMappaGridpaneImmagini()
				.entrySet()) {

			String gridPaneKey = entry.getKey();
			LinkedHashMap<Posizione, ImageView> innerMap = entry.getValue();

			if (gridPaneKey.equals(GridPaneGioco.carteManoG1.toString())
					|| gridPaneKey.equals(GridPaneGioco.carteManoG2.toString()))
				continue;

			for (GridPane grid : grids) {
				if (gridPaneKey.equals(grid.getId())) {
					for (Posizione p : innerMap.keySet()) {
						ImageView oldImageView = innerMap.get(p);
						if (oldImageView != null) {
							ImageView newImageView = this.creaImpostaImageView(oldImageView.getImage(),
									oldImageView.getFitHeight(), oldImageView.getFitWidth());

							LinkedHashMap<Posizione, ImageView> currentInnerMap = this.mappaGridpaneImmagini
									.get(gridPaneKey);
							newImageView.setRotate(oldImageView.getRotate());
							this.nuoveImmagini.add(newImageView);

							currentInnerMap.replace(this.ricercaNewPosizione(p, currentInnerMap), newImageView);
							grid.add(newImageView, p.getColonna(), p.getRiga());

							this.impostaTooltip(newImageView, this.ricercaCartaStrada(grid.getId(), p));

						}
					}
				}
			}
		}

	}

	// stessa cosa del ripristino ma per la mano
	public void ripristinaCarteMano(GridPane gridManoCorrente) {
		LinkedHashMap<Posizione, ImageView> oldInnerMap = this.getUltimoStatoGiocatore().mappaGridpaneImmagini
				.get(gridManoCorrente.getId());
		LinkedHashMap<Posizione, ImageView> currentInnerMap = this.mappaGridpaneImmagini.get(gridManoCorrente.getId());

		for (Posizione p : oldInnerMap.keySet()) {
			ImageView oldImageView = oldInnerMap.get(p);
			if (oldImageView != null) {
				ImageView newImageView = this.creaImpostaImageView(oldImageView.getImage(), oldImageView.getFitHeight(),
						oldImageView.getFitWidth());

				this.setImageDragProperty(gridManoCorrente, newImageView);

				currentInnerMap.replace(this.ricercaNewPosizione(p, currentInnerMap), newImageView);
				gridManoCorrente.add(newImageView, p.getColonna(), p.getRiga());

			}
		}

		LinkedHashMap<Posizione, Carta> oldInnerMapCarte = this.getUltimoStatoGiocatore().mappaGridpaneCarte
				.get(gridManoCorrente.getId());
		LinkedHashMap<Posizione, Carta> currentInnerMapCarte = this.mappaGridpaneCarte.get(gridManoCorrente.getId());

		for (Posizione p : oldInnerMapCarte.keySet()) {
			Carta oldCarta = oldInnerMapCarte.get(p);
			if (oldCarta != null) {
				currentInnerMapCarte.replace(this.ricercaNewPosizione(p, currentInnerMapCarte), oldCarta);
			}
		}

	}

	/* anche questo è fondamentale perché ad ogni istanza di questa classe 
	 * gli oggetti posizione sono sempre nuovi, quindi con questo trovo sempre
	 * la posizione corretta delle carte (si potrebbe ridefinire anche l'equals
	 * nella classe Posizione)
	 */
	public Posizione ricercaNewPosizione(Posizione toSearch, LinkedHashMap<Posizione, ?> innerMap) {
		Posizione toReturn = null;

		for (Posizione p : innerMap.keySet()) {
			// equals tra posizioni
			if (p.getColonna() == toSearch.getColonna() && p.getRiga() == toSearch.getRiga()) {
				toReturn = p;
				break;
			}
		}

		return toReturn;
	}

	public int calcolaNumeroCarteTerreno() {
		int numero = 0;

		for (Map.Entry<String, LinkedHashMap<Posizione, Carta>> entry : this.mappaGridpaneCarte.entrySet()) {

			String gridPaneKey = entry.getKey();
			LinkedHashMap<Posizione, Carta> innerMap = entry.getValue();

			if (gridPaneKey.equals(GridPaneGioco.carteManoG1.toString())
					|| gridPaneKey.equals(GridPaneGioco.carteManoG2.toString()))
				continue;

			for (Posizione p : innerMap.keySet()) {
				Carta carta = innerMap.get(p);
				if (carta != null)
					numero += 1;
			}
		}
		return numero;
	}

	public int calcolaValoreCarteTerreno() {
		int valore = 0;

		for (Map.Entry<String, LinkedHashMap<Posizione, Carta>> entry : this.mappaGridpaneCarte.entrySet()) {

			String gridPaneKey = entry.getKey();
			LinkedHashMap<Posizione, Carta> innerMap = entry.getValue();

			if (gridPaneKey.equals(GridPaneGioco.carteManoG1.toString())
					|| gridPaneKey.equals(GridPaneGioco.carteManoG2.toString()))
				continue;

			for (Posizione p : innerMap.keySet()) {
				Carta carta = innerMap.get(p);
				if (carta != null)
					valore += carta.getCostoSchieramento();

			}
		}
		return valore;
	}

	@Override
	public void reset() {
		i = 0;
		stati.clear();
	}
	
	// ripristino dello stato dopo la deserializzazione
	public void ripristinaStato(List<GridPane> grids,
			Map<String, LinkedHashMap<Posizione, Carta>> mappaDeserializzata) {

		// RIPRISTINO DELLE IMMAGINI SUL TERRENO

		for (Map.Entry<String, LinkedHashMap<Posizione, Carta>> entry : mappaDeserializzata.entrySet()) {

			String gridPaneKey = entry.getKey();
			LinkedHashMap<Posizione, Carta> innerMap = entry.getValue();

			for (GridPane gridDaPopolare : grids) {
				if (gridPaneKey.equals(gridDaPopolare.getId())) {
					for (Posizione p : innerMap.keySet()) {
						Carta carta = innerMap.get(p);
						if (carta != null) {
							ImageView newImageView = this.creaImpostaImageView(carta.getImmagineCarta(), 60, 60);

							if (gridDaPopolare.getId().equals(GridPaneGioco.carteManoG1.toString())
									|| gridDaPopolare.getId().equals(GridPaneGioco.carteManoG2.toString())) {
								this.setImageDragProperty(gridDaPopolare, newImageView);
							} else
								this.nuoveImmagini.add(newImageView);

							gridDaPopolare.add(newImageView, p.getColonna(), p.getRiga());

							LinkedHashMap<Posizione, ImageView> currentInnerMap = this.mappaGridpaneImmagini
									.get(gridDaPopolare.getId());
							currentInnerMap.replace(this.ricercaNewPosizione(p, currentInnerMap), newImageView);

						}
					}
				}
			}

			this.setMappaGridpaneCarte(mappaDeserializzata);
		}

	}
	
	
	
	public void ripristinaDopoAnnullamento(List<GridPane> grids, List<GridPane> gridsAvversario) {


		if (stati.size() > 0) {
			for (Map.Entry<String, LinkedHashMap<Posizione, Carta>> entry : this.mappaGridpaneCarteBackup.entrySet()) {

				String gridPaneKey = entry.getKey();
				LinkedHashMap<Posizione, Carta> innerMap = entry.getValue();

				if (gridPaneKey.equals(GridPaneGioco.carteManoG1.toString())
						|| gridPaneKey.equals(GridPaneGioco.carteManoG2.toString()))
					continue;

				for (GridPane grid : gridsAvversario) {
					if (gridPaneKey.equals(grid.getId())) {
						for (Posizione p : innerMap.keySet()) {
							Carta carta = innerMap.get(p);
							if (carta != null) {
								Carta nuovaCarta = null;
								try {
									nuovaCarta = (Carta) carta.clone();
								} catch (CloneNotSupportedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								LinkedHashMap<Posizione, Carta> currentInnerMap = this.mappaGridpaneCarte
										.get(gridPaneKey);
								currentInnerMap.replace(this.ricercaNewPosizione(p, currentInnerMap), nuovaCarta);
							}
						}
					}
				}
			}
		}

	}
	
	// ripristino dopo aver annullato le mosse
	public void ripristinaCarteManoDopoAnnullamento(GridPane gridManoCorrente) {
		
		LinkedHashMap<Posizione, ImageView> oldInnerMap = this.getUltimoStatoGiocatore().mappaGridpaneImmagini
				.get(gridManoCorrente.getId());
		LinkedHashMap<Posizione, ImageView> currentInnerMap = this.mappaGridpaneImmagini.get(gridManoCorrente.getId());

		for (Posizione p : oldInnerMap.keySet()) {
			ImageView oldImageView = oldInnerMap.get(p);
			if (oldImageView != null) {
				ImageView newImageView = this.creaImpostaImageView(oldImageView.getImage(), oldImageView.getFitHeight(),
						oldImageView.getFitWidth());

				this.setImageDragProperty(gridManoCorrente, newImageView);

				currentInnerMap.replace(this.ricercaNewPosizione(p, currentInnerMap), newImageView);
				gridManoCorrente.add(newImageView, p.getColonna(), p.getRiga());

			}
		}

		LinkedHashMap<Posizione, Carta> oldInnerMapCarte = this.getUltimoStatoGiocatore().mappaGridpaneCarte
				.get(gridManoCorrente.getId());
		LinkedHashMap<Posizione, Carta> currentInnerMapCarte = this.mappaGridpaneCarte.get(gridManoCorrente.getId());

		for (Posizione p : oldInnerMapCarte.keySet()) {
			Carta oldCarta = oldInnerMapCarte.get(p);
			if (oldCarta != null) {
				currentInnerMapCarte.replace(this.ricercaNewPosizione(p, currentInnerMapCarte), oldCarta);
			}
		}

	}
	
	// copia profonda della mappa carte
	private void copyMappaCarta(List<GridPane> grids) {
		for (Map.Entry<String, LinkedHashMap<Posizione, Carta>> entry : this.getUltimoStato().mappaGridpaneCarte
				.entrySet()) {

			String gridPaneKey = entry.getKey();
			LinkedHashMap<Posizione, Carta> innerMap = entry.getValue();

			if (gridPaneKey.equals(GridPaneGioco.carteManoG1.toString())
					|| gridPaneKey.equals(GridPaneGioco.carteManoG2.toString()))
				continue;

			for (GridPane grid : grids) {
				if (gridPaneKey.equals(grid.getId())) {
					for (Posizione p : innerMap.keySet()) {
						Carta carta = innerMap.get(p);
						if (carta != null) {
							Carta cartaCopy = null;
							try {
								cartaCopy = (Carta) carta.clone();
							} catch (CloneNotSupportedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							LinkedHashMap<Posizione, Carta> currentInnerMap = this.mappaGridpaneCarteBackup
									.get(grid.getId());
							currentInnerMap.put(this.ricercaNewPosizione(p, currentInnerMap), cartaCopy);

						}
					}
				}
			}

		}
	}
	
	// controllo per vedere se ci sono personaggi davanti all'attaccante
	public boolean checkAttaccoTorre(String stradaAttaccante) {
		for (Map.Entry<String, LinkedHashMap<Posizione, Carta>> entry : this.mappaGridpaneCarte.entrySet()) {
			String gridPaneKey = entry.getKey();
			LinkedHashMap<Posizione, Carta> innerMap = entry.getValue();

			if (!((stradaAttaccante.toString() + "avversario").equals(gridPaneKey)
					|| (gridPaneKey + "avversario").equals(stradaAttaccante.toString()))) {
				continue;
			}

			for (Posizione p : innerMap.keySet()) {
				Carta carta = innerMap.get(p);
				if (carta != null)
					return false;
			}

		}

		return true;
	}
	
	//quando un personaggio muore uso questo per eliminare l'immagine e la carta
	public void eliminaImmagineCarta(GridPane grid, Posizione posizione, ImageView imageViewDaEliminare) {
		if(grid.getChildren().contains(imageViewDaEliminare))
			grid.getChildren().remove(imageViewDaEliminare);
		
		for (Map.Entry<String, LinkedHashMap<Posizione, ImageView>> entry : this.mappaGridpaneImmagini.entrySet()) {
			String gridPaneKey = entry.getKey();
			LinkedHashMap<Posizione, ImageView> innerMap = entry.getValue();
			
			if(gridPaneKey.equals(grid.getId()))
				innerMap.replace(this.ricercaNewPosizione(posizione, innerMap), null);
		}
		
		for (Map.Entry<String, LinkedHashMap<Posizione, Carta>> entry : this.mappaGridpaneCarte.entrySet()) {
			String gridPaneKey = entry.getKey();
			LinkedHashMap<Posizione, Carta> innerMap = entry.getValue();
			
			if(gridPaneKey.equals(grid.getId()))
				innerMap.replace(this.ricercaNewPosizione(posizione, innerMap), null);

		}
		
	}
	
	public Posizione getPosizionePersonaggio(Personaggio personaggio) {
		for (Map.Entry<String, LinkedHashMap<Posizione, Carta>> entry : this.mappaGridpaneCarte.entrySet()) {

			String gridPaneKey = entry.getKey();
			LinkedHashMap<Posizione, Carta> innerMap = entry.getValue();

			if (gridPaneKey.equals(GridPaneGioco.carteManoG1.toString())
					|| gridPaneKey.equals(GridPaneGioco.carteManoG2.toString()))
				continue;

			for (Posizione p : innerMap.keySet()) {
				Personaggio personaggioMappa = (Personaggio) innerMap.get(p);
				if (personaggioMappa != null) {
					if(personaggioMappa.equals(personaggio))
						return p;
				}
					

			}
		}
		
		
		return null;
	}
	
	public String getStradaPersonaggio(Personaggio personaggio) {
		for (Map.Entry<String, LinkedHashMap<Posizione, Carta>> entry : this.mappaGridpaneCarte.entrySet()) {

			String gridPaneKey = entry.getKey();
			LinkedHashMap<Posizione, Carta> innerMap = entry.getValue();

			if (gridPaneKey.equals(GridPaneGioco.carteManoG1.toString())
					|| gridPaneKey.equals(GridPaneGioco.carteManoG2.toString()))
				continue;

			for (Posizione p : innerMap.keySet()) {
				Personaggio personaggioMappa = (Personaggio) innerMap.get(p);
				if (personaggioMappa != null) {
					if(personaggioMappa.equals(personaggio))
						return gridPaneKey;
				}
					

			}
		}
		
		
		return null;
	}
	
	// prima di serializzare mi costruisco questa mappa che mi servirà dopo per recuperare gl incantesimi
	public void popolaMappaIncantesimi(List<Incantesimo> listIncantesimiAttivi) {
		String stradaPersonaggio = null;
		LinkedHashMap<Incantesimo, Posizione> innerMap = new LinkedHashMap<>();
		for(Incantesimo incantesimo : listIncantesimiAttivi) {
			stradaPersonaggio = this.getStradaPersonaggio(incantesimo.getPersonaggioTarget());
			Posizione posizionePersonaggio = this.getPosizionePersonaggio(incantesimo.getPersonaggioTarget());
			
			innerMap.put(incantesimo, posizionePersonaggio);	
		}
		
		this.mappaGridpaneIncantesimi.put(stradaPersonaggio, innerMap);
	}
	
	public List<Personaggio> cercaCarteStrada(String strada, Personaggio personaggioChiamante) {
		List<Personaggio> list = new ArrayList<>();
		
		for (Map.Entry<String, LinkedHashMap<Posizione, Carta>> entry : this.mappaGridpaneCarte.entrySet()) {

			String gridPaneKey = entry.getKey();
			LinkedHashMap<Posizione, Carta> innerMap = entry.getValue();

			if (gridPaneKey.equals(GridPaneGioco.carteManoG1.toString())
					|| gridPaneKey.equals(GridPaneGioco.carteManoG2.toString()))
				continue;
			
			if(gridPaneKey.equals(strada)) {
				for (Posizione p : innerMap.keySet()) {
					Personaggio personaggioMappa = (Personaggio) innerMap.get(p);
					if (personaggioMappa != null && !personaggioMappa.equals(personaggioChiamante)) {
						list.add(personaggioMappa);
					}
						

				}
				break;
			}

		}
		
		return list;
	}

}