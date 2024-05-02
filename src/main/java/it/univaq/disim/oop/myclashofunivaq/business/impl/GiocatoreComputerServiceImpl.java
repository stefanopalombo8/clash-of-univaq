package it.univaq.disim.oop.myclashofunivaq.business.impl;

import java.util.Random;

import it.univaq.disim.oop.myclashofunivaq.business.GiocatoreComputerService;

public class GiocatoreComputerServiceImpl implements GiocatoreComputerService {
	
	private Random random;
	
	public GiocatoreComputerServiceImpl() {
		this.random = new Random();
	}
	
	@Override
	public int getPosizioneRandom(int intervallo) {
		return random.nextInt(intervallo);
	}

}