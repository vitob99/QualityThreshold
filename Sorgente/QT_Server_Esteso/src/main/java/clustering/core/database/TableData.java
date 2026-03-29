package clustering.core.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import clustering.core.database.TableSchema.Column;

/**
 * Classe che permette di interagire con il database tramite l’oggetto {@link DbAccess} 
 * per estrarre gli esempi dalla tabella come valori distinti e aggregati.
 * 
 * Fornisce metodi per ottenere:
 * - le transazioni distinte di una tabella.
 * - i valori distinti di una colonna.
 * - i valori aggregati (minimo o massimo) di una colonna.
 * 
 */
public class TableData {
	/** Riferimento alla classe di accesso al database {@link DbAccess} */
    DbAccess db;

    /**
     * Costruttore parametrizzato di TableData.
     * 
     * @param db istanza di {@link DbAccess} per la connessione al database.
     */
    public TableData(DbAccess db) {
        this.db=db;
    }

    /**
     * Restituisce tutte le tuple distinte di una tabella.
     *
     * @param table nome della tabella.
     * @return lista di {@link Example} contenente le tuple.
     * @throws SQLException se si verifica un errore SQL.
     * @throws EmptySetException se la tabella non contiene tuple.
     * 
     */
    public List<Example> getDistinctTransazioni(String table) throws SQLException, EmptySetException{
        LinkedList<Example> transSet = new LinkedList<Example>();
        Statement statement;
        TableSchema tSchema=new TableSchema(db,table);


        String query="select distinct ";

        for(int i=0;i<tSchema.getNumberOfAttributes();i++){
            Column c=tSchema.getColumn(i);
            if(i>0)
                query+=",";
            query += c.getColumnName();
        }
        if(tSchema.getNumberOfAttributes()==0)
            throw new SQLException();
        query += (" FROM "+table);

        statement = db.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(query);
        boolean empty=true;
        while (rs.next()) {
            empty=false;
            Example currentTuple=new Example();
            for(int i=0;i<tSchema.getNumberOfAttributes();i++)
                if(tSchema.getColumn(i).isNumber())
                    currentTuple.add(rs.getDouble(i+1));
                else
                    currentTuple.add(rs.getString(i+1));
            transSet.add(currentTuple);
        }
        rs.close();
        statement.close();
        if(empty) throw new EmptySetException();


        return transSet;

    }

    /**
     * Restituisce i valori distinti di una colonna.
     *
     * @param table nome della tabella.
     * @param column colonna da cui ottenere i valori.
     * @return insieme di valori distinti.
     * @throws SQLException se si verifica un errore SQL.
     * 
     */
    public Set<Object> getDistinctColumnValues(String table,Column column) throws SQLException{
        Set<Object> valueSet = new TreeSet<Object>();
        Statement statement;
        TableSchema tSchema=new TableSchema(db,table);


        String query="select distinct ";

        query+= column.getColumnName();

        query += (" FROM "+table);

        query += (" ORDER BY " +column.getColumnName());



        statement = db.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            if(column.isNumber())
                valueSet.add(rs.getDouble(1));
            else
                valueSet.add(rs.getString(1));

        }
        rs.close();
        statement.close();

        return valueSet;

    }

    /**
     * Restituisce un valore aggregato (minimo o massimo) di una colonna.
     *
     * @param table nome della tabella.
     * @param column colonna di cui calcolare l'aggregato.
     * @param aggregate tipo di aggregato {@link QUERY_TYPE}.
     * @return valore aggregato della colonna.
     * @throws SQLException se si verifica un errore SQL.
     * @throws NoValueException se non è presente alcun valore.
     */
    public Object getAggregateColumnValue(String table,Column column,QUERY_TYPE aggregate) throws SQLException,NoValueException{
        Statement statement;
        TableSchema tSchema=new TableSchema(db,table);
        Object value=null;
        String aggregateOp="";

        String query="select ";
        if(aggregate==QUERY_TYPE.MAX)
            aggregateOp+="max";
        else
            aggregateOp+="min";
        query+=aggregateOp+"("+column.getColumnName()+ ") FROM "+table;


        statement = db.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            if(column.isNumber())
                value=rs.getFloat(1);
            else
                value=rs.getString(1);

        }
        rs.close();
        statement.close();
        if(value==null)
            throw new NoValueException("No " + aggregateOp+ " on "+ column.getColumnName());

        return value;

    }
}
