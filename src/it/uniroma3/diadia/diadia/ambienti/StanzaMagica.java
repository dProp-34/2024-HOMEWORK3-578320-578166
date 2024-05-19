package it.uniroma3.diadia.ambienti;

import it.uniroma3.diadia.attrezzi.Attrezzo;

public class StanzaMagica extends Stanza {
	static final private int SOGLIA_MAGICA_DEFAULT = 3;
	private int contaAttrezziPosati;
	private int sogliaMagica;

	public StanzaMagica(String nome) {
		this(nome, SOGLIA_MAGICA_DEFAULT);
	}

	public StanzaMagica(String nome, int soglia) {
		super(nome);
		this.contaAttrezziPosati = 0;
		this.sogliaMagica = soglia;
	}

	public int getContaAttrezziPosati() {
		return contaAttrezziPosati;
	}

	public void setContaAttrezziPosati(int contaAttrezziPosati) {
		this.contaAttrezziPosati = contaAttrezziPosati;
	}

	public int getSogliaMagica() {
		return sogliaMagica;
	}

	public void setSogliaMagica(int sogliaMagica) {
		this.sogliaMagica = sogliaMagica;
	}

	@Override
	public boolean addAttrezzo(Attrezzo myAttrezzo) {
		if (myAttrezzo == null)
			return false;
		else {
			this.contaAttrezziPosati++;
			if (this.contaAttrezziPosati > this.sogliaMagica)
				myAttrezzo = this.modificaAttrezzo(myAttrezzo);
			return super.addAttrezzo(myAttrezzo);
		}
	}

	private Attrezzo modificaAttrezzo(Attrezzo attrezzo) {
		if (attrezzo != null) {
			StringBuilder nomeInvertito;
			int doppioPeso = attrezzo.getPeso() * 2;
			nomeInvertito = new StringBuilder(attrezzo.getNome()).reverse();
			attrezzo = new Attrezzo(nomeInvertito.toString(), doppioPeso);
		}
		return attrezzo;
	}
}
