package it.uniroma3.diadia.personaggi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class Mago extends AbstractPersonaggio {

	private Attrezzo attrezzo;

	public Mago(String nome, String presentazione, Attrezzo attrezzo) {
		super(nome, presentazione);
		this.attrezzo = attrezzo;
	}

	@Override
	public String agisci(Partita partita) {
		// TODO Auto-generated method stub
		StringBuilder out = new StringBuilder("Farò una piccola magia alla tua borsa.\n");
		if (this.attrezzo != null) {
			if (!partita.getGiocatore().getBorsa().addAttrezzo(this.attrezzo)) {
				out.append("Peccato sembra che tu non abbia spazio nella borsa. Lascerò questo oggetto a terra.\n");
				partita.getLabirinto().getStanzaCorrente().addAttrezzo(attrezzo);
			}
			this.attrezzo = null;
		}
		else
			out.append("Mi spiace, non ho più nulla...\n");
		return out.toString();
	}

	@Override
	public String riceviRegalo(Attrezzo attrezzo, Partita partita) {
		// TODO Auto-generated method stub
		StringBuilder out = new StringBuilder("Aahhh ma guarda un po'. Grazie per ");
		out.append(attrezzo.getNome() + ". Farò una piccola modifica. e te lo restituirò subito.");

		Attrezzo attrezzoModificato = new Attrezzo(attrezzo.getNome(), attrezzo.getPeso()/2);
		partita.getLabirinto().getStanzaCorrente().addAttrezzo(attrezzoModificato);

		return out.toString();
	}

}
