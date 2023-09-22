package it.univaq.disim.oop.myclashofunivaq.domain;

public class MossaSpeciale {
	private String nome;
	private MossaSpecialeInterface mossaImpl;
	private Personaggio personaggioTarget;
	
	public MossaSpeciale(String nome, MossaSpecialeInterface mossaImpl) {
		this.nome = nome;
		this.mossaImpl = mossaImpl;
	}
	
	public MossaSpecialeInterface getMossaImpl() {
		return mossaImpl;
	}

	public void setMossaImpl(MossaSpecialeInterface mossaImpl) {
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
	
	public void esegui(Personaggio personaggioTarget) { // oppure booleana per riscontro
		this.personaggioTarget = personaggioTarget;
		mossaImpl.esegui(this);
	}
}
