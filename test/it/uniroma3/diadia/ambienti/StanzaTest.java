package it.uniroma3.diadia.ambienti;

import it.uniroma3.diadia.attrezzi.Attrezzo;
import static org.junit.Assert.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StanzaTest {
	private Stanza bar;
	private Stanza mensa;
	private Stanza etere;
	private Attrezzo tazzina;
	private Attrezzo piatto;

	@BeforeEach
	public void setUp() {
		this.bar = new Stanza("Bar");
		this.mensa = new Stanza("Mensa");
		this.etere = new Stanza(null);
		this.tazzina = new Attrezzo("Tazzina", 1);
		this.piatto = new Attrezzo("Piatto", 3);

		this.bar.addAttrezzo(this.tazzina);
		this.mensa.addAttrezzo(this.piatto);
		this.bar.setStanzaAdiacente("nord", this.mensa);
		this.mensa.setStanzaAdiacente("sud", this.bar);
	}

	@Test
	void testSetStanzaAdiacente() {
		assertEquals("Piatto", this.bar.getStanzaAdiacente("nord").getAttrezzo("Piatto").getNome());
		assertEquals("Tazzina", this.mensa.getStanzaAdiacente("sud").getAttrezzo("Tazzina").getNome());
	}

	@Test
	void testGetDirezioniStanzaVuota() {
		assertNotNull(this.etere.getDirezioni());
	}

	@Test
	void testAddAttrezzoNull() {
		assertFalse(this.etere.addAttrezzo(null));
	}

	@Test
	void testAddAttrezzo() {
		Attrezzo attrezzo = this.bar.getAttrezzo("Tazzina");
		assertNotNull(attrezzo);
		assertEquals("Tazzina", attrezzo.getNome());
	}

	@Test
	void testRemoveAttrezzoNull() {
		assertFalse(this.etere.removeAttrezzo(null));
	}

	@Test
	void testRemoveAttrezzoBorsaVuota() {
		assertFalse(this.etere.removeAttrezzo(this.piatto));
	}

	@Test
	void testRemoveAttrezzoInesistente() {
		assertFalse(this.bar.removeAttrezzo(this.piatto));
	}

	@Test
	void testRemoveAttrezzo() {
		assertTrue(this.bar.removeAttrezzo(this.tazzina));
		assertFalse(this.bar.hasAttrezzo("Tazzina"));
	}
}