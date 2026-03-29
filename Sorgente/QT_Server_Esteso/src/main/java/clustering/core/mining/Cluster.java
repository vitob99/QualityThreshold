package clustering.core.mining;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import clustering.core.data.Data;
import clustering.core.data.EmptyDatasetException;
import clustering.core.data.Tuple;
/**
 * Classe che rappresenta un insieme di tuple (identificate da id interi)
 * raggruppate intorno a un centroide. 
 * Ogni cluster ha: Un attributo di tipo {@link Tuple} che rappresenta il centroide e 
 * un insieme di indici interi che identificano le tuple appartenenti al cluster.
 * 
 */
class Cluster implements Iterable<Integer>, Comparable<Cluster>, Serializable {
	private static final long serialVersionUID = 1L;
	
	/** Centroide del cluster, rappresentato come tupla. */
    private Tuple centroid;
    /** Insieme degli ID delle tuple del dataset appartenenti al cluster. */
    private Set<Integer> clusteredData;

    /**
     * Costruttore parametrizzato della classe Cluser.
     * Costruisce un cluster con un centroide specificato e un insieme vuoto di tuple.
     * @param centroid
     * 
     */
    public Cluster(Tuple centroid) {
        this.centroid = centroid;
        clusteredData = new HashSet<>();
    }

    /**
     * Restituisce il centroide del cluster.
     *
     * @return il centroide
     */
    Tuple getCentroid() {
        return centroid;
    }

    /**
     * Aggiunge l'id di una tupla al cluster.
     *
     * @param id identificatore della tupla
     * @return "true" se la tupla e' stata aggiunta, "false" se era gia' presente.
     *         
     */
    boolean addData(int id) {
        return clusteredData.add(id);
    }

    /**
     * Verifica se una tupla (su base dell'id) appartiene al cluster.
     *
     * @param id identificatore della tupla.
     * @return "true" se l'id è contenuto nel cluster, "false" altrimenti
     * 
     */
    boolean contain(int id) {
        return clusteredData.contains(id);
    }

    /**
     * Rimuove (su base dell'id) una tupla dal cluster.
     *
     * @param id identificatore della tupla da rimuovere.
     * 
     */
    void removeTuple(int id) {
        clusteredData.remove(id);
    }

    /**
     * Restituisce il numero totale di tuple appartenenti al cluster.
     *
     * @return la cardinalita' del cluster.
     * 
     */
    int getSize() {
        return clusteredData.size();
    }

    /**
     * Restituisce un iteratore sugli id delle tuple contenute nel cluster.
     *
     * @return iteratore sugli id.
     * 
     */
    @Override
    public Iterator<Integer> iterator() {
        return clusteredData.iterator();
    }

    
    /**
     * Confronta due cluster in base al numero di tuple contenute in essi.
     * In caso di parità, confronta le rappresentazioni testuali dei centroidi.
     *
     * @param other cluster con cui confrontare.
     * @return un valore negativo se questo cluster ha meno tuple, un valore positivo se ne ha di piu', 0 in caso di uguaglianza.
     *         
     */
    @Override
    public int compareTo(Cluster other) {
    	  int sizeCompare = Integer.compare(this.getSize(), other.getSize());
          if (sizeCompare != 0) return sizeCompare;

          
          return this.centroid.toString().compareTo(other.centroid.toString());
    }

    
    /**
     * Restituisce una rappresentazione come stringa del cluster, mostrando solo il centroide.
     *
     * @return rappresentazione testuale del centroide.
     * 
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Centroid=(");
        for (int i = 0; i < centroid.getLength(); i++)
            str.append(centroid.get(i));
        str.append(")");
        return str.toString();
    }

    /**
     * Restituisce una rappresentazione estesa del cluster, 
     * mostrando il centroide, le tuple appartenenti al cluster
     * e le distanze di ciascuna dal centroide.
     *
     * @param data l'oggetto {@link Data} che contiene le tuple.
     * @return rappresentazione testuale del cluster.
     * @throws EmptyDatasetException se il dataset e' vuoto.
     */
    public String toString(Data data) throws EmptyDatasetException {
        StringBuilder str = new StringBuilder("Centroid=(");
        for (int i = 0; i < centroid.getLength(); i++)
            str.append(centroid.get(i)).append(" ");
        str.append(")\nExamples:\n");

        for (Integer id : clusteredData) {
            str.append("[");
            for (int j = 0; j < data.getNumberOfAttributes(); j++)
                str.append(data.getAttributeValue(id, j)).append(" ");
            str.append("] dist=").append(getCentroid().getDistance(data.getItemSet(id))).append("\n");
        }

        str.append("\nAvgDistance=").append(getCentroid().avgDistance(data, clusteredData));

        return str.toString();
    }
}
