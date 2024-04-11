package it.uniroma3.diadia.giocatore;

import it.uniroma3.diadia.attrezzi.Attrezzo;
import static org.junit.Assert.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BorsaTest {
	private Borsa zaino;
	private Borsa tasche;
	private Attrezzo tazzina;
	private Attrezzo piatto;

	@BeforeEach
	public void setUp() {
		this.zaino = new Borsa();
		this.tasche = new Borsa();
		this.tazzina = new Attrezzo("Tazzina", 1);
		this.piatto = new Attrezzo("Piatto", 3);

		this.zaino.addAttrezzo(this.tazzina);
		this.zaino.addAttrezzo(this.piatto);
	}

	@Test
	void testGetPesoBorsaVuota() {
		assertTrue(this.tasche.isEmpty());
		assertTrue(this.tasche.getPeso() == 0);
	}

	@Test
	void testGetPeso() {
		assertFalse(this.zaino.isEmpty());
		assertTrue(this.zaino.getPeso() != 0);
	}

	@Test
	void testAddAttrezzoNull() {
		assertFalse(this.tasche.addAttrezzo(null));
	}

	@Test
	void testAddAttrezzo() {
		Attrezzo attrezzo = this.zaino.getAttrezzo("Tazzina");
		assertNotNull(attrezzo);
		assertEquals("Tazzina", attrezzo.getNome());
	}

	@Test
	void testRemoveAttrezzoNull() {
		assertNull(this.tasche.removeAttrezzo(null));
	}

	@Test
	void testRemoveAttrezzoBorsaVuota() {
		assertNull(this.tasche.removeAttrezzo("1M€"));
	}

	@Test
	void testRemoveAttrezzoInesistente() {
		assertNull(this.zaino.removeAttrezzo("1M€"));
	}

	@Test
	void testRemoveAttrezzo() {
		assertNotNull(this.zaino.removeAttrezzo(this.tazzina.getNome()));
		assertFalse(this.zaino.hasAttrezzo("Tazzina"));
	}
}
