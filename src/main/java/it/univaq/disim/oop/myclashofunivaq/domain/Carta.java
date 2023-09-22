package it.univaq.disim.oop.myclashofunivaq.domain;

import javafx.scene.image.Image;

public abstract class Carta {
	private int costoSchieramento;
	private Image immagineCarta;
	
	public int getCostoSchieramento() {
		return costoSchieramento;
	}
	public void setCostoSchieramento(int costoSchieramento) {
		this.costoSchieramento = costoSchieramento;
	}
	public Image getImmagineCarta() {
		return immagineCarta;
	}
	public void setImmagineCarta(Image immagineCarta) {
		this.immagineCarta = immagineCarta;
	}
	

}
