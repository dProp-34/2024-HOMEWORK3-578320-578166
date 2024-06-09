package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.personaggi.AbstractPersonaggio;

public class ComandoSaluta extends AbstractComando {
	
	private final String NOME = "saluta";

	@Override
	public String esegui(Partita partita) {
		StringBuilder out = new StringBuilder("Ciao!\n");
		AbstractPersonaggio personaggio = partita.getLabirinto().getStanzaCorrente().getPersonaggio();
		
		if (personaggio != null)
			out.append(partita.getLabirinto().getStanzaCorrente().getPersonaggio().saluta());
		else 
			out.append("(Il suono della tua voce riecheggia nella stanza...)");
		
		return out.toString();
	}

	@Override
	public String getNome() {
		return this.NOME;
	}

}
