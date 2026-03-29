/**
 * Eccezione lanciata quando si verifica un errore lato server.
 * 
 * Viene sollevata quando il server risponde con un messaggio di errore
 * invece che con la conferma dell’operazione richiesta.
 */
class ServerException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Costruttore predefinito.
     * Inizializza l'eccezione con un messaggio specificato.
     *
     * @param msg messaggio di errore.
     */
    public ServerException(String msg) {
        super(msg);
    }
}
