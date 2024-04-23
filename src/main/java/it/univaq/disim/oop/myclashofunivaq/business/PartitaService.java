package it.univaq.disim.oop.myclashofunivaq.business;

import java.util.List;
import java.util.Set;

import it.univaq.disim.oop.myclashofunivaq.domain.Giocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.Partita;
import it.univaq.disim.oop.myclashofunivaq.domain.Turno;

public interface PartitaService {
	Partita creaPartita();
	
	Partita trovaPartitaByID(Integer ID);
	
	boolean aggiungiGiocatore(Giocatore giocatore, Partita partita);
	
	Set<Giocatore> findAllGiocatori();
	Giocatore[] findAllGiocatori(Partita partita);
	
	boolean salvaTurnoPartita(Turno turno, Partita partita);
	
	int calcolaNumeroMossePartita(Partita partita);	
	
	void salvaPartita(Partita partita);
	void impostaParamentriSalvataggio(Partita partita, int numeroMosse, int numeroCarte, int valoreCarte);
	List<Partita> getPartiteDeserializzate();
	void mappaPartitaSerializzata(Partita partita);
}
