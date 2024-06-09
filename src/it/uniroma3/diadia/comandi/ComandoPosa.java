package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoPosa extends AbstractComando {
	private String nomeAttrezzo;
	private final String NOME = "posa";

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
	public String getNome() {
		return this.NOME;
	}
	
}
