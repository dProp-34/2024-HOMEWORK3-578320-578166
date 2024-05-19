package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

import static org.junit.Assert.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ComandoPrendiTest {
	private Attrezzo chiavi;
	private Partita martedi;
	private ComandoPrendi primo;

	@BeforeEach
	public void setUp() {
		chiavi = new Attrezzo("Chiavi", 1);
		martedi = new Partita();
		martedi.getLabirinto().getStanzaCorrente().addAttrezzo(chiavi);
		primo = new ComandoPrendi();
	}

	@Test
	void testEseguiPrendi() {
		primo.setParametro("Chiavi");
		primo.esegui(martedi);
		assertEquals(chiavi, martedi.getGiocatore().getBorsa().getAttrezzo("Chiavi"));
	}

	@Test
	void testCosaVuoiPrendere() {
		primo.esegui(martedi);
		assertEquals(null, martedi.getGiocatore().getBorsa().getAttrezzo("Chiavi"));
	}

	@Test
	void testQuellAttrezzoNonPresenteNellaStanza() {
		primo.setParametro("Etere");
		primo.esegui(martedi);
		assertEquals(null, martedi.getGiocatore().getBorsa().getAttrezzo("Chiavi"));
	}

	@Test
	void testNonPuoiPrendereQuellAttrezzo() {
		Attrezzo uno = new Attrezzo("Uno", 1);
		for (int i = 0; i < 15; i++)
			martedi.getGiocatore().getBorsa().addAttrezzo(uno);
		primo.setParametro("Chiavi");
		primo.esegui(martedi);
		assertEquals(null, martedi.getGiocatore().getBorsa().getAttrezzo("Chiavi"));
	}
}
