package it.uniroma3.diadia;

import java.util.Scanner;

public class IOConsole {
	public static void mostraMessaggio(String msg) {
		System.out.println(msg);
	}

	public static void mostraMessaggioNoLn(String msg) {
		System.out.print(msg);
	}

	public String leggiRiga() {
		Scanner scannerDiLinee = new Scanner(System.in);
		String riga = scannerDiLinee.nextLine();
		scannerDiLinee.close();
		return riga;
	}
}
