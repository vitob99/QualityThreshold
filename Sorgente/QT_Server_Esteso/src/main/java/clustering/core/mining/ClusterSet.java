package clustering.core.mining;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import clustering.core.data.Data;
import clustering.core.data.EmptyDatasetException;

/**
 * Classe che rappresenta lo schema degli esempi clusterizzati.
 * 
 */
public class ClusterSet implements Iterable<Cluster>, Serializable {
    private static final long serialVersionUID = 1L;

    /** Insieme ordinato dei cluster. */
    private Set<Cluster> C = new TreeSet<>();

    /**
     * Aggiunge un cluster all'insieme.
     *
     * @param c il cluster da aggiungere.
     */
    void add(Cluster c) {
        C.add(c);
    }

    /**
     * Restituisce un iteratore sui cluster contenuti nell'insieme.
     *
     * @return un iteratore sui {@link Cluster}
     */
    @Override
    public Iterator<Cluster> iterator() {
        return C.iterator();
    }

    /**
     * Restituisce una rappresentazione come stringa dell'insieme dei cluster,
     * che mostra l'indice del cluster e il suo centroide.
     *
     * @return rappresentazione testuale dei cluster.
     * 
     */
    @Override
    public String toString() {
        int i = 1;
        StringBuilder s = new StringBuilder();
        for (Cluster c : C) {
            s.append(i).append(":").append(c).append("\n");
            i++;
        }
        return s.toString();
    }

    /**
     * Restituisce una rappresentazione come stringa dell'insieme dei cluster, 
     * comprensiva degli esempi appartenenti a ciascun cluster.
     *
     * @param data il dataset di riferimento.
     * @return rappresentazione testuale di centroidi ed esempi.
     * @throws EmptyDatasetException se il dataset risulta vuoto.
     */
    public String toString(Data data) throws EmptyDatasetException {
        StringBuilder str = new StringBuilder();
        int i = 0;
        for (Cluster c : C) {
            str.append(i).append(":").append(c.toString(data)).append("\n");
            i++;
        }
        return str.toString();
    }
}
