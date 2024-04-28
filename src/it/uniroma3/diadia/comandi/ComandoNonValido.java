package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;

public class ComandoNonValido implements Comando {
	/**
	 * Stampa informazioni di aiuto.
	 */
	@Override
	public String esegui(Partita partita) {
		return ("Comando sconosciuto.\n");
	}

	@Override
	public void setParametro(String parametro) { }
}
