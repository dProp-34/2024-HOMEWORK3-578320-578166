package it.uniroma3.diadia.ambienti;

public class StanzaBuia extends Stanza {
	private String attrezzoPerVedere;

	public StanzaBuia(String nome, String perVedere) {
		super(nome);
		this.attrezzoPerVedere = perVedere;
	}

	@Override
	public String toString() {
		if (this.getAttrezzo(attrezzoPerVedere) == null)
			return ("Qui c'e' buio pesto.\n");
		else
			return super.toString();
	}
}
