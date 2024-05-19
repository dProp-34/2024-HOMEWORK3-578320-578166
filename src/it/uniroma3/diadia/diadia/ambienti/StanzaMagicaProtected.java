package it.uniroma3.diadia.ambienti;

import it.uniroma3.diadia.attrezzi.Attrezzo;

public class StanzaMagicaProtected extends StanzaProtected {
	static final protected int SOGLIA_MAGICA_DEFAULT = 3;
	protected int contaAttrezziPosati;
	protected int sogliaMagica;

	public StanzaMagicaProtected(String nome) {
		this(nome, SOGLIA_MAGICA_DEFAULT);
	}

	public StanzaMagicaProtected(String nome, int soglia) {
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
	public boolean addAttrezzo(Attrezzo attrezzo) {
		this.contaAttrezziPosati++;
		if (this.contaAttrezziPosati > this.sogliaMagica)
			attrezzo = this.modificaAttrezzo(attrezzo);
		return super.addAttrezzo(attrezzo);
	}

	private Attrezzo modificaAttrezzo(Attrezzo attrezzo) {
		StringBuilder nomeInvertito;
		int doppioPeso = attrezzo.getPeso() * 2;
		nomeInvertito = new StringBuilder(attrezzo.getNome()).reverse();
		attrezzo = new Attrezzo(nomeInvertito.toString(), doppioPeso);
		return attrezzo;
	}
}
