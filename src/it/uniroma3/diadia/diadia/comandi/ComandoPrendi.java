package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoPrendi implements Comando {
	private String nomeAttrezzo;
	private IO io;

	/**
	 * Permette al giocatore di prendere l'attrezzo
	 * nomeAttrezzo dalla stanza nella quale si trova.
	 */
	@Override
	public String esegui(Partita partita) {
		if (nomeAttrezzo == null)
			return("Cosa vuoi prendere?\n");
		else {
			Attrezzo daPrendere = partita.getLabirinto().getStanzaCorrente().getAttrezzo(nomeAttrezzo);
			if (daPrendere == null)
				return("Quell'attrezzo non e' presente nella stanza.\n");
			else {
				boolean successo = partita.getGiocatore().getBorsa().addAttrezzo(daPrendere);
				successo |= partita.getLabirinto().getStanzaCorrente().removeAttrezzo(daPrendere);
				if (!successo)
					return("Non puoi prendere quell'attrezzo.\n");
				else
					return("Hai preso " + nomeAttrezzo);
			}
		}
	}

	/**
	 * Imposta il nome dell'attrezzo da prendere.
	 */
	@Override
	public void setParametro(String parametro) {
		this.nomeAttrezzo = parametro;
	}
	
	@Override
	public void setIo(IO io) {
		// TODO Auto-generated method stub
		this.io = io;
	}
}
