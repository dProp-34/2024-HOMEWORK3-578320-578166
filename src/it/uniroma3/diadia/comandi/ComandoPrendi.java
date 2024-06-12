package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoPrendi extends AbstractComando {
	private final String NOME = "prendi";

	/**
	 * Permette al giocatore di prendere l'attrezzo
	 * nomeAttrezzo dalla stanza nella quale si trova.
	 */
	@Override
	public String esegui(Partita partita) {
		if (this.getParametro() == null)
			return ("\nCosa vuoi prendere?");
		else {
			Attrezzo daPrendere = partita.getLabirinto().getStanzaCorrente().getAttrezzo(this.getParametro());
			if (daPrendere == null)
				return ("\nQuell'attrezzo non e' presente nella stanza.");
			else {
				boolean successo = partita.getGiocatore().getBorsa().addAttrezzo(daPrendere);
				successo |= partita.getLabirinto().getStanzaCorrente().removeAttrezzo(daPrendere);
				if (!successo)
					return ("\nNon puoi prendere quell'attrezzo.");
				else
					return ("\nHai preso " + this.getParametro() + ".");
			}
		}
	}

	@Override
	public String getNome() {
		return this.NOME;
	}
}
