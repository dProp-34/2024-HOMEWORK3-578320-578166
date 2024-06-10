package it.uniroma3.diadia.comandi;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoPrendiTest {
	private Attrezzo chiavi;
	private Partita martedi;
	private ComandoPrendi primo;
	private Labirinto monolocale;

	@BeforeEach
	public void setUp() {
		chiavi = new Attrezzo("Chiavi", 1);
		monolocale = Labirinto.newBuilder().addStanzaIniziale("Corrente").getLabirinto();
		martedi = new Partita(monolocale);
		martedi.getLabirinto().getStanzaCorrente().addAttrezzo(chiavi);
		primo = new ComandoPrendi();
	}

	@Test
	public void testEseguiPrendi() {
		primo.setParametro("Chiavi");
		primo.esegui(martedi);
		assertEquals(chiavi, martedi.getGiocatore().getBorsa().getAttrezzo("Chiavi"));
	}

	@Test
	public void testCosaVuoiPrendere() {
		primo.esegui(martedi);
		assertEquals(null, martedi.getGiocatore().getBorsa().getAttrezzo("Chiavi"));
	}

	@Test
	public void testQuellAttrezzoNonPresenteNellaStanza() {
		primo.setParametro("Etere");
		primo.esegui(martedi);
		assertEquals(null, martedi.getGiocatore().getBorsa().getAttrezzo("Chiavi"));
	}

	@Test
	public void testNonPuoiPrendereQuellAttrezzo() {
		Attrezzo uno = new Attrezzo("Uno", 1);
		for (int i = 0; i < 15; i++)
			martedi.getGiocatore().getBorsa().addAttrezzo(uno);
		primo.setParametro("Chiavi");
		primo.esegui(martedi);
		assertEquals(null, martedi.getGiocatore().getBorsa().getAttrezzo("Chiavi"));
	}
}
