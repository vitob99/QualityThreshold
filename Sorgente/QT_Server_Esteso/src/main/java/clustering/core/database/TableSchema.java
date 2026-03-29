package clustering.core.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * La classe rappresenta in memoria lo schema di una tabella del database.
 * 
 * Recupera i nomi e i tipi delle colonne di una tabella e li memorizza internamente.
 */
public class TableSchema {

    /** Riferimento alla classe di accesso al database {@link DbAccess} */
    DbAccess db;

    /**
     * Rappresenta una colonna della tabella, con nome e tipo.
     */
    public class Column {
        /** Nome della colonna. */
        private String name;

        /** Tipo della colonna. */
        private String type;

        /**
         * Costruttore parametrizzato della colonna.
         * Inizializza nome e tipo della colonna.
         *
         * @param name nome della colonna.
         * @param type tipo della colonna.
         */
        Column(String name, String type) {
            this.name = name;
            this.type = type;
        }

        /**
         * Costruttore predefinito della colonna.
         * Restituisce il nome della colonna.
         *
         * @return nome della colonna.
         */
        public String getColumnName() {
            return name;
        }

        /**
         * Verifica se il tipo della colonna e' numerico.
         *
         * @return "true" se il tipo è numerico, "false" altrimenti.
         */
        public boolean isNumber() {
            return type.equals("number");
        }

        /**
         * Rappresentazione testuale della colonna.
         *
         * @return rappresentazione testuale come stringa "nome:tipo".
         */
        @Override
        public String toString() {
            return name + ":" + type;
        }
    }

    /** Lista delle colonne della tabella. */
    List<Column> tableSchema = new ArrayList<>();

    /**
     * Costruttore parametrizzato della classe TableSchema
     * Costruisce lo schema della tabella specificata "tableName".
     *
     * @param db riferimento alla connessione al database.
     * @param tableName nome della tabella.
     * @throws SQLException se si verifica un errore SQL durante il recupero delle colonne.
     */
    public TableSchema(DbAccess db, String tableName) throws SQLException {
        this.db = db;

        HashMap<String, String> mapSQL_JAVATypes = new HashMap<>();
        // Mappatura tipi SQL -> tipi Java
        mapSQL_JAVATypes.put("CHAR", "string");
        mapSQL_JAVATypes.put("VARCHAR", "string");
        mapSQL_JAVATypes.put("LONGVARCHAR", "string");
        mapSQL_JAVATypes.put("BIT", "string");
        mapSQL_JAVATypes.put("SHORT", "number");
        mapSQL_JAVATypes.put("INT", "number");
        mapSQL_JAVATypes.put("LONG", "number");
        mapSQL_JAVATypes.put("FLOAT", "number");
        mapSQL_JAVATypes.put("DOUBLE", "number");

        Connection con = db.getConnection();
        DatabaseMetaData meta = con.getMetaData();
        ResultSet res = meta.getColumns(null, null, tableName, null);

        while (res.next()) {
            if (mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME"))) {
                tableSchema.add(new Column(
                        res.getString("COLUMN_NAME"),
                        mapSQL_JAVATypes.get(res.getString("TYPE_NAME")))
                );
            }
        }
        res.close();
    }

    /**
     * Restituisce il numero di attributi (colonne) della tabella.
     *
     * @return numero di colonne.
     */
    public int getNumberOfAttributes() {
        return tableSchema.size();
    }

    /**
     * Restituisce la colonna alla posizione specificata da index.
     *
     * @param index indice della colonna.
     * @return oggetto {@link Column} corrispondente rappresentante la colonna.
     */
    public Column getColumn(int index) {
        return tableSchema.get(index);
    }
}
