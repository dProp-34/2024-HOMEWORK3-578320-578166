package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;

public class ComandoAiuto implements Comando {
	/**
	 * Stampa informazioni di aiuto.
	 */
	@Override
	public String esegui(Partita partita) {
		return ("\n" + partita.getGiocatore().getBorsa().toString() +
				"\nCFU Rimanenti: " + partita.getGiocatore().getCfu());
	}

	@Override
	public void setParametro(String parametro) { }
}
