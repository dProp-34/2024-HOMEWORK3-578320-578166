package it.uniroma3.diadia;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.StringReader;

import it.uniroma3.diadia.ambienti.FormatoFileNonValidoException;

public class CaricatoreProprieta {
	private static final String LABIRINTO_MARKER = "Labirinto in uso:";
	private static final String CFU_MARKER = "CFU Iniziali:";
	private static final String PESO_MAX_MARKER = "Peso Max Borsa:";
	public static final String FILE_PROPERTIES = "resources/diadia.properties.txt";
	private LineNumberReader reader;

	public CaricatoreProprieta() throws FileNotFoundException {
		InputStream stream = getClass().getClassLoader().getResourceAsStream(FILE_PROPERTIES);
		if (stream == null)
			throw new FileNotFoundException();
		else
			this.reader = new LineNumberReader(new InputStreamReader(stream));
	}

	public CaricatoreProprieta(StringReader fixtureFile) throws FileNotFoundException, FormatoFileNonValidoException {
		this.reader = new LineNumberReader(fixtureFile);
	}

	private String leggiRigaCheCominciaPer(String marker) throws FormatoFileNonValidoException {
		try {
			reader.reset();
			String riga;
			while ((riga = this.reader.readLine()) != null) {
				// System.out.println("Sto leggendo: " + riga);
				if (riga.startsWith(marker))
					return riga.substring(marker.length()).trim();
			}
			// System.out.println("Non è presente una riga che cominci per " + marker);
			return null;
		} catch (IOException e) {
			throw new FormatoFileNonValidoException(e.getMessage());
		}
	}

	private int leggiPesoMax() throws FormatoFileNonValidoException {
		try {
			return Integer.parseInt(this.leggiRigaCheCominciaPer(PESO_MAX_MARKER));
		} catch (NumberFormatException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public int caricaPesoMax() throws FormatoFileNonValidoException {
		try {
			reader.mark(1000000); // Impone un limite ai file Properties da caricare di ~1MB
			return this.leggiPesoMax();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}

	private int leggiCFU() throws FormatoFileNonValidoException {
		try {
			return Integer.parseInt(this.leggiRigaCheCominciaPer(CFU_MARKER));
		} catch (NumberFormatException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public int caricaCFU() throws FormatoFileNonValidoException {
		try {
			reader.mark(1000000); // Impone un limite ai file Proprieta da caricare di ~1MB
			return this.leggiCFU();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}

	private String leggiLabirinto() throws FormatoFileNonValidoException {
		return (this.leggiRigaCheCominciaPer(LABIRINTO_MARKER));
	}

	public String caricaLabirinto() throws FormatoFileNonValidoException {
		try {
			reader.mark(1000000); // Impone un limite ai file Proprieta da caricare di ~1MB
			return this.leggiLabirinto();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
}
