package it.univaq.disim.oop.myclashofunivaq.business.impl;

import java.util.HashMap;

import java.util.LinkedHashMap;
import java.util.Map;

import it.univaq.disim.oop.myclashofunivaq.business.PartitaService;
import it.univaq.disim.oop.myclashofunivaq.domain.Turno;
import it.univaq.disim.oop.myclashofunivaq.business.TurnoService;
import it.univaq.disim.oop.myclashofunivaq.controller.utilitis.Posizione;
import it.univaq.disim.oop.myclashofunivaq.domain.Carta;
import it.univaq.disim.oop.myclashofunivaq.domain.FaseTurno;
import it.univaq.disim.oop.myclashofunivaq.domain.Giocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.MossaGiocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.Partita;
import it.univaq.disim.oop.myclashofunivaq.domain.Torre;
import javafx.animation.Timeline;

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
		turno.setFase(FaseTurno.Schieramento);
		
		if(isFirstTurno(turno)) {
			turno.setElisirGiocatore(0.7);
			Torre torre = new Torre();
			torre.setVita(1);
			turno.setTorreGiocatore(torre);
		}
		else {
			double newElisir = turniPartita.get(j - 2).getElisirGiocatore() + 0.1;
			turno.setElisirGiocatore(newElisir);
			
			Torre torre = turniPartita.get(j - 2).getTorreGiocatore();
			double newVitaTorre = torre.getVita();
			torre.setVita(newVitaTorre);
			turno.setTorreGiocatore(torre);
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
	public void cambiaFase(Turno turno) {
		FaseTurno faseTurno = turno.getFase();
		
		switch (faseTurno) {
		case Schieramento: 
			turno.setFase(FaseTurno.Difesa);
			break;
		case Difesa:
			turno.setFase(FaseTurno.Attacco);
			break;
		default:
			System.out.println("FASI FINITE");
			break;
		}
		
		
	}
	
	@Override
	public void controllaSchieramento(Turno turno, Carta carta) throws ElisirException {
		//if(!turniPartita.containsKey(turno.getNumero()))
		if(turno.getElisirGiocatore() < (double) carta.getCostoSchieramento() / 10)
			throw new ElisirException("ELISIR INSUFFICIENTE");
		
	}

	@Override
	public void aggiornaElisir(Turno turno, int costo) {
		double newElisir = turno.getElisirGiocatore() - (double) costo / 10;
		turno.setElisirGiocatore(newElisir);
	}

	@Override
	public void salvaMossaGiocatore(Turno turno, MossaGiocatore mossa) {
		turno.getMosseGiocatore().add(mossa);
	}

	@Override
	public Giocatore trovaAltroGiocatore(Partita partita) {
		int j = i;
		return partitaService.findAllGiocatori(partita)[j++ % partitaService.findAllGiocatori(partita).length];
	}

	@Override
	public Torre trovaTorreGiocatore(Giocatore giocatore) {
		Torre torre = null;
		
		for(Integer i : turniPartita.keySet()) {
			Turno turno = turniPartita.get(i);
			if(turno.getGiocatore().equals(giocatore)) {
				torre = turno.getTorreGiocatore();
			}
		}
		return torre;
	}

	@Override
	public void reset() {
		i = 0;
		j = 0;
		turniPartita.clear();
	}

	@Override
	public void ripopolaMappaTurni(Partita partita) {
		if(turniPartita.isEmpty()) {
			i++;
			for(Turno t : partita.getTurni()) {
				turniPartita.put(t.getNumero(), t);
				j = t.getNumero();
			}
			j++;
		}
	}
	
}
