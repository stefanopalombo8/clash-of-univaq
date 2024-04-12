package it.univaq.disim.oop.myclashofunivaq.business.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import it.univaq.disim.oop.myclashofunivaq.business.PartitaService;
import it.univaq.disim.oop.myclashofunivaq.domain.Turno;
import it.univaq.disim.oop.myclashofunivaq.business.TurnoService;
import it.univaq.disim.oop.myclashofunivaq.controller.utilitis.Posizione;
import it.univaq.disim.oop.myclashofunivaq.domain.Carta;
import it.univaq.disim.oop.myclashofunivaq.domain.Giocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.Partita;
import it.univaq.disim.oop.myclashofunivaq.domain.Stato;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class TurnoServiceImpl implements TurnoService {
	
	private final PartitaService partitaService;
	private static int i = 0; // indice per i giocatori
	private static int j = 0; // indice per l'ID dei turni
	private static Map<Integer, Turno> turniPartita = new HashMap<>();
	
	public TurnoServiceImpl() {
		partitaService = new PartitaServiceImpl();
	}

	@Override
	public Giocatore alternaGiocatore(Partita partita) {
		return partitaService.findAllGiocatori(partita)[i++ % partitaService.findAllGiocatori(partita).length];
	}

	@Override
	public Turno avviaTurno(Timeline timeline, Giocatore giocatore) {
		if(timeline == null)
			return null;
		
		Turno turno = new Turno(giocatore);
		turno.setNumero(j);
		
		if(isFirstTurno(turno))
			turno.setElisirGiocatore(0.5);
		else {
			double newElisir = turniPartita.get(j - 2).getElisirGiocatore() + 0.1;
			turno.setElisirGiocatore(newElisir);
			turno.setStato(turniPartita.get(j - 2).getStato());
		}
			
		
		turniPartita.put(turno.getNumero(), turno);
		
		j++;
		
		timeline.play();
		return turno;
	}

	@Override
	public boolean isFirstTurno(Turno turno) {
		return turno.getNumero() == 0 || turno.getNumero() == 1 ? true : false;
	}
	
	@Override
	public void creaSalvaStatoTurno(Map<GridPane, LinkedHashMap<Posizione, ImageView>> mappaGridpaneImmagini,
			Map<GridPane, LinkedHashMap<Posizione, Carta>> mappaGridpaneCarte, Turno turno) {
		
		Stato stato = new Stato();
		stato.setMappaGridpaneImmagini(mappaGridpaneImmagini);
		stato.setMappaGridpaneCarte(mappaGridpaneCarte);
		
		turno.setStato(stato);
		
	}

	

}
