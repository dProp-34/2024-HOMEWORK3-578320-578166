package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoRegala extends AbstractComando {

	@Override
	public String esegui(Partita partita) {
		if (this.getParametro() == null)
			return "(Devi scegliere un attrezzo da regalare)";
		Attrezzo attrezzo = partita.getGiocatore().getBorsa().getAttrezzo(this.getParametro());
		if (attrezzo == null) {
			return "(Non hai questo oggetto nella borsa)";
		}
		if (partita.getLabirinto().getStanzaCorrente().getPersonaggio() == null) {
			partita.getGiocatore().getBorsa().removeAttrezzo(attrezzo.getNome());
			partita.getLabirinto().getStanzaCorrente().addAttrezzo(attrezzo);
			return "(Sei da solo nella stanza. Hai lasciato l'oggetto cadere per terra)";
		}

		partita.getGiocatore().getBorsa().removeAttrezzo(this.getParametro());
		return partita.getLabirinto().getStanzaCorrente().getPersonaggio().riceviRegalo(attrezzo, partita);
	}

	@Override
	public String getNome() {
		return null;
	}
}
