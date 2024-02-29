package it.univaq.disim.oop.myclashofunivaq.domain;

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
	
	public boolean inserisci_carta(Carta carta) {
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
	
	
}
