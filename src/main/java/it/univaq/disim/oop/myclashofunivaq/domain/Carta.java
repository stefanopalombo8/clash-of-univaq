package it.univaq.disim.oop.myclashofunivaq.domain;

import javafx.scene.image.Image;

public abstract class Carta {
	private String nome;
	private int costoSchieramento;
	private Image immagineCarta;
	
	public Carta(String nome) {
		this.nome = nome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
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
