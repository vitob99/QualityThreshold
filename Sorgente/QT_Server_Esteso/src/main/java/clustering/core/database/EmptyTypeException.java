package clustering.core.database;

/**
 * Eccezione lanciata quando è presente un errore 
 * nella definizione del tipo di un attributo nel database.
 * 
 * Viene sollevata quando il tipo di un campo della tabella
 * non e' gestito impedendo il corretto caricamento dei dati.
 * 
 */
public class EmptyTypeException extends Exception{

	private static final long serialVersionUID = 1L;

    /**
     * Costruttore parametrizzato.
     * Inizializza l'eccezione con un messaggio specificato.
     *
     * @param msg messaggio di errore.
     */
	public EmptyTypeException(String msg){
        super(msg);
    }
}
