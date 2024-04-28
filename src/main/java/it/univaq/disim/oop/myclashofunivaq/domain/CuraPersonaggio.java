package it.univaq.disim.oop.myclashofunivaq.domain;

public class CuraPersonaggio extends Incantesimo{
	private int cura;

	public CuraPersonaggio(String nome) {
		super(nome);
		// TODO Auto-generated constructor stub
	}
	
	public int getCura() {
		return cura;
	}

	public void setCura(int cura) {
		this.cura = cura;
	}

	@Override
	public void esegui() {
		System.out.println("sono qui, cura " + cura);
		this.getPersonaggioTarget().setVita(this.getPersonaggioTarget().getVita() + cura);
	}

}
