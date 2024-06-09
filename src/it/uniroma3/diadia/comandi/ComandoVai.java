package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Stanza;

@SuppressWarnings("unused")
public class ComandoVai implements Comando {
	private Direzione direzione;
	private IO io;

	/**
	 * Cerca di andare in una direzione. Se c'e' una stanza ci
	 * entra e ne stampa il nome, altrimenti stampa un messaggio di errore.
	 * Ogni volta che il giocatore cambia stanza i suoi
	 * Cfu vengono decrementati di uno.
	 */
	@Override
	public String esegui(Partita partita) {
		if (this.direzione == null)
			return ("Dove vuoi andare?\n");
		else {
			Stanza stanzaCorrente = partita.getLabirinto().getStanzaCorrente();
			Stanza prossimaStanza = stanzaCorrente.getStanzaAdiacente(direzione);
			if (prossimaStanza == null)
				// return ("Non puoi andare li'.\n");
				return ("*Bonk*\n");
			else {
				partita.getLabirinto().setStanzaCorrente(prossimaStanza);
				partita.getGiocatore().decrementaCfu();
			}
		}
		return null;
	}

	@Override
	public void setParametro(String parametro) {
		if (parametro != null)
			this.direzione = Direzione.valueOf(parametro);
	}

	@Override
	public void setIo(IO io) {
		this.io = io;
	}
}
