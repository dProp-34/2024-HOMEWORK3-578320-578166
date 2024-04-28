package it.uniroma3.diadia;

import java.util.Scanner;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.comandi.*;

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
@SuppressWarnings("unused")
public class DiaDia {
	static final private String MESSAGGIO_BENVENUTO = "\n" +
			"Ti trovi nell'Universita', ma oggi e' diversa dal solito...\n" +
			"Meglio andare al piu' presto in biblioteca a studiare. Ma dov'e'?\n" +
			"I locali sono popolati da strani personaggi, " +
			"alcuni amici, altri... chissa!\n" +
			"Ci sono attrezzi che potrebbero servirti nell'impresa:\n" +
			"puoi raccoglierli, usarli, posarli quando ti sembrano inutili " +
			"oppure\nregalarli se pensi che possano ingraziarti qualcuno...";
	static final private String[] ELENCO_COMANDI = {
			"vai", "prendi", "posa", "guarda", "aiuto", "fine" };
	private Partita partita;
	private IO io;

	public DiaDia(IO io) {
		this.partita = new Partita();
		this.io = io;
	}

	@SuppressWarnings("resource")
	public void gioca() {
		String istruzione;
		Scanner scannerDiLinee;
		io.mostraMessaggio(DiaDia.MESSAGGIO_BENVENUTO);
		scannerDiLinee = new Scanner(System.in);
		FabbricaDiComandi factory = new FabbricaDiComandiFisarmonica();

		do {
			io.mostraMessaggioNoLn("\nComandi:");
			for (String comando : DiaDia.ELENCO_COMANDI)
				io.mostraMessaggioNoLn(" " + comando);
			io.mostraMessaggio("");
			istruzione = scannerDiLinee.nextLine();
		} while (!processaIstruzione(istruzione, factory));
	}

	/**
	 * Processa un'istruzione.
	 *
	 * @return true se la partita e' finita al
	 *         termine dell'istruzione, false altrimenti.
	 */
	private boolean processaIstruzione(String istruzione, FabbricaDiComandi factory) {
		if (istruzione.isEmpty())
			return false;
		else {
			Comando comandoDaEseguire = factory.costruisciComando(istruzione);
			String daMostrare = comandoDaEseguire.esegui(this.partita);
			if (daMostrare != null)
				io.mostraMessaggioNoLn(daMostrare);

			if (this.partita.isVinta()) {
				io.mostraMessaggio("Hai raggiunto la " +
						this.partita.getLabirinto().getStanzaCorrente().getNome() + ". Hai vinto!");
				return true;
			} else if (!this.partita.getGiocatore().isVivo()) {
				io.mostraMessaggio("Hai esaurito i CFU. Hai perso...");
				return true;
			} else
				return this.partita.isFinita();
		}
	}

	public static void main(String[] argc) {
		IO io = new IOConsole();
		DiaDia gioco = new DiaDia(io);
		gioco.gioca();
	}
}
