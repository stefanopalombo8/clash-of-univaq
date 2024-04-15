package it.univaq.disim.oop.myclashofunivaq.controller.utilitis;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import it.univaq.disim.oop.myclashofunivaq.domain.Carta;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;

public class GraphicUtility {

	private Map<GridPane, LinkedHashMap<Posizione, ImageView>> mappaGridpaneImmagini = new HashMap<>();
	private Map<GridPane, LinkedHashMap<Posizione, Carta>> mappaGridpaneCarte = new HashMap<>();

	private Posizione[] posizioneCartaSelezionata = new Posizione[1];
	
	private static List<GraphicUtility> stati = new ArrayList<>();
	private static int i = 0;

	public Map<GridPane, LinkedHashMap<Posizione, ImageView>> getMappaGridpaneImmagini() {
		return mappaGridpaneImmagini;
	}

	public Map<GridPane, LinkedHashMap<Posizione, Carta>> getMappaGridpaneCarte() {
		return mappaGridpaneCarte;
	}

	public Posizione[] getPosizioneCartaSelezionata() {
		return posizioneCartaSelezionata;
	}
	
	public void setMappaGridpaneCarte(Map<GridPane, LinkedHashMap<Posizione, Carta>> mappaGridpaneCarte) {
		this.mappaGridpaneCarte = mappaGridpaneCarte;
	}

	public void resetArrayCopy() {
		this.posizioneCartaSelezionata[0] = null;
	}
	
	public void aggiungiStato(GraphicUtility stato) {
		stati.add(stato);
		i++;
	}
	
	public GraphicUtility getUltimoStato() {
		return stati.get(i - 2);
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

			Posizione posizione = new Posizione(GridPane.getColumnIndex(imageView), GridPane.getRowIndex(imageView));
			posizioneCartaSelezionata[0] = posizione;			
			
		});
	}

	
	/* predisposizione per ogni griglia di un numero di posizioni 
	 * congruo al numero di righe e colonne
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

			mappaGridpaneImmagini.put(grid, mappaImmagini);
			mappaGridpaneCarte.put(grid, mappaCarte);
		}

	}

	
	/* inserimento di un immagine in una particolare griglia
	 * nella prima posizione libera 
	 */
	
	public void aggiungiCartaImmagineGriglia(GridPane grid, Carta carta, ImageView imageView) {
		
		if(imageView != null) {
			for (Map.Entry<GridPane, LinkedHashMap<Posizione, ImageView>> entry : mappaGridpaneImmagini.entrySet()) {
				GridPane gridPaneKey = entry.getKey();

				if (gridPaneKey.equals(grid)) {
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
		
		
		if(carta != null) {
			for (Map.Entry<GridPane, LinkedHashMap<Posizione, Carta>> entry : mappaGridpaneCarte.entrySet()) {
				GridPane gridPaneKey = entry.getKey();

				if (gridPaneKey.equals(grid)) {
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

	}

	/* inserimento di un immagine in una particolare griglia
	 * in una particolare posizione (quando si schiera una carta dalla mano si libera una posizione)
	 */
	
	public void aggiungiCartaImmagineGriglia(GridPane grid, Carta carta, ImageView imageView, Posizione posizione) {

		if(imageView != null) {
			for (Map.Entry<GridPane, LinkedHashMap<Posizione, ImageView>> entry : mappaGridpaneImmagini.entrySet()) {
				GridPane gridPaneKey = entry.getKey();

				if (gridPaneKey.equals(grid)) {
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
		
		if(carta != null) {
			for (Map.Entry<GridPane, LinkedHashMap<Posizione, Carta>> entry : mappaGridpaneCarte.entrySet()) {
				GridPane gridPaneKey = entry.getKey();

				if (gridPaneKey.equals(grid)) {
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
		
	}
	
	
	public Carta ricercaCartaSelezionataInMano(Posizione posizione) {
		Carta carta = null;
		
		for (Map.Entry<GridPane, LinkedHashMap<Posizione, Carta>> entry : mappaGridpaneCarte.entrySet()) {
			GridPane gridPaneKey = entry.getKey();

			if (gridPaneKey.getId().equals(GridPaneGioco.carteMano.toString())) {

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

	public GridPaneGioco ricercaStradaSchieramento(GridPane grid) {
		Optional<GridPaneGioco> optionalName = Arrays.stream(GridPaneGioco.values())
				.filter(n -> n.toString().equals(grid.getId())).findAny();

		return optionalName.get();
	}

	public void ripristinaStato(List<GridPane> grids)  {
		this.setMappaGridpaneImmagini(getUltimoStato().getMappaGridpaneImmagini());
		
		//RIPRISTINO DELLE IMMAGINI SUL TERRENO
		for (Map.Entry<GridPane, LinkedHashMap<Posizione, ImageView>> entry : getUltimoStato().getMappaGridpaneImmagini()
				.entrySet()) {
			
			GridPane gridPaneKey = entry.getKey();
			LinkedHashMap<Posizione, ImageView> innerMap = entry.getValue();
			
			for(GridPane grid : grids) {
				if(gridPaneKey.getId().equals(grid.getId())) {
					for (Posizione p : innerMap.keySet()) {
						if(innerMap.get(p) != null) {
							if(grid.getId().equals(GridPaneGioco.carteMano.toString()))
								this.setImageDragProperty(grid, innerMap.get(p));
							grid.add(innerMap.get(p), p.getColonna(), p.getRiga());
						}
					}
				}
			}
			
			
		}
		
		//RIPRISTINO DELLE CARTE
		this.setMappaGridpaneCarte(getUltimoStato().getMappaGridpaneCarte());
		
	}

	public void setMappaGridpaneImmagini(Map<GridPane, LinkedHashMap<Posizione, ImageView>> mappaGridpaneImmagini) {
		this.mappaGridpaneImmagini = mappaGridpaneImmagini;
	}
	
}