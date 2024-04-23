package it.univaq.disim.oop.myclashofunivaq.domain;

import java.io.Serializable;

public class Tank extends Personaggio implements Serializable {
	
	public Tank() {
        super(""); // Chiama il costruttore della superclasse con un valore predefinito per il nome
    }
	
	public Tank(String nome) {
		super(nome);
	}

}
