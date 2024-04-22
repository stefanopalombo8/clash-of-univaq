package it.univaq.disim.oop.myclashofunivaq.business;

import it.univaq.disim.oop.myclashofunivaq.business.impl.PosizionamentoException;
import it.univaq.disim.oop.myclashofunivaq.domain.Attacco;
import it.univaq.disim.oop.myclashofunivaq.domain.Personaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.PosizionamentoPersonaggio;

public interface PersonaggioService {
	void sceltaPosizionamento(Personaggio personaggio, PosizionamentoPersonaggio posizionamento) throws PosizionamentoException;
	void attacca(Attacco attaco);
}
