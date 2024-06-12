package it.uniroma3.diadia.ios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import it.uniroma3.diadia.Direzione;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.personaggi.Cane;
import it.uniroma3.diadia.personaggi.Mago;
import it.uniroma3.diadia.personaggi.Strega;

public class CaricatoreLabirinto {
	/*
	 * Esempio di un possibile file di
	 * specifica di un labirinto:
	 * 
	 * Stanze: biblioteca, N10, N11
	 * Inizio: N10
	 * Vincente: N11
	 * Magiche: N10 10
	 * Attrezzi: martello 10 biblioteca, pinza 2 N10
	 * Streghe: N11 ciao ciao
	 * Uscite: biblioteca nord N10, biblioteca sud N11
	 */
	private static final String STANZE_MARKER = "Stanze:";
	private static final String STANZA_INIZIALE_MARKER = "Inizio:";
	private static final String STANZA_VINCENTE_MARKER = "Vincente:";
	private static final String STANZE_MAGICHE_MARKER = "Magiche:";
	private static final String STANZE_BLOCCATE_MARKER = "Bloccate:";
	private static final String STANZE_BUIE_MARKER = "Buie:";
	private static final String STREGHE_MARKER = "Streghe:";
	private static final String MAGHI_MARKER = "Maghi:";
	private static final String CANI_MARKER = "Cani:";
	private static final String ATTREZZI_MARKER = "Attrezzi:";
	private static final String USCITE_MARKER = "Uscite:";

	private Labirinto.LabirintoBuilder builder;
	private LineNumberReader reader;

	public CaricatoreLabirinto(String nomeFile) throws IOException, FormatoFileNonValidoException {
		this.builder = Labirinto.newBuilder();
		// this.reader = new LineNumberReader(new FileReader(nomeFile));
		ClassLoader loader = this.getClass().getClassLoader();
		URL resource = loader.getResource(nomeFile);
		if (resource == null)
			throw new FileNotFoundException("Resource not found: " + nomeFile);
		else {
			try {
				this.reader = new LineNumberReader(new InputStreamReader(resource.openStream()));
			} catch (IOException e) {
				e.printStackTrace(); // Print the stack trace for debugging
				throw new IOException("Failed to open stream for resource: " + nomeFile);
			}
		}
	}

	public CaricatoreLabirinto(StringReader fixtureFile) throws FileNotFoundException, FormatoFileNonValidoException {
		this.builder = Labirinto.newBuilder();
		this.reader = new LineNumberReader(fixtureFile);
	}

