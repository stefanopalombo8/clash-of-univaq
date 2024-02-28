package it.univaq.disim.oop.myclashofunivaq.configuration;

import it.univaq.disim.oop.myclashofunivaq.domain.Carta;

public interface CartaFactory {
	
	<T extends Carta> void modellaCarta (T carta);
	<T extends Carta> String ricercaCategoriaEimpostaNome (T carta);
	
}
