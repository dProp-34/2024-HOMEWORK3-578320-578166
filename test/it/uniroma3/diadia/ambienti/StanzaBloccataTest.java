package it.uniroma3.diadia.ambienti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.Assert.*;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class StanzaBloccataTest {
	private StanzaBloccata bloccata;
	private Stanza nord;
	private Attrezzo pieDiPorco;

	@BeforeEach
	public void setUp() {
		bloccata = new StanzaBloccata("Bloccata", "Nord", "Pie'di Porco");
		nord = new Stanza("Nord");
		bloccata.setStanzaAdiacente("Nord", nord);
		pieDiPorco = new Attrezzo("Pie'di Porco", 2);
	}

	@Test
	public void testQuestUscitaSembraBloccata() {
		assertEquals(bloccata, bloccata.getStanzaAdiacente("Nord"));
	}

	@Test
	public void testUscitaSbloccata() {
		bloccata.addAttrezzo(pieDiPorco);
		assertEquals(nord, bloccata.getStanzaAdiacente("Nord"));
	}
}
