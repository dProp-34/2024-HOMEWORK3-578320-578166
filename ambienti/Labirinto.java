package it.uniroma3.diadia.ambienti;

import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.comandi.Direzione;

public class Labirinto {
	private Stanza stanzaCorrente;
	private Stanza stanzaVincente;

	public Labirinto() {
		this.stanzaCorrente = new Stanza();
		this.stanzaVincente = new Stanza();
	}
	
	public static LabirintoBuilder newLabirintoBuilder() {
		return new LabirintoBuilder();
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
		atrio.setStanzaAdiacente(Direzione.valueOf("NORD"), biblioteca);
		atrio.setStanzaAdiacente(Direzione.valueOf("EST"), aulaN11);
		atrio.setStanzaAdiacente(Direzione.valueOf("SUD"), aulaN10);
		atrio.setStanzaAdiacente(Direzione.valueOf("OVEST"), laboratorio);
		aulaN11.setStanzaAdiacente(Direzione.valueOf("EST"), laboratorio);
		aulaN11.setStanzaAdiacente(Direzione.valueOf("OVEST"), atrio);
		aulaN10.setStanzaAdiacente(Direzione.valueOf("NORD"), atrio);
		aulaN10.setStanzaAdiacente(Direzione.valueOf("EST"), aulaN11);
		aulaN10.setStanzaAdiacente(Direzione.valueOf("OVEST"), laboratorio);
		laboratorio.setStanzaAdiacente(Direzione.valueOf("EST"), atrio);
		laboratorio.setStanzaAdiacente(Direzione.valueOf("OVEST"), aulaN11);
		biblioteca.setStanzaAdiacente(Direzione.valueOf("SUD"), atrio);

		/* pone gli attrezzi nelle stanze */
		aulaN10.addAttrezzo(lanterna);
		atrio.addAttrezzo(osso);

		/* il gioco comincia nell'atrio */
		this.setStanzaCorrente(atrio);
		this.setStanzaVincente(biblioteca);
	}
}
