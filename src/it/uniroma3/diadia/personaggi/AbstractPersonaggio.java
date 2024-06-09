package it.uniroma3.diadia.personaggi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public abstract class AbstractPersonaggio {
	
	private String nome;
	private String presentazione;
	private boolean haSalutato = false;
	
	public AbstractPersonaggio(String nome, String presentazione) {
		this.nome = nome;
		this.presentazione = presentazione;
	}

	public String getNome() {
		return this.nome;
	}

	@Override
	public String toString() {
		return this.nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
		
	public String getPresentazione() {
		return this.presentazione;
	}
	
	public void setPresentazione(String presentazione) {
		this.presentazione = presentazione;
	}
	
	public boolean haSalutato() {
		return this.haSalutato;
	}
	
	public void setHaSalutato(boolean haSalutato) {
		this.haSalutato = haSalutato;
	}
	
	public String saluta() {
		StringBuilder out = new StringBuilder("Ciao, io sono");
		out.append(this.getNome() + ".\n");
		if (!this.haSalutato)
			out.append(this.presentazione + "\n");
		else
			out.append("Mi pare che ci siamo già presentati.\n");
		setHaSalutato(true);
		
		return out.toString();
	}
	
	abstract public String agisci(Partita partita);
	
	abstract public String riceviRegalo(Attrezzo attrezzo, Partita partita);
	
}