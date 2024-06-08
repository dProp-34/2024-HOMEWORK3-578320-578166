package it.uniroma3.diadia;

import java.io.FileNotFoundException;

import it.uniroma3.diadia.ambienti.FormatoFileNonValidoException;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.comandi.Comando;
import it.uniroma3.diadia.comandi.Direzione;
import it.uniroma3.diadia.comandi.FabbricaDiComandiFisarmonica;

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
	static final private String MESSAGGIO_BENVENUTO = "" +
			"Ti trovi nell'Universita', ma oggi e' diversa dal solito...\n" +
			"Meglio andare al piu' presto in biblioteca a studiare. Ma dov'e'?\n" +
			"I locali sono popolati da strani personaggi, " +
			"alcuni amici, altri... chissa!\n" +
			"Ci sono attrezzi che potrebbero servirti nell'impresa:\n" +
			"puoi raccoglierli, usarli, posarli quando ti sembrano inutili\n" +
			"o regalarli se pensi che possano ingraziarti qualcuno.\n\n" +
			"Per conoscere le istruzioni usa il comando 'aiuto'.";
	private Partita partita;
	private IO io;

	public DiaDia(String nomeFile, IO io) {
		this.io = io;
		try {
			this.partita = new Partita(new Labirinto(nomeFile));
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Eccezione: File non trovato!");
		} catch (FormatoFileNonValidoException e) {
			throw new RuntimeException("Eccezione: Formato file non valido!");
		}
	}

	public DiaDia(Labirinto labirinto, IO io) {
		this.partita = new Partita(labirinto);
		this.io = io;
	}

	public DiaDia(IO io) {
		this(new Labirinto(), io);
		this.partita.getLabirinto().demo();
	}

	/*
	 * @SuppressWarnings("resource")
	 * public void gioca() {
	 * String istruzione;
	 * Scanner scannerDiLinee;
	 * io.mostraMessaggio(DiaDia.MESSAGGIO_BENVENUTO);
	 * scannerDiLinee = new Scanner(System.in);
	 * FabbricaDiComandi factory = new FabbricaDiComandiFisarmonica();
	 * do {
	 * istruzione = scannerDiLinee.nextLine();
	 * } while (!processaIstruzione(istruzione, factory));
	 * }
	 */

	public void gioca() {
		String istruzione;
		io.mostraMessaggio(MESSAGGIO_BENVENUTO);
		do {
			istruzione = io.leggiRiga();
		} while (!processaIstruzione(istruzione));
	}

	/**
	 * Processa un'istruzione.
	 *
	 * @return true se la partita e' finita al
	 *         termine dell'istruzione, false altrimenti.
	 */

	/*
	 * private boolean processaIstruzione(String istruzione, FabbricaDiComandi
	 * factory) {
	 * if (istruzione.isEmpty())
	 * return false;
	 * else {
	 * Comando comandoDaEseguire = factory.costruisciComando(istruzione);
	 * String daMostrare = comandoDaEseguire.esegui(this.partita);
	 * if (daMostrare != null)
	 * io.mostraMessaggioNoLn(daMostrare);
	 * if (this.partita.isVinta()) {
	 * io.mostraMessaggio("Hai raggiunto la " +
	 * this.partita.getLabirinto().getStanzaCorrente().getNome() + ". Hai vinto!");
	 * return true;
	 * } else if (!this.partita.getGiocatore().isVivo()) {
	 * io.mostraMessaggio("Hai esaurito i CFU. Hai perso...");
	 * return true;
	 * } else
	 * return this.partita.isFinita();
	 * }
	 * }
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
		 * N.B. unica istanza di IOConsole
		 * di cui sia ammessa la creazione
		 * 
		 * Labirinto labirinto = new LabirintoBuilder()
		 * .addStanzaIniziale("LabCampusOne")
		 * .addAttrezzo("osso", 1)
		 * .addStanzaVincente("Biblioteca")
		 * .addAdiacenza("LabCampusOne", "Biblioteca", "ovest")
		 * .addStanza("Aula N11")
		 * .addAttrezzo("lanterna", 2)
		 * .addAdiacenza("LabCampusOne", "Aula N11", "est")
		 * .addAdiacenza("Aula N11", "LabCampusOne", "ovest")
		 * .getLabirinto();
		 */
		IO io = new IOConsole();
		DiaDia gioco = new DiaDia("labirinto1.txt", io); // TODO leggere il labirinto da resources
		gioco.gioca();
	}
}
