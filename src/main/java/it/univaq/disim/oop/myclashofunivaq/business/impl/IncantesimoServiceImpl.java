package it.univaq.disim.oop.myclashofunivaq.business.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import it.univaq.disim.oop.myclashofunivaq.business.IncantesimoService;
import it.univaq.disim.oop.myclashofunivaq.business.ResetStaticVariables;
import it.univaq.disim.oop.myclashofunivaq.domain.Incantesimo;
import it.univaq.disim.oop.myclashofunivaq.domain.Personaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.Turno;

public class IncantesimoServiceImpl implements IncantesimoService, ResetStaticVariables {

	private static Map<Incantesimo, Integer> incantesimiAttivi = new HashMap<>();
	private static final int durataIncantesimo = 1; // espressa in turni
	
	@Override
	public void aggiungiIncantesimoAttivo(Turno turno, Incantesimo incantesimo, Personaggio personaggioTarget) {
		if(personaggioTarget != null && incantesimo != null) {
			
			incantesimo.setPersonaggioTarget(personaggioTarget);
			
			incantesimiAttivi.put(incantesimo, 0);
			
			System.out.println("aggiunto incantesimo attivo " + incantesimo.getPersonaggioTarget().toString());
		}
	}

	@Override
	public List<Incantesimo> getIncantesimiAttivi() {
		List<Incantesimo> listToReturn = new ArrayList<>();
		listToReturn.addAll(incantesimiAttivi.keySet());
		
		return listToReturn;
	}

	@Override
	public void eseguiIncantesimo(Incantesimo incantesimo, Personaggio personaggioTarget) {
		if(personaggioTarget != null && incantesimo != null) {
			if(incantesimo.getPersonaggioTarget() == null)
				incantesimo.setPersonaggioTarget(personaggioTarget);
			
			incantesimo.esegui();
			
		}
	}
	
	@Override
	public List<Incantesimo> getIncantesimiAttivi(Personaggio personaggioTarget) {
		List<Incantesimo> incantesimiSulPersonaggio = new ArrayList<>();
		for(Incantesimo incantesimo : incantesimiAttivi.keySet()) {
			if(incantesimo.getPersonaggioTarget().equals(personaggioTarget)) 
				incantesimiSulPersonaggio.add(incantesimo);
		}
		
		return incantesimiSulPersonaggio;
		
	}

	@Override
	public void checkAnnullaEffettoIncantesimi() {
		Iterator<Map.Entry<Incantesimo, Integer>> iterator = incantesimiAttivi.entrySet().iterator();
		while (iterator.hasNext()) {
		    Map.Entry<Incantesimo, Integer> entry = iterator.next();
		    Incantesimo incantesimo = entry.getKey();
		    Integer numero = entry.getValue();
		    
		    if (numero.equals(durataIncantesimo)) {
		        iterator.remove();
		    } else {
		        incantesimiAttivi.put(incantesimo, numero + 1);
		    }
		}
	}

	@Override
	public void reset() {
		incantesimiAttivi.clear();
	}

	@Override
	public void annullamentoIncantesimi() {
		for(Incantesimo incantesimo : incantesimiAttivi.keySet()) {
			Integer numero = incantesimiAttivi.get(incantesimo);
			if(numero == 0) { // vuol dire che l'incantesimo è stato appena schierato
				incantesimiAttivi.remove(incantesimo);
			}
		}
		
	}

}