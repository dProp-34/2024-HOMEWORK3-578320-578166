package it.uniroma3.diadia.ambienti;

import it.uniroma3.diadia.attrezzi.Attrezzo;

/**
 * Questa classe modella una stanza nel gioco di ruolo.
 * Una stanza e' un luogo fisico nel gioco, collegato ad
 * altre stanze attraverso delle uscite.
 * Ogni uscita e' associata ad una direzione.
 *
 * @author docente di POO
 * @see Attrezzo
 * @version base
 */
public class Stanza {
	static final private int NUMERO_MASSIMO_DIREZIONI = 4;
	static final private int NUMERO_MASSIMO_ATTREZZI = 10;

	private String nome;
	private Attrezzo[] attrezzi;
	private int numeroAttrezzi;
	private Stanza[] stanzeAdiacenti;
	private int numeroStanzeAdiacenti;
	private String[] direzioni;

	/**
	 * Crea una stanza, senza attrezzi ne' stanze adiacenti.
	 *
	 * @param nome - il nome della stanza.
	 */
	public Stanza(String nome) {
		this.nome = nome;
		this.numeroStanzeAdiacenti = 0; // Potrebbero venire sostituiti da .length
		this.numeroAttrezzi = 0;
		this.direzioni = new String[NUMERO_MASSIMO_DIREZIONI];
		this.stanzeAdiacenti = new Stanza[NUMERO_MASSIMO_DIREZIONI];
		this.attrezzi = new Attrezzo[NUMERO_MASSIMO_ATTREZZI];
	}

	/**
	 * Restituisce il nome della stanza.
	 *
	 * @return il nome della stanza
	 */
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Restituisce la collezione di attrezzi presenti nella stanza.
	 *
	 * @return la collezione di attrezzi nella stanza.
	 */
	public Attrezzo[] getAttrezzi() {
		return this.attrezzi;
	}

	public void setAttrezzi(Attrezzo[] attrezzi) {
		this.attrezzi = attrezzi;
	}

	/**
	 * Restituisce la stanza adiacente nella direzione specificata.
	 *
	 * @param direzione - la direzione della stanza da restituire.
	 */
	public Stanza getStanzaAdiacente(String direzione) {
		Stanza stanza = null;
		for (int i = 0; i < this.numeroStanzeAdiacenti; i++)
			if (this.direzioni[i].equals(direzione))
				stanza = this.stanzeAdiacenti[i];
		return stanza;
	}

	/**
	 * Imposta una stanza adiacente.
	 *
	 * @param direzione - la direzione in cui sara' posta la stanza adiacente.
	 * @param stanza    - la stanza adiacente nella direzione indicata dal
	 *                  primo parametro.
	 */
	public void setStanzaAdiacente(String direzione, Stanza stanza) {
		if (direzione != null && stanza != null) {
			boolean aggiornato = false;
			for (int i = 0; i < this.direzioni.length; i++)
				if (direzione.equals(this.direzioni[i])) {
					this.stanzeAdiacenti[i] = stanza;
					aggiornato = true;
				}
			if (!aggiornato)
				if (this.numeroStanzeAdiacenti < NUMERO_MASSIMO_DIREZIONI) {
					this.direzioni[numeroStanzeAdiacenti] = direzione;
					this.stanzeAdiacenti[numeroStanzeAdiacenti] = stanza;
					this.numeroStanzeAdiacenti++;
				}
		}
	}

	/**
	 * Restituisce la lista di direzioni che si
	 * possono prendere a partire da questa stanza.
	 *
	 * @return la lista di direzioni che si
	 *         possono prendere a partire da questa stanza.
	 */
	public String[] getDirezioni() {
		String[] direzioni = new String[this.numeroStanzeAdiacenti];
		for (int i = 0; i < this.numeroStanzeAdiacenti; i++)
			direzioni[i] = this.direzioni[i];
		return direzioni;
	}

	public void setDirezioni(String[] direzioni) {
		this.direzioni = direzioni;
	}

	/**
	 * Restituisce l'attrezzo nomeAttrezzo, se presente nella stanza.
	 *
	 * @param nomeAttrezzo - il nome dell'attrezzo da restituire.
	 * @return l'attrezzo presente nella stanza,
	 *         null se l'attrezzo non e' presente.
	 */
	public Attrezzo getAttrezzo(String nomeAttrezzo) {
		Attrezzo attrezzoCercato = null;
		for (Attrezzo attrezzo : this.attrezzi) {
			if (attrezzo != null && attrezzo.getNome().equals(nomeAttrezzo)) {
				attrezzoCercato = attrezzo;
				break; // Smetti di cercare una volta trovato
			}
		}
		return attrezzoCercato;
	}

	/**
	 * Controlla se un attrezzo esiste nella stanza (uguaglianza sul nome).
	 *
	 * @return true se l'attrezzo esiste nella stanza, false altrimenti.
	 */
	public boolean hasAttrezzo(String nomeAttrezzo) {
		return (getAttrezzo(nomeAttrezzo) != null);
	}

	/**
	 * Pone un attrezzo nella stanza.
	 *
	 * @param attrezzo - l'attrezzo da mettere nella stanza.
	 * @return true se si riesce ad aggiungere l'attrezzo, false atrimenti.
	 */
	public boolean addAttrezzo(Attrezzo myAttrezzo) {
		if (myAttrezzo == null ||
				this.numeroAttrezzi == NUMERO_MASSIMO_ATTREZZI)
			return false;
		else {
			this.attrezzi[this.numeroAttrezzi] = myAttrezzo;
			this.numeroAttrezzi++;
			return true;
		}
	}

	/**
	 * Rimuove un attrezzo dalla stanza (assume che non ci siano duplicati).
	 *
	 * @param nomeAttrezzo - il nome dell'attrezzo da rimuovere.
	 * @return true se l'attrezzo e' stato rimosso, false altrimenti.
	 */
	public boolean removeAttrezzo(Attrezzo myAttrezzo) {
		if (myAttrezzo != null && this.numeroAttrezzi > 0) {
			for (int i = 0; i < this.attrezzi.length; i++) {
				if (this.attrezzi[i] != null && this.attrezzi[i].getNome().equals(myAttrezzo.getNome())) {
					this.attrezzi[i] = null;
					this.numeroAttrezzi--;
					return true; // Smetti di cercare una volta trovato
				}
			}
		} // else
		return false;
	}

	/**
	 * Restituisce una rappresentazione stringa di questa stanza,
	 * stampadone la descrizione, le uscite e gli eventuali attrezzi contenuti.
	 *
	 * @return la descrizione della stanza
	 */
	public String toString() {
		StringBuilder risultato = new StringBuilder();
		risultato.append("- " + this.getNome() + " -");
		risultato.append("\nUscite:");
		for (String direzione : this.direzioni) {
			if (direzione != null)
				risultato.append(" " + direzione);
		}
		risultato.append("\nAttrezzi:");
		for (Attrezzo attrezzo : this.attrezzi) {
			if (attrezzo != null)
				risultato.append(" " + attrezzo.toString());
		}
		return risultato.toString();
	}
}
