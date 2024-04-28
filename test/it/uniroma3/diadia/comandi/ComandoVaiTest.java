package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;
import static org.junit.Assert.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ComandoVaiTest {
	private Partita mercoledi;
	private ComandoVai primo;

	@BeforeEach
	public void setUp() {
		mercoledi = new Partita();
		primo = new ComandoVai();
	}

	@Test
	void testEseguiVai() {
		int cfuPrec = mercoledi.getGiocatore().getCfu();
		primo.setParametro("nord");
		primo.esegui(mercoledi);
		assertEquals("Biblioteca", mercoledi.getLabirinto().getStanzaCorrente().getNome());
		assertTrue(mercoledi.getGiocatore().getCfu() < cfuPrec);
	}

	@Test
	void testDoveVuoiAndare() {
		primo.esegui(mercoledi);
		assertEquals("Atrio", mercoledi.getLabirinto().getStanzaCorrente().getNome());
	}

	@Test
	void testNonPuoiAndareLi() {
		mercoledi.getLabirinto()
				.setStanzaCorrente(mercoledi.getLabirinto().getStanzaCorrente().getStanzaAdiacente("nord"));
		assertEquals("Biblioteca", mercoledi.getLabirinto().getStanzaCorrente().getNome());
		primo.setParametro("nord");
		primo.esegui(mercoledi);
		assertEquals("Biblioteca", mercoledi.getLabirinto().getStanzaCorrente().getNome());
	}
}
