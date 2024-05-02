package it.univaq.disim.oop.myclashofunivaq.business.impl;

import java.io.BufferedWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import it.univaq.disim.oop.myclashofunivaq.business.PartitaService;
import it.univaq.disim.oop.myclashofunivaq.business.ResetStaticVariables;
import it.univaq.disim.oop.myclashofunivaq.domain.Turno;
import it.univaq.disim.oop.myclashofunivaq.business.TurnoService;
import it.univaq.disim.oop.myclashofunivaq.domain.Attacco;
import it.univaq.disim.oop.myclashofunivaq.domain.CambioPosizionamentoPersonaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.Carta;
import it.univaq.disim.oop.myclashofunivaq.domain.FaseTurno;
import it.univaq.disim.oop.myclashofunivaq.domain.Giocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.MossaGiocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.Partita;
import it.univaq.disim.oop.myclashofunivaq.domain.Schieramento;
import it.univaq.disim.oop.myclashofunivaq.domain.Torre;

import javafx.animation.Timeline;

public class TurnoServiceImpl implements TurnoService, ResetStaticVariables {
	
	private final PartitaService partitaService;
	private static int i = 0; // indice per i giocatori
	private static int j = 0; // indice per l'ID dei turni
	private static Map<Integer, Turno> turniPartita = new HashMap<>();
	private static Map<Integer, Torre> torriPartitaCopy = new HashMap<>();
	
	private static final String path = "src/main/resourses/files/logsPartite/";
	
	private double limiteElisir = 20;
	
	public TurnoServiceImpl() {
		partitaService = new PartitaServiceImpl();
	}

	@Override
	public Giocatore alternaGiocatore(Partita partita) {
		return partitaService.findAllGiocatori(partita)[i++ % partitaService.findAllGiocatori(partita).length];
	}

