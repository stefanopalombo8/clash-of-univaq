package it.univaq.disim.oop.myclashofunivaq.business;

import it.univaq.disim.oop.myclashofunivaq.business.impl.ElisirException;
import it.univaq.disim.oop.myclashofunivaq.domain.Carta;
import it.univaq.disim.oop.myclashofunivaq.domain.Giocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.MossaGiocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.Partita;
import it.univaq.disim.oop.myclashofunivaq.domain.Torre;
import it.univaq.disim.oop.myclashofunivaq.domain.Turno;
import javafx.animation.Timeline;

public interface TurnoService extends ResetStaticVariables {
	Giocatore alternaGiocatore(Partita partita);
	Giocatore trovaAltroGiocatore(Partita partita);
	Turno avviaTurno(Timeline timeline, Giocatore giocatore);
	boolean isFirstTurno(Turno turno);
	void cambiaFase(Turno turno);
	void controllaSchieramento(Turno turno, Carta carta) throws ElisirException;
	void aggiornaElisir(Turno turno, int costo);
	void salvaMossaGiocatore(Turno turno, MossaGiocatore mossa);
	Torre trovaTorreGiocatore(Giocatore giocatore);
	void ripopolaMappaTurni(Partita partita);
}
