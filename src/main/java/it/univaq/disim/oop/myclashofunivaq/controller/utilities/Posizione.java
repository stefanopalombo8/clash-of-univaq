package it.univaq.disim.oop.myclashofunivaq.controller.utilities;

import java.io.Serializable;

public class Posizione implements Serializable {
	private int riga;
	private int colonna;
	
	public Posizione(int colonna, int riga) {
		this.riga = riga;
		this.colonna = colonna;
	} 
	
	public int getRiga() {
		return riga;
	}
	public void setRiga(int riga) {
		this.riga = riga;
	}
	public int getColonna() {
		return colonna;
	}
	public void setColonna(int colonna) {
		this.colonna = colonna;
	}
	@Override
	public String toString() {
		return "Posizione [riga=" + riga + ", colonna=" + colonna + "]";
	}
	
}