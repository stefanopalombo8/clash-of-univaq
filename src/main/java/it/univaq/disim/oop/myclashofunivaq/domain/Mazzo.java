package it.univaq.disim.oop.myclashofunivaq.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Mazzo implements Serializable {

	private Carta[] carte;
	
	public Mazzo() {
		this.carte = new Carta[8];
	}
	public Carta[] getCarte() {
		return carte;
	}
	public void setCarte(Carta[] carte) {
		this.carte = carte;
	}
	
	@Override
	public String toString() {
		return "Mazzo [carte=" + Arrays.toString(carte) + "]";
	}	
}