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
		personaggioService = new PersonaggioServiceImpl();
		turnoService = new TurnoServiceImpl();
		personaggiAttaccantiTurno = new ArrayList<>();
		incantesimoService = new IncantesimoServiceImpl();
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
		List<Incantesimo> incantesimiAttivi = incantesimoService.getIncantesimiAttivi(personaggioAttaccante);
		boolean match = incantesimiAttivi.stream().anyMatch(i -> i.getNome().equals(IncantesimiNomi.BloccaAttacco.toString()));
		
		if(!incantesimiAttivi.isEmpty() && match) { // se non c'è quel match gli incantesimi sono irrilevanti
			StringBuilder builder = new StringBuilder("QUESTO PERSONAGGIO HA ATTIVO ");
			for(Incantesimo incantesimo : incantesimiAttivi) {
				builder.append(incantesimo.getNome());
				builder.append(" ");
			}
			
			throw new AttaccoException(builder.toString());
		}
		
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
		
		List<Incantesimo> incantesimiAttivi = incantesimoService.getIncantesimiAttivi(personaggioDaAttaccare);
		boolean match = incantesimiAttivi.stream().anyMatch(i -> i.getNome().equals(IncantesimiNomi.RendiInvulnerabile.toString()));
		if(!incantesimiAttivi.isEmpty() && match) { // se non c'è quel match gli incantesimi sono irrilevanti
			StringBuilder builder = new StringBuilder("QUESTO PERSONAGGIO HA ATTIVO ");
			for(Incantesimo incantesimo : incantesimiAttivi) {
				builder.append(incantesimo.getNome());
				builder.append(" ");
			}
			
			throw new AttaccoException(builder.toString());
		}
		
		Attacco attacco = new Attacco();
		attacco.setPersonaggioAttaccante(personaggioAttaccante);
		attacco.setPersonaggioDaAttaccare(personaggioDaAttaccare);
		attacco.setTorreAttaccata(torreAvversaria);
		
		personaggioService.attacca(attacco);
		
		
		if(personaggioService.getPersonaggiConMosseAttive().contains(personaggioAttaccante)) {
			MossaSpeciale mossaSpecialeAttaccante = personaggioAttaccante.getMossaSpeciale();
			if(mossaSpecialeAttaccante.getNome().equals("attaccaDueVolte")) {
				this.personaggioService.rimuoviPersonaggioConMossaAttivo(personaggioAttaccante);
			}
			
		}
		else 
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
		
		List<Incantesimo> incantesimiAttivi = incantesimoService.getIncantesimiAttivi(personaggioAttaccante);
		boolean match = incantesimiAttivi.stream().anyMatch(i -> i.getNome().equals(IncantesimiNomi.BloccaAttacco.toString()));
		
		if(!incantesimiAttivi.isEmpty() && match) {
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
			
		}
		else 
			this.personaggiAttaccantiTurno.add(personaggioAttaccante);
		
		return attacco;
	}

	@Override
	public MossaGiocatore effettuaSchieramentoIncantesimo(Turno turno, Incantesimo incantesimo) {
		Schieramento schieramento = new Schieramento();
		schieramento.setCartaSchierata(incantesimo);
		
		turnoService.aggiornaElisir(turno, incantesimo.getCostoSchieramento());
		
		return schieramento;
	}
	
}
