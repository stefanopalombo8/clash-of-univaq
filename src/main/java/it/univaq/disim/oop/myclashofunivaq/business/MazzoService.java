package it.univaq.disim.oop.myclashofunivaq.business;

import java.util.List;

import it.univaq.disim.oop.myclashofunivaq.business.impl.MazzoException;
import it.univaq.disim.oop.myclashofunivaq.domain.Carta;
import it.univaq.disim.oop.myclashofunivaq.domain.Giocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.Mazzo;

public interface MazzoService {
	
	void checkCartaScelta(Carta carta, List<Carta> carteScelte) throws MazzoException;
	
	void checkSizeCarteScelte(List<Carta> carteScelte) throws MazzoException;
	
	Mazzo creaMazzo(List<Carta> carteScelte);
	
	void controllaMazzo(Mazzo mazzo) throws MazzoException;
	
	void aggiungiMazzo(Mazzo mazzo, Giocatore giocatore);
	
	Mazzo trovaMazzo(Giocatore giocatore);
	
	Carta[] mostraCarteMano(Mazzo mazzo);
	
	Carta mostraProssimaCarta(Mazzo mazzo, List<Carta> carteManoCorrente);
	
}