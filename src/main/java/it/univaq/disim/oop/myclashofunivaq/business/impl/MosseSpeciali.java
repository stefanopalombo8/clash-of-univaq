package it.univaq.disim.oop.myclashofunivaq.business.impl;

import java.util.HashSet;
import java.util.Set;

import it.univaq.disim.oop.myclashofunivaq.domain.MossaSpeciale;

public class MosseSpeciali {
	private static Set<MossaSpeciale> mosseSpeciali = new HashSet<>();
	
	static {
		MossaSpeciale mossa1 = new MossaSpeciale("ricaricaEnergia", 
				(mossa) -> {
					//System.out.println("hai attivato " + mossa.getNome());
					mossa.getPersonaggioTarget().setVita(100);
				});
		mosseSpeciali.add(mossa1);
	}
	
	
	public static Set<MossaSpeciale> getMosseSpeciali() {
		return mosseSpeciali;
	}
}
