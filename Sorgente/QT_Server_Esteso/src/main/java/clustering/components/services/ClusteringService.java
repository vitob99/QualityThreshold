package clustering.components.services;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import clustering.core.data.Data;
import clustering.core.data.EmptyDatasetException;
import clustering.core.database.DatabaseConnectionException;
import clustering.core.database.EmptySetException;
import clustering.core.database.EmptyTypeException;
import clustering.core.database.NoValueException;
import clustering.core.mining.ClusteringRadiusException;
import clustering.core.mining.QTMiner;

@Service
public class ClusteringService {
    /**
     * Avvia il clustering leggendo i dati da file.
     * 
     * @param fileName nome del file da elaborare.
     * @return risultato del clustering in formato testo.
     */
	public String learningfromfile(String fileName) {
        try {
        	QTMiner file_miner = new QTMiner(fileName);
        	return file_miner.getC().toString();
        }
        catch (IOException ex) {
        	return null;
        } 
        catch (ClassNotFoundException e) {
        	return null;
		}
	}
	
    /**
     * Avvia il clustering leggendo i dati dal database.
     * 
     * @param tableName nome del file da elaborare.
     * @param qt_radius raggio con cui eseguire il clustering.
     * @param fileName nome del file in cui salvare il clustering.
     * @return risultato del clustering da database in formato testo.
     */
	public String learningfromdb(String tableName, int qt_radius, String fileName) {
		String clustering;
		try {
			Data data = new Data(tableName);
			QTMiner lastMiner = new QTMiner(qt_radius);
	    	int numIter = lastMiner.compute(data); 
	    	lastMiner.salva(fileName);
	    	clustering = "Number of clusters: " + numIter + "\n" + lastMiner.getC().toString(data);
	    	return clustering;
	    	
		} catch (EmptySetException | SQLException | DatabaseConnectionException 
				| NoValueException| ClusteringRadiusException| EmptyDatasetException |
				EmptyTypeException | IOException  e) {
			return null;
		} 
	}
	
    /**
     * Restituisce la lista dei file da cui e' possibile 
     * leggere clustering precedentemente salvati.
     * 
     * @return lista dei file disponibili.
     */
	public List<String> getfilelist() {
		List<String> dump_files = new ArrayList<String>();
		File currentDir = new File(System.getProperty("user.dir"));
        File[] files = currentDir.listFiles();
        
        if (files != null) {
            for (File file : files) {
            	String file_name = file.getName();
                if (file.isFile() && file_name.endsWith(".dmp")) {
                    dump_files.add(file_name);
                }
            }
        }
        return dump_files;
	}
	
}
