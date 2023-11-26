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
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		Giocatore giocatore = (Giocatore) obj;
		return this.getNickname().equals(giocatore.getNickname());
	}
}
