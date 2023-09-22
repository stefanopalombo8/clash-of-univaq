package it.univaq.disim.oop.myclashofunivaq.domain;

public abstract class Personaggio extends Carta implements Cloneable {
	private String nome;
	private int vita;
	private int armatura;
	private int danno;
	private int mana;
	private MossaSpeciale mossaSpeciale;
	
	public Personaggio(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getVita() {
		return vita;
	}

	public void setVita(int vita) {
		this.vita = vita;
	}

	public int getArmatura() {
		return armatura;
	}

	public void setArmatura(int armatura) {
		this.armatura = armatura;
	}

	public int getDanno() {
		return danno;
	}

	public void setDanno(int danno) {
		this.danno = danno;
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public MossaSpeciale getMossaSpeciale() {
		return mossaSpeciale;
	}

	public void setMossaSpeciale(MossaSpeciale mossaSpeciale) {
		this.mossaSpeciale = mossaSpeciale;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return (Personaggio) super.clone();
	}

}
