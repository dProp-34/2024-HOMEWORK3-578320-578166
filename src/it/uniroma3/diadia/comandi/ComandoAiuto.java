package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoAiuto implements Comando {
	static final private String[] ELENCO_COMANDI = { "vai", "prendi", "posa", "guarda", "aiuto", "fine" };
	private IO io;

	/**
	 * Stampa informazioni di aiuto.
	 */
	@Override
	public String esegui(Partita partita) {
		StringBuilder out = new StringBuilder();
		for (String s : ComandoAiuto.ELENCO_COMANDI)
			out.append(s + " ");
		out.append("\n" + partita.getGiocatore().getBorsa().toString() +
				"\nCFU Rimanenti: " + partita.getGiocatore().getCfu());
		return out.toString();
	}

	@Override
	public void setParametro(String parametro) {
	}

	@Override
	public void setIo(IO io) {
		this.io = io;
	}
}
