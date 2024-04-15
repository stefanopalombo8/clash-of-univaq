package it.univaq.disim.oop.myclashofunivaq.business;

import it.univaq.disim.oop.myclashofunivaq.controller.utilitis.GridPaneGioco;
import it.univaq.disim.oop.myclashofunivaq.domain.MossaGiocatore;
import it.univaq.disim.oop.myclashofunivaq.domain.Personaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.PosizionamentoPersonaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.Turno;

public interface GiocatoreService {
	MossaGiocatore effettuaSchieramentoPersonaggio(Turno turno, Personaggio personaggio, GridPaneGioco strada, PosizionamentoPersonaggio posizionamento);

}
