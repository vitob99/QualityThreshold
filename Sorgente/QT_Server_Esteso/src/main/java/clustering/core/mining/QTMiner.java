package clustering.core.mining;

import java.io.*;

import clustering.core.data.Data;
import clustering.core.data.EmptyDatasetException;
import clustering.core.data.Tuple;
/**
 * Classe che implementa l'algoritmo di clustering QT (Quality Threshold).
 * 
 * L'algoritmo raggruppa le tuple di un dataset in cluster sulla base di un raggio massimo
 * in modo che ogni cluster contenga solo elementi sufficientemente simili.
 *
 */
public class QTMiner {
    /** Estensione con cui verranno salvati i file del clustering. */
    public static final String FILE_FORMAT = ".dmp";

    /** Insieme dei cluster trovati dall’algoritmo. */
    private ClusterSet C;

    /** Raggio massimo di distanza per l’inclusione di una tupla in un cluster. */
    private double radius;

    /**
     * Costruttore parametrizzato delal classe QTMiner.
     * Inizializza un nuovo miner con raggio dato.
     * @param radius distanza (raggio) massima entro la quale includere le tuple in un cluster.
     */
    public QTMiner(double radius) {
        C = new ClusterSet();
        this.radius = radius;
    }

    /**
     * Costruttore parametrizzato della classe QTMiner.
     * Carica da file un clastering precedentemente dalvato.
     * 
     * @param fileName nome del file da cui caricare il clustering.
     * @throws IOException se il file non e' accessibile o non esiste.
     * @throws ClassNotFoundException se la deserializzazione fallisce
     * 
     */
    public QTMiner(String fileName) throws IOException, ClassNotFoundException{
        FileInputStream inFile = new FileInputStream(fileName + FILE_FORMAT);
        ObjectInputStream inStream = new ObjectInputStream(inFile);
        C = (ClusterSet) inStream.readObject();
        inStream.close();
    }

    /**
     * Salva su file il clustering attuale serializzando l’insieme dei cluster.
     *
     * @param fileName nome del file (senza estensione) in cui salvare il clustering.
     * @throws IOException se si verifica un errore di scrittura.
     * 
     */
    public void salva(String fileName) throws FileNotFoundException, IOException{
        FileOutputStream outFile = new FileOutputStream(fileName + FILE_FORMAT);
        ObjectOutputStream outStream = new ObjectOutputStream(outFile);
        outStream.writeObject(C);
        outStream.close();
    }

    /**
     * Restituisce l’insieme dei cluster trovati.
     *
     * @return restituisce un {@link ClusterSet} che rappresenta il clustering.
     */
    public ClusterSet getC() {
        return C;
    }

    /**
     * Esegue l’algoritmo di clustering QT sul dataset dato.
     *
     * @param data dataset su cui verrà eseguito l'algoritmo.
     * @return restituisce il numero di cluster trovati.
     * @throws ClusteringRadiusException se tutte le tuple vengono raggruppate in un unico cluster.
     * @throws EmptyDatasetException se il dataset è vuoto.
     */
    public int compute(Data data) throws ClusteringRadiusException, EmptyDatasetException {
        int numclusters = 0;
        boolean[]  isClustered = new boolean[data.getNumberOfExamples()];
        for (int i = 0; i < isClustered.length; i++)
            isClustered[i] = false;

        if (data.getNumberOfExamples() == 0) {
            throw new EmptyDatasetException();
        }

        int countClustered = 0;
        while (countClustered != data.getNumberOfExamples()) {
           
            Cluster c = buildCandidateCluster(data, isClustered);
            C.add(c);
            numclusters++;

            
            for (Integer id : c) {   
                isClustered[id] = true;
            }
            countClustered += c.getSize();

            if ((numclusters == 1) && (c.getSize() == data.getNumberOfExamples())) {
                throw new ClusteringRadiusException();
            }
        }
        return numclusters;
    }

    /**
     * Costruisce un cluster candidato a partire da un centroide includendo tutte le tuple entro il "radius"
     *
     * @param data dataset contenente le tuple.
     * @param isClustered array booleano che indica quali tuple sono gia' state assegnate.
     * @return il cluster candidato più popolato che e' stato trovato
     * @throws EmptyDatasetException se il dataset e' vuoto.
     */
    private Cluster buildCandidateCluster(Data data, boolean[] isClustered) throws EmptyDatasetException {
        Cluster bestCluster = null;
        int maxSize = -1;
        for (int i = 0; i < data.getNumberOfExamples(); i++) {
            if (!isClustered[i]) { 
                Tuple centroid = data.getItemSet(i);
                Cluster candidate = new Cluster(centroid);

                for (int j = 0; j < data.getNumberOfExamples(); j++) {
                    if (!isClustered[j]) { 
                        Tuple current = data.getItemSet(j);
                        double dist = centroid.getDistance(current);

                        if (dist <= radius) {
                            candidate.addData(j);

                        }
                    }
                }
                if (candidate.getSize() > maxSize) {
                    bestCluster = candidate;
                    maxSize = candidate.getSize();
                }
            }
        }


        return bestCluster;
    }
}

