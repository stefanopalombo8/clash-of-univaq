package it.univaq.disim.oop.myclashofunivaq.business.impl;

import java.util.HashSet;

import java.util.Set;

import it.univaq.disim.oop.myclashofunivaq.business.PersonaggioService;
import it.univaq.disim.oop.myclashofunivaq.configuration.CartaFactory;
import it.univaq.disim.oop.myclashofunivaq.configuration.Factory;
import it.univaq.disim.oop.myclashofunivaq.domain.Personaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.Tank;

public class Personaggi implements PersonaggioService {
	
	private static Set<Personaggio> personaggi = new HashSet<>();
	private static CartaFactory personaggioFactory = Factory.getInstance();
	
	static {
		Tank gigante = new Tank("GIGANTE");
		personaggioFactory.modellaCarta(gigante);
		personaggi.add(gigante); 
		
		Tank golem = new Tank("GOLEM");
		personaggioFactory.modellaCarta(golem);
		personaggi.add(golem);
	}

	@Override
	public Set<Personaggio> trovaTuttiPersonaggi() {
		return personaggi;
	}

}
