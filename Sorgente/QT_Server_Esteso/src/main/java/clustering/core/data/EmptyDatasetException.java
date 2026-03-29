package clustering.core.data;

/**
 * Eccezione lanciata quando il dataset risulta vuoto.
 * Questa eccezione viene usata per segnalare che non ci sono record
 * disponibili nel dataset su cui operare.
 */
public class EmptyDatasetException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Costruttore di default.
     * Inizializza l'eccezione con un messaggio predefinito.
     */
    public EmptyDatasetException() {
        super("[!] Il dataset risulta essere vuoto!\n");
    }
}