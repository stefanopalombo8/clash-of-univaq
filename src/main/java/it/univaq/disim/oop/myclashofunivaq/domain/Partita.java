package it.univaq.disim.oop.myclashofunivaq.domain;

import java.util.Set;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Partita implements Serializable {
	private Integer ID;
	private Set<Giocatore> giocatori = new HashSet<>();
	private List<Turno> turni = new ArrayList<>();
	
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public Set<Giocatore> getGiocatori() {
		return giocatori;
	}
	public void setGiocatori(Set<Giocatore> giocatori) {
		this.giocatori = giocatori;
	}
	public List<Turno> getTurni() {
		return turni;
	}
}