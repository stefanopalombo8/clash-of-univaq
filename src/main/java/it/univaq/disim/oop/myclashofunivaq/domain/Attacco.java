package it.univaq.disim.oop.myclashofunivaq.domain;

public class Attacco implements MossaGiocatore{
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
