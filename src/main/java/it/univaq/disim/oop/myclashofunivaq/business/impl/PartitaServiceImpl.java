package it.univaq.disim.oop.myclashofunivaq.business.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	
	private static String path = "src/main/resourses/files/partiteSalvate/";

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

		String path = PartitaServiceImpl.path + "partitaSerializzata" + partita.getID() + ".txt";

		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
			oos.writeObject(partita);
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

	}

	@Override
	public void impostaParamentriSalvataggio(Partita partita, int numeroMosse, int numeroCarte, int valoreCarte) {
		partita.setNumeroTotaleMosse(numeroMosse);
		partita.setNumeroCarteInCampo(numeroCarte);
		partita.setValoreCarteInCampo(valoreCarte);
	}

	@Override
	public List<Partita> getPartiteDeserializzate() {
		List<Partita> partite = new ArrayList<>();

		File directory = new File(PartitaServiceImpl.path);
		File[] elencoFile = directory.listFiles();

		if (elencoFile != null) {
			for (File file : elencoFile) {
				if (file.isFile() && file.getName().startsWith("partita")) {
					try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
						Partita partitaDeserializzata = (Partita) ois.readObject();
						partite.add(partitaDeserializzata);

					} catch (ClassNotFoundException e) {
						System.err.println("1 " + e.getMessage());
					} catch (FileNotFoundException e) {
						System.err.println("2 " + e.getMessage());
					} catch (IOException e) {
						if (e.getMessage() != null)
							System.err.println("3 " + e.getMessage());
					}
				}
			}
		}

		System.out.println("numero partite deserializzate " + partite.size());
		return partite;
	}

	@Override
	public void mappaPartitaSerializzata(Partita partita) {
		partite.put(partita.getID(), partita);
		ID++;
	}

}
