package it.univaq.disim.oop.myclashofunivaq.business.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.univaq.disim.oop.myclashofunivaq.business.MazzoService;
import it.univaq.disim.oop.myclashofunivaq.business.ResetStaticVariables;
import it.univaq.disim.oop.myclashofunivaq.configuration.CartaFactory;
import it.univaq.disim.oop.myclashofunivaq.configuration.Factory;
import it.univaq.disim.oop.myclashofunivaq.domain.Carta;
import it.univaq.disim.oop.myclashofunivaq.domain.Giocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.Mazzo;

public class MazzoServiceImpl implements MazzoService, ResetStaticVariables {
	
	private final CartaFactory cartaFactory = Factory.getInstance();
	
	private final int numCarteInMano = 4;
	private final int numCategorieMinime = 5;
	
	private static int index = 0;
	
	@Override
	public Mazzo creaMazzo(List<Carta> carteScelte) {
		Mazzo mazzo = new Mazzo();
		
		return inserisciCarte(mazzo, carteScelte) == true ? mazzo : null;
		
	}
	
	private boolean inserisciCarte(Mazzo mazzo, List<Carta> lista) {
		for(Carta carta : lista) {
			if(!this.inserisciCarta(mazzo, carta))
				return false;
		}
		
		return true;
	}
	
	private boolean inserisciCarta(Mazzo mazzo, Carta carta) {
		if(carta == null) 
			return false;
		
		Carta[] carte = mazzo.getCarte();
		
		for(int i = 0; i < carte.length; i++) {
			if(carte[i] == null) {
				carte[i] = carta;
				return true;
			}
		}
		
		return false;
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
		
		num_categorie_mazzo = categorie.size(); //si contano anche gli incantesimi
		
		if(num_categorie_mazzo >= numCategorieMinime)
			return true;
		
		return false;
	}

	@Override
	public Mazzo trovaMazzo(Giocatore giocatore) {
		return giocatore.getMazzo();
	}

	@Override
	public Carta[] mostraCarteMano(Mazzo mazzo) {
		Carta[] carteMano = new Carta[numCarteInMano];
		
		index = 0;
		
		Collections.shuffle(Arrays.asList(mazzo.getCarte())); // mischia le carte a caso
		
		for(; index < numCarteInMano; index++) {
			carteMano[index] = mazzo.getCarte()[index];
		}
		
		
		return carteMano;
	}

	@Override
	public Carta mostraProssimaCarta(Mazzo mazzo, List<Carta> carteManoCorrente) {
		Carta prossimaCarta = mazzo.getCarte()[index++ % mazzo.getCarte().length];
		boolean flag = this.contieneCarta(prossimaCarta, carteManoCorrente);
		
		while(flag) {
			prossimaCarta = mazzo.getCarte()[index++ % mazzo.getCarte().length];
			flag = this.contieneCarta(prossimaCarta, carteManoCorrente);
		}
		
		return prossimaCarta;
	}
	
	private boolean contieneCarta(Carta carta, List<Carta> carteManoCorrente) {
		return carteManoCorrente.stream().anyMatch(c -> c.getNome().equals(carta.getNome()));
	}

	@Override
	public void reset() {
		index = 0;
		
	}
}