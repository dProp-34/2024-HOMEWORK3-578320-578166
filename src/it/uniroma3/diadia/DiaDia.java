package it.uniroma3.diadia;

import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.Scanner;

import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.comandi.Comando;
import it.uniroma3.diadia.comandi.FabbricaDiComandiFisarmonica;
import it.uniroma3.diadia.ios.CaricatoreLabirinto;
import it.uniroma3.diadia.ios.CaricatoreProprieta;
import it.uniroma3.diadia.ios.FormatoFileNonValidoException;
import it.uniroma3.diadia.ios.IO;
import it.uniroma3.diadia.ios.IOConsole;

/**
 * Classe principale di diadia, un semplice gioco di ruolo ambientato al dia.
 * Per giocare crea un'istanza di questa classe ed invoca il metodo gioca.
 * Questa e' la classe principale che crea ed istanzia tutte le altre.
 *
 * @author docente di POO
 *         (da un'idea di Michael Kolling and David J. Barnes)
 *
 * @version base
 */
public class DiaDia {
	static final private String MESSAGGIO_BENVENUTO = "Ti trovi nell'Universita', ma oggi e' diversa dal solito...\n" +
			"Meglio andare al piu' presto in biblioteca a studiare. Ma dov'e'?\n" +
			"I locali sono popolati da strani personaggi, " +
			"alcuni amici, altri... chissa!\n" +
			"Ci sono attrezzi che potrebbero servirti nell'impresa:\n" +
			"puoi raccoglierli, usarli, posarli quando ti sembrano inutili\n" +
			"o regalarli se pensi che possano ingraziarti qualcuno.\n\n" +
			"Per conoscere le istruzioni usa il comando 'aiuto'.";
	static final private String LABIRINTO_DEFAULT = "Stanze: biblioteca, N10, N11\n" +
			"Inizio: N10\n" +
			"Vincente: N11\n" +
			"Attrezzi: martello 10 biblioteca, pinza 2 N10\n" +
			"Uscite: biblioteca nord N10, biblioteca sud N11";

	private Partita partita;
	private IO io;

	public DiaDia(IO io) {
		this.io = io;
		try {
			io.mostraMessaggio(System.getProperty("java.class.path")); // TODO da togliere
			CaricatoreProprieta caricatore = new CaricatoreProprieta();
			String labirintoInUso = caricatore.caricaLabirinto();
			this.partita = new Partita(Labirinto.newBuilder(labirintoInUso).getLabirinto());
		} catch (FileNotFoundException e) {
			try {
				io.mostraMessaggio("Labirinto non trovato, carico il labirinto di default"); // TODO da togliere
				CaricatoreLabirinto caricatore = new CaricatoreLabirinto(new StringReader(LABIRINTO_DEFAULT));
				caricatore.carica();
				Labirinto labirinto = caricatore.getBuilder().getLabirinto();
				this.partita = new Partita(labirinto);
			} catch (FileNotFoundException | FormatoFileNonValidoException e1) {
				throw new RuntimeException(LABIRINTO_DEFAULT + " non trovato!");
			}
		} catch (FormatoFileNonValidoException e) {
			throw new RuntimeException("Formato labirinto non valido!");
		}
	}

	public DiaDia(Labirinto labirinto, IO io) {
		this.partita = new Partita(labirinto);
		this.io = io;
	}

	public void gioca(Scanner scannerDiLinee) {
		String istruzione;
		io.mostraMessaggio(MESSAGGIO_BENVENUTO);
		do {
			istruzione = io.leggiRiga(scannerDiLinee);
		} while (!processaIstruzione(istruzione));
	}

	/**
	 * Processa un'istruzione.
	 *
	 * @return true se la partita e' finita al
	 *         termine dell'istruzione, false altrimenti.
	 */
	private boolean processaIstruzione(String istruzione) {
		Comando comandoDaEseguire;
		FabbricaDiComandiFisarmonica factory = new FabbricaDiComandiFisarmonica(this.io);
		comandoDaEseguire = factory.costruisciComando(istruzione);
		String m = comandoDaEseguire.esegui(this.partita);
		if (m != null)
			io.mostraMessaggio(m);
		if (this.partita.isVinta())
			io.mostraMessaggio("Hai raggiunto " +
					this.partita.getLabirinto().getStanzaCorrente().getNome() + ". Hai vinto!\n");
		if (!this.partita.getGiocatore().isVivo())
			io.mostraMessaggio("Hai esaurito i CFU...\nGAME OVER");
		return this.partita.isFinita();
	}

	public static void main(String[] argc) {
		/*
		 * NB. l'unica istanza di IOConsole
		 * di cui sia ammessa la creazione
		 * si trova qui
		 * 
		 * Labirinto labirinto = new LabirintoBuilder()
		 * .addStanzaIniziale("LabCampusOne")
		 * .addAttrezzo("osso", 1)
		 * .addStanzaVincente("Biblioteca")
		 * .addAdiacenza("LabCampusOne", "Biblioteca", Direzione.valueOf("ovest"))
		 * .addStanza("Aula N11")
		 * .addAttrezzo("lanterna", 2)
		 * .addAdiacenza("LabCampusOne", "Aula N11", Direzione.valueOf("est"))
		 * .addAdiacenza("Aula N11", "LabCampusOne", Direzione.valueOf("ovest"))
		 * .getLabirinto();
		 * DiaDia gioco = new DiaDia(labirinto, io);
		 */
		IO io = new IOConsole();
		Scanner scannerDiLinee = new Scanner(System.in);
		DiaDia gioco = new DiaDia(io);
		if (gioco.partita.isFinita())
			scannerDiLinee.close();
		gioco.gioca(scannerDiLinee);
	}
}
