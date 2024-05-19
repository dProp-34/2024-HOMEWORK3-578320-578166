package it.uniroma3.diadia.comandi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.LabirintoBuilder;

public class ComandoVaiTest {
	private Partita mercoledi;
	private ComandoVai primo;

	@BeforeEach
	void setUp() {
		mercoledi = new Partita();
		primo = new ComandoVai();
	}

	@Test
	void testEseguiVai() {
		int cfuPrec = mercoledi.getGiocatore().getCfu();
		mercoledi.getLabirinto().demo();

		primo.setParametro("nord");
		primo.esegui(mercoledi);
		assertEquals("Biblioteca", mercoledi.getLabirinto().getStanzaCorrente().getNome());
		assertTrue(mercoledi.getGiocatore().getCfu() < cfuPrec);
	}

	@Test
	void testDoveVuoiAndare() {
		Labirinto labirinto = new LabirintoBuilder()
				.addStanzaIniziale("LabCampusOne")
				.getLabirinto();
		mercoledi.setLabirinto(labirinto);

		primo.esegui(mercoledi);
		assertEquals("LabCampusOne", mercoledi.getLabirinto().getStanzaCorrente().getNome());
	}

	@Test
	void testNonPuoiAndareLi() {
		Labirinto labirinto = new LabirintoBuilder()
				.addStanzaIniziale("LabCampusOne")
				.getLabirinto();
		mercoledi.setLabirinto(labirinto);

		primo.setParametro("nord");
		primo.esegui(mercoledi);
		assertEquals("LabCampusOne", mercoledi.getLabirinto().getStanzaCorrente().getNome());
	}

	@Test
	void testLabirintoBilocale() {
		Labirinto labirinto = new LabirintoBuilder()
				.addStanzaIniziale("LabCampusOne")
				.addStanzaVincente("Biblioteca")
				.addAdiacenza("LabCampusOne", "Biblioteca", "ovest")
				.getLabirinto();
		mercoledi.setLabirinto(labirinto);

		primo.setParametro("ovest");
		primo.esegui(mercoledi);
		assertEquals("Biblioteca", mercoledi.getLabirinto().getStanzaCorrente().getNome());
		assertTrue(mercoledi.isVinta());
	}

	@Test
	void testLabirintoCompleto() {
		Labirinto labirinto = new LabirintoBuilder()
				.addStanzaIniziale("LabCampusOne")
				.addStanza("Biblioteca")
				.addAdiacenza("LabCampusOne", "Biblioteca", "ovest")
				.addStanza("Aula N11")
				.addStanzaVincente("Aula N10")
				.addAdiacenza("LabCampusOne", "Aula N11", "sud")
				.addAdiacenza("Aula N11", "LabCampusOne", "nord")
				.addAdiacenza("Biblioteca", "Aula N10", "sud")
				.getLabirinto();
		mercoledi.setLabirinto(labirinto);

		primo.setParametro("ovest");
		primo.esegui(mercoledi);
		assertEquals("Biblioteca", mercoledi.getLabirinto().getStanzaCorrente().getNome());
		primo.setParametro("sud");
		primo.esegui(mercoledi);
		assertEquals("Aula N10", mercoledi.getLabirinto().getStanzaCorrente().getNome());
		assertTrue(mercoledi.isVinta());
	}
}
