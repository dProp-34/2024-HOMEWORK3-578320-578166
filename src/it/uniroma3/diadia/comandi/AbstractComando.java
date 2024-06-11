package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ios.IO;

public abstract class AbstractComando implements Comando {
	private IO io;
	private String parametro;
	// private String NOME = "AbstractComando";

	public void setParametro(String parametro) {
		this.parametro = parametro;
	}

	public String getParametro() {
		return this.parametro;
	}

	public void setIo(IO io) {
		this.io = io;
	}

	public IO getIo() {
		return this.io;
	}

	abstract public String esegui(Partita partita);
	abstract public String getNome();
}
