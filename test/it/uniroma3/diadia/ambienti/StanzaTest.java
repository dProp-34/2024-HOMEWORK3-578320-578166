package it.uniroma3.diadia.ambienti;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.comandi.Direzione;

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
		this.bar.setStanzaAdiacente(Direzione.nord, this.mensa);
		this.mensa.setStanzaAdiacente(Direzione.sud, this.bar);
	}

	@Test
	void testSetStanzaAdiacente() {
		assertEquals("Piatto", this.bar.getStanzaAdiacente(Direzione.nord).getAttrezzo("Piatto").getNome());
		assertEquals("Tazzina", this.mensa.getStanzaAdiacente(Direzione.sud).getAttrezzo("Tazzina").getNome());
	}

	@Test
	public void testGetDirezioniStanzaVuota() {
		assertNotNull(this.etere.getDirezioni());
	}

	@Test
	public void testAddAttrezzoNull() {
		assertFalse(this.etere.addAttrezzo(null));
	}

	@Test
	public void testAddAttrezzo() {
		Attrezzo attrezzo = this.bar.getAttrezzo("Tazzina");
		assertNotNull(attrezzo);
		assertEquals("Tazzina", attrezzo.getNome());
	}

	@Test
	public void testRemoveAttrezzoNull() {
		assertFalse(this.etere.removeAttrezzo(null));
	}

	@Test
	public void testRemoveAttrezzoBorsaVuota() {
		assertFalse(this.etere.removeAttrezzo(this.piatto));
	}

	@Test
	public void testRemoveAttrezzoInesistente() {
		assertFalse(this.bar.removeAttrezzo(this.piatto));
	}

	@Test
	public void testRemoveAttrezzo() {
		assertTrue(this.bar.removeAttrezzo(this.tazzina));
		assertFalse(this.bar.hasAttrezzo("Tazzina"));
	}
}
