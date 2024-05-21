package it.univaq.disim.oop.myclashofunivaq.domain;

import java.io.Serializable;

public class Assassino extends Personaggio implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public Assassino() {
		super("");
	}
	
	public Assassino(String nome) {
		super(nome);
	}

}