package it.univaq.disim.oop.myclashofunivaq.business.impl;

import java.util.List;

import it.univaq.disim.oop.myclashofunivaq.business.GiocatoreUtenteService;
import it.univaq.disim.oop.myclashofunivaq.domain.GiocatoreUtente;

public class GiocatoreUtenteServiceImpl implements GiocatoreUtenteService {
	
	private PartitaServiceImpl partitaImpl;
	
	public GiocatoreUtenteServiceImpl() {
		partitaImpl = new PartitaServiceImpl();
	}

	@Override
	public GiocatoreUtente convalidaNickName(String nickname) {
		boolean match = partitaImpl.giocatoriPartita().stream()
				.anyMatch(n -> n.getNickname().equals(nickname));
		
		//giusto un po' di logica di convalidazione non necessaria
		if (nickname.equals("") || nickname.length() < 3 || !nickname.matches(".*\\d$") || match)
			throw new NicknameNonValido("ERRORE NICKNAME NON VALIDO");
		
		return new GiocatoreUtente(nickname);
	}

}
