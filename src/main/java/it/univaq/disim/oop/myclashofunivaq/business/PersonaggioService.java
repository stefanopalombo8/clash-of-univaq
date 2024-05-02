package it.univaq.disim.oop.myclashofunivaq.business;

import java.util.List;

import it.univaq.disim.oop.myclashofunivaq.business.impl.ManaException;
import it.univaq.disim.oop.myclashofunivaq.business.impl.PosizionamentoException;
import it.univaq.disim.oop.myclashofunivaq.domain.Attacco;
import it.univaq.disim.oop.myclashofunivaq.domain.Personaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.PosizionamentoPersonaggio;

public interface PersonaggioService {
	
	void sceltaPosizionamento(Personaggio personaggio, PosizionamentoPersonaggio posizionamento) 
			throws PosizionamentoException;
	
	void eseguiMossaSpeciale(Personaggio personaggio) throws ManaException;
	
	void eseguiMossaSpeciale(Personaggio personaggio, List<Personaggio> listaPersonaggiTarget) throws ManaException;
	
	void attacca(Attacco attaco);
	
	List<Personaggio> getPersonaggiConMosseAttive();
	
	void rimuoviPersonaggioConMossaAttivo(Personaggio personaggio);
	
	void resetMosseSpecialiAttive();
}