package it.univaq.disim.oop.myclashofunivaq.business;

import it.univaq.disim.oop.myclashofunivaq.domain.GiocatoreUtente;
import it.univaq.disim.oop.myclashofunivaq.domain.Partita;

public interface GiocatoreUtenteService {
	
	GiocatoreUtente convalidaNickName(String nickname, Partita partita);
	
	
}
