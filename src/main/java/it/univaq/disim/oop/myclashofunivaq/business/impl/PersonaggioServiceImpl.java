package it.univaq.disim.oop.myclashofunivaq.business.impl;

import it.univaq.disim.oop.myclashofunivaq.business.PersonaggioService;
import it.univaq.disim.oop.myclashofunivaq.controller.utilitis.GridPaneGioco;
import it.univaq.disim.oop.myclashofunivaq.domain.Attacco;
import it.univaq.disim.oop.myclashofunivaq.domain.Personaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.PosizionamentoPersonaggio;

public class PersonaggioServiceImpl implements PersonaggioService{

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
		if(attacco.getTorreAttaccata() == null) {
			System.out.println("VITA ATTACCANTE " + attacco.getPersonaggioAttaccante().getVita() + 
					" VITA ATTACCATO " + attacco.getPersonaggioDaAttaccare().getVita());
			
			attacco.getPersonaggioDaAttaccare().setVita(attacco.getPersonaggioDaAttaccare().getVita() -  
					attacco.getPersonaggioAttaccante().getDanno());
			
			
			System.out.println("VITA ATTACCATO " + attacco.getPersonaggioDaAttaccare().getVita());
		}
		else { //attacca direttamente la torre
			double danno = (double) attacco.getPersonaggioAttaccante().getDanno() / 100;
			System.out.println("danno " + danno);
			
			System.out.println("vita torre " + attacco.getTorreAttaccata().getVita());
			attacco.getTorreAttaccata().setVita(attacco.getTorreAttaccata().getVita() - 
					danno);
			
			System.out.println("VITA TORRE " + attacco.getTorreAttaccata().getVita());
		}
			
	}

}
