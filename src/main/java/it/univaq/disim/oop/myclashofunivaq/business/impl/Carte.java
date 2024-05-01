package it.univaq.disim.oop.myclashofunivaq.business.impl;

import java.util.HashSet;


import java.util.Set;

import it.univaq.disim.oop.myclashofunivaq.business.CartaService;
import it.univaq.disim.oop.myclashofunivaq.configuration.CartaFactory;
import it.univaq.disim.oop.myclashofunivaq.configuration.Factory;
import it.univaq.disim.oop.myclashofunivaq.domain.Carta;
import it.univaq.disim.oop.myclashofunivaq.domain.CuraPersonaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.Fulmine;
import it.univaq.disim.oop.myclashofunivaq.domain.Furia;
import it.univaq.disim.oop.myclashofunivaq.domain.Personaggio;
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
		
		CuraPersonaggio cura = new CuraPersonaggio("CuraPersonaggio");
		cartaFactory.modellaCarta(cura);
		carte.add(cura);
		
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

}