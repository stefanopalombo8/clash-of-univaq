package it.univaq.disim.oop.myclashofunivaq.domain;

import java.io.Serializable;

public class Assassino extends Personaggio implements Serializable{

	public Assassino() {
		super("");
	}
	
	public Assassino(String nome) {
		super(nome);
	}

}