package it.univaq.disim.oop.myclashofunivaq.business.impl;

import java.util.ArrayList;
import java.util.List;

import it.univaq.disim.oop.myclashofunivaq.business.GiocatoreService;
import it.univaq.disim.oop.myclashofunivaq.business.IncantesimoService;
import it.univaq.disim.oop.myclashofunivaq.business.PersonaggioService;
import it.univaq.disim.oop.myclashofunivaq.business.TurnoService;
import it.univaq.disim.oop.myclashofunivaq.controller.utilities.GridPaneGioco;
import it.univaq.disim.oop.myclashofunivaq.domain.Attacco;
import it.univaq.disim.oop.myclashofunivaq.domain.CambioPosizionamentoPersonaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.Incantesimo;
import it.univaq.disim.oop.myclashofunivaq.domain.MossaGiocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.MossaSpeciale;
import it.univaq.disim.oop.myclashofunivaq.domain.Personaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.PosizionamentoPersonaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.Schieramento;
import it.univaq.disim.oop.myclashofunivaq.domain.Torre;
import it.univaq.disim.oop.myclashofunivaq.domain.Turno;
import it.univaq.disim.oop.myclashofunivaq.domain.nomicarte.IncantesimiNomi;

public class GiocatoreServiceImpl implements GiocatoreService {
	
	private final PersonaggioService personaggioService;
	private final TurnoService turnoService;
	private final IncantesimoService incantesimoService;
	
	private List<Personaggio> personaggiAttaccantiTurno;
	private Personaggio personaggioAttaccante;
	private GridPaneGioco stradaAttaccante;
	
	public GiocatoreServiceImpl() {
		this.personaggioService = new PersonaggioServiceImpl();
		this.turnoService = new TurnoServiceImpl();
		this.personaggiAttaccantiTurno = new ArrayList<>();
		this.incantesimoService = new IncantesimoServiceImpl();
	}


	@Override
	public MossaGiocatore effettuaSchieramento(Turno turno, Personaggio personaggio, GridPaneGioco strada, PosizionamentoPersonaggio posizionamento) {
		Schieramento schieramento = new Schieramento();
		schieramento.setCartaSchierata(personaggio);
		schieramento.setStrada(strada);
		
		try {
			this.personaggioService.sceltaPosizionamento(personaggio, posizionamento);
		} catch (PosizionamentoException e) {
		}
		
		this.turnoService.aggiornaElisir(turno, personaggio.getCostoSchieramento());
		
		return schieramento;
	}
	
	@Override
	public MossaGiocatore effettuaSchieramento(Turno turno, Incantesimo incantesimo) {
		Schieramento schieramento = new Schieramento();
		schieramento.setCartaSchierata(incantesimo);
		
		this.turnoService.aggiornaElisir(turno, incantesimo.getCostoSchieramento());
		
		return schieramento;
	}

	@Override
	public void preparaAttacco(Personaggio personaggioAttaccante, GridPaneGioco stradaAttaccante) throws AttaccoException{
		List<Incantesimo> incantesimiAttivi = this.incantesimoService.getIncantesimiAttivi(personaggioAttaccante);
		boolean match = incantesimiAttivi.stream().anyMatch(i -> i.getNome().equals(IncantesimiNomi.BloccaAttacco.toString()));
		
		if(match) { // se non c'è quel match gli incantesimi sono irrilevanti
			StringBuilder builder = new StringBuilder("QUESTO PERSONAGGIO HA ATTIVO ");
			for(Incantesimo incantesimo : incantesimiAttivi) {
				builder.append(incantesimo.getNome());
				builder.append(" ");
			}
			
			throw new AttaccoException(builder.toString());
		}
		
		if(!(personaggioAttaccante.equals(this.personaggioAttaccante))) {
			this.personaggioAttaccante = personaggioAttaccante;
			this.stradaAttaccante = stradaAttaccante;
		}
		else
			throw new AttaccoException("NON PUOI ATTACCARE TE STESSO");
		
	}

