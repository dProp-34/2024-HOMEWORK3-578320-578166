package it.uniroma3.diadia.giocatore;

import java.io.FileNotFoundException;

import it.uniroma3.diadia.CaricatoreProprieta;
import it.uniroma3.diadia.ambienti.FormatoFileNonValidoException;

public class Giocatore {
	static final private int CFU_DEFAULT = 20;

	private Borsa borsa;
	private int cfu;

	public Giocatore() {
		this.borsa = new Borsa();
		try {
			CaricatoreProprieta caricatore = new CaricatoreProprieta();
			// String labirintoInUso = caricatore.caricaLabirinto();
			this.cfu = caricatore.caricaCFU();
		} catch (FileNotFoundException e) {
			// try {
			// io.mostraMessaggio("Labirinto non trovato, carico " + LABIRINTO_DEFAULT); //
			this.cfu = CFU_DEFAULT;
			// } catch (FileNotFoundException | FormatoFileNonValidoException e1) {
			// throw new RuntimeException(LABIRINTO_DEFAULT + "non trovato!");
			// }
		} catch (FormatoFileNonValidoException e) {
			throw new RuntimeException("Formato properties non valido!");
		}
	}

	public Borsa getBorsa() {
		return borsa;
	}

	public void setBorsa(Borsa borsa) {
		this.borsa = borsa;
	}

	public int getCfu() {
		return this.cfu;
	}

	public void setCfu(int cfu) {
		this.cfu = cfu;
	}

	public void decrementaCfu() {
		if (this.cfu >= 0)
			this.cfu--;
	}

	public boolean isVivo() {
		return (this.cfu > 0);
	}
}
