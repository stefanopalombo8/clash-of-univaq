package it.univaq.disim.oop.myclashofunivaq.domain;

import java.io.Serializable;

import javafx.scene.image.Image;

public abstract class Carta implements Cloneable, Serializable{
	
	private static final long serialVersionUID = 5505535608162922139L;
	private String nome;
	private int costoSchieramento;
	private transient Image immagineCarta;
	
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
	
	@Override
	public String toString() {
		return "Carta [nome=" + nome + "]";
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return (Carta) super.clone();
	}
	

}