	public void carica() throws FormatoFileNonValidoException {
		try {
			reader.mark(1000000); // Impone un limite ai file Labirinto da caricare di ~1MB
			this.leggiMagiche();
			this.leggiBloccate();
			this.leggiBuie(); // Occorre prima creare le Stanze speciali
			this.leggiECreaStanze();
			this.leggiInizialeEvincente(); // Possono venire impostate dopo averle create
			this.leggiECollocaAttrezzi();
			this.leggiECollocaStreghe();
			this.leggiECollocaMaghi();
			this.leggiECollocaCani();
			this.leggiEImpostaUscite();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}

	private String leggiRigaCheCominciaPer(String marker) throws FormatoFileNonValidoException {
		try {
			reader.reset();
			String riga;
			while ((riga = this.reader.readLine()) != null) {
				// System.out.println("Sto leggendo: " + riga);
				if (riga.startsWith(marker))
					return riga.substring(marker.length()).trim();
			}
			// System.out.println("Non è presente una riga che cominci per " + marker);
			return null;
		} catch (IOException e) {
			throw new FormatoFileNonValidoException(e.getMessage());
		}
	}

	private void leggiECreaStanze() throws FormatoFileNonValidoException {
		String nomiStanze = this.leggiRigaCheCominciaPer(STANZE_MARKER);
		for (String nomeStanza : separaStringheAlleVirgole(nomiStanze)) {
			if (this.builder.get(nomeStanza) == null) // Posso creare una Stanza solo se non esiste già
				this.builder.addStanza(nomeStanza);
		}
	}

	@SuppressWarnings("resource")
	private List<String> separaStringheAlleVirgole(String string) {
		List<String> result = new LinkedList<>();
		Scanner scanner = new Scanner(string);
		scanner.useDelimiter(",");
		// try (Scanner scannerDiParole = scanner) {
		while (scanner.hasNext()) {
			result.add(scanner.next().trim()); // Trim each string to remove leading and trailing spaces
		}
		return result;
	}

	private void leggiInizialeEvincente() throws FormatoFileNonValidoException {
		String nomeStanzaIniziale = this.leggiRigaCheCominciaPer(STANZA_INIZIALE_MARKER);
		check(this.isStanzaValida(nomeStanzaIniziale), nomeStanzaIniziale + " non definita");
		String nomeStanzaVincente = this.leggiRigaCheCominciaPer(STANZA_VINCENTE_MARKER);
		check(this.isStanzaValida(nomeStanzaVincente), nomeStanzaVincente + " non definita");
		this.builder.setStanzaIniziale(nomeStanzaIniziale);
		this.builder.setStanzaVincente(nomeStanzaVincente);
	}

	private void leggiMagiche() throws FormatoFileNonValidoException {
		String specificheMagiche = this.leggiRigaCheCominciaPer(STANZE_MAGICHE_MARKER);
		if (specificheMagiche != null) { // Se esiste una riga che comincia per "Magiche:"
			for (String specificaMagica : separaStringheAlleVirgole(specificheMagiche)) {
				String nomeMagica = null;
				String sogliaMagica = null;
				try (Scanner scannerDiLinea = new Scanner(specificaMagica)) {
					// while (scannerDiLinea.hasNext()) {
					check(scannerDiLinea.hasNext(),
							msgTerminazionePrecoce("il nome della stanza magica."));
					nomeMagica = scannerDiLinea.next();
					check(scannerDiLinea.hasNext(),
							msgTerminazionePrecoce("la soglia magica della stanza " + nomeMagica + "."));
					sogliaMagica = scannerDiLinea.next();
				}
				creaMagica(nomeMagica, sogliaMagica);
			}
		}
	}

	private void leggiBloccate() throws FormatoFileNonValidoException {
		String specificheBloccate = this.leggiRigaCheCominciaPer(STANZE_BLOCCATE_MARKER);
		if (specificheBloccate != null) { // Se esiste una riga che comincia per "Bloccate:"
			for (String specificaBloccata : separaStringheAlleVirgole(specificheBloccate)) {
				String nomeBloccata = null;
				String direzioneBloccata = null;
				String attrezzoPerSbloccare = null;
				try (Scanner scannerDiLinea = new Scanner(specificaBloccata)) {
					// while (scannerDiLinea.hasNext()) {
					check(scannerDiLinea.hasNext(),
							msgTerminazionePrecoce("il nome della stanza bloccata."));
					nomeBloccata = scannerDiLinea.next();
					check(scannerDiLinea.hasNext(),
							msgTerminazionePrecoce("la direzione bloccata della stanza " + nomeBloccata + "."));
					direzioneBloccata = scannerDiLinea.next();
					check(scannerDiLinea.hasNext(),
							msgTerminazionePrecoce("l'attrezzo per sbloccare la stanza " + nomeBloccata + "."));
					attrezzoPerSbloccare = scannerDiLinea.next();
				}
				creaBloccata(nomeBloccata, direzioneBloccata, attrezzoPerSbloccare);
			}
		}
	}

	private void leggiBuie() throws FormatoFileNonValidoException {
		String specificheBuie = this.leggiRigaCheCominciaPer(STANZE_BUIE_MARKER);
		if (specificheBuie != null) { // Se esiste una riga che comincia per "Buie:"
			for (String specificaBuia : separaStringheAlleVirgole(specificheBuie)) {
				String nomeBuia = null;
				String attrezzoPerVedere = null;
				try (Scanner scannerDiLinea = new Scanner(specificaBuia)) {
					// while (scannerDiLinea.hasNext()) {
					check(scannerDiLinea.hasNext(),
							msgTerminazionePrecoce("il nome della stanza buia."));
					nomeBuia = scannerDiLinea.next();
					check(scannerDiLinea.hasNext(),
							msgTerminazionePrecoce("l'attrezzo per vedere la stanza " + nomeBuia + "."));
					attrezzoPerVedere = scannerDiLinea.next();
				}
				creaBuia(nomeBuia, attrezzoPerVedere);
			}
		}
	}

	private void leggiECollocaAttrezzi() throws FormatoFileNonValidoException {
		String specificheAttrezzi = this.leggiRigaCheCominciaPer(ATTREZZI_MARKER);
		if (specificheAttrezzi != null) { // Se esiste una riga che comincia per "Attrezzi:"
			for (String specificaAttrezzo : separaStringheAlleVirgole(specificheAttrezzi)) {
				String nomeAttrezzo = null;
				String pesoAttrezzo = null;
				String nomeStanza = null;
				try (Scanner scannerDiLinea = new Scanner(specificaAttrezzo)) {
					// while (scannerDiLinea.hasNext()) {
					check(scannerDiLinea.hasNext(),
							msgTerminazionePrecoce("il nome di un attrezzo."));
					nomeAttrezzo = scannerDiLinea.next();
					check(scannerDiLinea.hasNext(),
							msgTerminazionePrecoce("il peso dell'attrezzo " + nomeAttrezzo + "."));
					pesoAttrezzo = scannerDiLinea.next();
					check(scannerDiLinea.hasNext(),
							msgTerminazionePrecoce("il nome della stanza in cui collocare l'attrezzo " + nomeAttrezzo + "."));
					nomeStanza = scannerDiLinea.next();
				}
				posaAttrezzo(nomeAttrezzo, pesoAttrezzo, nomeStanza);
			}
		}
	}

	private void leggiECollocaStreghe() throws FormatoFileNonValidoException {
		String specificheStreghe = this.leggiRigaCheCominciaPer(STREGHE_MARKER);
		if (specificheStreghe != null) { // Se esiste una riga che comincia per "Streghe:"
			for (String specificaStrega : separaStringheAlleVirgole(specificheStreghe)) {
				String nomeStrega = null;
				String presentazione = null;
				String nomeStanza = null;
				try (Scanner scannerDiLinea = new Scanner(specificaStrega)) {
					// while (scannerDiLinea.hasNext()) {
					check(scannerDiLinea.hasNext(),
							msgTerminazionePrecoce("il nome della strega."));
					nomeStrega = scannerDiLinea.next();
					check(scannerDiLinea.hasNext(),
							msgTerminazionePrecoce("la presentazione della strega " + nomeStrega + "."));
					presentazione = scannerDiLinea.next();
					check(scannerDiLinea.hasNext(),
							msgTerminazionePrecoce("il nome della stanza in cui collocare la strega " + nomeStrega + "."));
					nomeStanza = scannerDiLinea.next();
				}
				posaStrega(nomeStrega, presentazione, nomeStanza);
			}
		}
	}

	private void leggiECollocaMaghi() throws FormatoFileNonValidoException {
		String specificheMaghi = this.leggiRigaCheCominciaPer(MAGHI_MARKER);
		if (specificheMaghi != null) { // Se esiste una riga che comincia per "Maghi:"
			for (String specificaMago : separaStringheAlleVirgole(specificheMaghi)) {
				String nomeMago = null;
				String presentazione = null;
				String nomeAttrezzo = null;
				String pesoAttrezzo = null;
				String nomeStanza = null;
				try (Scanner scannerDiLinea = new Scanner(specificaMago)) {
					// while (scannerDiLinea.hasNext()) {
					check(scannerDiLinea.hasNext(),
							msgTerminazionePrecoce("il nome del mago."));
					nomeMago = scannerDiLinea.next();
					check(scannerDiLinea.hasNext(),
							msgTerminazionePrecoce("la presentazione del mago " + nomeMago + "."));
					presentazione = scannerDiLinea.next();
					check(scannerDiLinea.hasNext(),
							msgTerminazionePrecoce("il nome dell'attrezzo del mago " + nomeMago + "."));
					nomeAttrezzo = scannerDiLinea.next();
					check(scannerDiLinea.hasNext(),
							msgTerminazionePrecoce("il peso dell'attrezzo del mago " + nomeMago + "."));
					pesoAttrezzo = scannerDiLinea.next();
					check(scannerDiLinea.hasNext(),
							msgTerminazionePrecoce("il nome della stanza in cui collocare il mago " + nomeMago + "."));
					nomeStanza = scannerDiLinea.next();
				}
				posaMago(nomeMago, presentazione, nomeAttrezzo, pesoAttrezzo, nomeStanza);
			}
		}
	}

	private void leggiECollocaCani() throws FormatoFileNonValidoException {
		String specificheCani = this.leggiRigaCheCominciaPer(CANI_MARKER);
		if (specificheCani != null) { // Se esiste una riga che comincia per "Cani:"
			for (String specificaCane : separaStringheAlleVirgole(specificheCani)) {
				String nomeCane = null;
				String presentazione = null;
				String nomeStanza = null;
				try (Scanner scannerDiLinea = new Scanner(specificaCane)) {
					// while (scannerDiLinea.hasNext()) {
					check(scannerDiLinea.hasNext(),
							msgTerminazionePrecoce("il nome del cane."));
					nomeCane = scannerDiLinea.next();
					check(scannerDiLinea.hasNext(),
							msgTerminazionePrecoce("la presentazione del cane " + nomeCane + "."));
					presentazione = scannerDiLinea.next();
					check(scannerDiLinea.hasNext(),
							msgTerminazionePrecoce("il nome della stanza in cui collocare il cane " + nomeCane + "."));
					nomeStanza = scannerDiLinea.next();
				}
				posaCane(nomeCane, presentazione, nomeStanza);
			}
		}
	}

	private void posaAttrezzo(String nomeAttrezzo, String pesoAttrezzo, String nomeStanza)
			throws FormatoFileNonValidoException {
		int peso;
		try {
			peso = Integer.parseInt(pesoAttrezzo);
			Attrezzo attrezzo = new Attrezzo(nomeAttrezzo, peso);
			check(isStanzaValida(nomeStanza),
					"Attrezzo " + nomeAttrezzo + " non collocabile: stanza " + nomeStanza + " inesistente");
			this.builder.get(nomeStanza).addAttrezzo(attrezzo);
		} catch (NumberFormatException e) {
			check(false, "Peso attrezzo " + nomeAttrezzo + " non valido");
		}
	}

	private void posaStrega(String nomeStrega, String presentazione, String nomeStanza)
			throws FormatoFileNonValidoException {
		check(isStanzaValida(nomeStanza),
				"Strega " + nomeStrega + " non collocabile: stanza " + nomeStanza + " inesistente");
		this.builder.get(nomeStanza).setPersonaggio(new Strega(nomeStrega, presentazione));
		/*
		 * int peso;
		 * try {
		 * peso = Integer.parseInt(pesoAttrezzo);
		 * Attrezzo attrezzo = new Attrezzo(nomeAttrezzo, peso);
		 * } catch (NumberFormatException e) {
		 * check(false, "Peso attrezzo " + nomeAttrezzo + " non valido");
		 * }
		 */
	}

	private void posaCane(String nomeCane, String presentazione, String nomeStanza)
			throws FormatoFileNonValidoException {
		check(isStanzaValida(nomeStanza),
				"Cane " + nomeCane + " non collocabile: stanza " + nomeStanza + " inesistente");
		this.builder.get(nomeStanza).setPersonaggio(new Cane(nomeCane, presentazione));
	}

	private void posaMago(String nomeMago, String presentazione, String nomeAttrezzo, String pesoAttrezzo,
			String nomeStanza) throws FormatoFileNonValidoException {
		int peso;
		try {
			peso = Integer.parseInt(pesoAttrezzo);
			Attrezzo attrezzo = new Attrezzo(nomeAttrezzo, peso);
			check(isStanzaValida(nomeStanza),
					"Mago " + nomeMago + " non collocabile: stanza " + nomeStanza + " inesistente");
			this.builder.get(nomeStanza).setPersonaggio(new Mago(nomeMago, presentazione, attrezzo));
		} catch (NumberFormatException e) {
			check(false, "Peso attrezzo " + pesoAttrezzo + " non valido");
		}
	}

	private void creaMagica(String nomeMagica, String sogliaMagica)
			throws FormatoFileNonValidoException {
		int soglia;
		try {
			soglia = Integer.parseInt(sogliaMagica);
			this.builder.addStanzaMagica(nomeMagica, soglia);
		} catch (NumberFormatException e) {
			check(false, "Soglia magica " + nomeMagica + " non valida");
		}
	}

	private void creaBloccata(String nomeBloccata, String direzioneBloccata, String attrezzoPerSbloccare)
			throws FormatoFileNonValidoException {
		Direzione bloccata;
		bloccata = Direzione.valueOf(direzioneBloccata);
		this.builder.addStanzaBloccata(nomeBloccata, bloccata, attrezzoPerSbloccare);
		/*
		 * try {
		 * } catch (NumberFormatException e) {
		 * check(false, "Soglia magica " + nomeBloccata + " non valida");
		 * }
		 */
	}

	private void creaBuia(String nomeMagica, String perVedere)
			throws FormatoFileNonValidoException {
		this.builder.addStanzaBuia(nomeMagica, perVedere);
	}

	private boolean isStanzaValida(String nomeStanza) {
		return (this.builder.get(nomeStanza) != null);
	}

	private void leggiEImpostaUscite() throws FormatoFileNonValidoException {
		String specificheUscite = this.leggiRigaCheCominciaPer(USCITE_MARKER);
		if (specificheUscite != null) { // Se esiste una riga che comincia per "Uscite:"
			for (String specificaUscita : separaStringheAlleVirgole(specificheUscite)) {
				String stanzaPartenza = null;
				Direzione dir = null;
				String stanzaDestinazione = null;
				try (Scanner scannerDiLinea = new Scanner(specificaUscita)) {
					// while (scannerDiLinea.hasNext()) {
					check(scannerDiLinea.hasNext(),
							msgTerminazionePrecoce("le uscite di una stanza."));
					stanzaPartenza = scannerDiLinea.next();
					check(scannerDiLinea.hasNext(),
							msgTerminazionePrecoce("la direzione di una uscita della stanza " + stanzaPartenza));
					dir = Direzione.valueOf(scannerDiLinea.next());
					check(scannerDiLinea.hasNext(), msgTerminazionePrecoce(
							"la destinazione di una uscita della stanza " + stanzaPartenza + " nella direzione " + dir));
					stanzaDestinazione = scannerDiLinea.next();
				}
				/*
				 * impostaUscita(stanzaPartenza, dir, stanzaDestinazione);}}}
				 * 
				 * private void impostaUscita(String nomeDa, Direzione dir, String nomeA) throws
				 * FormatoFileNonValidoException {
				 * check(isStanzaValida(nomeDa), "Stanza di partenza sconosciuta " + dir);
				 * check(isStanzaValida(nomeA), "Stanza di destinazione sconosciuta " + dir);
				 * Stanza partenzaDa = this.builder.get(nomeDa);
				 * Stanza arrivoA = this.builder.get(nomeA);
				 */
				this.builder.addAdiacenza(stanzaPartenza, stanzaDestinazione, dir);
			}
		}
	}

	private String msgTerminazionePrecoce(String msg) {
		return "Terminazione precoce del file prima di leggere " + msg;
	}

	final private void check(boolean condizioneCheDeveEsseraVera, String messaggioErrore)
			throws FormatoFileNonValidoException {
		if (!condizioneCheDeveEsseraVera)
			throw new FormatoFileNonValidoException(
					"Formato file non valido [" + this.reader.getLineNumber() + "] " + messaggioErrore);
	}

	public Stanza getStanzaIniziale() {
		return this.builder.getLabirinto().getStanzaCorrente();
	}

	public Stanza getStanzaVincente() {
		return this.builder.getLabirinto().getStanzaVincente();
	}

	public LineNumberReader getReader() {
		return this.reader;
	}

	public Labirinto.LabirintoBuilder getBuilder() {
		return this.builder;
	}
}
