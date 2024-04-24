package it.univaq.disim.oop.myclashofunivaq.domain;

import java.io.Serializable;

public class CambioPosizionamentoPersonaggio implements MossaGiocatore, Serializable {
	private Personaggio personaggio;
	private PosizionamentoPersonaggio nuovaPosizione;
	
	public Personaggio getPersonaggio() {
		return personaggio;
	}
	public void setPersonaggio(Personaggio personaggio) {
		this.personaggio = personaggio;
	}
	public PosizionamentoPersonaggio getNuovaPosizione() {
		return nuovaPosizione;
	}
	public void setNuovaPosizione(PosizionamentoPersonaggio nuovaPosizione) {
		this.nuovaPosizione = nuovaPosizione;
	}
	@Override
	public String toString() {
		return "CambioPosizionamentoPersonaggio [personaggio=" + personaggio + ", nuovaPosizione=" + nuovaPosizione
				+ "]";
	}
	
	
	
}
