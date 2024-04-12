package it.univaq.disim.oop.myclashofunivaq.business;

import java.util.LinkedHashMap;
import java.util.Map;

import it.univaq.disim.oop.myclashofunivaq.controller.utilitis.Posizione;
import it.univaq.disim.oop.myclashofunivaq.domain.Carta;
import it.univaq.disim.oop.myclashofunivaq.domain.Giocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.Partita;
import it.univaq.disim.oop.myclashofunivaq.domain.Stato;
import it.univaq.disim.oop.myclashofunivaq.domain.Turno;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public interface TurnoService {
	Giocatore alternaGiocatore(Partita partita);
	Turno avviaTurno(Timeline timeline, Giocatore giocatore);
	boolean isFirstTurno(Turno turno);
	
	void creaSalvaStatoTurno(Map<GridPane, LinkedHashMap<Posizione, ImageView>> mappaGridpaneImmagini,
			Map<GridPane, LinkedHashMap<Posizione, Carta>> mappaGridpaneCarte, Turno turno);
	
}
