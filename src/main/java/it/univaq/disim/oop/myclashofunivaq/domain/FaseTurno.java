package it.univaq.disim.oop.myclashofunivaq.domain;

import java.io.Serializable;

public enum FaseTurno implements Serializable {
	Schieramento, // nel caso di personaggio è inclusa la scelta della posizione
	Difesa,
	Attacco
}
