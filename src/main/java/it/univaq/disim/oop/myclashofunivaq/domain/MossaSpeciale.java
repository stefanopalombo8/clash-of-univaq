package it.univaq.disim.oop.myclashofunivaq.domain;

import java.io.Serializable;

public class MossaSpeciale implements Serializable {
	private String nome;
	private MossaSpecialeAzione mossaImpl;
	private Personaggio personaggioTarget;
	private int manaRichiesto;
	
	public MossaSpeciale(String nome) {
		this.nome = nome;
	}
	
	public MossaSpeciale(String nome, MossaSpecialeAzione mossaImpl) {
		this.nome = nome;
		this.mossaImpl = mossaImpl;
	}
	
	public MossaSpecialeAzione getMossaImpl() {
		return mossaImpl;
	}

	public void setMossaImpl(MossaSpecialeAzione mossaImpl) {
		this.mossaImpl = mossaImpl;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public Personaggio getPersonaggioTarget() {
		return personaggioTarget;
	}

	public void setPersonaggioTarget(Personaggio personaggioTarget) {
		this.personaggioTarget = personaggioTarget;
	}
	
	public int getManaRichiesto() {
		return manaRichiesto;
	}

	public void setManaRichiesto(int manaRichiesto) {
		this.manaRichiesto = manaRichiesto;
	}

	public void esegui(Personaggio personaggioTarget) { // oppure booleana per riscontro
		this.personaggioTarget = personaggioTarget;
		mossaImpl.esegui(this);
	}
}
