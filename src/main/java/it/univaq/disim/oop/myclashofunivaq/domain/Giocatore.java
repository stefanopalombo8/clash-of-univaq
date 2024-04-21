package it.univaq.disim.oop.myclashofunivaq.domain;

import java.io.Serializable;
import java.util.Objects;

public abstract class Giocatore implements Serializable {

	private String nickname;
	private Mazzo mazzo;

	public Giocatore (String nickname) {
		this.nickname = nickname;
	}
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		Giocatore giocatore = (Giocatore) obj;
		return this.getNickname().equals(giocatore.getNickname());
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(nickname);
	}

	public Mazzo getMazzo() {
		return mazzo;
	}

	public void setMazzo(Mazzo mazzo) {
		this.mazzo = mazzo;
	}
	
}
