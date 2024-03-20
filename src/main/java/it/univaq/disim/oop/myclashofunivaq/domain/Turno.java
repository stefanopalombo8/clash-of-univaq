package it.univaq.disim.oop.myclashofunivaq.domain;

public class Turno {
	private Integer numero;
	private int timer;
	private Giocatore giocatore;
	private int elisirGiocatore;
	//private List<MossaGiocatore> mosseGiocatore;
	
	public Turno(Giocatore giocatore) {
		this.giocatore = giocatore;
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

	public int getElisirGiocatore() {
		return elisirGiocatore;
	}

	public void setElisirGiocatore(int elisirGiocatore) {
		this.elisirGiocatore = elisirGiocatore;
	}
	  
}
