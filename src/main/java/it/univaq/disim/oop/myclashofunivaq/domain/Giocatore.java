package it.univaq.disim.oop.myclashofunivaq.domain;

public abstract class Giocatore {

	private String nickname;

	public Giocatore (String nickname) {
		this.nickname = nickname;
	}
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
