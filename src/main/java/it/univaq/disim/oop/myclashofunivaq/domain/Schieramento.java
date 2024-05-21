package it.univaq.disim.oop.myclashofunivaq.domain;

import java.io.Serializable;

import it.univaq.disim.oop.myclashofunivaq.controller.utilities.GridPaneGioco;

public class Schieramento implements MossaGiocatore, Serializable {

	private static final long serialVersionUID = 1L;
	
	private Carta cartaSchierata;
	private GridPaneGioco strada;	
	
	public Carta getCartaSchierata() {
		return cartaSchierata;
	}
	public void setCartaSchierata(Carta cartaSchierata) {
		this.cartaSchierata = cartaSchierata;
	}
	public GridPaneGioco getStrada() {
		return strada;
	}
	public void setStrada(GridPaneGioco strada) {
		this.strada = strada;
	}
	
	@Override
	public String toString() {
		return "Schieramento [cartaSchierata=" + cartaSchierata + ", strada=" + strada + "]";
	}
}