package it.univaq.disim.oop.myclashofunivaq.domain;

import java.util.ArrayList;
import java.util.List;

public class Turno {
	private Integer numero;
	private int timer;
	private Giocatore giocatore;
	private double elisirGiocatore;
	private Stato stato;
	private List<MossaGiocatore> mosseGiocatore;
	
	public Turno(Giocatore giocatore) {
		this.giocatore = giocatore;
		this.mosseGiocatore = new ArrayList<>();
	}
	
	public Stato getStato() {
		return stato;
	}

	public void setStato(Stato stato) {
		this.stato = stato;
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

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
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
	  
}
