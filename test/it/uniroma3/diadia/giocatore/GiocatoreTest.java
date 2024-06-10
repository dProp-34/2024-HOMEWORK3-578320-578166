package it.uniroma3.diadia.giocatore;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Labirinto;

public class GiocatoreTest {
	private Partita giovedi;
	private Giocatore io;

	@BeforeEach
	public void setUp() {
		giovedi = new Partita(Labirinto.newBuilder().getLabirinto());
		io = new Giocatore();
		for (int i = 0; i < 25; i++)
			io.decrementaCfu();
		giovedi.setGiocatore(io);
	}

	@Test
	public void testIsFinitaPerchePersa() {
		assertTrue(giovedi.isFinita());
	}
}
