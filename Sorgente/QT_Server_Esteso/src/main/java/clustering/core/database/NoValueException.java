package clustering.core.database;

/**
 * Eccezione lanciata quando viene letto un valore nullo dal database.
 * 
 * Viene sollevata quando un attributo o un campo del database,
 * che dovrebbe contenere un valore valido, risulta invece nullo
 * o non assegnato.
 */
public class NoValueException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Costruttore parametrizzato.
     * Inizializza l'eccezione con un messaggio specificato.
     *
     * @param msg messaggio di errore.
     */
    public NoValueException(String msg) {
        super(msg);
    }
}
