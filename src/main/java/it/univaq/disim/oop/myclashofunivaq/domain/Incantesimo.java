package it.univaq.disim.oop.myclashofunivaq.domain;

import java.io.Serializable;

public abstract class Incantesimo extends Carta implements Serializable {
	
	private Personaggio personaggioTarget;
	
	public Incantesimo(String nome) {
		super(nome);
	}
	public Personaggio getPersonaggioTarget() {
		return personaggioTarget;
	}
	public void setPersonaggioTarget(Personaggio personaggioTarget) {
		this.personaggioTarget = personaggioTarget;
	}
	
	public abstract void esegui();
	
}