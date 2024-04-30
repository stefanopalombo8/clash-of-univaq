package it.univaq.disim.oop.myclashofunivaq.domain;

import java.io.Serializable;

public class Tank extends Personaggio implements Serializable {
	
	public Tank() {
        super("");
    }
	public Tank(String nome) {
		super(nome);
	}
}