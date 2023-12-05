package it.univaq.disim.oop.myclashofunivaq.business.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import it.univaq.disim.oop.myclashofunivaq.business.PartitaService;
import it.univaq.disim.oop.myclashofunivaq.domain.Giocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.Partita;

public class PartitaServiceImpl implements PartitaService {
	
	private static Map<Integer, Partita> partite = new HashMap<>();
	private static Integer ID = 0;
	
	@Override
	public Set<Giocatore> findAllGiocatori() {
		Set<Giocatore> setToReturn = new HashSet<>();
		
		for(Integer key : partite.keySet()) {
			for(Giocatore giocatore : partite.get(key).getGiocatori())
				setToReturn.add(giocatore);
		}
		
		return setToReturn;
	}
	
	
	@Override
	public boolean aggiungiGiocatore(Giocatore giocatore, Partita partita) {
		if(!partita.getGiocatori().add(giocatore)) 
			throw new NicknameNonValido("ERRORE NICKNAME GIÀ UTILIZZATO");
		
		return true;
		
			
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
