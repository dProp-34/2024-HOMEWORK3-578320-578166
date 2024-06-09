package it.uniroma3.diadia.personaggi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class Cane extends AbstractPersonaggio{
	
	private final String SALUTO = "Bau bau! grr... (Ciao! Che fame...)\n";
	private String ciboPreferito = "osso";
	private boolean contento = false;

	public Cane(String nome, String presentazione) {
		super(nome, presentazione);
	}
	
	private void setCcontento(boolean b) {
		this.contento = b;
	}
	
	@Override
	public String saluta() {		
		return SALUTO;
	}
	
	@Override
	public String agisci(Partita partita) {
		String out;
		
		if (contento) {
			partita.getLabirinto().getStanzaCorrente().addAttrezzo(new Attrezzo("spada", 10));
			out = "Bau, bau bau bau. (Tieni, questo è per te.)\n";
		}
			
		else {
			partita.getGiocatore().setCfu(partita.getGiocatore().getCfu() - 1);
			out = "GRRR BAU BAU! Bau bau. (Così impari.)\n" + "Hai perso 1 CFU\n";
		}
		return out;
	}

	@Override
	public String riceviRegalo(Attrezzo attrezzo, Partita partita) {
		StringBuilder out = new StringBuilder("Bau bau bau");
		if (attrezzo == null) 
			out.append("... BAU! (Ma cosa è questo uno scherzo?!)\n");
		else if (attrezzo.getNome() != ciboPreferito) 
			out.append("bau... (Cosa diavolo è questo?)\n");
		else {
			out.append("bau! (Grazie! È il mio cibo preferito.)\n");
			setCcontento(true);
		}
		out.append(agisci(partita));
		return out.toString();
	}



}
