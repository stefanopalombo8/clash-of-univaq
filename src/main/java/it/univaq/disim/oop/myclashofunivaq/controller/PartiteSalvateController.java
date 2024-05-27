package it.univaq.disim.oop.myclashofunivaq.controller;

import java.net.URL;

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

	@FXML
	private Button buttonHome;

	@FXML
	private Button eliminaPartita;

	private final PartitaService partitaService;

	private ViewDispatcher dispatcher;
	private String sceltaOrdinamento;

	public PartiteSalvateController() {
		this.partitaService = new PartitaServiceImpl();
		this.dispatcher = ViewDispatcher.getInstance();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.gioca.disableProperty()
				.bind(Bindings.isNull(this.listViewPartite.getSelectionModel().selectedItemProperty()));
		this.eliminaPartita.disableProperty()
				.bind(Bindings.isNull(this.listViewPartite.getSelectionModel().selectedItemProperty()));
		
		this.choiceBox.setItems(FXCollections.observableArrayList("ID", "numeroMosse", "numeroCarte", "valoreCarte"));

		List<Partita> partiteDeserializzate = this.partitaService.getPartiteDeserializzate();

		this.sceltaOrdinamento = "ID";
		this.choiceBox.setValue(sceltaOrdinamento);

		this.choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			this.sceltaOrdinamento = newValue;
			this.aggiornaListaPartite(partiteDeserializzate);
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
				for (Giocatore g : giocatoriPartita) {
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
		String selectedItem = this.listViewPartite.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			String[] parts = selectedItem.split(" ");
			Integer partitaID = Integer.parseInt(parts[1]);

			Partita partitaDaGiocare = null;

			for (Partita p : this.partitaService.getPartiteDeserializzate()) {
				if (p.getID().equals(partitaID)) {
					partitaDaGiocare = p;
					break;
				}
			}

			try {
				if (partitaDaGiocare != null) {
					partitaDaGiocare.setRecuperata(true);
					this.dispatcher.caricaVista("gioco", partitaDaGiocare);
				}
			} catch (ViewException e) {
				e.printStackTrace();
			}

		}
	}

	@FXML
	public void ritornaHomeAction(ActionEvent event) {
		try {
			this.dispatcher.caricaVista("applicationLayout");
			this.dispatcher.caricaVista("homepage");
		} catch (ViewException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void eliminaPartitaAction(ActionEvent event) {
		String selectedItem = this.listViewPartite.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			String[] parts = selectedItem.split(" ");
			Integer partitaID = Integer.parseInt(parts[1]);

			this.partitaService.eliminaPartitaSalvata(partitaID);
		}

		ObservableList<String> items = this.listViewPartite.getItems();
		int selectedIndex = this.listViewPartite.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			items.remove(selectedIndex);
		}

	}

	private class PartitaComparator implements Comparator<Partita> {

		@Override
		public int compare(Partita p1, Partita p2) {

			switch (sceltaOrdinamento) {
			// ordinamento decrescente
			case "numeroMosse":
				return Integer.compare(p2.getNumeroTotaleMosse(), p1.getNumeroTotaleMosse()); // più veloce
			case "numeroCarte":
				return Integer.compare(p2.getNumeroCarteInCampo(), p1.getNumeroCarteInCampo());
			case "valoreCarte":
				return Integer.compare(p2.getValoreCarteInCampo(), p1.getValoreCarteInCampo());
			// ordinamento crescente
			case "ID":
				return Integer.compare(p1.getID(), p2.getID());
			default:
				return 0;
			}

		}
	}
}