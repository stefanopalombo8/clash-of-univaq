package it.univaq.disim.oop.myclashofunivaq.domain;

import java.io.Serializable;

public class Torre implements Serializable, Cloneable{

	private static final long serialVersionUID = 1L;
	
	private double vita;
	
	public double getVita() {
		return vita;
	}
	public void setVita(double vita) {
		this.vita = vita;
	}
	@Override
	public String toString() {
		return "Torre [vita=" + vita + "]";
	}
	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
}