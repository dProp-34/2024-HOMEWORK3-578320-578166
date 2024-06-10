package it.uniroma3.diadia.comandi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.FormatoFileNonValidoException;
import it.uniroma3.diadia.ambienti.Labirinto;

public class ComandoVaiTest {
	private Partita mercoledi;
	private ComandoVai primo;

	@BeforeEach
	void setUp() {
		mercoledi = new Partita(Labirinto.newBuilder().getLabirinto());
		primo = new ComandoVai();
	}

	@Test
	public void testDoveVuoiAndare() throws FileNotFoundException, FormatoFileNonValidoException {
		Labirinto labirinto = Labirinto.newBuilder()
				.addStanzaIniziale("LabCampusOne")
				.getLabirinto();
		mercoledi.setLabirinto(labirinto);

		primo.esegui(mercoledi);
		assertEquals("LabCampusOne", mercoledi.getLabirinto().getStanzaCorrente().getNome());
	}

	@Test
	public void testNonPuoiAndareLi() throws FileNotFoundException, FormatoFileNonValidoException {
		Labirinto labirinto = Labirinto.newBuilder()
				.addStanzaIniziale("LabCampusOne")
				.getLabirinto();
		mercoledi.setLabirinto(labirinto);

		primo.setParametro("nord");
		primo.esegui(mercoledi);
		assertEquals("LabCampusOne", mercoledi.getLabirinto().getStanzaCorrente().getNome());
	}

	@Test
	public void testLabirintoBilocale() throws FileNotFoundException, FormatoFileNonValidoException {
		int cfuPrec = mercoledi.getGiocatore().getCfu();
		Labirinto labirinto = Labirinto.newBuilder()
				.addStanzaIniziale("LabCampusOne")
				.addStanzaVincente("Biblioteca")
				.addAdiacenza("LabCampusOne", "Biblioteca", Direzione.ovest)
				.getLabirinto();
		mercoledi.setLabirinto(labirinto);

		primo.setParametro("ovest");
		primo.esegui(mercoledi);
		assertEquals("Biblioteca", mercoledi.getLabirinto().getStanzaCorrente().getNome());
		assertTrue(mercoledi.getGiocatore().getCfu() < cfuPrec);
		assertTrue(mercoledi.isVinta());
	}

	@Test
	public void testLabirintoCompleto() throws FileNotFoundException, FormatoFileNonValidoException {
		Labirinto labirinto = Labirinto.newBuilder()
				.addStanzaIniziale("LabCampusOne")
				.addStanza("Biblioteca")
				.addAdiacenza("LabCampusOne", "Biblioteca", Direzione.ovest)
				.addStanza("Aula N11")
				.addStanzaVincente("Aula N10")
				.addAdiacenza("LabCampusOne", "Aula N11", Direzione.sud)
				.addAdiacenza("Aula N11", "LabCampusOne", Direzione.nord)
				.addAdiacenza("Biblioteca", "Aula N10", Direzione.sud)
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
