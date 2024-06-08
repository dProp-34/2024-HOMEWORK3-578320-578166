package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoNonValido implements Comando {
	
	private IO io;
	
	/**
	 * Stampa informazioni di aiuto.
	 */
	@Override
	public String esegui(Partita partita) {
		return ("Comando sconosciuto.\n");
	}

	@Override
	public void setParametro(String parametro) { }
	
	@Override
	public void setIo(IO io) {
		// TODO Auto-generated method stub
		this.io = io;
	}
}
