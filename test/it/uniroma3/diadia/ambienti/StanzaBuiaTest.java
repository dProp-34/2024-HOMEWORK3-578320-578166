package it.uniroma3.diadia.ambienti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.Assert.*;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class StanzaBuiaTest {
	private StanzaBuia buia;
	private Attrezzo torcia;

	@BeforeEach
	public void setUp() {
		buia = new StanzaBuia("Buia", "Torcia");
		torcia = new Attrezzo("Torcia", 2);
	}

	@Test
	public void testQuiBuioPesto() {
		assertEquals("Qui c'e' buio pesto.\n", buia.toString());
	}

	@Test
	public void testQuiNonBuioPesto() {
		buia.addAttrezzo(torcia);
		assertNotEquals("Qui c'e' buio pesto.\n", buia.toString());
	}
}
