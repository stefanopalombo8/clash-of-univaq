package it.univaq.disim.oop.myclashofunivaq.business;

import java.util.Set;

import it.univaq.disim.oop.myclashofunivaq.domain.Carta;

public interface CartaService {
	Set<Carta> trovaTutteCarte();
	Carta cercaCarta(String nome);
}