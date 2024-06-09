package it.uniroma3.diadia;

import java.util.Scanner;

public class IOConsole implements IO {
	public void mostraMessaggio(String msg) {
		System.out.println(msg);
	}

	public void mostraMessaggioNoLn(String msg) {
		System.out.print(msg);
	}

	public String leggiRiga(Scanner scannerDiLinee) {
		// Scanner scannerDiLinee = new Scanner(System.in);
		String riga = scannerDiLinee.nextLine();
		// scannerDiLinee.close();
		return riga;
	}
}
