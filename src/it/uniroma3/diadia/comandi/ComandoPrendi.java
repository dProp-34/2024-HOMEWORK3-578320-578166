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
			return ("Cosa vuoi prendere?\n");
		else {
			Attrezzo daPrendere = partita.getLabirinto().getStanzaCorrente().getAttrezzo(this.getParametro());
			if (daPrendere == null)
				return ("Quell'attrezzo non e' presente nella stanza.\n");
			else {
				boolean successo = partita.getGiocatore().getBorsa().addAttrezzo(daPrendere);
				successo |= partita.getLabirinto().getStanzaCorrente().removeAttrezzo(daPrendere);
				if (!successo)
					return ("Non puoi prendere quell'attrezzo.\n");
				else
					return ("Hai preso " + this.getParametro());
			}
		}
	}

	@Override
	public String getNome() {
		return this.NOME;
	}
}
