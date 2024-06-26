package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ios.IO;

/**
 * Questa classe modella un comando.
 * Un comando consiste al piu' di due parole:
 * il nome del comando ed un parametro
 * su cui si applica il comando.
 * (Ad es. alla riga digitata dall'utente "vai nord"
 * corrisponde un comando di nome "vai" e parametro "nord").
 *
 * @author docente di POO
 * @version base
 */
public interface Comando {
    /*
     * Esegue il comando e ritorna la
     * stringa che DiaDia ha il compito di stampare
     */
    public String esegui(Partita partita);

    /*
     * Imposta il parametro del comando
     */
    public void setParametro(String parametro);

    public String getParametro();

    public void setIo(IO io);

    // public String getNome();
}
