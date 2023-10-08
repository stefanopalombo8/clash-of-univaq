package it.univaq.disim.oop.myclashofunivaq.view;

public interface InizializzaDati<T> {
	
	default void inizializza(T data) {}
	
	default void inizializza(T data1, T data2) {}
}
