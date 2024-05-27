package it.univaq.disim.oop.myclashofunivaq.business;

import java.util.List;
import java.util.Set;

import it.univaq.disim.oop.myclashofunivaq.business.impl.NicknameNonValido;
import it.univaq.disim.oop.myclashofunivaq.domain.Giocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.Partita;
import it.univaq.disim.oop.myclashofunivaq.domain.Turno;

public interface PartitaService {
	
	Partita creaPartita();
	
	Partita trovaPartitaByID(Integer ID);
	
	boolean aggiungiGiocatore(Giocatore giocatore, Partita partita) throws NicknameNonValido;
	
	Set<Giocatore> findAllGiocatori();
	
	Giocatore[] findAllGiocatori(Partita partita);
	
	Giocatore alternaGiocatore(Partita partita);
	
	Giocatore trovaAltroGiocatore(Partita partita, Giocatore giocatoreCorrente);
	
	boolean salvaTurnoPartita(Turno turno, Partita partita);
	
	int calcolaNumeroMossePartita(Partita partita);	
	
	void salvaPartita(Partita partita);
	
	void eliminaPartitaSalvata(Integer ID);
	
	void impostaParamentriSalvataggio(Partita partita, int numeroMosse, int numeroCarte, int valoreCarte);
	
	List<Partita> getPartiteDeserializzate();
	
	void mappaPartitaSerializzata(Partita partita);
}