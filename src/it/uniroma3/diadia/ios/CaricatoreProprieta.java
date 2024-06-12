package it.uniroma3.diadia.ios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.net.URL;

public class CaricatoreProprieta {
	private static final String LABIRINTO_MARKER = "Labirinto in uso:";
	private static final String CFU_MARKER = "CFU Iniziali:";
	private static final String PESO_MAX_MARKER = "Peso Max Borsa:";
	public static final String FILE_PROPERTIES = "diadia.properties.txt";

	private LineNumberReader reader;

	public CaricatoreProprieta() throws IOException {
		ClassLoader loader = this.getClass().getClassLoader();
		URL resource = loader.getResource(FILE_PROPERTIES);
		if (resource == null)
			throw new FileNotFoundException("Resource not found: " + FILE_PROPERTIES);
		else {
			try {
				this.reader = new LineNumberReader(new InputStreamReader(resource.openStream()));
			} catch (IOException e) {
				e.printStackTrace(); // Print the stack trace for debugging
				throw new IOException("Failed to open stream for resource: " + FILE_PROPERTIES);
			}
		}
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
			reader.mark(1000000); // Impone un limite ai file Proprieta da caricare di ~1MB
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
			String labirintoFile = this.leggiLabirinto();
			System.out.println("Labirinto file to load: " + labirintoFile); // Debug statement
			if (labirintoFile == null || labirintoFile.isEmpty()) {
				throw new FormatoFileNonValidoException("Labirinto file name is missing or empty in properties file");
			}
			return labirintoFile;
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
