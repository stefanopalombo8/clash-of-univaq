package it.univaq.disim.oop.myclashofunivaq.business;

import java.util.List;
import it.univaq.disim.oop.myclashofunivaq.domain.Giocatore;

public interface PartitaService {
	//probabilmente giocatoriUtenti se non si vuole tenere traccia del player CPU
	List<Giocatore> giocatoriPartita(); 
	void aggiungiGiocatore(Giocatore giocatore);
}
