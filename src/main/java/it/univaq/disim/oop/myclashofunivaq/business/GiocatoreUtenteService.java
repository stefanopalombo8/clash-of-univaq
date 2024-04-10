package it.univaq.disim.oop.myclashofunivaq.business;

import it.univaq.disim.oop.myclashofunivaq.domain.GiocatoreUtente;

public interface GiocatoreUtenteService extends GiocatoreService {
	
	GiocatoreUtente convalidaNickName(String nickname);
	
}
