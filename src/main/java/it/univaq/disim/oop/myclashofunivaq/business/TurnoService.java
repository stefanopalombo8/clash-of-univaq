package it.univaq.disim.oop.myclashofunivaq.business;

import it.univaq.disim.oop.myclashofunivaq.business.impl.ElisirException;
import it.univaq.disim.oop.myclashofunivaq.business.impl.FasiTerminateException;
import it.univaq.disim.oop.myclashofunivaq.domain.Carta;
import it.univaq.disim.oop.myclashofunivaq.domain.Giocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.MossaGiocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.Partita;
import it.univaq.disim.oop.myclashofunivaq.domain.Torre;
import it.univaq.disim.oop.myclashofunivaq.domain.Turno;
import javafx.animation.Timeline;

public interface TurnoService {
	
	Torre trovaTorreGiocatore(Giocatore giocatore);
	
	Turno avviaTurno(Timeline timeline, Giocatore giocatore);
	
	Turno getUltimoTurno(Partita partita);
	
	boolean isFirstTurno(Turno turno);
	
	boolean isTurnoPari(Turno turno);
	
	void cambiaFase(Turno turno) throws FasiTerminateException;
	
	void controllaSchieramento(Turno turno, Carta carta) throws ElisirException;
	
	void aggiornaElisir(Turno turno, int costo);
	
	void salvaMossaGiocatore(Partita partita, Turno turno, MossaGiocatore mossa);
	
	void ripopolaMappaTurni(Partita partita);
	
	void annullaUltimoTurno(Turno turnoCorrente);
	
}