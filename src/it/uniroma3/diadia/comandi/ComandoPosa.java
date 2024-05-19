package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoPosa implements Comando {
	private String nomeAttrezzo;
	private IO io;

	/**
	 * Permette al giocatore di posare un
	 * attrezzo dalla sua borsa.
	 */
	@Override
	public String esegui(Partita partita) {
		if (nomeAttrezzo == null)
			return("Cosa vuoi posare?\n");
		else {
			Attrezzo daPosare = partita.getGiocatore().getBorsa().removeAttrezzo(nomeAttrezzo);
			if (daPosare == null)
				return("Non possiedi quell'attrezzo.\n");
			else if (!partita.getLabirinto().getStanzaCorrente().addAttrezzo(daPosare))
				return("Non puoi posare quell'attrezzo.\n");
			else 
				return("Hai posato " + nomeAttrezzo);
		}
	}

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
