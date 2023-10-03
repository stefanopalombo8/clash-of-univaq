package it.univaq.disim.oop.myclashofunivaq.configuration;

import it.univaq.disim.oop.myclashofunivaq.domain.Personaggio;

public interface PersonaggioFactory {
	<T extends Personaggio> T creaPersonaggio(T personaggioEmpty);

}
