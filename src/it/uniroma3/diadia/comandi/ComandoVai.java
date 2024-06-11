package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Direzione;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Stanza;

public class ComandoVai extends AbstractComando {
	private Direzione direzione;
	private final String NOME = "vai";

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
		Direzione d = Direzione.controllaSeEsiste(parametro);
		if (d != null)
			this.direzione = d;
	}

	@Override
	public String getParametro() {
		return this.direzione.name();
	}

	@Override
	public String getNome() {
		return this.NOME;
	}
}
