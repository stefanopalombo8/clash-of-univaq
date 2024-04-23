package it.univaq.disim.oop.myclashofunivaq.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Partita implements Serializable {
	private Integer ID;
	private Set<Giocatore> giocatori = new HashSet<>();
	private List<Turno> turni = new ArrayList<>();
	
	private boolean isRecuperata = false;
	private int numeroTotaleMosse = 0;
	private int numeroCarteInCampo = 0;
	private int valoreCarteInCampo = 0;
	
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
	public int getNumeroTotaleMosse() {
		return numeroTotaleMosse;
	}
	public void setNumeroTotaleMosse(int numeroTotaleMosse) {
		this.numeroTotaleMosse = numeroTotaleMosse;
	}
	public int getNumeroCarteInCampo() {
		return numeroCarteInCampo;
	}
	public void setNumeroCarteInCampo(int numeroCarteInCampo) {
		this.numeroCarteInCampo = numeroCarteInCampo;
	}
	public int getValoreCarteInCampo() {
		return valoreCarteInCampo;
	}
	public void setValoreCarteInCampo(int valoreCarteInCampo) {
		this.valoreCarteInCampo = valoreCarteInCampo;
	}
	public boolean isRecuperata() {
		return isRecuperata;
	}
	public void setRecuperata(boolean isRecuperata) {
		this.isRecuperata = isRecuperata;
	}
}