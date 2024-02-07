package it.univaq.disim.oop.myclashofunivaq.configuration;

import java.io.FileInputStream;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.HashSet;

import it.univaq.disim.oop.myclashofunivaq.domain.MossaSpeciale;
import it.univaq.disim.oop.myclashofunivaq.domain.Personaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.Tank;
import it.univaq.disim.oop.myclashofunivaq.domain.nomipersonaggi.TankNomi;

public class Factory implements PersonaggioFactory {
	
	private static final String cartellaConfigurazione = "/configurations/";
	private static final String puntoSeparatore = "."; //nel caso nel file si vuole cambiare il separatore

	private static Factory instance = new Factory();
	
	public static Factory getInstance() {
		return instance;
	}
	
	@Override
	public Set<Personaggio> findAllPersonaggi() {
		return ImplementazionePersonaggi.getPersonaggi();
	}
	
	@Override
	public <T extends Personaggio> void modellaPersonaggio(T personaggioEmpty) {
		if(personaggioEmpty == null)
			throw new IllegalArgumentException();
	
		String keyDaCercare = costruisciChiave(personaggioEmpty);
		
		String path = costruisciPath("personaggi.properties");
		
		Set<MossaSpeciale> mosseSpeciali = ImplementazioneMosse.getMosseSpeciali();
		
		try(FileInputStream fis = new FileInputStream(path)) {
			final Properties props = new Properties();
			props.load(fis);
			
			props.stringPropertyNames().stream().forEach(
					riga -> {
						if(riga.startsWith(keyDaCercare)) {
							String attributo = riga.substring(keyDaCercare.length());
							
							switch(attributo) {
							case "vita":
								personaggioEmpty.setVita(Integer.valueOf(props.getProperty(riga)));
							case "danno":
								personaggioEmpty.setDanno(Integer.valueOf(props.getProperty(riga)));
							case "mossaSpeciale":
								String nomeMossaPersonaggio = props.getProperty(riga);
								
								MossaSpeciale mossaImpl = mosseSpeciali.stream().filter(
										mossa -> mossa.getNome().equals(nomeMossaPersonaggio)).findAny()
													.orElseThrow(() -> new RuntimeException("error"));
								
								personaggioEmpty.setMossaSpeciale(mossaImpl);
							}
							
						}
					});
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static <T extends Personaggio> String costruisciChiave(T personaggio) {
		StringBuilder builder = new StringBuilder();
		builder.append(ricercaCategoriaEimpostaNome(personaggio));
		builder.append(puntoSeparatore);
		builder.append(personaggio.getNome());
		builder.append(puntoSeparatore);
		
		return builder.toString();
	}
	
	private static <T extends Personaggio> String ricercaCategoriaEimpostaNome (T personaggioEmpty) {
		Enum<?>[] nomi;
		String categoriaPersonaggio;
		
		if(personaggioEmpty instanceof Tank) {
			nomi = TankNomi.values();
			categoriaPersonaggio = Tank.class.getSimpleName();
		
		}
		else
			throw new CategoriaNonTrovataException("Categoria non ancora implementata");
		
		Optional<Enum<?>> optionalName = Arrays.stream(nomi)
				.filter(n -> n.toString().equals(personaggioEmpty.getNome())).findAny();
		
		String nomeInEnum = optionalName.orElseThrow(
				() -> new NomeNonTrovatoException("nome non presente nell'elenco")).toString();
		
		if(!nomeInEnum.equals(personaggioEmpty.getNome()))
			personaggioEmpty.setNome(nomeInEnum);
		//vuol dire che il nome inserito del personaggio è presente ed è giusto
		
		return categoriaPersonaggio;
	}
	
	private String costruisciPath(String fileName) { 
		String pathCompleto = getClass().getResource(cartellaConfigurazione + fileName).toExternalForm();
		return pathCompleto.substring(pathCompleto.indexOf("C"));
	}
	
	private static class ImplementazioneMosse {
		private static Set<MossaSpeciale> mosseSpeciali = new HashSet<>();
		
		static {
			MossaSpeciale mossa1 = new MossaSpeciale("ricaricaEnergia", 
					(mossa) -> {
						//System.out.println("hai attivato " + mossa.getNome());
						mossa.getPersonaggioTarget().setVita(100);
					});
			mosseSpeciali.add(mossa1);
		}
		
		private static Set<MossaSpeciale> getMosseSpeciali() {
			return mosseSpeciali;
		}
	}
	
	private static class ImplementazionePersonaggi {
		private static Set<Personaggio> personaggi;
		private static PersonaggioFactory personaggioFactory = instance;
		
		static {
			Tank gigante = new Tank("GIGANTE");
			personaggioFactory.modellaPersonaggio(gigante);
			personaggi.add(gigante);
		}
		
		private static Set<Personaggio> getPersonaggi() {
			return personaggi;
		}

	}
		
}