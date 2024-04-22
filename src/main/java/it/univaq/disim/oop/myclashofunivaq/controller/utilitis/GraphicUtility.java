package it.univaq.disim.oop.myclashofunivaq.controller.utilitis;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import it.univaq.disim.oop.myclashofunivaq.business.GiocatoreService;
import it.univaq.disim.oop.myclashofunivaq.domain.Carta;
import it.univaq.disim.oop.myclashofunivaq.domain.FaseTurno;
import it.univaq.disim.oop.myclashofunivaq.domain.Personaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.Tank;
import it.univaq.disim.oop.myclashofunivaq.domain.Turno;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;

public class GraphicUtility {

	private Map<String, LinkedHashMap<Posizione, ImageView>> mappaGridpaneImmagini = new HashMap<>();
	private Map<String, LinkedHashMap<Posizione, Carta>> mappaGridpaneCarte = new HashMap<>();

	private Posizione[] posizioneCartaSelezionata = new Posizione[1];

	private static List<GraphicUtility> stati = new ArrayList<>();
	private static int i = 0;

	public List<ImageView> nuoveImmagini = new ArrayList<>();

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

	public ImageView creaImpostaImageView(Image image, double height, double width) {
		ImageView imageView = new ImageView(image);
		imageView.setFitHeight(height);
		imageView.setFitWidth(width);

		return imageView;
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

			int num_colonne = grid.getColumnConstraints().size();
			int num_righe = grid.getRowConstraints().size();

			for (int i = 0; i < num_righe; i++) {
				for (int j = 0; j < num_colonne; j++) {
					Posizione p = new Posizione(j, i);
					mappaImmagini.put(p, null);
					mappaCarte.put(p, null);
				}

			}

			mappaGridpaneImmagini.put(grid.getId(), mappaImmagini);
			mappaGridpaneCarte.put(grid.getId(), mappaCarte);

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

	public Carta ricercaCartaSelezionataInMano(Posizione posizione) {
		Carta carta = null;

		for (Map.Entry<String, LinkedHashMap<Posizione, Carta>> entry : mappaGridpaneCarte.entrySet()) {
			String gridPaneKey = entry.getKey();

			if (gridPaneKey.equals("carteManoG1") || gridPaneKey.equals("carteManoG2")) {

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

	/*
	 * inserimento di un immagine in una particolare griglia in una particolare
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

	public GridPaneGioco ricercaStradaSchieramento(GridPane grid) {
		Optional<GridPaneGioco> optionalName = Arrays.stream(GridPaneGioco.values())
				.filter(n -> n.toString().equals(grid.getId())).findAny();

		return optionalName.get();
	}

	public void ripristinaStato(List<GridPane> grids) {
		// RIPRISTINO DELLE IMMAGINI SUL TERRENO

		for (Map.Entry<String, LinkedHashMap<Posizione, ImageView>> entry : getUltimoStato().getMappaGridpaneImmagini()
				.entrySet()) {

			String gridPaneKey = entry.getKey();
			LinkedHashMap<Posizione, ImageView> innerMap = entry.getValue();

			if (gridPaneKey.equals("carteManoG1") || gridPaneKey.equals("carteManoG2"))
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

						}
					}
				}
			}
		}

		this.setMappaGridpaneCarte(this.getUltimoStato().mappaGridpaneCarte);

		for (Map.Entry<String, LinkedHashMap<Posizione, Carta>> entry : this.mappaGridpaneCarte.entrySet()) {

			String gridPaneKey = entry.getKey();
			LinkedHashMap<Posizione, Carta> innerMap = entry.getValue();

			if (gridPaneKey.equals("carteManoG1") || gridPaneKey.equals("carteManoG2"))
				continue;

			for (GridPane grid : grids) {
				if (gridPaneKey.equals(grid.getId())) {
					for (Posizione p : innerMap.keySet()) {
						Personaggio personaggio = (Personaggio) innerMap.get(p);
//						if (personaggio != null) {
//							System.out.println(grid.getId() + " " + p + " vita " + personaggio.getVita());
//						}
					}
				}
			}
		}

	}

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

	}

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

}