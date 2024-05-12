package it.uniroma3.diadia.giocatore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.attrezzi.Attrezzo;

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

	@Test
	void testGetContenutoBorsaVuota() {
		assertTrue(tasche.getContenutoOrdinatoPerPeso().isEmpty());
		assertTrue(tasche.getContenutoOrdinatoPerNome().isEmpty());
		assertTrue(tasche.getSortedSetOrdinatoPerPeso().isEmpty());
		assertTrue(tasche.getContenutoRaggruppatoPerPeso().isEmpty());
	}

	@Test
	void testGetContenutoOrdinatoPerPeso() {
		Attrezzo cucchiaio = new Attrezzo("Cucchiaio", 1);
		this.zaino.addAttrezzo(cucchiaio);
		List<Attrezzo> ordinata = zaino.getContenutoOrdinatoPerPeso();
		Iterator<Attrezzo> it = ordinata.iterator();
		Attrezzo a = it.next();
		assertEquals(cucchiaio, a);
		a = it.next();
		assertEquals(tazzina, a);
		a = it.next();
		assertEquals(piatto, a);
	}

	@Test
	void testGetContenutoOrdinatoPerNome() {
		this.zaino.addAttrezzo(tazzina);
		assertTrue(this.zaino.getAttrezzi().size() == 3);
		SortedSet<Attrezzo> ordinata = zaino.getContenutoOrdinatoPerNome();
		Iterator<Attrezzo> it = ordinata.iterator();
		assertTrue(ordinata.size() == 2);
		Attrezzo a = it.next();
		assertEquals(piatto, a);
		a = it.next();
		assertEquals(tazzina, a);
	}

	@Test
	void testGetSortedSetOrdinatoPerPeso() {
		this.zaino.addAttrezzo(tazzina);
		Attrezzo cucchiaio = new Attrezzo("Cucchiaio", 1);
		this.zaino.addAttrezzo(cucchiaio);
		assertTrue(this.zaino.getAttrezzi().size() == 4);
		Set<Attrezzo> ordinata = zaino.getSortedSetOrdinatoPerPeso();
		Iterator<Attrezzo> it = ordinata.iterator();
		assertTrue(ordinata.size() == 3);
		Attrezzo a = it.next();
		assertEquals(cucchiaio, a);
		a = it.next();
		assertEquals(tazzina, a);
		a = it.next();
		assertEquals(piatto, a);
	}

	@Test
	void testGetContenutoRaggruppatoPerPeso() {
		Attrezzo cucchiaio = new Attrezzo("Cucchiaio", 1);
		this.zaino.addAttrezzo(cucchiaio);
		Map<Integer, Set<Attrezzo>> ordinata = zaino.getContenutoRaggruppatoPerPeso();
		/*
		 * Iterator<Attrezzo> it = ordinata.iterator();
		 * Attrezzo a = it.next();
		 */
		assertTrue(ordinata.get(1).contains(cucchiaio));
		assertTrue(ordinata.get(1).contains(tazzina));
		assertTrue(ordinata.get(2) == null);
		assertTrue(ordinata.get(3).contains(piatto));
	}
}
