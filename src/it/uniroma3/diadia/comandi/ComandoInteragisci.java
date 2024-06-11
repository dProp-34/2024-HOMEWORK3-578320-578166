package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.personaggi.AbstractPersonaggio;

public class ComandoInteragisci extends AbstractComando {
	private final String NOME = "interagisci";

	@Override
	public String esegui(Partita partita) {
		AbstractPersonaggio personaggio = partita.getLabirinto().getStanzaCorrente().getPersonaggio();
		if (personaggio != null)
			return personaggio.agisci(partita);
		return "(E con chi vorresti interagire?)";
	}

	@Override
	public String getNome() {
		return this.NOME;
	}
}
