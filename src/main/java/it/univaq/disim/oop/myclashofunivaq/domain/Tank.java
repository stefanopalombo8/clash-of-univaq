package it.univaq.disim.oop.myclashofunivaq.domain;

import java.io.Serializable;

public class Tank extends Personaggio implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Tank() {
        super("");
    }
	public Tank(String nome) {
		super(nome);
	}
}