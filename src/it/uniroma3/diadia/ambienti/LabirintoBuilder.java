package it.uniroma3.diadia.ambienti;

import java.util.Iterator;
import java.util.LinkedList;

import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.comandi.Direzione;

public class LabirintoBuilder {
	private Labirinto labirinto;
	private LinkedList<Stanza> listaStanze;

	public LabirintoBuilder() {
		this.listaStanze = new LinkedList<>();
		this.labirinto = new Labirinto();
	}

	public Labirinto getLabirinto() {
		return labirinto;
	}

	public void setLabirinto(Labirinto labirinto) {
		this.labirinto = labirinto;
	}

	public LinkedList<Stanza> getListaStanze() {
		return listaStanze;
	}

	public void setListaStanze(LinkedList<Stanza> listaStanze) {
		this.listaStanze = listaStanze;
	}

	public LabirintoBuilder addStanza(String nome) {
		if (this.get(nome) == null)
			this.listaStanze.add(new Stanza(nome));
		// tiene traccia
		return this;
	}

	public LabirintoBuilder addStanzaIniziale(String nome) {
		this.addStanza(nome);
		this.labirinto.setStanzaCorrente(this.listaStanze.getLast());
		// tiene traccia
		return this;
	}

	public LabirintoBuilder addStanzaVincente(String nome) {
		this.addStanza(nome);
		this.labirinto.setStanzaVincente(this.listaStanze.getLast());
		// tiene traccia
		return this;
	}

	public void setStanzaIniziale(String nomeStanzaIniziale) {
		this.labirinto.setStanzaCorrente(this.get(nomeStanzaIniziale));
	}

	public void setStanzaVincente(String nomeStanzaVincente) {
		this.labirinto.setStanzaVincente(this.get(nomeStanzaVincente));
	}

	public LabirintoBuilder addStanzaMagica(String nomeStanzaMagica, int sogliaMagica) {
		Stanza daAggiungere = new StanzaMagica(nomeStanzaMagica, sogliaMagica);
		this.listaStanze.add(daAggiungere);
		// tiene traccia
		return this;
	}

	public LabirintoBuilder addStanzaBloccata(String string, Direzione direzioneBloccata, String string2) {
		Stanza daAggiungere = new StanzaBloccata(string, direzioneBloccata, string2);
		this.listaStanze.add(daAggiungere);
		// tiene traccia
		return this;
	}

	public LabirintoBuilder addStanzaBuia(String string, String string2) {
		Stanza daAggiungere = new StanzaBuia(string, string2);
		this.listaStanze.add(daAggiungere);
		// tiene traccia
		return this;
	}

	public LabirintoBuilder addAttrezzo(String nome, int peso) {
		this.listaStanze.getLast().addAttrezzo(new Attrezzo(nome, peso));
		// tiene traccia
		return this;
	}

	public LabirintoBuilder addAdiacenza(String nomeFrom, String nomeTo, Direzione direzione) {
		if (direzione == null)
			return null;
		Stanza from = this.get(nomeFrom);
		Stanza to = this.get(nomeTo);
		if (from != null && to != null)
			from.setStanzaAdiacente(direzione, to);
		// tiene traccia
		return this;
	}

	public Stanza get(String nomeStanza) {
		Stanza stanza = null;
		Iterator<Stanza> it = this.listaStanze.iterator();
		while (it.hasNext()) {
			Stanza s = it.next();
			if (s != null && s.getNome().equals(nomeStanza))
				stanza = s;
		}
		return stanza;
	}
}
