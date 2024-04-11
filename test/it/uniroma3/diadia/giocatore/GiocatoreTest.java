package it.uniroma3.diadia.giocatore;

import it.uniroma3.diadia.Partita;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GiocatoreTest {
	private Partita giovedi;
	private Giocatore io;

	@BeforeEach
	public void setUp() {
		giovedi = new Partita();
		io = new Giocatore();
		for (int i = 0; i < 25; i++)
			io.decrementaCfu();
		giovedi.setGiocatore(io);
	}

	@Test
	void testIsFinitaPerchePersa() {
		assertTrue(giovedi.isFinita());
	}
}
