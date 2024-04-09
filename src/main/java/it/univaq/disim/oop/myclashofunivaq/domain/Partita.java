package it.univaq.disim.oop.myclashofunivaq.domain;

import java.util.Set;
import java.util.HashSet;
import java.util.List;

public class Partita {
	private Integer ID;
	private Set<Giocatore> giocatori = new HashSet<>();
	
	private List<Turno> turni;
	
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