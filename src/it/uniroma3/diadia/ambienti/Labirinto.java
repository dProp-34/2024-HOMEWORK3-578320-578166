package it.uniroma3.diadia.ambienti;

import it.uniroma3.diadia.attrezzi.Attrezzo;

public class Labirinto {
	private Stanza stanzaCorrente;
	private Stanza stanzaVincente;

	public Labirinto() {
		this.stanzaCorrente = new Stanza();
		this.stanzaVincente = new Stanza();
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

	/**
	 * Crea le stanze e le porte di collegamento di un
	 * esempio di Labirinto, senza l'utilizzo di LabirintoBuilder
	 */
	public void demo() {
		/* crea gli attrezzi */
		Attrezzo lanterna = new Attrezzo("lanterna", 3);
		Attrezzo osso = new Attrezzo("osso", 1);

		/* crea le stanze del labirinto */
		Stanza atrio = new Stanza("Atrio");
		Stanza aulaN11 = new Stanza("Aula N11");
		Stanza aulaN10 = new Stanza("Aula N10");
		Stanza laboratorio = new Stanza("Laboratorio Campus");
		Stanza biblioteca = new Stanza("Biblioteca");

		/* collega le stanze */
		atrio.setStanzaAdiacente("nord", biblioteca);
		atrio.setStanzaAdiacente("est", aulaN11);
		atrio.setStanzaAdiacente("sud", aulaN10);
		atrio.setStanzaAdiacente("ovest", laboratorio);
		aulaN11.setStanzaAdiacente("est", laboratorio);
		aulaN11.setStanzaAdiacente("ovest", atrio);
		aulaN10.setStanzaAdiacente("nord", atrio);
		aulaN10.setStanzaAdiacente("est", aulaN11);
		aulaN10.setStanzaAdiacente("ovest", laboratorio);
		laboratorio.setStanzaAdiacente("est", atrio);
		laboratorio.setStanzaAdiacente("ovest", aulaN11);
		biblioteca.setStanzaAdiacente("sud", atrio);

		/* pone gli attrezzi nelle stanze */
		aulaN10.addAttrezzo(lanterna);
		atrio.addAttrezzo(osso);

		/* il gioco comincia nell'atrio */
		this.setStanzaCorrente(atrio);
		this.setStanzaVincente(biblioteca);
	}
}
