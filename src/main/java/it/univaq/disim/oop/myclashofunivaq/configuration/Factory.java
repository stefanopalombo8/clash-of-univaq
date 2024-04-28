package it.univaq.disim.oop.myclashofunivaq.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import it.univaq.disim.oop.myclashofunivaq.business.impl.MosseSpeciali;
import it.univaq.disim.oop.myclashofunivaq.domain.BloccaAttacco;
import it.univaq.disim.oop.myclashofunivaq.domain.Carta;
import it.univaq.disim.oop.myclashofunivaq.domain.CuraPersonaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.Incantesimo;
import it.univaq.disim.oop.myclashofunivaq.domain.MossaSpeciale;
import it.univaq.disim.oop.myclashofunivaq.domain.Personaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.RendiInvulnerabile;
import it.univaq.disim.oop.myclashofunivaq.domain.Tank;
import it.univaq.disim.oop.myclashofunivaq.domain.nomipersonaggi.IncantesimiNomi;
import it.univaq.disim.oop.myclashofunivaq.domain.nomipersonaggi.TankNomi;
import javafx.scene.image.Image;

public class Factory implements CartaFactory {

	private static final String cartellaConfigurazione = "/configurations/";
	private static final String puntoSeparatore = "."; // nel caso nel file si vuole cambiare il separatore

	private static Factory instance = new Factory();

	public static Factory getInstance() {
		return instance;
	}

	private String costruisciPath(String fileName) {
		String pathCompleto = getClass().getResource(cartellaConfigurazione + fileName).toExternalForm();
		return pathCompleto.substring(pathCompleto.indexOf("C"));
	}

	private <T extends Carta> String costruisciChiave(T carta) {
		StringBuilder builder = new StringBuilder();
		builder.append(instance.ricercaCategoriaEimpostaNome(carta));
		builder.append(puntoSeparatore);
		builder.append(carta.getNome());
		builder.append(puntoSeparatore);

		return builder.toString();
	}

	@Override
	public <T extends Carta> void modellaCarta(T carta) {
		if (carta == null)
			throw new IllegalArgumentException();

		String keyDaCercare = costruisciChiave(carta);

		if (carta instanceof Personaggio) {
			String path = costruisciPath("personaggi.properties");

			Set<MossaSpeciale> mosseSpeciali = MosseSpeciali.getMosseSpeciali();

			try (FileInputStream fis = new FileInputStream(path)) {
				final Properties props = new Properties();
				props.load(fis);

				props.stringPropertyNames().stream().forEach(riga -> {
					if (riga.startsWith(keyDaCercare)) {
						String attributo = riga.substring(keyDaCercare.length());

						Personaggio personaggio = (Personaggio) carta;

						switch (attributo) {
						case "costoSchieramento":
							personaggio.setCostoSchieramento(Integer.valueOf(props.getProperty(riga)));
							break;
						case "vita":
							personaggio.setVita(Integer.valueOf(props.getProperty(riga)));
							break;
						case "danno":
							personaggio.setDanno(Integer.valueOf(props.getProperty(riga)));
							break;
						case "mossaSpeciale":
							String nomeMossaPersonaggio = props.getProperty(riga);

							MossaSpeciale mossaImpl = mosseSpeciali.stream()
									.filter(mossa -> mossa.getNome().equals(nomeMossaPersonaggio)).findAny()
									.orElseThrow(() -> new NomeNonTrovatoException("nome non presente nell'elenco"));

							personaggio.setMossaSpeciale(mossaImpl);
							break;
						case "immagine":
							Image immagine = new Image(costruisciPath(props.getProperty(riga)));

							personaggio.setImmagineCarta(immagine);
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
		} else {
			String path = costruisciPath("incantesimi.properties");
			
			System.out.println("key " + keyDaCercare);
			try (FileInputStream fis = new FileInputStream(path)) {
				final Properties props = new Properties();
				props.load(fis);

				props.stringPropertyNames().stream().forEach(riga -> {
					if (riga.startsWith(keyDaCercare)) {
						String attributo = riga.substring(keyDaCercare.length());

						Incantesimo incantesimo = (Incantesimo) carta;

						switch (attributo) {
						case "costoSchieramento":
							incantesimo.setCostoSchieramento(Integer.valueOf(props.getProperty(riga)));
							break;
						case "cura":
							CuraPersonaggio incantesimoCura = (CuraPersonaggio) incantesimo;
							incantesimoCura.setCura(Integer.valueOf(props.getProperty(riga)));
							break;
						case "immagine":
							Image immagine = new Image(costruisciPath(props.getProperty(riga)));
							incantesimo.setImmagineCarta(immagine);
							break;
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

	}

	@Override
	public <T extends Carta> String ricercaCategoriaEimpostaNome(T carta) {
		Enum<?>[] nomi;
		String categoriaCarta;

		if (carta instanceof Tank) {
			nomi = TankNomi.values();
			categoriaCarta = Tank.class.getSimpleName();

		} else if (carta instanceof BloccaAttacco) {
			nomi = IncantesimiNomi.values();
			categoriaCarta = BloccaAttacco.class.getSimpleName();
		} else if (carta instanceof CuraPersonaggio) {
			nomi = IncantesimiNomi.values();
			categoriaCarta = CuraPersonaggio.class.getSimpleName();
		} else if (carta instanceof RendiInvulnerabile) {
			nomi = IncantesimiNomi.values();
			categoriaCarta = RendiInvulnerabile.class.getSimpleName(); 
		}
		else
			throw new CategoriaNonTrovataException("Categoria non ancora implementata");

		Optional<Enum<?>> optionalName = Arrays.stream(nomi).filter(n -> n.toString().equals(carta.getNome()))
				.findAny();

		String nomeInEnum = optionalName.orElseThrow(() -> new NomeNonTrovatoException("nome non presente nell'elenco"))
				.toString();

		if (!nomeInEnum.equals(carta.getNome()))
			carta.setNome(nomeInEnum);

		// vuol dire che il nome inserito della è presente ed è giusto

		return categoriaCarta;
	}

	@Override
	public void reimpostaImmagine(List<Carta> carte) {
		for (Carta carta : carte) {
			String keyDaCercare = costruisciChiave(carta);

			String path = costruisciPath("personaggi.properties");

			try (FileInputStream fis = new FileInputStream(path)) {
				final Properties props = new Properties();
				props.load(fis);

				props.stringPropertyNames().stream().forEach(riga -> {
					if (riga.startsWith(keyDaCercare)) {
						String attributo = riga.substring(keyDaCercare.length());

						if (carta instanceof Personaggio) {

							Personaggio personaggio = (Personaggio) carta;

							switch (attributo) {
							case "immagine":
								Image immagine = new Image(costruisciPath(props.getProperty(riga)));

								personaggio.setImmagineCarta(immagine);
							}
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

	}

}