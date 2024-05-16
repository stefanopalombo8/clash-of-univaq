package it.univaq.disim.oop.myclashofunivaq.business;

import java.util.List;

import it.univaq.disim.oop.myclashofunivaq.domain.Carta;
import it.univaq.disim.oop.myclashofunivaq.domain.Giocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.Mazzo;

public interface MazzoService {
	Mazzo creaMazzo(List<Carta> carteScelte);
	
	boolean controllaMazzo(Mazzo mazzo);
	
	void aggiungiMazzo(Mazzo mazzo, Giocatore giocatore);
	
	Mazzo trovaMazzo(Giocatore giocatore);
	
	Carta[] mostraCarteMano(Mazzo mazzo);
	
	Carta mostraProssimaCarta(Mazzo mazzo, List<Carta> carteManoCorrente);
	
}