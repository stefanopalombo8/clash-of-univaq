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
import it.univaq.disim.oop.myclashofunivaq.domain.Assassino;
import it.univaq.disim.oop.myclashofunivaq.domain.BloccaAttacco;
import it.univaq.disim.oop.myclashofunivaq.domain.Carta;
import it.univaq.disim.oop.myclashofunivaq.domain.CuraPersonaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.Fulmine;
import it.univaq.disim.oop.myclashofunivaq.domain.Furia;
import it.univaq.disim.oop.myclashofunivaq.domain.Incantesimo;
import it.univaq.disim.oop.myclashofunivaq.domain.Mago;
import it.univaq.disim.oop.myclashofunivaq.domain.MossaSpeciale;
import it.univaq.disim.oop.myclashofunivaq.domain.Personaggio;
import it.univaq.disim.oop.myclashofunivaq.domain.RendiInvulnerabile;
import it.univaq.disim.oop.myclashofunivaq.domain.Tank;
import it.univaq.disim.oop.myclashofunivaq.domain.nomicarte.AssassinoNomi;
import it.univaq.disim.oop.myclashofunivaq.domain.nomicarte.IncantesimiNomi;
import it.univaq.disim.oop.myclashofunivaq.domain.nomicarte.MagoNomi;
import it.univaq.disim.oop.myclashofunivaq.domain.nomicarte.TankNomi;
import javafx.scene.image.Image;

public class Factory implements CartaFactory {

	private static final String cartellaConfigurazione = "/configurations/";
	private static final String puntoSeparatore = ".";

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
						case "armatura":
							personaggio.setArmatura(Integer.valueOf(props.getProperty(riga)));
							break;
						case "danno":
							personaggio.setDanno(Integer.valueOf(props.getProperty(riga)));
							break;
						case "mossaSpeciale":
							String nomeMossaPersonaggio = props.getProperty(riga);

							MossaSpeciale mossaImpl = mosseSpeciali.stream()
									.filter(mossa -> mossa.getNome().equals(nomeMossaPersonaggio)).findAny()
									.orElseThrow(() -> new NomeNonTrovatoException("nome non presente nell'elenco "
											+ "delle mosse speciali"));

							personaggio.setMossaSpeciale(mossaImpl);
							break;
						case "immagine":
							Image immagine = new Image(costruisciPath(props.getProperty(riga)));
							personaggio.setImmagineCarta(immagine);
							break;
						}

					}
				});

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			String path = costruisciPath("incantesimi.properties");

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
						case "danno":
							Fulmine fulmine = (Fulmine) incantesimo;
							fulmine.setDanno(Integer.valueOf(props.getProperty(riga)));
							break;
						case "aumento":
							Furia furia = (Furia) incantesimo;
							furia.setAumento(Integer.valueOf(props.getProperty(riga)));
							break;
						case "immagine":
							Image immagine = new Image(costruisciPath(props.getProperty(riga)));
							incantesimo.setImmagineCarta(immagine);
							break;
						}
					}
				});

			} catch (FileNotFoundException e) {
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
		String categoriaCarta = null;
		
		if (carta instanceof Tank) {
			nomi = TankNomi.values();
			categoriaCarta = Tank.class.getSimpleName();
		} else if(carta instanceof Assassino) {
			nomi = AssassinoNomi.values();
			categoriaCarta = Assassino.class.getSimpleName();
		} else if(carta instanceof Mago) {
			nomi = MagoNomi.values();
			categoriaCarta = Mago.class.getSimpleName();
		}
		else if(carta instanceof Incantesimo) {
			nomi = IncantesimiNomi.values();
			if (carta instanceof BloccaAttacco) {
				categoriaCarta = BloccaAttacco.class.getSimpleName();
			} else if (carta instanceof CuraPersonaggio) {
				categoriaCarta = CuraPersonaggio.class.getSimpleName();
			} else if (carta instanceof RendiInvulnerabile) {
				categoriaCarta = RendiInvulnerabile.class.getSimpleName();
			} else if(carta instanceof Fulmine) {
				categoriaCarta = Fulmine.class.getSimpleName();
			} else if(carta instanceof Furia) {
				categoriaCarta = Furia.class.getSimpleName();
			} 
		}
		else
			throw new CategoriaNonTrovataException("Categoria non ancora implementata");

		Optional<Enum<?>> optionalName = Arrays.stream(nomi).filter(n -> n.toString().equals(carta.getNome()))
				.findAny();

		String nomeInEnum = optionalName.orElseThrow(() -> new NomeNonTrovatoException("nome non presente nell'elenco "
				+ "della Enum corrente"))
				.toString();

		if (!nomeInEnum.equals(carta.getNome()))
			carta.setNome(nomeInEnum);

		// vuol dire che il nome inserito è presente ed è giusto

		return categoriaCarta;
	}

	@Override
	public void reimpostaImmagine(List<Carta> carte) {
		for (Carta carta : carte) {
			String keyDaCercare = costruisciChiave(carta);

			if(carta instanceof Personaggio) {
				String path = costruisciPath("personaggi.properties");
				
				try (FileInputStream fis = new FileInputStream(path)) {
					final Properties props = new Properties();
					props.load(fis);

					props.stringPropertyNames().stream().forEach(riga -> {
						if (riga.startsWith(keyDaCercare)) {
							String attributo = riga.substring(keyDaCercare.length());

							switch (attributo) {
							case "immagine":
								Image immagine = new Image(costruisciPath(props.getProperty(riga)));
								carta.setImmagineCarta(immagine);
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
			else {
				String path = costruisciPath("incantesimi.properties");
				
				try (FileInputStream fis = new FileInputStream(path)) {
					final Properties props = new Properties();
					props.load(fis);

					props.stringPropertyNames().stream().forEach(riga -> {
						if (riga.startsWith(keyDaCercare)) {
							String attributo = riga.substring(keyDaCercare.length());

							switch (attributo) {
							case "immagine":
								Image immagine = new Image(costruisciPath(props.getProperty(riga)));
								carta.setImmagineCarta(immagine);
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

	}

}