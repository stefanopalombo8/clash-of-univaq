package it.univaq.disim.oop.myclashofunivaq.business.impl;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import it.univaq.disim.oop.myclashofunivaq.domain.MossaSpeciale;

public class MosseSpeciali implements Serializable {
	private static Set<MossaSpeciale> mosseSpeciali = new HashSet<>();
	
	static {
//		MossaSpeciale mossa1 = new MossaSpeciale("ricaricaEnergia", 
//				(mossa) -> {
//					//System.out.println("hai attivato " + mossa.getNome());
//					mossa.getPersonaggioTarget().setVita(100);
//				});
		MossaSpeciale mossa1 = new MossaSpeciale("ricaricaEnergia",null);
		
		mosseSpeciali.add(mossa1);
	}
	
	
	public static Set<MossaSpeciale> getMosseSpeciali() {
		return mosseSpeciali;
	}
}
