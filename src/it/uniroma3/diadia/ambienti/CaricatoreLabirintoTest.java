package it.uniroma3.diadia.ambienti;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.attrezzi.Attrezzo;

public class CaricatoreLabirintoTest {
/* 	@Test
	public void testMonolocale() {
		Labirinto monolocale = labirintoBuilder
				.addStanzaIniziale(nomeStanzaIniziale)
				.addStanzaVincente(nomeStanzaIniziale)
				.getLabirinto();
		assertEquals(nomeStanzaIniziale, monolocale.getStanzaCorrente().getNome());
		assertEquals(nomeStanzaIniziale, monolocale.getStanzaVincente().getNome());
	}

	@Test
	public void testMonolocaleConAttrezzo() {
		Labirinto monolocale = labirintoBuilder
				.addStanzaIniziale(nomeStanzaIniziale).addAttrezzo("spada", 1)
				.addStanzaVincente(nomeStanzaIniziale).addAttrezzo("spadina", 3)
				.getLabirinto();
		assertEquals(nomeStanzaIniziale, monolocale.getStanzaCorrente().getNome());
		assertEquals(nomeStanzaIniziale, monolocale.getStanzaVincente().getNome());
		assertEquals("spada", monolocale.getStanzaCorrente().getAttrezzo("spada").getNome());
		assertEquals("spadina", monolocale.getStanzaVincente().getAttrezzo("spadina").getNome());
	}

	@Test
	public void testMonolocaleConAttrezzoSingoloDuplicato() {
		Labirinto monolocale = labirintoBuilder
				.addStanzaIniziale(nomeStanzaIniziale)
				.addAttrezzo("spada", 1)
				.addAttrezzo("spada", 1)
				.getLabirinto();
		int size = monolocale.getStanzaCorrente().getAttrezzi().size();
		assertTrue(size == 1);
		assertEquals(Arrays.asList(new Attrezzo("spada", 1)), monolocale.getStanzaCorrente().getAttrezzi());
	}

	@Test
	public void testBilocale() {
		Labirinto bilocale = labirintoBuilder
				.addStanzaIniziale(nomeStanzaIniziale)
				.addStanzaVincente(nomeStanzaVincente)
				.addAdiacenza(nomeStanzaIniziale, nomeStanzaVincente, "nord")
				.addAdiacenza(nomeStanzaVincente, nomeStanzaIniziale, "sud")
				.getLabirinto();
		assertEquals(bilocale.getStanzaVincente(), bilocale.getStanzaCorrente().getStanzaAdiacente("nord"));
		assertEquals(Collections.singletonList("nord"), bilocale.getStanzaCorrente().getDirezioni());
		assertEquals(Collections.singletonList("sud"), bilocale.getStanzaVincente().getDirezioni());
	}

	@Test
	public void testTrilocale() {
		Labirinto trilocale = labirintoBuilder
				.addStanzaIniziale(nomeStanzaIniziale).addAttrezzo("sedia", 1)
				.addStanza("biblioteca")
				.addAdiacenza(nomeStanzaIniziale, "biblioteca", "sud")
				.addAdiacenza("biblioteca", nomeStanzaIniziale, "nord")
				.addAttrezzo("libro antico", 5)
				.addStanzaVincente(nomeStanzaVincente)
				.addAdiacenza("biblioteca", nomeStanzaVincente, "est")
				.addAdiacenza(nomeStanzaVincente, "biblioteca", "ovest")
				.getLabirinto();
		assertEquals(nomeStanzaIniziale, trilocale.getStanzaCorrente().getNome());
		assertEquals(nomeStanzaVincente, trilocale.getStanzaVincente().getNome());
		assertEquals("biblioteca", trilocale.getStanzaCorrente().getStanzaAdiacente("sud").getNome());
	} */
}
