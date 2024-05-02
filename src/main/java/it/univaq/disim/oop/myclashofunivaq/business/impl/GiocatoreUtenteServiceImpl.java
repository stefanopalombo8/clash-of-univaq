package it.univaq.disim.oop.myclashofunivaq.business.impl;

import it.univaq.disim.oop.myclashofunivaq.business.GiocatoreUtenteService;
import it.univaq.disim.oop.myclashofunivaq.business.PartitaService;
import it.univaq.disim.oop.myclashofunivaq.domain.GiocatoreUtente;

public class GiocatoreUtenteServiceImpl implements GiocatoreUtenteService {
	
	private final PartitaService partitaService;
	
	public GiocatoreUtenteServiceImpl() {
		partitaService = new PartitaServiceImpl();
	}


	@Override
	public GiocatoreUtente convalidaNickName(String nickname) throws NicknameNonValido {
		
		/* se viene inserito un nickname già presente nel sistema
		 * 
		 */
		GiocatoreUtente giocatoreMemory = (GiocatoreUtente) partitaService.findAllGiocatori().stream().findAny().filter(
				(g) -> g.getNickname().equals(nickname)).orElse(null);
		
		if(giocatoreMemory != null)
			return giocatoreMemory;
		else if (nickname.equals("") || nickname.length() < 3 || !nickname.matches(".*\\d$"))
			throw new NicknameNonValido("ERRORE NICKNAME NON VALIDO");
		
			
		return new GiocatoreUtente(nickname);
	}

}
