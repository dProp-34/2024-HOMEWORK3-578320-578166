package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoFine implements Comando {
	
	private IO io;
	/**
	 * Termina il gioco.
	 */
	@Override
	public String esegui(Partita partita) {
		partita.setFinita();
		return ("Grazie per aver giocato!\n");
	}

	@Override
	public void setParametro(String parametro) { }
	
	@Override
	public void setIo(IO io) {
		// TODO Auto-generated method stub
		this.io = io;
	}
}
