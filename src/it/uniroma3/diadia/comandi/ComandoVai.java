package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Stanza;

public class ComandoVai implements Comando {
	private String direzione;

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
				return ("Non puoi andare li'.\n");
			else {
				partita.getLabirinto().setStanzaCorrente(prossimaStanza);
				partita.getGiocatore().decrementaCfu();
			}
		}
		return null;
	}

	@Override
	public void setParametro(String parametro) {
		this.direzione = parametro;
	}
}
