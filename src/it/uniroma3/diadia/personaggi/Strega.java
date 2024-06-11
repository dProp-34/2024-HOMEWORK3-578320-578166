package it.uniroma3.diadia.personaggi;

import java.util.List;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class Strega extends AbstractPersonaggio {
	public Strega(String nome, String presentazione) {
		super(nome, presentazione);
	}

	@Override
	public String agisci(Partita partita) {
		List<Stanza> stanzeAdiacenti = (List<Stanza>) partita.getLabirinto().getStanzaCorrente().getStanzeAdiacenti()
				.values();
		String out;

		Stanza piuAttrezzi = stanzeAdiacenti.get(0);
		Stanza menoAttrezzi = stanzeAdiacenti.get(0);

		for (Stanza s : stanzeAdiacenti) {
			if (s.getAttrezzi().size() > piuAttrezzi.getAttrezzi().size())
				piuAttrezzi = s;
			if (s.getAttrezzi().size() < menoAttrezzi.getAttrezzi().size())
				menoAttrezzi = s;
		}

		if (this.haSalutato()) {
			partita.getLabirinto().setStanzaCorrente(piuAttrezzi);
			out = "Ti sei comportato bene... Per ora.\nSei stato teletrasportato nella stanza con più attrezzi.\n";
		} else {
			partita.getLabirinto().setStanzaCorrente(menoAttrezzi);
			out = "CON CHI CREDI DI AVERE A CHE FARE?!\nSei stato teletraspostato nella stanza con meno attrezzi.\n";
		}

		return out;
	}

	@Override
	public String riceviRegalo(Attrezzo attrezzo, Partita partita) {
		return "...";
	}
}
