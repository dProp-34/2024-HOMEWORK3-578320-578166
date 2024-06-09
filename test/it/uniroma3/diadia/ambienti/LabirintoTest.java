package it.uniroma3.diadia.ambienti;

import it.uniroma3.diadia.Partita;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LabirintoTest {
	private Partita martedi;
	private Labirinto uni;

	@BeforeEach
	public void setUp() {
		martedi = new Partita();
		uni = new Labirinto();
		uni.setStanzaCorrente(uni.getStanzaVincente());
		martedi.setLabirinto(uni);
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
