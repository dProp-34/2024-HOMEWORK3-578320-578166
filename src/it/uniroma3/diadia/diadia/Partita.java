package it.uniroma3.diadia;

import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.giocatore.Giocatore;

/**
 * Questa classe modella una partita del gioco
 * 
 * @author docente di POO
 * @see Stanza
 * @version base
 */
public class Partita {
	private Labirinto labirinto;
	private Giocatore giocatore;
	private boolean finita;

	public Partita() {
		this.labirinto = new Labirinto();
		this.giocatore = new Giocatore();
		this.finita = false;
	}

	public Labirinto getLabirinto() {
		return labirinto;
	}

	public void setLabirinto(Labirinto labirinto) {
		this.labirinto = labirinto;
	}

	public Giocatore getGiocatore() {
		return giocatore;
	}

	public void setGiocatore(Giocatore giocatore) {
		this.giocatore = giocatore;
	}

	/**
	 * Imposta la partita come finita
	 */
	public void setFinita() {
		this.finita = true;
	}

	/**
	 * Restituisce vero se la partita e' stata vinta, ovvero
	 * se la stanza corrente e' quella vincente
	 * 
	 * @return vero se partita vinta
	 */
	public boolean isVinta() {
		return this.labirinto.getStanzaCorrente() == this.labirinto.getStanzaVincente();
	}

	/**
	 * Restituisce vero se e solo se la partita e' finita
	 * 
	 * @return vero se partita finita
	 */
	public boolean isFinita() {
		return this.finita ||
				this.isVinta() ||
				(this.giocatore.getCfu() <= 0);
	}
}
