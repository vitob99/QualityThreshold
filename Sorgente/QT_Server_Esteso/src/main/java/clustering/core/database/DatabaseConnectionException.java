package clustering.core.database;

/**
 * Eccezione lanciata quando si verifica un errore di connessione al database.
 * Viene sollevata quando non è possibile stabilire una connessione,
 * oppure se la connessione viene interrotta inaspettatamente.
 * 
 */
public class DatabaseConnectionException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Costruttore di default.
     * Inizializza l'eccezione con un messaggio predefinito.
     */
    public DatabaseConnectionException() {
        super("[!] Errore di connessione al database!\n");
    }
}
