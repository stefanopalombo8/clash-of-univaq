package it.univaq.disim.oop.myclashofunivaq.business.impl;

import java.util.List;

import it.univaq.disim.oop.myclashofunivaq.business.GiocatoreUtenteService;
import it.univaq.disim.oop.myclashofunivaq.domain.GiocatoreUtente;

public class GiocatoreUtenteServiceImpl implements GiocatoreUtenteService {

	@Override
	public GiocatoreUtente convalidaNickName(String nickname) {
		//giusto un po' di logica di convalidazione non necessaria
		if (nickname.equals("") || nickname.length() < 3 || !nickname.matches(".*\\d$"))
			throw new NicknameNonValido("ERRORE NICKNAME NON VALIDO");

		return new GiocatoreUtente(nickname);
	}

}
