package it.univaq.disim.oop.myclashofunivaq.domain;

public abstract class Incantesimo extends Carta{
	private Personaggio personaggioTarget;
	
	public Incantesimo(String nome) {
		super(nome);
		// TODO Auto-generated constructor stub
	}

	public Personaggio getPersonaggioTarget() {
		return personaggioTarget;
	}

	public void setPersonaggioTarget(Personaggio personaggioTarget) {
		this.personaggioTarget = personaggioTarget;
	}
	
	public abstract void esegui();
	
}
