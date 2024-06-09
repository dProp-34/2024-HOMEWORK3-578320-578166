package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoRegala extends AbstractComando {

	@Override
	public String esegui(Partita partita) {
		// TODO Auto-generated method stub
		Attrezzo attrezzo = partita.getGiocatore().getBorsa().getAttrezzo(this.getParametro());
		partita.getGiocatore().getBorsa().removeAttrezzo(this.getParametro());
		return partita.getLabirinto().getStanzaCorrente().getPersonaggio().riceviRegalo(attrezzo, partita);
	}

	@Override
	public String getNome() {
		// TODO Auto-generated method stub
		return null;
	}

}
