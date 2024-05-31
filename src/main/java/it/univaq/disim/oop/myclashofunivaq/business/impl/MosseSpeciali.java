package it.univaq.disim.oop.myclashofunivaq.business.impl;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import it.univaq.disim.oop.myclashofunivaq.domain.MossaSpeciale;
import it.univaq.disim.oop.myclashofunivaq.domain.MossaSpecialeAzione;

public class MosseSpeciali implements Serializable {

	private static final long serialVersionUID = 1L;
	
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
		mossa3.setMossaImpl(new AttaccaDueVolteAzione());
		
		mosseSpeciali.add(mossa3);
		
		MossaSpeciale mossa4 = new MossaSpeciale("attaccaDiretto");
		mossa4.setManaRichiesto(2);
		mossa4.setMossaImpl(new AttaccaDirettamenteTorreAzione());
		
		mosseSpeciali.add(mossa4);
		
		MossaSpeciale mossa5 = new MossaSpeciale("ricaricaEnergiaStrada");
		mossa5.setManaRichiesto(2);
		mossa5.setMossaImpl(new RicaricaEnergiaStradaAzione());
		
		mosseSpeciali.add(mossa5);
		
	}
	
	
	public static Set<MossaSpeciale> getMosseSpeciali() {
		return mosseSpeciali;
	}
	
    // NON POSSO USARE LE LAMBDA PER QUESTE CLASSI perché non sono serializzabili
	private static class RicaricaEnergiaAzione implements MossaSpecialeAzione, Serializable {

		private static final long serialVersionUID = 1L;

		@Override
        public void esegui(MossaSpeciale mossaSpeciale) {
            mossaSpeciale.getPersonaggioTarget().setVita(mossaSpeciale.getPersonaggioTarget().getVita() + 10);
        }
    }
	private static class RicaricaEnergiaStradaAzione implements MossaSpecialeAzione, Serializable {

		private static final long serialVersionUID = 1L;

		@Override
		public void esegui(MossaSpeciale mossaSpeciale) {
			mossaSpeciale.getPersonaggioTarget().setVita(mossaSpeciale.getPersonaggioTarget().getVita() + 5);
		}
	}
	private static class DoppioArmorAzione implements MossaSpecialeAzione, Serializable {

		private static final long serialVersionUID = 1L;

		@Override
        public void esegui(MossaSpeciale mossaSpeciale) {
            mossaSpeciale.getPersonaggioTarget().setArmatura(mossaSpeciale.getPersonaggioTarget().getArmatura() * 2);
        }
    }
	private static class AttaccaDueVolteAzione implements MossaSpecialeAzione, Serializable {

		private static final long serialVersionUID = 1L;

		@Override
		public void esegui(MossaSpeciale mossaSpeciale) {
		}
	}
	private static class AttaccaDirettamenteTorreAzione implements MossaSpecialeAzione, Serializable {

		private static final long serialVersionUID = 1L;

		@Override
		public void esegui(MossaSpeciale mossaSpeciale) {
		}
	}
	
}