package it.univaq.disim.oop.myclashofunivaq.controller.utilitis;

public class Posizione {
	private int riga;
	private int colonna;
	
	public Posizione(int colonna, int riga) {
		this.riga = riga;
		this.colonna = colonna;
	} 
	
	public int getRiga() {
		return riga;
	}
	@Override
	public String toString() {
		return "Posizione [riga=" + riga + ", colonna=" + colonna + "]";
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
	
}
