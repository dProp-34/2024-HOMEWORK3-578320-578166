package it.uniroma3.diadia.ambienti;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.comandi.Direzione;

public class CaricatoreLabirinto {
	/* prefisso di una singola riga di testo contenente tutti i nomi delle stanze */
	private static final String STANZE_MARKER = "Stanze:";
	/* prefisso di una singola riga contenente il nome della stanza iniziale */
	private static final String STANZA_INIZIALE_MARKER = "Inizio:";
	/* prefisso della riga contenente il nome stanza vincente */
	private static final String STANZA_VINCENTE_MARKER = "Vincente:";
	/*
	 * prefisso della riga contenente le specifiche degli attrezzi da collocare nel
	 * formato <nomeAttrezzo> <peso> <nomeStanza>
	 */
	private static final String ATTREZZI_MARKER = "Attrezzi:";
	/*
	 * prefisso della riga contenente le specifiche dei collegamenti tra stanza nel
	 * formato <nomeStanzaDa> <direzione> <nomeStanzaA>
	 */
	private static final String USCITE_MARKER = "Uscite:";

	/*
	 * Esempio di un possibile file di specifica di un labirinto (vedi
	 * POO-26-eccezioni-file.pdf)
	 * 
	 * Stanze: biblioteca, N10, N11
	 * Inizio: N10
	 * Vincente: N11
	 * Attrezzi: martello 10 biblioteca, pinza 2 N10
	 * Uscite: biblioteca nord N10, biblioteca sud N11
	 */
	private LineNumberReader reader;
	private Labirinto.LabirintoBuilder builder;

	public CaricatoreLabirinto(String nomeFile) throws FileNotFoundException, FormatoFileNonValidoException {
		this.builder = Labirinto.newBuilder();
		this.reader = new LineNumberReader(new FileReader(nomeFile));
	}

	public CaricatoreLabirinto(StringReader fixtureFile) throws FileNotFoundException, FormatoFileNonValidoException {
		this.builder = Labirinto.newBuilder();
		this.reader = new LineNumberReader(fixtureFile);
	}

	public void carica() throws FormatoFileNonValidoException {
		try {
			reader.mark(1000000); // Impone un limite ai file Labirinto da caricare di ~1MB
			this.leggiECreaStanze();
			this.leggiInizialeEvincente();
			this.leggiECollocaAttrezzi();
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
