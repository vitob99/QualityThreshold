package clustering.core.database;

/**
 * Eccezione lanciato quando un insieme di dati (set) risulta vuoto.
 * 
 */
public class EmptySetException extends RuntimeException {
	private static final long serialVersionUID = 1L;

    /**
     * Costruttore di default.
     * Inizializza l'eccezione con un messaggio predefinito.
     */
	public EmptySetException(){
        super("[!] Il set risulta essere vuoto!\n");
    }
}
