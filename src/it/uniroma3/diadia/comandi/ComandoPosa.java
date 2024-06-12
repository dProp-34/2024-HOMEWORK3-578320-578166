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
			return ("\nCosa vuoi posare?");
		else {
			Attrezzo daPosare = partita.getGiocatore().getBorsa().removeAttrezzo(this.getParametro());
			if (daPosare == null)
				return ("\nNon possiedi quell'attrezzo.");
			else if (!partita.getLabirinto().getStanzaCorrente().addAttrezzo(daPosare))
				return ("\nNon puoi posare quell'attrezzo.");
			else
				return ("\nHai posato " + this.getParametro() + ".");
		}
	}

	@Override
	public String getNome() {
		return this.NOME;
	}
}
