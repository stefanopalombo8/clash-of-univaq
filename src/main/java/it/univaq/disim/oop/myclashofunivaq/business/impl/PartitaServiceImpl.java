package it.univaq.disim.oop.myclashofunivaq.business.impl;

import java.util.ArrayList;
import java.util.List;

import it.univaq.disim.oop.myclashofunivaq.business.PartitaService;
import it.univaq.disim.oop.myclashofunivaq.domain.Giocatore;

public class PartitaServiceImpl implements PartitaService {
	
	private static List<Giocatore> giocatoriAggiunti = new ArrayList<>();
	
	@Override
	public List<Giocatore> giocatoriPartita() {
		List<Giocatore> listaToReturn = new ArrayList<>();
		listaToReturn.addAll(giocatoriAggiunti);
		return listaToReturn;
	}

	@Override
	public void aggiungiGiocatore(Giocatore giocatore) {
		giocatoriAggiunti.add(giocatore);
		
	}
}
