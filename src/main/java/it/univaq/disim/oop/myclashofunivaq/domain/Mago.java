package it.univaq.disim.oop.myclashofunivaq.domain;

import java.io.Serializable;

public class Mago extends Personaggio implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public Mago() {
		super("");
	}
	public Mago(String nome) {
		super(nome);
	}

}