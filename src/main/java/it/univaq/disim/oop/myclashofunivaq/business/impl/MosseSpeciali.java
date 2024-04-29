package it.univaq.disim.oop.myclashofunivaq.business.impl;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import it.univaq.disim.oop.myclashofunivaq.domain.MossaSpeciale;
import it.univaq.disim.oop.myclashofunivaq.domain.MossaSpecialeAzione;

public class MosseSpeciali implements Serializable {
	private static Set<MossaSpeciale> mosseSpeciali = new HashSet<>();
	
	static {
		
		MossaSpeciale mossa1 = new MossaSpeciale("ricaricaEnergia");
		mossa1.setManaRichiesto(1);
		mossa1.setMossaImpl(new RicaricaEnergiaAzione());
		
		mosseSpeciali.add(mossa1);
		
		MossaSpeciale mossa2 = new MossaSpeciale("doppioArmor");
		mossa2.setManaRichiesto(2);
		mossa2.setMossaImpl(new DoppioArmorAzione());
		
		mosseSpeciali.add(mossa2);
		
		MossaSpeciale mossa3 = new MossaSpeciale("attaccaDueVolte");
		mossa3.setManaRichiesto(1);
		mossa3.setMossaImpl(new AttaccaDueVolte());
		
		mosseSpeciali.add(mossa3);
		
	}
	
	
	public static Set<MossaSpeciale> getMosseSpeciali() {
		return mosseSpeciali;
	}
	
	private static class RicaricaEnergiaAzione implements MossaSpecialeAzione, Serializable {
        @Override
        public void esegui(MossaSpeciale mossa) {
            mossa.getPersonaggioTarget().setVita( mossa.getPersonaggioTarget().getVita() + 10);
        }
    }
	
	private static class DoppioArmorAzione implements MossaSpecialeAzione, Serializable {
        @Override
        public void esegui(MossaSpeciale mossa) {
            mossa.getPersonaggioTarget().setArmatura(mossa.getPersonaggioTarget().getArmatura() * 2);
        }
    }
	
	public static class AttaccaDueVolte implements MossaSpecialeAzione, Serializable {
		@Override
		public void esegui(MossaSpeciale mossaSpeciale) {
		}
	}
}
