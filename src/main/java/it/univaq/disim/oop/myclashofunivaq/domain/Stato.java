package it.univaq.disim.oop.myclashofunivaq.domain;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import it.univaq.disim.oop.myclashofunivaq.controller.utilitis.Posizione;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Stato {
	
	private Map<GridPane, LinkedHashMap<Posizione, ImageView>> mappaGridpaneImmagini = new HashMap<>();
	private Map<GridPane, LinkedHashMap<Posizione, Carta>> mappaGridpaneCarte = new HashMap<>();
	
	public Map<GridPane, LinkedHashMap<Posizione, ImageView>> getMappaGridpaneImmagini() {
		return mappaGridpaneImmagini;
	}
	public void setMappaGridpaneImmagini(Map<GridPane, LinkedHashMap<Posizione, ImageView>> mappaGridpaneImmagini) {
		this.mappaGridpaneImmagini = mappaGridpaneImmagini;
	}
	public Map<GridPane, LinkedHashMap<Posizione, Carta>> getMappaGridpaneCarte() {
		return mappaGridpaneCarte;
	}
	public void setMappaGridpaneCarte(Map<GridPane, LinkedHashMap<Posizione, Carta>> mappaGridpaneCarte) {
		this.mappaGridpaneCarte = mappaGridpaneCarte;
	}
	

}
