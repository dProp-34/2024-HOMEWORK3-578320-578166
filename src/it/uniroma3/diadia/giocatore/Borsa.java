package it.uniroma3.diadia.giocatore;

import it.uniroma3.diadia.attrezzi.Attrezzo;

public class Borsa {
	public final static int PESO_MAX_BORSA = 10;
	public final static int NUMERO_MAX_ATTREZZI = 10;

	private Attrezzo[] attrezzi;
	private int numeroAttrezzi;
	private int pesoMax;

	public Borsa() {
		this(PESO_MAX_BORSA);
	}

	public Borsa(int pesoMax) {
		this.pesoMax = pesoMax;
		this.attrezzi = new Attrezzo[NUMERO_MAX_ATTREZZI];
		this.numeroAttrezzi = 0;
	}

	public Attrezzo[] getAttrezzi() {
		return attrezzi;
	}

	public void setAttrezzi(Attrezzo[] attrezzi) {
		this.attrezzi = attrezzi;
	}

	public int getNumeroAttrezzi() {
		return numeroAttrezzi;
	}

	public void setNumeroAttrezzi(int numeroAttrezzi) {
		this.numeroAttrezzi = numeroAttrezzi;
	}

	public int getPesoMax() {
		return pesoMax;
	}

	public void setPesoMax(int pesoMax) {
		this.pesoMax = pesoMax;
	}

	public boolean isEmpty() {
		return this.numeroAttrezzi == 0;
	}

	public int getPeso() {
		int peso = 0;
		for (int i = 0; i < this.numeroAttrezzi; i++)
			if (this.attrezzi[i] != null)
				peso += this.attrezzi[i].getPeso();
		return peso;
	}

	public boolean hasAttrezzo(String nomeAttrezzo) {
		return this.getAttrezzo(nomeAttrezzo) != null;
	}

	public Attrezzo getAttrezzo(String nomeAttrezzo) {
		Attrezzo a = null;
		if (this.numeroAttrezzi > 0)
			for (int i = 0; i < this.attrezzi.length; i++)
				if (this.attrezzi[i] != null && this.attrezzi[i].getNome().equals(nomeAttrezzo)) {
					a = attrezzi[i];
				}
		return a;
	}

	public boolean addAttrezzo(Attrezzo attrezzo) {
		if (attrezzo == null ||
				this.numeroAttrezzi == NUMERO_MAX_ATTREZZI ||
				this.getPeso() + attrezzo.getPeso() > this.getPesoMax())
			return false;
		else {
			this.attrezzi[this.numeroAttrezzi] = attrezzo;
			this.numeroAttrezzi++;
			return true;
		}
	}

	/**
	 * Rimuove un attrezzo dalla borsa (assume che non ci siano duplicati).
	 *
	 * @param nomeAttrezzo - il nome dell'attrezzo da rimuovere.
	 * @return l'attrezzo rimosso.
	 */
	public Attrezzo removeAttrezzo(String myAttrezzo) {
		Attrezzo a = null;
		if (this.numeroAttrezzi > 0)
			for (int i = 0; i < this.attrezzi.length; i++)
				if (this.attrezzi[i] != null && this.attrezzi[i].getNome().equals(myAttrezzo)) {
					a = this.attrezzi[i];
					this.attrezzi[i] = null;
					this.numeroAttrezzi--;
					break;
				}
		return a;
	}

	public String toString() {
		StringBuilder risultato = new StringBuilder();
		risultato.append("Borsa(" + this.getPeso() + "kg / " + this.getPesoMax() + "kg):");
		for (Attrezzo attrezzo : this.attrezzi) {
			if (attrezzo != null)
				risultato.append(" " + attrezzo.toString());
		}
		return risultato.toString();
	}
}
