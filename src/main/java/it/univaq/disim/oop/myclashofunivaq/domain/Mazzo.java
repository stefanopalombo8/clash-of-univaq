package it.univaq.disim.oop.myclashofunivaq.domain;

import java.util.Arrays;
import java.util.List;

public class Mazzo {

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
	
	public boolean inserisci_carte(List<Carta> lista) {
		for(Carta carta : lista) {
			if(!this.inserisci_carta(carta))
				return false;
		}
		
		return true;
	}
	
	private boolean inserisci_carta(Carta carta) {
		if(carta == null) 
			return false;
		
		for(int i = 0; i < carte.length; i++) {
			if(carte[i] == null) {
				carte[i] = carta;
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return "Mazzo [carte=" + Arrays.toString(carte) + "]";
	}
	
}
