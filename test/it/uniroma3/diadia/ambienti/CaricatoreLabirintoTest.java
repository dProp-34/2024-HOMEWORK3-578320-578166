package it.uniroma3.diadia.ambienti;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.StringReader;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.comandi.Direzione;

public class CaricatoreLabirintoTest {
	private String nomeStanzaIniziale = "Atrio";
	private String nomeStanzaVincente = "Uscita";
	private CaricatoreLabirinto caricatore;

	/*
	 * Esempio di un possibile file di specifica di un labirinto (vedi
	 * POO-26-eccezioni-file.pdf)
	 * 
	 * Stanze: biblioteca, N10, N11
	 * Inizio: N10
	 * Vincente: N11
	 * Attrezzi: martello 10 biblioteca, pinza 2 N10
	 * Uscite: biblioteca nord N10, biblioteca sud N11
	 * 
	 * @Test(expected = FormatoFileNonValidoException.class)
	 */
	@Test
	public void testMonolocale() throws Exception {
		String fixture = "Stanze: " + nomeStanzaIniziale +
				"\nInizio: " + nomeStanzaIniziale +
				"\nVincente: " + nomeStanzaIniziale;
		this.caricatore = new CaricatoreLabirinto(new StringReader(fixture));
		caricatore.carica();

		Labirinto monolocale = caricatore.getBuilder().getLabirinto();
		assertEquals(nomeStanzaIniziale, monolocale.getStanzaCorrente().getNome());
		assertEquals(nomeStanzaIniziale, monolocale.getStanzaVincente().getNome());
	}

	@Test
	public void testMonolocaleConAttrezzo() throws Exception {
		String fixture = "Stanze: " + nomeStanzaIniziale +
				"\nInizio: " + nomeStanzaIniziale +
				"\nVincente: " + nomeStanzaIniziale +
				"\nAttrezzi: spada 1 " + nomeStanzaIniziale + ", spadina 3 " + nomeStanzaIniziale;
		this.caricatore = new CaricatoreLabirinto(new StringReader(fixture));
		caricatore.carica();

		Labirinto monolocale = caricatore.getBuilder().getLabirinto();
		assertEquals(nomeStanzaIniziale, monolocale.getStanzaCorrente().getNome());
		assertEquals(nomeStanzaIniziale, monolocale.getStanzaVincente().getNome());
		assertEquals("spada", monolocale.getStanzaCorrente().getAttrezzo("spada").getNome());
		assertEquals("spadina", monolocale.getStanzaVincente().getAttrezzo("spadina").getNome());
	}

	@Test
	public void testMonolocaleConAttrezzoSingoloDuplicato() throws Exception {
		String fixture = "Stanze: " + nomeStanzaIniziale +
				"\nInizio: " + nomeStanzaIniziale +
				"\nVincente: " + nomeStanzaIniziale +
				"\nAttrezzi: spada 1 " + nomeStanzaIniziale + ", spada 1 " + nomeStanzaIniziale;
		this.caricatore = new CaricatoreLabirinto(new StringReader(fixture));
		caricatore.carica();

		Labirinto monolocale = caricatore.getBuilder().getLabirinto();
		assertEquals(nomeStanzaIniziale, monolocale.getStanzaCorrente().getNome());
		int size = monolocale.getStanzaCorrente().getAttrezzi().size();
		assertTrue(size == 1);
		assertEquals(Arrays.asList(new Attrezzo("spada", 1)), monolocale.getStanzaCorrente().getAttrezzi());
	}

	/*
	 * .addStanzaIniziale(nomeStanzaIniziale)
	 * .addStanzaVincente(nomeStanzaVincente)
	 * .addAdiacenza(nomeStanzaIniziale, nomeStanzaVincente, "nord") Se da iniziale
	 * vai verso nord sei in vincente
	 * .addAdiacenza(nomeStanzaVincente, nomeStanzaIniziale, "sud")
	 * .getLabirinto();
	 */
	@Test
	public void testBilocale() throws Exception {
		String fixture = "Stanze: " + nomeStanzaIniziale + ", " + nomeStanzaVincente +
				"\nInizio: " + nomeStanzaIniziale +
				"\nVincente: " + nomeStanzaVincente +
				"\nUscite: " + nomeStanzaIniziale + " nord " + nomeStanzaVincente +
				", " + nomeStanzaVincente + " sud " + nomeStanzaIniziale;
		this.caricatore = new CaricatoreLabirinto(new StringReader(fixture));
		caricatore.carica();

		Labirinto bilocale = caricatore.getBuilder().getLabirinto();
		assertEquals(bilocale.getStanzaVincente(), bilocale.getStanzaCorrente().getStanzaAdiacente(Direzione.nord));
		assertEquals(Collections.singletonList(Direzione.nord), bilocale.getStanzaCorrente().getDirezioni());
		assertEquals(Collections.singletonList(Direzione.sud), bilocale.getStanzaVincente().getDirezioni());
	}

	/*
	 * .addStanzaIniziale(nomeStanzaIniziale).addAttrezzo("sedia", 1)
	 * .addStanza("biblioteca")
	 * .addAdiacenza(nomeStanzaIniziale, "biblioteca", Direzione.sud)
	 * .addAdiacenza("biblioteca", nomeStanzaIniziale, Direzione.nord)
	 * .addAttrezzo("libro antico", 5)
	 * .addStanzaVincente(nomeStanzaVincente)
	 * .addAdiacenza("biblioteca", nomeStanzaVincente, Direzione.est)
	 * .addAdiacenza(nomeStanzaVincente, "biblioteca", Direzione.ovest)
	 * .getLabirinto();
	 */
	@Test
	public void testTrilocale() throws Exception {
		String fixture = "Stanze: " + nomeStanzaIniziale + ", biblioteca, " + nomeStanzaVincente +
				"\nInizio: " + nomeStanzaIniziale +
				"\nVincente: " + nomeStanzaVincente +
				"\nAttrezzi: sedia 1 " + nomeStanzaIniziale +
				", libro_antico 5 biblioteca" +
				"\nUscite: " + nomeStanzaIniziale + " sud biblioteca, " +
				"biblioteca nord " + nomeStanzaIniziale + ", " +
				"biblioteca est " + nomeStanzaVincente + ", " +
				nomeStanzaVincente + " ovest biblioteca ";
		this.caricatore = new CaricatoreLabirinto(new StringReader(fixture));
		caricatore.carica();

		Labirinto trilocale = caricatore.getBuilder().getLabirinto();
		assertEquals(nomeStanzaIniziale, trilocale.getStanzaCorrente().getNome());
		assertEquals(nomeStanzaVincente, trilocale.getStanzaVincente().getNome());
		assertEquals("biblioteca", trilocale.getStanzaCorrente().getStanzaAdiacente(Direzione.sud).getNome());
	}
}
