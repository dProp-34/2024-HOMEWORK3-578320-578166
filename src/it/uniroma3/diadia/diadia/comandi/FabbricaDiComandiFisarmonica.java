package it.uniroma3.diadia.comandi;

import java.util.Scanner;

@SuppressWarnings("resource")
public class FabbricaDiComandiFisarmonica implements FabbricaDiComandi {
	private String nomeComando;
	private String parametro;

	public Comando costruisciComando(String istruzione) {
		Comando comando = null;
		if (istruzione != null) {
			Scanner scannerDiParole = new Scanner(istruzione);

			if (scannerDiParole.hasNext())
				this.nomeComando = scannerDiParole.next(); // prima parola: nome del comando
			if (scannerDiParole.hasNext())
				this.parametro = scannerDiParole.next(); // seconda parola: eventuale parametro
			if (this.nomeComando == null)
				comando = new ComandoNonValido();
			else
				switch (this.nomeComando) {
					case "vai":
						comando = new ComandoVai();
						break;
					case "prendi":
						comando = new ComandoPrendi();
						break;
					case "posa":
						comando = new ComandoPosa();
						break;
					case "aiuto":
						comando = new ComandoAiuto();
						break;
					case "fine":
						comando = new ComandoFine();
						break;
					case "guarda":
						comando = new ComandoGuarda();
						break;
					default:
						comando = new ComandoNonValido();
						break;
				}
			comando.setParametro(this.parametro);
		}
		return comando;
	}

	public String getNomeComando() {
		return nomeComando;
	}

	public void setNomeComando(String nomeComando) {
		this.nomeComando = nomeComando;
	}

	public String getParametro() {
		return parametro;
	}

	public void setParametro(String parametro) {
		this.parametro = parametro;
	}
}
