package it.uniroma3.diadia.simulatore;

import java.util.List;	

import it.uniroma3.diadia.DiaDia;
import it.uniroma3.diadia.IOSimulator;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.LabirintoBuilder;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;


public class Simulatore {
	public static IOSimulator creaSimulazionePartitaFacile(List<String> comandiDaLeggere) {
		IOSimulator io = new IOSimulator(comandiDaLeggere);
		Labirinto labirinto = Labirinto.newLabirintoBuilder()
				.addStanzaIniziale("Atrio")
				.addAttrezzo("osso", 1)
				.addStanzaVincente("Biblioteca")
				.addAdiacenza("Atrio", "Biblioteca", "nord")
				.addAdiacenza("Biblioteca", "Atrio", "sud")
				.getLabirinto();
		DiaDia gioco = new DiaDia(labirinto, io);
		gioco.gioca();
		return io;
	}

	public static IOSimulator creaSimulazionePartitaDifficile(List<String> comandiDaLeggere) {
		IOSimulator io = new IOSimulator(comandiDaLeggere);
		Labirinto labirinto = Labirinto.newLabirintoBuilder()
				.addStanzaIniziale("Atrio")
				.addAttrezzo("osso", 1)
				.addStanzaVincente("Biblioteca")
				.addAdiacenza("Atrio", "Biblioteca", "nord")
				.addAdiacenza("Biblioteca", "Atrio", "sud")
				.addStanza("Bagno")
				.addAttrezzo("carta igenica", 1)
				.addAdiacenza("Bagno", "Atrio", "nord")
				.addAdiacenza("Atrio", "Bagno", "sud")
				.addStanza("Studio")
				.addAdiacenza("Studio", "Atrio", "est")
				.addAdiacenza("Atrio", "Studio", "ovest")
				.getLabirinto();
		DiaDia gioco = new DiaDia(labirinto, io);
		gioco.gioca();
		return io;
	}

	public static IOSimulator creaSimulazionePartitaMonolocale(List<String> comandiDaLeggere) {
		IOSimulator io = new IOSimulator(comandiDaLeggere);
		Labirinto monolocale = Labirinto.newLabirintoBuilder()
				.addStanzaIniziale("salotto") 
				.addStanzaVincente("salotto") 
				.getLabirinto();
		DiaDia gioco = new DiaDia(monolocale, io);
		gioco.gioca();
		return io;
	}
	
	
	public static IOSimulator creaSimulazionePartitaBilocale(List<String> comandiDaLeggere) {
		IOSimulator io = new IOSimulator(comandiDaLeggere);
		Labirinto bilocale = Labirinto.newLabirintoBuilder()
				.addStanzaIniziale("salotto")
				.addStanzaVincente("camera")
				.addAttrezzo("letto",10) // dove? fa riferimento all’ultima stanza aggiunta
				.addAdiacenza("salotto", "camera", "ovest") // camera si trova a nord di salotto
				.getLabirinto();
		DiaDia gioco = new DiaDia(bilocale, io);
		gioco.gioca();
		return io;
	}
	
	public static IOSimulator creaSimulazionePartitaTrilocale(List<String> comandiDaLeggere) {
		IOSimulator io = new IOSimulator(comandiDaLeggere);
		Labirinto trilocale = Labirinto.newLabirintoBuilder()
				.addStanzaIniziale("salotto")
				.addStanza("cucina")
				.addAttrezzo("pentola",1) // dove? fa riferimento all’ultima stanza aggiunta
				.addStanzaVincente("camera")
				.addAdiacenza("salotto", "cucina", "nord")
				.addAdiacenza("cucina", "camera", "est")
				.getLabirinto();
		DiaDia gioco = new DiaDia(trilocale, io);
		gioco.gioca();
		return io;
	}

	public static Attrezzo creaAttrezzoEAggiugniAStanza(Stanza stanzaDaRiempire, String nomeAttrezzo, int peso) {
		Attrezzo attrezzo = new Attrezzo(nomeAttrezzo, peso);
		stanzaDaRiempire.addAttrezzo(attrezzo);
		return attrezzo;
	}

}
