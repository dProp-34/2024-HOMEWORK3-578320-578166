package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.ambienti.Labirinto;

public class PartitaTest {
	private Partita lunedi;
	private Partita mercoledi;
	private Labirinto monolocale;

	@BeforeEach
	public void setUp() {
		monolocale = Labirinto.newBuilder().addStanzaIniziale("Corrente").getLabirinto();
		lunedi = new Partita(monolocale);
		mercoledi = new Partita(monolocale);
		mercoledi.setFinita();
	}

	@Test
	public void testIsNotVinta() {
		assertFalse(lunedi.isVinta());
	}

	@Test
	public void testIsFinitaPercheFinita() {
		assertTrue(mercoledi.isFinita());
	}
}
