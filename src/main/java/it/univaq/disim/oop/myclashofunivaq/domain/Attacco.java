package it.univaq.disim.oop.myclashofunivaq.domain;

import java.io.Serializable;

public class Attacco implements MossaGiocatore, Serializable {
	private Personaggio personaggioAttaccante;
	private Personaggio personaggioDaAttaccare;
	private Torre torreAttaccata;
	
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
	public Torre getTorreAttaccata() {
		return torreAttaccata;
	}
	public void setTorreAttaccata(Torre torreAttaccata) {
		this.torreAttaccata = torreAttaccata;
	}
	@Override
	public String toString() {
		return "Attacco [personaggioAttaccante=" + personaggioAttaccante + ", personaggioDaAttaccare="
				+ personaggioDaAttaccare + ", torreAttaccata=" + torreAttaccata + "]";
	}
}