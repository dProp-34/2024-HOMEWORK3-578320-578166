package it.uniroma3.diadia.ambienti;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;

import it.uniroma3.diadia.Direzione;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.ios.CaricatoreLabirinto;
import it.uniroma3.diadia.ios.FormatoFileNonValidoException;
import it.uniroma3.diadia.personaggi.Cane;
import it.uniroma3.diadia.personaggi.Mago;
import it.uniroma3.diadia.personaggi.Strega;

public class Labirinto {
	private Stanza stanzaCorrente;
	private Stanza stanzaVincente;

	private Labirinto(String nomeFile) throws FileNotFoundException, FormatoFileNonValidoException {
		CaricatoreLabirinto c = new CaricatoreLabirinto(nomeFile);
		c.carica();
		this.stanzaCorrente = c.getStanzaIniziale();
		this.stanzaVincente = c.getStanzaVincente();
	}

	private Labirinto() {
		this.stanzaCorrente = null;
		this.stanzaVincente = null;
	}

	public Stanza getStanzaCorrente() {
		return this.stanzaCorrente;
	}

	public void setStanzaCorrente(Stanza stanzaCorrente) {
		this.stanzaCorrente = stanzaCorrente;
	}

	public Stanza getStanzaVincente() {
		return this.stanzaVincente;
	}

	public void setStanzaVincente(Stanza stanzaVincente) {
		this.stanzaVincente = stanzaVincente;
	}

	public static LabirintoBuilder newBuilder() {
		return new LabirintoBuilder();
	}

	public static LabirintoBuilder newBuilder(String nomeFile)
			throws FileNotFoundException, FormatoFileNonValidoException {
		return new LabirintoBuilder(nomeFile);
	}

	/**
	 * Crea le stanze e le porte di collegamento di un
	 * esempio di Labirinto, senza l'utilizzo di LabirintoBuilder
	 * public void demo() {
	 * /* crea gli attrezzi *
	 * Attrezzo lanterna = new Attrezzo("lanterna", 3);
	 * Attrezzo osso = new Attrezzo("osso", 1);
	 * /* crea le stanze del labirinto *
	 * Stanza atrio = new Stanza("Atrio");
	 * Stanza aulaN11 = new Stanza("Aula N11");
	 * Stanza aulaN10 = new Stanza("Aula N10");
	 * Stanza laboratorio = new Stanza("Laboratorio Campus");
	 * Stanza biblioteca = new Stanza("Biblioteca");
	 * /* collega le stanze *
	 * atrio.setStanzaAdiacente(Direzione.valueOf("nord"), biblioteca);
	 * atrio.setStanzaAdiacente(Direzione.valueOf("est"), aulaN11);
	 * atrio.setStanzaAdiacente(Direzione.valueOf("sud"), aulaN10);
	 * atrio.setStanzaAdiacente(Direzione.valueOf("ovest"), laboratorio);
	 * aulaN11.setStanzaAdiacente(Direzione.valueOf("est"), laboratorio);
	 * aulaN11.setStanzaAdiacente(Direzione.valueOf("ovest"), atrio);
	 * aulaN10.setStanzaAdiacente(Direzione.valueOf("nord"), atrio);
	 * aulaN10.setStanzaAdiacente(Direzione.valueOf("est"), aulaN11);
	 * aulaN10.setStanzaAdiacente(Direzione.valueOf("ovest"), laboratorio);
	 * laboratorio.setStanzaAdiacente(Direzione.valueOf("est"), atrio);
	 * laboratorio.setStanzaAdiacente(Direzione.valueOf("ovest"), aulaN11);
	 * biblioteca.setStanzaAdiacente(Direzione.valueOf("sud"), atrio);
	 * /* pone gli attrezzi nelle stanze *
	 * aulaN10.addAttrezzo(lanterna);
	 * atrio.addAttrezzo(osso);
	 * /* il gioco comincia nell'atrio *
	 * this.setStanzaCorrente(atrio);
	 * this.setStanzaVincente(biblioteca);
	 * }
	 */
	public static class LabirintoBuilder {
		private Labirinto labirinto;
		private LinkedList<Stanza> listaStanze;

		public LabirintoBuilder() {
			this.listaStanze = new LinkedList<>();
			this.labirinto = new Labirinto();
		}

		public LabirintoBuilder(String nomeFile) throws FileNotFoundException, FormatoFileNonValidoException {
			this.listaStanze = new LinkedList<>();
			this.labirinto = new Labirinto(nomeFile);
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

		public LabirintoBuilder setStrega(String nome, String presentazione) {
			this.listaStanze.getLast().setPersonaggio(new Strega(nome, presentazione));
			// tiene traccia
			return this;
		}

		public LabirintoBuilder setMago(String nome, String presentazione, Attrezzo attrezzo) {
			this.listaStanze.getLast().setPersonaggio(new Mago(nome, presentazione, attrezzo));
			// tiene traccia
			return this;
		}

		public LabirintoBuilder setCane(String nome, String presentazione) {
			this.listaStanze.getLast().setPersonaggio(new Cane(nome, presentazione));
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
}
