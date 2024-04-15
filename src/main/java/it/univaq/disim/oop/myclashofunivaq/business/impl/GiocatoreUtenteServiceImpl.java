package it.univaq.disim.oop.myclashofunivaq.business.impl;

import it.univaq.disim.oop.myclashofunivaq.business.GiocatoreUtenteService;
import it.univaq.disim.oop.myclashofunivaq.business.PartitaService;
import it.univaq.disim.oop.myclashofunivaq.business.PersonaggioService;
import it.univaq.disim.oop.myclashofunivaq.business.TurnoService;
import it.univaq.disim.oop.myclashofunivaq.controller.utilitis.GridPaneGioco;
import it.univaq.disim.oop.myclashofunivaq.domain.Carta;
import it.univaq.disim.oop.myclashofunivaq.domain.GiocatoreUtente;
import it.univaq.disim.oop.myclashofunivaq.domain.MossaGiocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.Personaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.PosizionamentoPersonaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.Schieramento;
import it.univaq.disim.oop.myclashofunivaq.domain.Turno;

public class GiocatoreUtenteServiceImpl implements GiocatoreUtenteService {
	
	private final PartitaService partitaService;
	private final PersonaggioService personaggioService;
	private final TurnoService turnoService;
	
	public GiocatoreUtenteServiceImpl() {
		partitaService = new PartitaServiceImpl();
		personaggioService = new PersonaggioServiceImpl();
		turnoService = new TurnoServiceImpl();
	}

	@Override
	public GiocatoreUtente convalidaNickName(String nickname) {
		
		/* se viene inserito un nickname già presente nel sistema
		 * 
		 */
		GiocatoreUtente giocatoreMemory = (GiocatoreUtente) partitaService.findAllGiocatori().stream().findAny().filter(
				(g) -> g.getNickname().equals(nickname)).orElse(null);
		
		if(giocatoreMemory != null)
			return giocatoreMemory;
		else if (nickname.equals("") || nickname.length() < 1 || !nickname.matches(".*\\d$"))
			throw new NicknameNonValido("ERRORE NICKNAME NON VALIDO");
		
			
		return new GiocatoreUtente(nickname);
	}

	@Override
	public MossaGiocatore effettuaSchieramentoPersonaggio(Turno turno, Personaggio personaggio, GridPaneGioco strada, PosizionamentoPersonaggio posizionamento) {
		Schieramento schieramento = new Schieramento();
		schieramento.setCartaSchierata(personaggio);
		schieramento.setStrada(strada);
		
		personaggioService.sceltaPosizionamento(personaggio, posizionamento);
		
		turnoService.aggiornaElisir(turno, personaggio.getCostoSchieramento());
		
		return schieramento;
	}

	

}
