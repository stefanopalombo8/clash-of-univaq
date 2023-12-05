package it.univaq.disim.oop.myclashofunivaq.domain;

public class GiocatoreUtente extends Giocatore {

	public GiocatoreUtente(String nickname) {
		super(nickname);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		Giocatore giocatore = (Giocatore) obj;
		System.out.println("giocatore corrente " + this.getNickname() + " giocatore casted: " + giocatore.getNickname());
		return this.getNickname().equals(giocatore.getNickname());
	}
	
	
}
