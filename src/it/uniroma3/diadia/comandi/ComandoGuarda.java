package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;

public class ComandoGuarda extends AbstractComando {
	private final String NOME = "guarda";

	/**
	 * Stampa informazioni di aiuto.
	 */
	@Override
	public String esegui(Partita partita) {
		return ("\n" + partita.getLabirinto().getStanzaCorrente().toString());
	}

	@Override
	public String getNome() {
		return this.NOME;
	}
}
