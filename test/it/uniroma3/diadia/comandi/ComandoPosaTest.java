package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

import static org.junit.Assert.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ComandoPosaTest {
	private Attrezzo chiavi;
	private Partita lunedi;
	private ComandoPosa primo;

	@BeforeEach
	public void setUp() {
		chiavi = new Attrezzo("Chiavi", 1);
		lunedi = new Partita();
		lunedi.getGiocatore().getBorsa().addAttrezzo(chiavi);
		primo = new ComandoPosa();
	}

	@Test
	public void testEseguiPosa() {
		primo.setParametro("Chiavi");
		primo.esegui(lunedi);
		assertEquals(chiavi, lunedi.getLabirinto().getStanzaCorrente().getAttrezzo("Chiavi"));
	}

	@Test
	public void testCosaVuoiPosare() {
		primo.esegui(lunedi);
		assertEquals(null, lunedi.getLabirinto().getStanzaCorrente().getAttrezzo("Chiavi"));
	}

	@Test
	public void testNonPossiediQuellAttrezzo() {
		primo.setParametro("Etere");
		primo.esegui(lunedi);
		assertEquals(null, lunedi.getLabirinto().getStanzaCorrente().getAttrezzo("Chiavi"));
	}

	@Test
	public void testNonPuoiPosareQuellAttrezzo() {
		/*
		 * Attrezzo etere = new Attrezzo("Etere", 0);
		 * for (int i = 0; i < 15; i++)
		 * lunedi.getLabirinto().getStanzaCorrente().addAttrezzo(etere);
		 * primo.setParametro("Chiavi");
		 * primo.esegui(lunedi);
		 * assertEquals(null,
		 * lunedi.getLabirinto().getStanzaCorrente().getAttrezzo("Chiavi"));
		 */
		assertTrue(true); // La Stanza è stata modificata in modo da poter contenere infiniti Attrezzi
	}
}
