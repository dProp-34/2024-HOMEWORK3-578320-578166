package it.uniroma3.diadia;

import java.util.Scanner;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.IOConsole;

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
	static final private String[] ELENCO_COMANDI = { "vai", "prendi", "posa", "aiuto", "fine" };

	private Partita partita;

	public DiaDia() {
		this.partita = new Partita();
	}

	@SuppressWarnings("resource")
	public void gioca() {
		String istruzione;
		Scanner scannerDiLinee;

		IOConsole.mostraMessaggio(DiaDia.MESSAGGIO_BENVENUTO);
		scannerDiLinee = new Scanner(System.in);

		do {
			IOConsole.mostraMessaggioNoLn("\nComandi:");
			for (String comando : DiaDia.ELENCO_COMANDI)
				IOConsole.mostraMessaggioNoLn(" " + comando);
			IOConsole.mostraMessaggio("");
			istruzione = scannerDiLinee.nextLine();
		} while (!processaIstruzione(istruzione));
	}

	/**
	 * Processa un'istruzione.
	 *
	 * @return true se la partita e' finita al
	 *         termine dell'istruzione, false altrimenti.
	 */
	private boolean processaIstruzione(String istruzione) {
		Comando comandoDaEseguire = new Comando(istruzione);
		if (istruzione.isEmpty())
			return false;

		else
			switch (comandoDaEseguire.getNome()) {
				case "fine":
					this.fine();
					return true;
				case "aiuto":
					this.aiuto();
					break;
				case "posa":
					this.posa(comandoDaEseguire.getParametro());
					break;
				case "prendi":
					this.prendi(comandoDaEseguire.getParametro());
					break;
				case "vai":
					this.vai(comandoDaEseguire.getParametro());
					break;
				default:
					IOConsole.mostraMessaggio("Comando sconosciuto.");
			}

		if (this.partita.isVinta()) {
			IOConsole.mostraMessaggio("Hai raggiunto la " +
					this.partita.getLabirinto().getStanzaCorrente().getNome() + ". Hai vinto!");
			return true;
		} else
			return false;
	}

	/* Implementazioni dei comandi dell'utente: */

	/**
	 * Termina il gioco.
	 */
	private void fine() {
		IOConsole.mostraMessaggio("Grazie per aver giocato!");
	}

	/**
	 * Stampa informazioni di aiuto.
	 */
	private void aiuto() {
		IOConsole.mostraMessaggioNoLn("\n" + this.partita.getLabirinto().getStanzaCorrente().toString());
		IOConsole.mostraMessaggioNoLn("\n" + this.partita.getGiocatore().getBorsa().toString());
	}

	/**
	 * Permette al giocatore di posare un
	 * attrezzo dalla sua borsa.
	 */
	private void posa(String nomeAttrezzo) {
		if (nomeAttrezzo == null)
			IOConsole.mostraMessaggio("Cosa vuoi posare?");
		else {
			Attrezzo daPosare = this.partita.getGiocatore().getBorsa().removeAttrezzo(nomeAttrezzo);
			if (daPosare == null)
				IOConsole.mostraMessaggio("Non possiedi quell'attrezzo.");
			else if (!this.partita.getLabirinto().getStanzaCorrente().addAttrezzo(daPosare))
				IOConsole.mostraMessaggio("Non puoi posare quell'attrezzo.");
		}
	}

	/**
	 * Permette al giocatore di prendere un
	 * attrezzo dalla stanza nella quale si trova.
	 */
	private void prendi(String nomeAttrezzo) {
		if (nomeAttrezzo == null)
			IOConsole.mostraMessaggio("Cosa vuoi prendere?");
		else {
			Attrezzo daPrendere = this.partita.getLabirinto().getStanzaCorrente().getAttrezzo(nomeAttrezzo);
			if (daPrendere == null)
				IOConsole.mostraMessaggio("Quell'attrezzo non e' presente nella stanza.");
			else {
				boolean successo = this.partita.getGiocatore().getBorsa().addAttrezzo(daPrendere);
				successo |= this.partita.getLabirinto().getStanzaCorrente().removeAttrezzo(daPrendere);
				if (!successo)
					IOConsole.mostraMessaggio("Non puoi prendere quell'attrezzo.");
			}
		}
	}

	/**
	 * Cerca di andare in una direzione. Se c'e' una stanza ci
	 * entra e ne stampa il nome, altrimenti stampa un messaggio di errore.
	 * Ogni volta che il giocatore cambia stanza i suoi
	 * Cfu vengono decrementati di uno.
	 */
	private void vai(String direzione) {
		if (direzione == null)
			IOConsole.mostraMessaggio("Dove vuoi andare?");
		else {
			Stanza prossimaStanza = this.partita.getLabirinto().getStanzaCorrente().getStanzaAdiacente(direzione);
			if (prossimaStanza == null)
				IOConsole.mostraMessaggio("Non puoi andare li'.");
			else {
				this.partita.getLabirinto().setStanzaCorrente(prossimaStanza);
				this.partita.getGiocatore().decrementaCfu();
			}
		}
	}

	public static void main(String[] argc) {
		DiaDia gioco = new DiaDia();
		IOConsole console = new IOConsole();
		gioco.gioca();
	}
}
