package clustering.core.server;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

import clustering.core.data.Data;
import clustering.core.data.EmptyDatasetException;
import clustering.core.database.DatabaseConnectionException;
import clustering.core.database.EmptySetException;
import clustering.core.database.EmptyTypeException;
import clustering.core.database.NoValueException;
import clustering.core.mining.ClusteringRadiusException;
import clustering.core.mining.QTMiner;
/**
 * Classe Thread che si occupa della gestione del singolo client,
 * in modo da avere una gestione multi-client separata.
 * Istanziata dalla classe {@link MultiServer}
 */
class ServerOneClient extends Thread {
    /** Socket associato al client */
    private Socket socket;
    /** Flusso di input dal client */
    private ObjectInputStream in;
    /** Flusso di output verso il client */
    private ObjectOutputStream out;
    /** Nome della tabella corrente del database */
    private String tableName;
    /** Ultimo clustering eseguito */
    private QTMiner lastMiner;

    /**
     * Costruttore parametrizzato della classe ServerOneClient.
     * Inizializza il socket del client e flussi di input/output.
     * 
     * @param s socket associato al client.
     * @throws IOException se si verifica un errore di I/O nell'apertura dei flussi.
     */
    public ServerOneClient(Socket s) throws IOException {
        this.socket = s;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.out.flush(); 
        this.in = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * Esegue il ciclo di ascolto delle richieste del client.
     * In base al comando ricevuto, richiama le operazioni di clustering, apprendimento dal database o apprendimento da file.
     * In caso di errore esso viene comunicato al client.
     */
    @Override
    public void run() {
        try {
            while (true) {
                Object request = in.readObject(); 
                int command = (Integer) request;

                switch (command) {
                    case 0: 
                        System.out.println("[!] Richiesta storeTableFromDb");
                        tableName = (String) in.readObject();
                        out.writeObject("OK");
                        out.flush();
                        break;
                    case 1: 
                        System.out.println("[!] Richiesta learningFromDbTable");
                        
                        double radius = 1.0;
                        do{
                        	radius = (double) in.readObject();
                        }while(Double.isNaN(radius));
                        
                                
                        try {                    
                        	Data data = new Data(tableName); 
                           
                        	lastMiner = new QTMiner(radius);
                        	int numIter = lastMiner.compute(data); 
                        	

                        	
                        	out.writeObject("OK"); 
                        	out.writeObject(numIter);
                        	out.writeObject(lastMiner.getC().toString(data));
                        	out.flush();
                        	break;
                        }
                        catch(DatabaseConnectionException ex) {
                        	out.writeObject(ex.getMessage());
                        }
                        catch(EmptySetException ex) {
                        	out.writeObject(ex.getMessage());
                        }
                        catch(NoValueException ex) {
                        	out.writeObject(ex.getMessage());
                        }
                        catch(SQLException ex) {
                        	out.writeObject("Errore generico del database!");
                        }
                        catch(ClusteringRadiusException ex) {
                        	out.writeObject(ex.getMessage());
                        }
                        catch(EmptyDatasetException ex){
                        	out.writeObject(ex.getMessage());
                        }
                        catch(IllegalArgumentException ex) {
                        	out.writeObject(ex.getMessage());
                        }
                        catch(EmptyTypeException ex) {
                        	out.writeObject(ex.getMessage());
                        }
                    case 2: 
                        System.out.println("[!] Richiesta storeClusterInFile");
                        String fileName = (String) in.readObject();

                        if (lastMiner != null) {
                            lastMiner.salva(fileName);
                            out.writeObject("Clustering salvato correttamente in " + fileName);
                        } else {
                            out.writeObject("Errore: nessun clustering disponibile da salvare!");
                        }
                        out.writeObject("OK");
                        out.flush();
                        break;
                    case 3: 
                        System.out.println("[!] Richiesta learningFromFile");
                        String fileLoad = (String) in.readObject();
                        try {
                        	QTMiner file_miner = new QTMiner(fileLoad);
                        	out.writeObject("Cluster recuperati dal file: \n" + file_miner.getC());
                            out.writeObject("OK");
                            out.flush();
                            break;
                        }
                        catch (IOException ex) {
                        	out.writeObject("err");
                        	out.writeObject("Errore: File non trovato!");
                        	break;
                        }
                }
            }
        } catch (Exception e) {
            System.err.println("Client " + socket.getInetAddress().getHostAddress() + " disconnesso!");
        } finally {
            try {
                socket.close();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }
}
