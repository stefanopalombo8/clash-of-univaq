package it.univaq.disim.oop.myclashofunivaq.business.impl;

import java.util.HashMap;

import java.util.Map;

import it.univaq.disim.oop.myclashofunivaq.business.PartitaService;
import it.univaq.disim.oop.myclashofunivaq.domain.Turno;
import it.univaq.disim.oop.myclashofunivaq.business.TurnoService;
import it.univaq.disim.oop.myclashofunivaq.domain.Giocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.Partita;
import javafx.animation.Timeline;

public class TurnoServiceImpl implements TurnoService {
	
	private final PartitaService partitaService;
	private static int i = 0;
	private static int j = 0;
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
		
		if(turno.getNumero() == 0 || turno.getNumero() == 1)
			turno.setElisirGiocatore(0.5);
		else {
			double newElisir = turniPartita.get(j - 2).getElisirGiocatore() + 0.1;
			turno.setElisirGiocatore(newElisir);
		}
			
		
		turniPartita.put(turno.getNumero(), turno);
		
		j++;
		
		timeline.play();
		return turno;
	}

}
