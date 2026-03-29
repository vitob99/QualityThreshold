package clustering.core.mining;

/**
 * Eccezione lanciata quando, durante il processo di clustering,
 * tutte le tuple vengono assegnate ad un unico cluster.
 * 
 * Tipicamente accade se il raggio di clustering scelto
 * e' troppo grande e non consente la formazione di piu' cluster distinti.
 *
 */
public class ClusteringRadiusException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Costruttore di default.
     * Inizializza l'eccezione con un messaggio predefinito.
     */
    public ClusteringRadiusException() {
        super("[!] Le tuple sono tutte raggruppate in un unico cluster!");
    }
}