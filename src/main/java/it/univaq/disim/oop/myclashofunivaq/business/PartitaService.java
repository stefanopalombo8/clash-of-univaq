package it.univaq.disim.oop.myclashofunivaq.business;


import java.util.Set;

import it.univaq.disim.oop.myclashofunivaq.domain.Giocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.Partita;

public interface PartitaService {
	Partita creaPartita();
	
	Partita trovaPartitaByID(Integer ID);
	
	boolean aggiungiGiocatore(Giocatore giocatore, Partita partita);
	
	Set<Giocatore> findAllGiocatori();
	
	
	
}
