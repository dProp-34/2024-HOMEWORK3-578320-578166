package it.uniroma3.diadia.giocatore;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.attrezzi.ComparatorePerPeso;
import it.uniroma3.diadia.ios.CaricatoreProprieta;
import it.uniroma3.diadia.ios.FormatoFileNonValidoException;

public class Borsa {
	static final private int PESO_MAX_DEFAULT = 20;

	private List<Attrezzo> attrezzi;
	private int pesoMax;

	public Borsa() throws IOException {
		this.attrezzi = new ArrayList<Attrezzo>();
		try {
			CaricatoreProprieta caricatore = new CaricatoreProprieta();
			this.pesoMax = caricatore.caricaPesoMax();
		} catch (FileNotFoundException e) {
			this.pesoMax = PESO_MAX_DEFAULT;
		} catch (FormatoFileNonValidoException e) {
			throw new RuntimeException("Formato Proprieta non valido!");
		}
	}

	public List<Attrezzo> getAttrezzi() {
		return this.attrezzi;
	}

	public void setAttrezzi(List<Attrezzo> attrezzi) {
		this.attrezzi = attrezzi;
	}

	public int getPesoMax() {
		return pesoMax;
	}

	public void setPesoMax(int pesoMax) {
		this.pesoMax = pesoMax;
	}

	public boolean isEmpty() {
		return this.attrezzi.isEmpty();
	}

	public int getPeso() {
		int peso = 0;
		/*
		 * for (Attrezzo attrezzo : this.attrezzi)
		 * if (attrezzo != null)
		 * peso += attrezzo.getPeso();
		 */
		Iterator<Attrezzo> it = this.attrezzi.iterator();
		while (it.hasNext()) {
			Attrezzo a = it.next();
			if (a != null)
				peso += a.getPeso();
		}
		return peso;
	}

	public boolean hasAttrezzo(String nomeAttrezzo) {
		return this.getAttrezzo(nomeAttrezzo) != null;
	}

	public Attrezzo getAttrezzo(String nomeAttrezzo) {
		/*
		 * if (this.numeroAttrezzi > 0)
		 * for (int i = 0; i < this.attrezzi.length; i++)
		 * if (this.attrezzi[i] != null &&
		 * this.attrezzi[i].getNome().equals(nomeAttrezzo)) {
		 * a = attrezzi[i];
		 * }
		 */
		Iterator<Attrezzo> it = this.attrezzi.iterator();
		while (it.hasNext()) {
			Attrezzo a = it.next();
			if (a != null && a.getNome().equals(nomeAttrezzo))
				return a;
		}
		return null;
	}

	public boolean addAttrezzo(Attrezzo myAttrezzo) {
		if (myAttrezzo == null ||
				this.getPeso() + myAttrezzo.getPeso() > this.getPesoMax())
			return false;
		else {
			return this.attrezzi.add(myAttrezzo);
		}
	}

	/**
	 * Rimuove un attrezzo dalla borsa (assume che non ci siano duplicati).
	 *
	 * @param nomeAttrezzo - il nome dell'attrezzo da rimuovere.
	 * @return l'attrezzo rimosso.
	 */
	public Attrezzo removeAttrezzo(String nomeAttrezzo) {
		Attrezzo a = null;
		Iterator<Attrezzo> it = this.attrezzi.iterator();
		while (it.hasNext()) {
			a = it.next();
			// for (int i = 0; i < this.attrezzi.length; i++)
			if (a != null && a.getNome().equals(nomeAttrezzo)) {
				it.remove();
				return a;
			}
		}
		return null;
	}

	public List<Attrezzo> getContenutoOrdinatoPerPeso() {
		final List<Attrezzo> cont = new ArrayList<>(this.attrezzi);
		ComparatorePerPeso comp = new ComparatorePerPeso();
		Collections.sort(cont, comp);
		return cont;
	}

	public SortedSet<Attrezzo> getContenutoOrdinatoPerNome() {
		return (new TreeSet<>(this.attrezzi));
	}

	public SortedSet<Attrezzo> getSortedSetOrdinatoPerPeso() {
		SortedSet<Attrezzo> cont = new TreeSet<>(new ComparatorePerPeso());
		cont.addAll(this.attrezzi);
		return cont;
	}

	public Map<Integer, Set<Attrezzo>> getContenutoRaggruppatoPerPeso() {
		final Map<Integer, Set<Attrezzo>> peso2attrezzi = new HashMap<>();
		for (Attrezzo attrezzo : this.attrezzi) {
			final Set<Attrezzo> daAggiungere;
			if (peso2attrezzi.containsKey(attrezzo.getPeso())) {
				// Questo attrezzo ha un peso che ho già visto
				daAggiungere = peso2attrezzi.get(attrezzo.getPeso());
			} else {
				// Questo attrezzo ha un peso mai visto prima
				daAggiungere = new HashSet<>();
			}
			daAggiungere.add(attrezzo);
			peso2attrezzi.put(attrezzo.getPeso(), daAggiungere);
		}
		return peso2attrezzi;
	}

	public String toString() {
		StringBuilder risultato = new StringBuilder();
		risultato.append("Borsa(" + this.getPeso() + "kg / " + this.getPesoMax() + "kg): ");
		/*
		 * for (Attrezzo attrezzo : this.getContenutoOrdinatoPerNome()) {
		 * if (attrezzo != null)
		 * risultato.append(" " + attrezzo.toString());
		 * }
		 */
		risultato.append(this.getContenutoOrdinatoPerNome());
		return risultato.toString();
	}
}
