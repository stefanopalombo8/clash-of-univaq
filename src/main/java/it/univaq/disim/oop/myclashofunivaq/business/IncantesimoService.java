package it.univaq.disim.oop.myclashofunivaq.business;

import java.util.List;

import it.univaq.disim.oop.myclashofunivaq.domain.Incantesimo;
import it.univaq.disim.oop.myclashofunivaq.domain.Personaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.Turno;

public interface IncantesimoService {
	
	void aggiungiIncantesimoAttivo(Turno turno, Incantesimo incantesimo, Personaggio personaggioTarget);
	
	List<Incantesimo> getIncantesimiAttivi();
	
	void eseguiIncantesimo(Incantesimo incantesimo, Personaggio personaggioTarget);
	
	List<Incantesimo> getIncantesimiAttivi(Personaggio personaggio);
	
	void checkAnnullaEffettoIncantesimi();
	
}