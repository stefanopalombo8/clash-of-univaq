package it.univaq.disim.oop.myclashofunivaq.configuration;

import java.util.Set;

import it.univaq.disim.oop.myclashofunivaq.domain.Personaggio;

public interface PersonaggioFactory {
	
	Set<Personaggio> findAllPersonaggi();
	default <T extends Personaggio> void modellaPersonaggio(T personaggioEmpty) {};

}
