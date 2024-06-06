package it.uniroma3.diadia.ambienti;

import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.comandi.Direzione;

public class StanzaBloccata extends Stanza {
	private Direzione direzioneBloccata;
	private String attrezzoPerSbloccare;

	public StanzaBloccata(String nome, Direzione bloccata, String sbloccare) {
		super(nome);
		this.direzioneBloccata = bloccata;
		this.attrezzoPerSbloccare = sbloccare;
	}

	@Override
	public Stanza getStanzaAdiacente(Direzione direzione) {
		if (this.getAttrezzo(attrezzoPerSbloccare) == null)
			return this;
		else
			return super.getStanzaAdiacente(direzione);
	}

	@Override
	public String toString() {
		StringBuilder risultato = new StringBuilder();
		risultato.append("- " + this.getNome() + " -");
		risultato.append("\nUscite:");
		for (Direzione direzione : this.getDirezioni()) {
			if (direzione != null) {
				risultato.append(" " + direzione);
				if (direzione == this.direzioneBloccata)
					risultato.append("(serve: " + this.attrezzoPerSbloccare + ")");
			}
		}
		risultato.append("\nAttrezzi:");
		for (Attrezzo attrezzo : this.getAttrezzi()) {
			if (attrezzo != null)
				risultato.append(" " + attrezzo.toString());
		}
		return risultato.toString();
	}
}