	@Override
	public Turno avviaTurno(Timeline timeline, Giocatore giocatore) {
		if(timeline == null)
			return null;
		
		Turno turno = new Turno(giocatore);
		turno.setNumero(j);
		turno.setFase(FaseTurno.Schieramento);
		
		if(isFirstTurno(turno)) {
			turno.setElisirGiocatore(1.0);
			Torre torre = new Torre();
			torre.setVita(1);
			turno.setTorreGiocatore(torre);
		}
		else {
			double newElisir = turniPartita.get(j - 2).getElisirGiocatore() + 0.1;
			if(newElisir >= (limiteElisir/10))
				newElisir = limiteElisir/10;
			turno.setElisirGiocatore(this.formatElisir(newElisir));
			
			Torre torre = turniPartita.get(j - 2).getTorreGiocatore();
			double newVitaTorre = torre.getVita();
			torre.setVita(newVitaTorre);
			turno.setTorreGiocatore(torre);
		}
			
		
		turniPartita.put(turno.getNumero(), turno);
		
		Torre torreCopy = null;
		try {
			torreCopy = (Torre) turno.getTorreGiocatore().clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		torriPartitaCopy.put(turno.getNumero(), torreCopy);
		
		j++;
		
		timeline.play();
		return turno;
	}

	@Override
	public boolean isFirstTurno(Turno turno) {
		return turno.getNumero() == 0 || turno.getNumero() == 1 ? true : false;
	}

	@Override
	public void cambiaFase(Turno turno) throws FasiTerminateException {
		FaseTurno faseTurno = turno.getFase();
		
		switch (faseTurno) {
		case Schieramento: 
			turno.setFase(FaseTurno.Difesa);
			break;
		case Difesa:
			turno.setFase(FaseTurno.Attacco);
			break;
		default:
			throw new FasiTerminateException("FASI FINITE");
		}
	}
	
	@Override
	public void controllaSchieramento(Turno turno, Carta carta) throws ElisirException {
		System.out.println("elisir turno " + turno.getElisirGiocatore());
		System.out.println("costo " + (double) carta.getCostoSchieramento() / 10);
		if(turno.getElisirGiocatore() < (double) carta.getCostoSchieramento() / 10) {
			if(turno.getElisirGiocatore() < 0)
				turno.setElisirGiocatore(0);
			throw new ElisirException("ELISIR INSUFFICIENTE");
		}
			
		
	}					

	/* Si è usato l'oggetto BigDecimal per arrotondare la sottrazione tra double
	 * si sarebbe potuto usare anche il metodo Math.round()
	 */
	@Override
	public void aggiornaElisir(Turno turno, int costo) {
		double costoDaTogliere = (double) costo / 10;
		BigDecimal uno = new BigDecimal(Double.toString(costoDaTogliere));
		BigDecimal due = new BigDecimal(Double.toString(turno.getElisirGiocatore()));
		BigDecimal newElisir = due.subtract(uno);
		double doubleValue = newElisir.doubleValue();
		if(doubleValue <= 0)
			turno.setElisirGiocatore(0);
		else
			turno.setElisirGiocatore(this.formatElisir(doubleValue));
	}

	@Override
	public void salvaMossaGiocatore(Partita partita, Turno turno, MossaGiocatore mossa) {
		turno.getMosseGiocatore().add(mossa);
		StringBuilder builder = new StringBuilder();
		builder.append("GIOCATORE: ");
		builder.append(turno.getGiocatore().getNickname() + " ");
		
		if(mossa instanceof Schieramento) {
			Schieramento s = (Schieramento) mossa;
			builder.append(s.toString());
		}
		else if(mossa instanceof CambioPosizionamentoPersonaggio) {
			CambioPosizionamentoPersonaggio c = (CambioPosizionamentoPersonaggio) mossa;
			builder.append(c.toString());
		}
		else if(mossa instanceof Attacco) {
			Attacco a = (Attacco) mossa;
			builder.append(a.toString());
		}
		else
			builder.append("ERRORE NELLA MOSSA");
		
		builder.append("\n");
		String path = TurnoServiceImpl.path + "partita" + partita.getID() + ".txt";
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path), true))) {
            writer.write(builder.toString());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
		
		
	}

	@Override
	public Giocatore trovaAltroGiocatore(Partita partita) {
		Giocatore giocatoreCorrente = this.getUltimoTurno(partita).getGiocatore();
		
		for(Giocatore g : partitaService.findAllGiocatori(partita)) {
			if(!giocatoreCorrente.getNickname().equals(g.getNickname()))
					return g;
		}
		
		return null;
	}

	@Override
	public Torre trovaTorreGiocatore(Giocatore giocatore) {
		Torre torre = null;
		
		for(Integer i : turniPartita.keySet()) {
			Turno turno = turniPartita.get(i);
			if(turno.getGiocatore().equals(giocatore)) {
				torre = turno.getTorreGiocatore();
			}
		}
		return torre;
	}

	@Override
	public void reset() {
		i = 0;
		j = 0;
		turniPartita.clear();
	}

	@Override
	public void ripopolaMappaTurni(Partita partita) {
		if(turniPartita.isEmpty()) {
			i++;
			for(Turno t : partita.getTurni()) {
				turniPartita.put(t.getNumero(), t);
				j = t.getNumero();
			}
			j++;
		}
	}

	@Override
	public void annullaUltimoTurno(Turno turnoCorrente) {
		i--;
		j--;
		
		if(j > 0)
			turniPartita.get(j - 1).setTorreGiocatore(torriPartitaCopy.get(j - 1));
		
		turniPartita.remove(turnoCorrente.getNumero());
		
	}

	@Override
	public Turno getUltimoTurno(Partita partita) {
		return partita.getTurni().get(partita.getTurni().size() - 1);
	}

	@Override
	public boolean isTurnoPari(Turno turno) {
		return turno.getNumero() % 2 == 0;
	}
	
	//Ulteriore formatazzione perché alle volte esce un elisir di 0.300...2
	private double formatElisir(double value) {
	    BigDecimal bd = new BigDecimal(Double.toString(value));
	    bd = bd.setScale(1, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
