package it.univaq.disim.oop.myclashofunivaq.business;

import it.univaq.disim.oop.myclashofunivaq.business.impl.NicknameNonValido;
import it.univaq.disim.oop.myclashofunivaq.domain.GiocatoreUtente;

public interface GiocatoreUtenteService {
	
	GiocatoreUtente convalidaNickName(String nickname) throws NicknameNonValido;
	
}