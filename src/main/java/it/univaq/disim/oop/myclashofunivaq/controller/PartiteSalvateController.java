package it.univaq.disim.oop.myclashofunivaq.controller;

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import it.univaq.disim.oop.myclashofunivaq.business.PartitaService;
import it.univaq.disim.oop.myclashofunivaq.business.impl.PartitaServiceImpl;
import it.univaq.disim.oop.myclashofunivaq.domain.Giocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.Partita;
import it.univaq.disim.oop.myclashofunivaq.view.ViewDispatcher;
import it.univaq.disim.oop.myclashofunivaq.view.ViewException;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;

public class PartiteSalvateController implements Initializable {

	@FXML
	private ListView<String> listViewPartite;

	@FXML
	private ChoiceBox<String> choiceBox;

	@FXML
	private Button gioca;

	private final PartitaService partitaService;

	private ViewDispatcher dispatcher;
	private String sceltaOrdinamento;

	public PartiteSalvateController() {
		this.partitaService = new PartitaServiceImpl();
		this.dispatcher = ViewDispatcher.getInstance();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gioca.disableProperty().bind(Bindings.isNull(listViewPartite.getSelectionModel().selectedItemProperty()));
		choiceBox.setItems(FXCollections.observableArrayList("numeroMosse", "numeroCarte", "valoreCarte"));
		
		List<Partita> partiteDeserializzate = partitaService.getPartiteDeserializzate();
		
		sceltaOrdinamento = "ID";

		choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			sceltaOrdinamento = newValue; // Aggiorna il criterio di ordinamento quando l'utente cambia selezione
			aggiornaListaPartite(partiteDeserializzate); // Riordina la lista quando l'utente cambia criterio di ordinamento
		});
		
		this.aggiornaListaPartite(partiteDeserializzate);

	}

	private void aggiornaListaPartite(List<Partita> partiteDeserializzate) {
		ObservableList<String> partiteObservableList = FXCollections.observableArrayList();
		
		Collections.sort(partiteDeserializzate, new PartitaComparator());
		
		for (Partita p : partiteDeserializzate) {
			if (p != null) {
				Set<Giocatore> giocatoriPartita = p.getGiocatori();
				StringBuilder builder = new StringBuilder();
				for(Giocatore g : giocatoriPartita) {
					builder.append(g.getNickname());
					builder.append(" ");
				}
				
				partiteObservableList.add("ID_partita: " + p.getID() + " numero_mosse: " + p.getNumeroTotaleMosse()
						+ " numero_carte_campo: " + p.getNumeroCarteInCampo() + " valore_carte_campo: "
						+ p.getValoreCarteInCampo() + " giocatori: " + builder.toString());
			}
		}

		this.listViewPartite.setItems(partiteObservableList);
	}

	@FXML
	public void giocaAction(ActionEvent event) {
		String selectedItem = listViewPartite.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			String[] parts = selectedItem.split(" ");
			Integer partitaID = Integer.parseInt(parts[1]);

			Partita partitaInMemoria = this.partitaService.trovaPartitaByID(partitaID);
			Partita partitaDaGiocare = null;

			if (partitaInMemoria != null) {
				System.out.println("ho ripreso la partita dalla memoria");
				try {
					partitaInMemoria.setRecuperata(true);
					this.dispatcher.caricaVista("gioco", partitaInMemoria);
				} catch (ViewException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				for (Partita p : partitaService.getPartiteDeserializzate()) {
					if (p.getID().equals(partitaID)) {
						partitaDaGiocare = p;
						break;
					}
				}
			}

			try {
				if (partitaDaGiocare != null) {
					partitaDaGiocare.setRecuperata(true);
					this.dispatcher.caricaVista("gioco", partitaDaGiocare);
				}

			} catch (ViewException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	class PartitaComparator implements Comparator<Partita> {

		@Override
		public int compare(Partita p1, Partita p2) {
			
			switch (sceltaOrdinamento) {
			case "numeroMosse":
				return Integer.compare(p1.getNumeroTotaleMosse(), p2.getNumeroTotaleMosse()); // più veloce
			case "numeroCarte":
				return Integer.compare(p1.getNumeroCarteInCampo(), p2.getNumeroCarteInCampo());
			case "valoreCarte":
				return Integer.compare(p1.getValoreCarteInCampo(), p2.getValoreCarteInCampo());
			default:
				return Integer.compare(p1.getID(), p2.getID());
			}

		}

	}

}