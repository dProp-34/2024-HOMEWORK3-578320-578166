package it.uniroma3.diadia.ambienti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.Assert.*;
import it.uniroma3.diadia.giocatore.Giocatore;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class StanzaMagicaTest {
	private StanzaMagica magica;
	private Giocatore io;
	private Attrezzo matrioska;

	@BeforeEach
	public void setUp() {
		magica = new StanzaMagica("Magica");
		io = new Giocatore();
		matrioska = new Attrezzo("Matrioska", 1);
		for (int i = 0; i < 5; i++)
			io.getBorsa().addAttrezzo(matrioska);
	}

	@Test
	void testmodificaNull() {
		assertFalse(this.magica.addAttrezzo(null));
	}

	@Test
	void testmodificaAttrezzo() {
		for (int i = 0; i < 5; i++)
			magica.addAttrezzo(io.getBorsa().removeAttrezzo("Matrioska"));
		assertTrue(magica.getAttrezzo("aksoirtaM").getPeso() == 2);
	}
}
