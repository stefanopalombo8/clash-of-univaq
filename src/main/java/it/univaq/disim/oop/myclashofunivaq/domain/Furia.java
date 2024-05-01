package it.univaq.disim.oop.myclashofunivaq.domain;

public class Furia extends Incantesimo {

	private int aumento;
	
	public Furia(String nome) {
		super(nome);
	}
	
	public int getAumento() {
		return aumento;
	}
	public void setAumento(int aumento) {
		this.aumento = aumento;
	}
	@Override
	public void esegui() {
		this.getPersonaggioTarget().setDanno(this.getPersonaggioTarget().getDanno() + aumento);
	}
	
}