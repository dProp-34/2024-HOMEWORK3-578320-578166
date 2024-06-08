package it.uniroma3.diadia;

import java.util.Scanner;

public interface IO {
	public void mostraMessaggio(String messaggio);
	public void mostraMessaggioNoLn(String messaggio);
	public String leggiRiga(Scanner scannerDiLinee);
}