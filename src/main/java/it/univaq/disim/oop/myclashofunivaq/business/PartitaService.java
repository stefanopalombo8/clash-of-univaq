package it.univaq.disim.oop.myclashofunivaq.business;

import java.util.List;
import it.univaq.disim.oop.myclashofunivaq.domain.Giocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.Partita;

public interface PartitaService {
	Partita creaPartita();
	
	Partita trovaPartitaByID(Integer ID);
	
	void aggiungiGiocatore(Giocatore giocatore, Partita partita);
	
	List<Giocatore> giocatoriPartita(Partita partita);
	
}
