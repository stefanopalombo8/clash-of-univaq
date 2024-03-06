package it.univaq.disim.oop.myclashofunivaq.business.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.univaq.disim.oop.myclashofunivaq.business.MazzoService;
import it.univaq.disim.oop.myclashofunivaq.configuration.CartaFactory;
import it.univaq.disim.oop.myclashofunivaq.configuration.Factory;
import it.univaq.disim.oop.myclashofunivaq.domain.Carta;
import it.univaq.disim.oop.myclashofunivaq.domain.Giocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.Mazzo;

public class MazzoServiceImpl implements MazzoService {
	
	private int num_categorie_minime = 5;
	private final CartaFactory cartaFactory = Factory.getInstance();
	
	@Override
	public Mazzo creaMazzo(List<Carta> carteScelte) {
		Mazzo mazzo = new Mazzo();
		
		return mazzo.inserisci_carte(carteScelte) == true ? mazzo : null;
		
	}
	
	@Override
	public void aggiungiMazzo(Mazzo mazzo, Giocatore giocatore) {
		giocatore.setMazzo(mazzo);
	}

	@Override
	public boolean controllaMazzo(Mazzo mazzo) {
		Set<String> categorie = new HashSet<>();
		int num_categorie_mazzo = 0;
		
		for(int i = 0; i < mazzo.getCarte().length; i++) {
			categorie.add(cartaFactory.ricercaCategoriaEimpostaNome(mazzo.getCarte()[i]));
		}
		
		for(String categoria : categorie) {
			if(!categoria.equals("Incantesimo"))
				num_categorie_mazzo++;	
		}
		
		if(num_categorie_mazzo >= num_categorie_minime)
			return true;
		
		return false;
	}
	
	
	
	

	

}
