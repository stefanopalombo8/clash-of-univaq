package it.univaq.disim.oop.myclashofunivaq.business.impl;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import it.univaq.disim.oop.myclashofunivaq.business.PersonaggioService;
import it.univaq.disim.oop.myclashofunivaq.business.ResetStaticVariables;
import it.univaq.disim.oop.myclashofunivaq.domain.Attacco;
import it.univaq.disim.oop.myclashofunivaq.domain.MossaSpeciale;
import it.univaq.disim.oop.myclashofunivaq.domain.Personaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.PosizionamentoPersonaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.Torre;

public class PersonaggioServiceImpl implements PersonaggioService, ResetStaticVariables{
	
	private static List<Personaggio> personaggiConMosseAttive = new ArrayList<>();
	
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
		
		if(attacco.getPersonaggioDaAttaccare() == null) { //ATTACCARE DIRETTAMENTE LA TORRE
			double danno = (double) attaccante.getDanno() / 100;
			BigDecimal uno = new BigDecimal(Double.toString(danno));
			
			Torre torreAttaccata = attacco.getTorreAttaccata();
			System.out.println("VITA TORRE PRIMA DELL'ATTACCO " + torreAttaccata.getVita());
			
			BigDecimal due = new BigDecimal(Double.toString(torreAttaccata.getVita()));
			
			BigDecimal risultato = due.subtract(uno);
			
			torreAttaccata.setVita(risultato.doubleValue());
			
			if(torreAttaccata.getVita() <= 0.0) 
				torreAttaccata.setVita(0);
			
			System.out.println("VITA TORRE DOPO L'ATTACCO " + torreAttaccata.getVita());
		}
		else {
			System.out.println("VITA ATTACCANTE " + attaccante.getVita() + " ARMATURA ATTACCATO " 
					+ attaccato.getArmatura() + " VITA ATTACCATO " + attaccato.getVita());
			
			attaccato.setArmatura(attaccato.getArmatura() - attaccante.getDanno());
			
			System.out.println("ARMATURA ATTACCATO " + attaccato.getArmatura());
			
			if(attaccato.getArmatura() <= 0) {
				int dannoVita = attaccato.getArmatura();
				attaccato.setArmatura(0);
				
				attaccato.setVita(attaccato.getVita() + dannoVita);
				
				if(attaccato.getVita() <= 0) {
					double dannoTorre = (double) attaccato.getVita() / 100;
					BigDecimal uno = new BigDecimal(Double.toString(dannoTorre));
					Torre torreAttaccata = attacco.getTorreAttaccata();
					
					System.out.println("VITA TORRE PRIMA DELL'ATTACCO " + torreAttaccata.getVita());
					
					BigDecimal due = new BigDecimal(Double.toString(torreAttaccata.getVita()));
					
					BigDecimal risultato = uno.add(due);
					
					torreAttaccata.setVita(risultato.doubleValue());
					
					if(torreAttaccata.getVita() <= 0.0) 
						torreAttaccata.setVita(0);
					
					System.out.println("VITA TORRE DOPO L'ATTACCO " + torreAttaccata.getVita());
					
				}
			}
				
			
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
		if(mossaSpeciale.getNome().equals("attaccaDueVolte") || 
				mossaSpeciale.getNome().equals("attaccaDiretto")) {
			personaggiConMosseAttive.add(personaggio);
		}
			
		System.out.println("MOSSA SPECIALE ATTIVATA");
		
	}
	
	@Override
	public void eseguiMossaSpeciale(Personaggio personaggio, List<Personaggio> listaPersonaggiTarget) throws ManaException {
		MossaSpeciale mossaSpeciale = personaggio.getMossaSpeciale();
		
		if(personaggio.getMana() < mossaSpeciale.getManaRichiesto())
			throw new ManaException("MANA INSUFFICIENTE");
		
		for(Personaggio p : listaPersonaggiTarget) {
			mossaSpeciale.esegui(p);
		}
		personaggio.setMana(personaggio.getMana() - mossaSpeciale.getManaRichiesto());
		System.out.println("MOSSA SPECIALE ATTIVATA");

	}

	@Override
	public List<Personaggio> getPersonaggiConMosseAttive() {
		return new ArrayList<>(personaggiConMosseAttive);
	}

	@Override
	public void rimuoviPersonaggioConMossaAttivo(Personaggio personaggio) {
		personaggiConMosseAttive.remove(personaggio);
	}

	@Override
	public void reset() {
		personaggiConMosseAttive.clear();
	}

	@Override
	public void resetMosseSpecialiAttive() {
		personaggiConMosseAttive.clear();
	}

}
