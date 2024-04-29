package it.univaq.disim.oop.myclashofunivaq.business.impl;

import java.util.ArrayList;
import java.util.List;

import it.univaq.disim.oop.myclashofunivaq.business.PersonaggioService;
import it.univaq.disim.oop.myclashofunivaq.business.ResetStaticVariables;
import it.univaq.disim.oop.myclashofunivaq.controller.utilitis.GridPaneGioco;
import it.univaq.disim.oop.myclashofunivaq.domain.Attacco;
import it.univaq.disim.oop.myclashofunivaq.domain.MossaSpeciale;
import it.univaq.disim.oop.myclashofunivaq.domain.Personaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.PosizionamentoPersonaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.Torre;

public class PersonaggioServiceImpl implements PersonaggioService, ResetStaticVariables{
	
	private static List<Personaggio> personaggioConMosseAttive = new ArrayList<>();
	
	@Override
	public void sceltaPosizionamento(Personaggio personaggio, PosizionamentoPersonaggio posizionamento) 
			throws PosizionamentoException{
		if(personaggio.getPosizionamento() == null) // è stato appena schierato
			personaggio.setPosizionamento(posizionamento);
		
		else if(personaggio.getPosizionamento().equals(PosizionamentoPersonaggio.DIFESA) &&
				posizionamento.equals(PosizionamentoPersonaggio.DIFESA)) 
			throw new PosizionamentoException("Il personaggio è già in posizione di difesa");
		
		
		if(posizionamento.equals(PosizionamentoPersonaggio.DIFESA))
			personaggio.setArmatura(personaggio.getArmatura() * 2);
		
		personaggio.setPosizionamento(posizionamento);
		
	}

	@Override
	public void attacca(Attacco attacco) {
		Personaggio attaccante = attacco.getPersonaggioAttaccante();
		Personaggio attaccato = attacco.getPersonaggioDaAttaccare();
		
		if(attacco.getPersonaggioDaAttaccare() == null) {
			double danno = (double) attaccante.getDanno() / 100;
			System.out.println("danno " + danno);
			
			System.out.println("vita torre " + attacco.getTorreAttaccata().getVita());
			attacco.getTorreAttaccata().setVita(attacco.getTorreAttaccata().getVita() - 
					danno);
			
			System.out.println("VITA TORRE " + attacco.getTorreAttaccata().getVita());
		}
		else {
			System.out.println("VITA ATTACCANTE " + attaccante.getVita() + 
					" VITA ATTACCATO " + attaccato.getVita());
			
			if(attaccato.getArmatura() <= 0) {
				attaccato.setVita(attaccato.getVita() -  attaccante.getDanno());
				if(attaccato.getVita() <= 0) {
					double danno = (double) attaccato.getVita() / 100;
					Torre torreAttaccata = attacco.getTorreAttaccata();
					
					torreAttaccata.setVita(torreAttaccata.getVita() 
							+ danno);
					
					if(torreAttaccata.getVita() < 0)
						torreAttaccata.setVita(0);
						
				}
			}
			else
				attaccato.setArmatura(attaccato.getArmatura() - attaccante.getDanno());
			
			System.out.println("VITA ATTACCATO " + attacco.getPersonaggioDaAttaccare().getVita());
		}
			
	}

	@Override
	public void eseguiMossaSpeciale(Personaggio personaggio) throws ManaException {
		MossaSpeciale mossaSpeciale = personaggio.getMossaSpeciale();
		
		if(personaggio.getMana() < mossaSpeciale.getManaRichiesto())
			throw new ManaException("MANA INSUFFICIENTE");

		mossaSpeciale.esegui(personaggio);
		personaggio.setMana(personaggio.getMana() - mossaSpeciale.getManaRichiesto());
		if(mossaSpeciale.getNome().equals("attaccaDueVolte")) {
			personaggioConMosseAttive.add(personaggio);
		}
			
		System.out.println("MOSSA SPECIALE ATTIVATA");
		
	}

	@Override
	public List<Personaggio> getPersonaggiConMosseAttive() {
		return new ArrayList<>(personaggioConMosseAttive);
	}

	@Override
	public void rimuoviPersonaggioConMossaAttivo(Personaggio personaggio) {
		personaggioConMosseAttive.remove(personaggio);
	}

	@Override
	public void reset() {
		personaggioConMosseAttive.clear();
	}

	@Override
	public void resetMosseSpecialiAttive() {
		personaggioConMosseAttive.clear();
	}

}
