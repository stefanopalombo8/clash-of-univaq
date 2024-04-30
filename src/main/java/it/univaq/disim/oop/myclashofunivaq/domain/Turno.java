package it.univaq.disim.oop.myclashofunivaq.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Turno implements Serializable {
	private Integer numero;
	private FaseTurno fase;
	private Giocatore giocatore;
	private Torre torreGiocatore;
	private double elisirGiocatore;
	private List<MossaGiocatore> mosseGiocatore;
	
	public Turno(Giocatore giocatore) {
		this.giocatore = giocatore;
		this.mosseGiocatore = new ArrayList<>();
	}

	public List<MossaGiocatore> getMosseGiocatore() {
		return mosseGiocatore;
	}

	public void setMosseGiocatore(List<MossaGiocatore> mosseGiocatore) {
		this.mosseGiocatore = mosseGiocatore;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Giocatore getGiocatore() {
		return giocatore;
	}

	public void setGiocatore(Giocatore giocatore) {
		this.giocatore = giocatore;
	}

	public double getElisirGiocatore() {
		return elisirGiocatore;
	}

	public void setElisirGiocatore(double elisirGiocatore) {
		this.elisirGiocatore = elisirGiocatore;
	}
	
	public FaseTurno getFase() {
		return fase;
	}

	public void setFase(FaseTurno fase) {
		this.fase = fase;
	}
	
	public Torre getTorreGiocatore() {
		return torreGiocatore;
	}

	public void setTorreGiocatore(Torre torreGiocatore) {
		this.torreGiocatore = torreGiocatore;
	}
	  
}