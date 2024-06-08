package it.uniroma3.diadia.comandi;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FabbricaDiComandiFisarmonicaTest {
	private FabbricaDiComandiFisarmonica roma;

	@BeforeEach
	public void setUp() {
		roma = new FabbricaDiComandiFisarmonica();
	}

	@Test
	public void testCostruisciComandoSenzaNome() {
		assertNull(roma.costruisciComando(null));
	}

	@Test
	public void testCostruisciComandoNonDefinito() {
		assertThat(roma.costruisciComando("vinci"), instanceOf(ComandoNonValido.class));
	}

	@Test
	public void testCostruisciComandoVai() {
		assertThat(roma.costruisciComando("vai"), instanceOf(ComandoVai.class));
	}
}
