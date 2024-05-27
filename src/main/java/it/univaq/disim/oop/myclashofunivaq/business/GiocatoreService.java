package it.univaq.disim.oop.myclashofunivaq.business;

import it.univaq.disim.oop.myclashofunivaq.business.impl.AttaccoException;
import it.univaq.disim.oop.myclashofunivaq.business.impl.PosizionamentoException;
import it.univaq.disim.oop.myclashofunivaq.controller.utilities.GridPaneGioco;
import it.univaq.disim.oop.myclashofunivaq.domain.Incantesimo;
import it.univaq.disim.oop.myclashofunivaq.domain.MossaGiocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.Personaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.PosizionamentoPersonaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.Torre;
import it.univaq.disim.oop.myclashofunivaq.domain.Turno;

public interface GiocatoreService {
	
	MossaGiocatore effettuaSchieramento(Turno turno, Personaggio personaggio, GridPaneGioco strada, 
			PosizionamentoPersonaggio posizionamento);
	
	MossaGiocatore effettuaSchieramento(Turno turno, Incantesimo incantesimo);
	
	MossaGiocatore cambiaPosizionePersonaggio(Turno turno, Personaggio personaggio, PosizionamentoPersonaggio posizionamento)
			throws PosizionamentoException;
	
	void preparaAttacco(Personaggio personaggioAttaccante, GridPaneGioco strada) throws AttaccoException;
	
	MossaGiocatore effettuaAttacco(Turno turno, Personaggio personaggioDaAttaccare, GridPaneGioco strada, 
			Torre torre) throws AttaccoException;
	
	MossaGiocatore attaccaTorre(Turno turno, Personaggio personaggioAttaccante,GridPaneGioco strada, Torre torreAvversaria)
			throws AttaccoException;
	
}