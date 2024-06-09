package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;

public class ComandoFine extends AbstractComando {
	
	private final String NOME = "fine";
	
	/**
	 * Termina il gioco.
	 */
	@Override
	public String esegui(Partita partita) {
		partita.setFinita();
		return ("Grazie per aver giocato!\n");
	}
	
	@Override
	public String getNome() {
		return this.NOME;
	}
		
}
