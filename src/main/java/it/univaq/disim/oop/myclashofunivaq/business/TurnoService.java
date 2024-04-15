package it.univaq.disim.oop.myclashofunivaq.business;

import it.univaq.disim.oop.myclashofunivaq.business.impl.ElisirException;
import it.univaq.disim.oop.myclashofunivaq.domain.Carta;
import it.univaq.disim.oop.myclashofunivaq.domain.Giocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.Partita;
import it.univaq.disim.oop.myclashofunivaq.domain.Turno;
import javafx.animation.Timeline;

public interface TurnoService {
	Giocatore alternaGiocatore(Partita partita);
	Turno avviaTurno(Timeline timeline, Giocatore giocatore);
	boolean isFirstTurno(Turno turno);
	void cambiaFase(Turno turno);
	void controllaSchieramento(Turno turno, Carta carta) throws ElisirException;
	void aggiornaElisir(Turno turno, int costo);
	
}
