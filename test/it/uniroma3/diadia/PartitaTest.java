package it.uniroma3.diadia;

import static org.junit.Assert.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PartitaTest {
	private Partita lunedi;
	private Partita mercoledi;

	@BeforeEach
	public void setUp() {
		lunedi = new Partita();
		mercoledi = new Partita();
		mercoledi.setFinita();
	}

	@Test
	void testIsNotVinta() {
		assertFalse(lunedi.isVinta());
	}

	@Test
	void testIsFinitaPercheFinita() {
		assertTrue(mercoledi.isFinita());
	}
}
