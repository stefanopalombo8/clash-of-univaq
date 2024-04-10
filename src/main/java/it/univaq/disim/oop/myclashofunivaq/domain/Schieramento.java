package it.univaq.disim.oop.myclashofunivaq.domain;

import it.univaq.disim.oop.myclashofunivaq.controller.utilitis.GridPaneGioco;

public class Schieramento implements MossaGiocatore {
	
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
}
