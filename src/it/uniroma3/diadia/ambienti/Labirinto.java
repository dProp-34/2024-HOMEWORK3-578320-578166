package it.uniroma3.diadia.ambienti;

import java.io.FileNotFoundException;

import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.comandi.Direzione;

public class Labirinto {
	private Stanza stanzaCorrente;
	private Stanza stanzaVincente;

	public Labirinto(String nomeFile) throws FileNotFoundException, FormatoFileNonValidoException {
		CaricatoreLabirinto c = new CaricatoreLabirinto(nomeFile);
		c.carica();
		this.stanzaCorrente = c.getStanzaIniziale();
		this.stanzaVincente = c.getStanzaVincente();
	}

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
		atrio.setStanzaAdiacente(Direzione.valueOf("nord"), biblioteca);
		atrio.setStanzaAdiacente(Direzione.valueOf("est"), aulaN11);
		atrio.setStanzaAdiacente(Direzione.valueOf("sud"), aulaN10);
		atrio.setStanzaAdiacente(Direzione.valueOf("ovest"), laboratorio);
		aulaN11.setStanzaAdiacente(Direzione.valueOf("est"), laboratorio);
		aulaN11.setStanzaAdiacente(Direzione.valueOf("ovest"), atrio);
		aulaN10.setStanzaAdiacente(Direzione.valueOf("nord"), atrio);
		aulaN10.setStanzaAdiacente(Direzione.valueOf("est"), aulaN11);
		aulaN10.setStanzaAdiacente(Direzione.valueOf("ovest"), laboratorio);
		laboratorio.setStanzaAdiacente(Direzione.valueOf("est"), atrio);
		laboratorio.setStanzaAdiacente(Direzione.valueOf("ovest"), aulaN11);
		biblioteca.setStanzaAdiacente(Direzione.valueOf("sud"), atrio);

		/* pone gli attrezzi nelle stanze */
		aulaN10.addAttrezzo(lanterna);
		atrio.addAttrezzo(osso);

		/* il gioco comincia nell'atrio */
		this.setStanzaCorrente(atrio);
		this.setStanzaVincente(biblioteca);
	}
}
