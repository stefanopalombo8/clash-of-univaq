package it.univaq.disim.oop.myclashofunivaq.controller;

import it.univaq.disim.oop.myclashofunivaq.domain.GiocatoreUtente;
import it.univaq.disim.oop.myclashofunivaq.view.InizializzaDati;
import it.univaq.disim.oop.myclashofunivaq.view.ViewDispatcher;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class NicknameGiocatore2Controller implements InizializzaDati<GiocatoreUtente> {
	
	@FXML
	private Label giocatore1Label;
	
	private ViewDispatcher dispatcher;
	
	public NicknameGiocatore2Controller() {
		dispatcher = ViewDispatcher.getInstance();
	}

	@Override
	public void inizializza(GiocatoreUtente giocatore1) {
		this.giocatore1Label.setText(giocatore1.getNickname());
	}
}
