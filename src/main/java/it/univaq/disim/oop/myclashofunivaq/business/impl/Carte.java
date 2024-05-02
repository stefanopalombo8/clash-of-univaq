package it.univaq.disim.oop.myclashofunivaq.business.impl;

import java.util.HashSet;

import java.util.Set;

import it.univaq.disim.oop.myclashofunivaq.business.CartaService;
import it.univaq.disim.oop.myclashofunivaq.configuration.CartaFactory;
import it.univaq.disim.oop.myclashofunivaq.configuration.Factory;
import it.univaq.disim.oop.myclashofunivaq.domain.Assassino;
import it.univaq.disim.oop.myclashofunivaq.domain.BloccaAttacco;
import it.univaq.disim.oop.myclashofunivaq.domain.Carta;
import it.univaq.disim.oop.myclashofunivaq.domain.CuraPersonaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.Fulmine;
import it.univaq.disim.oop.myclashofunivaq.domain.Furia;
import it.univaq.disim.oop.myclashofunivaq.domain.Mago;
import it.univaq.disim.oop.myclashofunivaq.domain.RendiInvulnerabile;
import it.univaq.disim.oop.myclashofunivaq.domain.Tank;

public class Carte implements CartaService {
	
	private static Set<Carta> carte = new HashSet<>();
	private static CartaFactory cartaFactory = Factory.getInstance();
	
	static {
		Tank gigante = new Tank("GIGANTE");
		cartaFactory.modellaCarta(gigante);				
		carte.add(gigante); 
		
		Tank golem = new Tank("GOLEM");
		cartaFactory.modellaCarta(golem);
		carte.add(golem);
		
		Tank pekka = new Tank("PEKKA");
		cartaFactory.modellaCarta(pekka);
		carte.add(pekka);
		
		Assassino bombarolo = new Assassino("BOMBAROLO");
		cartaFactory.modellaCarta(bombarolo);
		carte.add(bombarolo);
		
		Assassino fantasma = new Assassino("FANTASMA");
		cartaFactory.modellaCarta(fantasma);
		carte.add(fantasma);
		
		Assassino minipekka = new Assassino("MINIPEKKA");
		cartaFactory.modellaCarta(minipekka);
		carte.add(minipekka);
		
		Mago curatrice = new Mago("CURATRICE");
		cartaFactory.modellaCarta(curatrice);
		carte.add(curatrice);
		
		Mago stregone = new Mago("STREGONEGHIACCIO");
		cartaFactory.modellaCarta(stregone);
		carte.add(stregone);
		
		Mago stregone2 = new Mago("STREGONEFUOCO");
		cartaFactory.modellaCarta(stregone2);
		carte.add(stregone2);
		
		CuraPersonaggio cura = new CuraPersonaggio("CuraPersonaggio");
		cartaFactory.modellaCarta(cura);
		carte.add(cura);
		
		BloccaAttacco blocca = new BloccaAttacco("BloccaAttacco");
		cartaFactory.modellaCarta(blocca);
		carte.add(blocca);
		
		RendiInvulnerabile invulnerabile = new RendiInvulnerabile("RendiInvulnerabile");
		cartaFactory.modellaCarta(invulnerabile);
		carte.add(invulnerabile);
		
		Fulmine fulmine = new Fulmine("Fulmine");
		cartaFactory.modellaCarta(fulmine);
		carte.add(fulmine);
		
		Furia furia = new Furia("Furia");
		cartaFactory.modellaCarta(furia);
		carte.add(furia);
	}

	@Override
	public Set<Carta> trovaTutteCarte() {
		return carte;
	}

	@Override
	public Carta cercaCarta(String nome) {
		for(Carta carta : carte) {
			if(carta.getNome().equals(nome))
				return carta;
		}
		return null;
	}

}