package it.univaq.disim.oop.myclashofunivaq.domain;

import java.io.Serializable;

public class Attacco implements MossaGiocatore, Serializable{
	private Personaggio personaggioAttaccante;
	private Personaggio personaggioDaAttaccare;
	
	public Personaggio getPersonaggioAttaccante() {
		return personaggioAttaccante;
	}
	public void setPersonaggioAttaccante(Personaggio personaggioAttaccante) {
		this.personaggioAttaccante = personaggioAttaccante;
	}
	public Personaggio getPersonaggioDaAttaccare() {
		return personaggioDaAttaccare;
	}
	public void setPersonaggioDaAttaccare(Personaggio personaggioDaAttaccare) {
		this.personaggioDaAttaccare = personaggioDaAttaccare;
	}

}
