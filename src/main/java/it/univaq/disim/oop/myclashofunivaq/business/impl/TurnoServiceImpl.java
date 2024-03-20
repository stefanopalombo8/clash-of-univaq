package it.univaq.disim.oop.myclashofunivaq.business.impl;

import it.univaq.disim.oop.myclashofunivaq.business.PartitaService;
import it.univaq.disim.oop.myclashofunivaq.domain.Turno;
import it.univaq.disim.oop.myclashofunivaq.business.TurnoService;
import it.univaq.disim.oop.myclashofunivaq.domain.Giocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.Partita;
import javafx.animation.Timeline;

public class TurnoServiceImpl implements TurnoService{
	
	private final PartitaService partitaService;
	private static int i = 0;
	private static int j = 0;
	
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
		turno.setNumero(j++);		
		
		timeline.play();
		return turno;
	}


}
