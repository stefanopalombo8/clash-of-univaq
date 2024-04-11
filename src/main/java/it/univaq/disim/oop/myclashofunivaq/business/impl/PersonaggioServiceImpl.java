package it.univaq.disim.oop.myclashofunivaq.business.impl;

import it.univaq.disim.oop.myclashofunivaq.business.PersonaggioService;
import it.univaq.disim.oop.myclashofunivaq.domain.Personaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.PosizionamentoPersonaggio;

public class PersonaggioServiceImpl implements PersonaggioService{

	@Override
	public void sceltaPosizionamento(Personaggio personaggio, PosizionamentoPersonaggio posizionamento) {
		personaggio.setPosizionamento(posizionamento);
		
		if(posizionamento.toString().equals(PosizionamentoPersonaggio.DIFESA.toString()))
			personaggio.setArmatura(personaggio.getArmatura() * 2);
		
	}

}
