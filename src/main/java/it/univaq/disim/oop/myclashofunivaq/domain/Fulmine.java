package it.univaq.disim.oop.myclashofunivaq.domain;

public class Fulmine extends Incantesimo {

	private int danno;
	
	public Fulmine(String nome) {
		super(nome);
	}
	
	public int getDanno() {
		return danno;
	}
	public void setDanno(int danno) {
		this.danno = danno;
	}
	
	@Override
	public void esegui() {
		this.getPersonaggioTarget().setVita(this.getPersonaggioTarget().getVita() - danno);
	}
}