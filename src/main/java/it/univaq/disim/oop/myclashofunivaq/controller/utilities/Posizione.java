package it.univaq.disim.oop.myclashofunivaq.controller.utilities;

import java.io.Serializable;
import java.util.Objects;

public class Posizione implements Serializable {

	private static final long serialVersionUID = 1L;
	
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

	@Override
	public int hashCode() {
		return Objects.hash(colonna, riga);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Posizione other = (Posizione) obj;
		return colonna == other.colonna && riga == other.riga;
	}
	
}