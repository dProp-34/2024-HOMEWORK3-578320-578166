package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;

public class ComandoGuarda implements Comando {
	/**
	 * Stampa informazioni di aiuto.
	 */
	@Override
	public String esegui(Partita partita) {
		return ("\n" + partita.getLabirinto().getStanzaCorrente().toString());
	}

	@Override
	public void setParametro(String parametro) { }
}
