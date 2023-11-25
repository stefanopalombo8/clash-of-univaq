package it.univaq.disim.oop.myclashofunivaq.business.impl;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import it.univaq.disim.oop.myclashofunivaq.business.PartitaService;
import it.univaq.disim.oop.myclashofunivaq.domain.Giocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.Partita;

public class PartitaServiceImpl implements PartitaService {
	
	private static Map<Integer, Partita> partite = new HashMap<>();
	private static Integer ID = 0;
	
	@Override
	public List<Giocatore> giocatoriPartita(Partita partita) {
		Partita partitaCorrente = this.trovaPartitaByID(partita.getID());
		
		List<Giocatore> listaToReturn = new ArrayList<>(partitaCorrente.getGiocatori());
		
		return listaToReturn;
	}

	@Override
	public void aggiungiGiocatore(Giocatore giocatore, Partita partita) {
		
		partita.getGiocatori().add(giocatore);
	}
	
	@Override
	public Partita creaPartita() { 
		Partita partita = new Partita();
		partita.setID(ID);
		partite.put(partita.getID(), partita);
		ID++;
		return partita;
	}

	@Override
	public Partita trovaPartitaByID(Integer ID) {
		return (partite.get(ID)) != null ? partite.get(ID) : null;
	}

}
