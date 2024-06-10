package it.uniroma3.diadia.ambienti;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.Partita;

public class LabirintoTest {
	private Partita martedi;
	// private Labirinto uni;

	@BeforeEach
	public void setUp() {
		martedi = new Partita(Labirinto.newBuilder().addStanzaVincente("Vincente").getLabirinto());
		martedi.getLabirinto().setStanzaCorrente(martedi.getLabirinto().getStanzaVincente());
	}

	@Test
	public void testIsVinta() {
		assertTrue(martedi.isVinta());
	}

	@Test
	public void testIsFinitaPercheVinta() {
		assertTrue(martedi.isFinita());
	}
}
