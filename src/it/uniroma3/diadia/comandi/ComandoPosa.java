package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoPosa extends AbstractComando {
	private final String NOME = "posa";
	// private String nomeAttrezzo;

	/**
	 * Permette al giocatore di posare un
	 * attrezzo dalla sua borsa.
	 */
	@Override
	public String esegui(Partita partita) {
		if (this.getParametro() == null)
			return ("Cosa vuoi posare?\n");
		else {
			Attrezzo daPosare = partita.getGiocatore().getBorsa().removeAttrezzo(this.getParametro());
			if (daPosare == null)
				return ("Non possiedi quell'attrezzo.\n");
			else if (!partita.getLabirinto().getStanzaCorrente().addAttrezzo(daPosare))
				return ("Non puoi posare quell'attrezzo.\n");
			else
				return ("Hai posato " + this.getParametro());
		}
	}

	@Override
	public String getNome() {
		return this.NOME;
	}
}
