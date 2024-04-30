package it.univaq.disim.oop.myclashofunivaq.domain;

import java.io.Serializable;

public abstract class Personaggio extends Carta implements Serializable{
	private int vita;
	private int armatura;
	private int danno;
	private int mana;
	private MossaSpeciale mossaSpeciale;
	private PosizionamentoPersonaggio posizionamento;
	
	public Personaggio(String nome) {
		super(nome);
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

	public PosizionamentoPersonaggio getPosizionamento() {
		return posizionamento;
	}

	public void setPosizionamento(PosizionamentoPersonaggio posizionamento) {
		this.posizionamento = posizionamento;
	}
}