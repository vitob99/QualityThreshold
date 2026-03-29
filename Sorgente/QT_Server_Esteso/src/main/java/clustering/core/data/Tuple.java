package clustering.core.data;

import java.io.Serializable;
import java.util.Set;

/**
 * Classe che rappresenta una tupla di {@link Item} all'interno nel dataset.
 * Ogni tupla contiene un array di {@link Item}, ciascuno associato
 * ad un attributo del dataset (discreto o continuo).  
 * Questa classe fornisce metodi per accedere agli item e calcolare
 * le distanze tra le tuple.
 * 
 */
public class Tuple implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Array degli item contenuti nella tupla */
    private Item[] tuple;

    /**
     * Costruttore parametrizzato della tupla.
     * Inizializza l'array di item.
     *
     * @param size numero di item nella tupla.
     */
    Tuple(int size) {
        tuple = new Item[size];
    }

    /**
     * Restituisce il numero di item presenti nella tupla.
     *
     * @return lunghezza della tupla.
     */
    public int getLength() {
        return tuple.length;
    }

    /**
     * Restituisce l'item alla posizione specificata.
     *
     * @param i indice dell'item nella tupla considerada.
     * @return item alla posizione specificata dall'indice "i".
     */
    public Item get(int i) {
        return tuple[i];
    }

    /**
     * Aggiunge un item alla tupla nella posizione specificata.
     *
     * @param c item da inserire.
     * @param i indice in cui inserire l'item.
     */
    void add(Item c, int i) {
        tuple[i] = c;
    }

    /**
     * Calcola la distanza tra la tupla considerata e un'altra tupla.
     * La distanza totale e' la somma delle distanze tra i singoli
     * item corrispondenti.
     *
     * @param obj tupla con cui calcolare la distanza.
     * @return distanza totale tra le due tuple.
     * @throws EmptyDatasetException se uno degli item e' nullo.
     */
    public double getDistance(Tuple obj) throws EmptyDatasetException {
        double distance = 0.0;
        for (int i = 0; i < this.getLength(); i++) {
            distance += this.get(i).distance(obj.get(i).getValue());
        }
        return distance;
    }

    /**
     * Calcola la distanza media tra la tupla considerata e un insieme di tuple (Clusterizzate).
     *
     * @param data dataset contenente le tuple.
     * @param clusteredData insieme di indici delle tuple da considerare.
     * @return distanza media tra la tupla considerata e le tuple indicate.
     * @throws EmptyDatasetException se uno degli item e' nullo.
     */
    public double avgDistance(Data data, Set<Integer> clusteredData) throws EmptyDatasetException {
        double sumD = 0.0;
        for (int clusteredDatum : clusteredData) {
            double d = getDistance(data.getItemSet(clusteredDatum));
            sumD += d;
        }
        return sumD / clusteredData.size();
    }
}