	@Override
	public MossaGiocatore effettuaAttacco(Turno turno, Personaggio personaggioDaAttaccare, GridPaneGioco stradaAttaccato, Torre torreAvversaria) throws AttaccoException {
		if(this.personaggioAttaccante == null)
			throw new AttaccoException("MANCA IL PERSONAGGIO ATTACCANTE");
		if(personaggioDaAttaccare == null)
			throw new AttaccoException("MANCA IL PERSONAGGIO DA ATTACCARE");
		
		if( !((this.stradaAttaccante.toString() + "avversario").equals(stradaAttaccato.toString())  
				|| (stradaAttaccato.toString() + "avversario").equals(this.stradaAttaccante.toString()))  )
			throw new AttaccoException("I PERSONAGGIO SONO SU DUE STRADE DIVERSE");
		
		if(this.personaggiAttaccantiTurno.contains(personaggioAttaccante))
			throw new AttaccoException("QUESTO PERSONAGGIO HA già ATTACCATO");
		
		List<Incantesimo> incantesimiAttivi = this.incantesimoService.getIncantesimiAttivi(personaggioDaAttaccare);
		boolean match = incantesimiAttivi.stream().anyMatch(i -> i.getNome().equals(IncantesimiNomi.RendiInvulnerabile.toString()));
		
		if(match) { // se non c'è quel match gli incantesimi sono irrilevanti
			StringBuilder builder = new StringBuilder("QUESTO PERSONAGGIO HA ATTIVO ");
			for(Incantesimo incantesimo : incantesimiAttivi) {
				builder.append(incantesimo.getNome());
				builder.append(" ");
			}
			
			throw new AttaccoException(builder.toString());
		}
		
		Attacco attacco = new Attacco();
		attacco.setPersonaggioAttaccante(this.personaggioAttaccante);
		attacco.setPersonaggioDaAttaccare(personaggioDaAttaccare);
		attacco.setTorreAttaccata(torreAvversaria);
		
		this.personaggioService.attacca(attacco);
			
		if(this.personaggioService.getPersonaggiConMosseAttive().contains(this.personaggioAttaccante)) {
			MossaSpeciale mossaSpecialeAttaccante = this.personaggioAttaccante.getMossaSpeciale();
			if(mossaSpecialeAttaccante.getNome().equals("attaccaDueVolte")) {
				this.personaggioService.rimuoviPersonaggioConMossaAttivo(this.personaggioAttaccante);
			}
			else
				this.personaggiAttaccantiTurno.add(personaggioAttaccante);	
		}
		else 
			this.personaggiAttaccantiTurno.add(this.personaggioAttaccante);
		
		
			
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
		if(this.personaggiAttaccantiTurno.contains(personaggioAttaccante))
			throw new AttaccoException("QUESTO PERSONAGGIO HA già ATTACCATO");
		
		List<Incantesimo> incantesimiAttivi = this.incantesimoService.getIncantesimiAttivi(personaggioAttaccante);
		boolean match = incantesimiAttivi.stream().anyMatch(i -> i.getNome().equals(IncantesimiNomi.BloccaAttacco.toString()));
		
		if(match) {
			StringBuilder builder = new StringBuilder("QUESTO PERSONAGGIO HA ATTIVO ");
			for(Incantesimo incantesimo : incantesimiAttivi) {
				builder.append(incantesimo.getNome());
				builder.append(" ");
			}
			
			throw new AttaccoException(builder.toString());
		}
		
		
		Attacco attacco = new Attacco();
		attacco.setPersonaggioAttaccante(personaggioAttaccante);
		attacco.setTorreAttaccata(torreAvversaria);
		
		this.personaggioService.attacca(attacco);
		
		if(personaggioService.getPersonaggiConMosseAttive().contains(personaggioAttaccante)) {
			MossaSpeciale mossaSpecialeAttaccante = personaggioAttaccante.getMossaSpeciale();
			if(mossaSpecialeAttaccante.getNome().equals("attaccaDueVolte")) {
				this.personaggioService.rimuoviPersonaggioConMossaAttivo(personaggioAttaccante);
			}
			else
				this.personaggiAttaccantiTurno.add(personaggioAttaccante);
		}
		else 
			this.personaggiAttaccantiTurno.add(personaggioAttaccante);
		
		return attacco;
	}
	
}