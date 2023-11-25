package it.univaq.disim.oop.myclashofunivaq.business.impl;

import it.univaq.disim.oop.myclashofunivaq.business.GiocatoreUtenteService;
import it.univaq.disim.oop.myclashofunivaq.business.PartitaService;
import it.univaq.disim.oop.myclashofunivaq.domain.GiocatoreUtente;
import it.univaq.disim.oop.myclashofunivaq.domain.Partita;

public class GiocatoreUtenteServiceImpl implements GiocatoreUtenteService {
	
	private PartitaService partitaService;
	
	public GiocatoreUtenteServiceImpl() {
		partitaService = new PartitaServiceImpl();
	}

	@Override
	public GiocatoreUtente convalidaNickName(String nickname, Partita partita) throws NicknameNonValido {
		boolean match = partitaService.giocatoriPartita(partita).stream()
				.anyMatch(n -> n.getNickname().equals(nickname));
		
		//giusto un po' di logica di convalidazione non necessaria
		if (nickname.equals("") || nickname.length() < 3 || !nickname.matches(".*\\d$")) {
			throw new NicknameNonValido("ERRORE NICKNAME NON VALIDO");
		}
			
		return new GiocatoreUtente(nickname);
	}

}
