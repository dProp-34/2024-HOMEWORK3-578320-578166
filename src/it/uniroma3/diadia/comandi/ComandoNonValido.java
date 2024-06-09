package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;

public class ComandoNonValido extends AbstractComando {
	
	private final String NOME = "ComandoNonValido";

	/**
	 * Stampa informazioni di aiuto.
	 */
	@Override
	public String esegui(Partita partita) {
		return ("Comando sconosciuto.\n");
	}

	@Override
	public String getNome() {
		return this.NOME;
	}
	
}
