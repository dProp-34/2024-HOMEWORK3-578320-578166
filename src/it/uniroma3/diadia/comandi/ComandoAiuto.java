package it.uniroma3.diadia.comandi;

import java.lang.reflect.*;

import java.util.ArrayList;
import java.util.List;

import it.uniroma3.diadia.Partita;

public class ComandoAiuto extends AbstractComando {
	static final private String[] ELENCO_COMANDI = { "vai", "prendi", "posa", "guarda", "aiuto", "fine", "saluta", "regala", "interagisci" };
	
	private final String NOME = "aiuto";

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
		
		/*
		 * StringBuilder out = new StringBuilder();
		List<String> nomiComandi = recuperaNomiComandi();
		for (String s : nomiComandi)
			out.append(s + " ");
		return out.toString();
		*/
	}
	

	@Override
	public String getNome() {
		return this.NOME;
	}
}
