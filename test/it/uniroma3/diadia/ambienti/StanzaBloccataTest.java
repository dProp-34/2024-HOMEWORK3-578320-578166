package it.uniroma3.diadia.ambienti;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.Direzione;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class StanzaBloccataTest {
	private StanzaBloccata bloccata;
	private Stanza nord;
	private Attrezzo pieDiPorco;

	@BeforeEach
	public void setUp() {
		bloccata = new StanzaBloccata("Bloccata", Direzione.nord, "Pie'di Porco");
		nord = new Stanza("Nord");
		bloccata.setStanzaAdiacente(Direzione.nord, nord);
		pieDiPorco = new Attrezzo("Pie'di Porco", 2);
	}

	@Test
	void testQuestUscitaSembraBloccata() {
		assertEquals(bloccata, bloccata.getStanzaAdiacente(Direzione.nord));
	}

	@Test
	public void testUscitaSbloccata() {
		bloccata.addAttrezzo(pieDiPorco);
		assertEquals(nord, bloccata.getStanzaAdiacente(Direzione.nord));
	}
}
