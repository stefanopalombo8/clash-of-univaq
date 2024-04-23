package it.univaq.disim.oop.myclashofunivaq.controller;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import it.univaq.disim.oop.myclashofunivaq.business.PartitaService;
import it.univaq.disim.oop.myclashofunivaq.business.impl.PartitaServiceImpl;
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
import javafx.scene.control.ListView;

public class PartiteSalvateController implements Initializable {
	
	@FXML
	private ListView<String> listViewPartite;
	
	@FXML
	private Button gioca;
	
	private final PartitaService partitaService;
	
	private ViewDispatcher dispatcher;
	
	public PartiteSalvateController() {
		this.partitaService = new PartitaServiceImpl();
		this.dispatcher = ViewDispatcher.getInstance();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gioca.disableProperty().bind(Bindings.isNull(listViewPartite.getSelectionModel().selectedItemProperty()));
		
		ObservableList<String> partiteObservableList = FXCollections.observableArrayList();
		
		for(Partita p : partitaService.getPartiteDeserializzate()) {
			if(p != null) {
				partiteObservableList.add("ID_partita: " + p.getID() + " numero_mosse: " + p.getNumeroTotaleMosse());
			}
		}
		
		this.listViewPartite.setItems(partiteObservableList);
	}
	
	@FXML
	public void giocaAction(ActionEvent event) {
		String selectedItem = listViewPartite.getSelectionModel().getSelectedItem();
		if(selectedItem != null) {
			String[] parts = selectedItem.split(" ");
		    Integer partitaID = Integer.parseInt(parts[1]);
		    
		    Partita partitaInMemoria = this.partitaService.trovaPartitaByID(partitaID);
		    Partita partitaDaGiocare = null;
		    
		    if(partitaInMemoria != null) {
		    	System.out.println("ho ripreso la partita dalla memoria");
		    	try {
					this.dispatcher.caricaVista("gioco", partitaInMemoria);
				} catch (ViewException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		    else {
		    	for(Partita p : partitaService.getPartiteDeserializzate()) {
		    		if(p.getID().equals(partitaID)) {
		    			partitaDaGiocare = p;
		    			break;
		    		}		
		    	}
		    }
		    
		    try {
		    	if(partitaDaGiocare != null) {
		    		partitaDaGiocare.setRecuperata(true);
		    		this.dispatcher.caricaVista("gioco", partitaDaGiocare);
		    	}
				
			} catch (ViewException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    	
		   
		}
	}

}
