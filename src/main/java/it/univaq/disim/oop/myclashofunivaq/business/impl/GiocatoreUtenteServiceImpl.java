package it.univaq.disim.oop.myclashofunivaq.business.impl;

import java.util.ArrayList;
import java.util.List;

import it.univaq.disim.oop.myclashofunivaq.business.GiocatoreUtenteService;
import it.univaq.disim.oop.myclashofunivaq.business.IncantesimoService;
import it.univaq.disim.oop.myclashofunivaq.business.PartitaService;
import it.univaq.disim.oop.myclashofunivaq.business.PersonaggioService;
import it.univaq.disim.oop.myclashofunivaq.business.TurnoService;
import it.univaq.disim.oop.myclashofunivaq.controller.utilitis.GridPaneGioco;
import it.univaq.disim.oop.myclashofunivaq.domain.Attacco;
import it.univaq.disim.oop.myclashofunivaq.domain.CambioPosizionamentoPersonaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.Carta;
import it.univaq.disim.oop.myclashofunivaq.domain.GiocatoreUtente;
import it.univaq.disim.oop.myclashofunivaq.domain.MossaGiocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.Personaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.PosizionamentoPersonaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.Schieramento;
import it.univaq.disim.oop.myclashofunivaq.domain.Torre;
import it.univaq.disim.oop.myclashofunivaq.domain.Turno;

public class GiocatoreUtenteServiceImpl implements GiocatoreUtenteService {
	
	private final PartitaService partitaService;
	private final PersonaggioService personaggioService;
	private final TurnoService turnoService;
	private final IncantesimoService incantesimoService;
	
	private List<Personaggio> personaggiAttaccantiTurno;
	private Personaggio personaggioAttaccante;
	private GridPaneGioco stradaAttaccante;

	public GiocatoreUtenteServiceImpl() {
		partitaService = new PartitaServiceImpl();
		personaggioService = new PersonaggioServiceImpl();
		turnoService = new TurnoServiceImpl();
		personaggiAttaccantiTurno = new ArrayList<>();
		incantesimoService = new IncantesimoServiceImpl();
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
		
		try {
			personaggioService.sceltaPosizionamento(personaggio, posizionamento);
		} catch (PosizionamentoException e) {
			e.printStackTrace();
		}
		
		turnoService.aggiornaElisir(turno, personaggio.getCostoSchieramento());
		
		return schieramento;
	}

	@Override
	public void preparaAttacco(Personaggio personaggioAttaccante, GridPaneGioco strada) throws AttaccoException{
		if(!(personaggioAttaccante.equals(this.personaggioAttaccante))) {
			this.personaggioAttaccante = personaggioAttaccante;
			this.stradaAttaccante = strada;
		}
			
		else
			throw new AttaccoException("NON PUOI ATTACCARE TE STESSO");
		
	}

	@Override
	public MossaGiocatore effettuaAttacco(Turno turno, Personaggio personaggioDaAttaccare, GridPaneGioco stradaAttaccato, Torre torreAvversaria) throws AttaccoException {
		if(personaggioAttaccante == null)
			throw new AttaccoException("MANCA IL PERSONAGGIO ATTACCANTE");
		if(personaggioDaAttaccare == null)
			throw new AttaccoException("MANCA IL PERSONAGGIO DA ATTACCARE");
		
		if( !((stradaAttaccante.toString() + "avversario").equals(stradaAttaccato.toString())  
				|| (stradaAttaccato.toString() + "avversario").equals(stradaAttaccante.toString()))  )
			throw new AttaccoException("I PERSONAGGIO SONO SU DUE STRADE DIVERSE");
		
		if(personaggiAttaccantiTurno.contains(personaggioAttaccante))
			throw new AttaccoException("QUESTO PERSONAGGIO HA già ATTACCATO");
		
		if("RendiInvulnerabile".equals(incantesimoService.checkPersonaggioTarget(personaggioDaAttaccare))) {
			throw new AttaccoException("QUESTO PERSONAGGIO è invulnerabile");
		}
		
		Attacco attacco = new Attacco();
		attacco.setPersonaggioAttaccante(personaggioAttaccante);
		attacco.setPersonaggioDaAttaccare(personaggioDaAttaccare);
		attacco.setTorreAttaccata(torreAvversaria);
		
		personaggioService.attacca(attacco);
		
		this.personaggiAttaccantiTurno.add(personaggioAttaccante);
		
		return attacco;
	}

	@Override
	public MossaGiocatore cambiaPosizionePersonaggio(Turno turno, Personaggio personaggio,
			PosizionamentoPersonaggio posizionamento) throws PosizionamentoException {
		
		CambioPosizionamentoPersonaggio cambioPosizione = new CambioPosizionamentoPersonaggio();
		cambioPosizione.setPersonaggio(personaggio);
		cambioPosizione.setNuovaPosizione(posizionamento);
		
		this.personaggioService.sceltaPosizionamento(personaggio, posizionamento);
		
		return cambioPosizione;
	}

	@Override
	public MossaGiocatore attaccaTorre(Turno turno, Personaggio personaggioAttaccante, GridPaneGioco strada,
			Torre torreAvversaria) throws AttaccoException {
		
		if(personaggioAttaccante == null)
			throw new AttaccoException("MANCA IL PERSONAGGIO ATTACCANTE");
		if(personaggiAttaccantiTurno.contains(personaggioAttaccante))
			throw new AttaccoException("QUESTO PERSONAGGIO HA già ATTACCATO");
		
		Attacco attacco = new Attacco();
		attacco.setPersonaggioAttaccante(personaggioAttaccante);
		attacco.setTorreAttaccata(torreAvversaria);
		
		this.personaggioService.attacca(attacco);
		
		this.personaggiAttaccantiTurno.add(personaggioAttaccante);
		
		return attacco;
	}
	
}
