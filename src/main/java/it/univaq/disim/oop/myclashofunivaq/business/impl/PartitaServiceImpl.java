package it.univaq.disim.oop.myclashofunivaq.business.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.univaq.disim.oop.myclashofunivaq.business.PartitaService;
import it.univaq.disim.oop.myclashofunivaq.domain.Giocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.Partita;
import it.univaq.disim.oop.myclashofunivaq.domain.Turno;

public class PartitaServiceImpl implements PartitaService {

	private static Map<Integer, Partita> partite = new HashMap<>();
	private static Integer ID = 0;

	private static final String nomeFilePartite = "partiteSerializzate.txt";
	private static final String path = "src/main/resourses/files/" + nomeFilePartite;

	@Override
	public Set<Giocatore> findAllGiocatori() {
		Set<Giocatore> setToReturn = new HashSet<>();

		for (Integer key : partite.keySet()) {
			for (Giocatore giocatore : partite.get(key).getGiocatori())
				setToReturn.add(giocatore);
		}

		return setToReturn;
	}

	@Override
	public Giocatore[] findAllGiocatori(Partita partita) {
		Giocatore[] giocatoriPartita = new Giocatore[2];
		int i = 0;

		for (Giocatore giocatore : partite.get(partita.getID()).getGiocatori()) {
			giocatoriPartita[i] = giocatore;
			i++;
		}

		return giocatoriPartita;
	}

	@Override
	public boolean aggiungiGiocatore(Giocatore giocatore, Partita partita) {
		if (!partita.getGiocatori().add(giocatore))
			throw new NicknameNonValido("ERRORE NICKNAME GIÀ UTILIZZATO");

		return true;

	}

	@Override
	public Partita creaPartita() {
		Partita partita = new Partita();
		partita.setID(ID);
		partite.put(partita.getID(), partita);
		ID++;
		return partita;
	}

	@Override
	public Partita trovaPartitaByID(Integer ID) {
		return (partite.get(ID)) != null ? partite.get(ID) : null;
	}

	@Override
	public boolean salvaTurnoPartita(Turno turno, Partita partita) {
		return partita.getTurni().add(turno);
	}

	@Override
	public int calcolaNumeroMossePartita(Partita partita) {
		if (!partite.containsKey(partita.getID()))
			return 0;

		int numeroDiMosse = 0;

		for (Turno turno : partita.getTurni()) {
			numeroDiMosse += turno.getMosseGiocatore().size();
		}

		return numeroDiMosse;
	}

	@Override
	public void salvaPartita(Partita partita) {
		if (!partite.containsKey(partita.getID()))
			return;

		try {
			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
				oos.writeObject(partita);
			}
			
//			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
//				Partita partitaDeserializzata = (Partita) ois.readObject();
//				System.out.println("PARTITA DESERIALIZZATA " + partitaDeserializzata);
//			}

		} catch (IOException e) {
			System.err.println("qui 1 " + e.getMessage());
		} //catch (ClassNotFoundException e) {
			//System.err.println("qui 2" + e.getMessage());
		//}

	}

}
