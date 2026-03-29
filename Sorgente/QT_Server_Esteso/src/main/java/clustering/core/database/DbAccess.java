package clustering.core.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsabile della gestione della connessione al database MySQL.
 * 
 * Contiene le informazioni per la connessione (server, database, porta,
 * username e password) e i metodi per inizializzare, recuperare e chiudere
 * la connessione.
 * 
 */
public class DbAccess {
    /** Contiene l’identificativo del DBMS JDBC. */
    private final String DBMS = "jdbc:mysql";
    /** Contiene l’identificativo del server su cui e' hostato il database(es. localhost). */
    private final String SERVER = "localhost";
    /** Contiene il nome del database. */
    private final String DATABASE = "MapDB";
    /** La porta su cui il DBMS MySQL accetta le connessioni. */
    private final String PORT = "3306";
    /** Nome dell’utente per l’accesso al databse. */
    private final String USER_ID = "MapUser";
    /** Password dell’utente identificato da USER_ID. */
    private final String PASSWORD = "map";
    /** Gestisce la connessione al database. */
    private Connection conn;

    /**
     * Inizializza la connessione al database.
     *
     * @throws DatabaseConnectionException se non è possibile stabilire la connessione
     * @throws SQLException se si verifica un errore SQL durante la connessione
     */
    public void initConnection() throws DatabaseConnectionException, SQLException {
        String conn_string = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE
                + "?user=" + USER_ID + "&password=" + PASSWORD + "&serverTimezone=UTC";
        try {
            conn = DriverManager.getConnection(conn_string);
        } catch (SQLException ex) {
            throw new DatabaseConnectionException();
        }
    }

    /**
     * Restituisce l’oggetto Connection appena aperto.
     *
     * @return connessione al database.
     */
    public Connection getConnection() {
        return conn;
    }

    /**
     * Chiude la connessione al database.
     *
     * @throws SQLException se si verifica un errore durante la chiusura della connessione.
     */
    public void closeConnection() throws SQLException {
        conn.close();
    }
}
