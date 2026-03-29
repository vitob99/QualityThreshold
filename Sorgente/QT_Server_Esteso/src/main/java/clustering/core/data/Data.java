package clustering.core.data;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.*;

import clustering.core.database.*;


/**
 * Classe che modella l'insieme di esempi del dataset.
 * Include l'insieme degli esempi, il numero e l'insieme degli attributi.
 */
public class Data implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Lista degli esempi del dataset. */
	private List<Example> data;
	/** Numero degli esempi nel dataset. */
    private int numberOfExamples;
    /** Lista degli attributi. */
    private List<Attribute> attributeSet;

    /**
     * Costruttore parametrizzato della classe Data.
     * Carica in memoria il dataset dal database, dalla tabella "tableName".
     * 
     * @param tableName nome della tabella del database da cui saranno estratti i dati.
     * @throws SQLException se si e' verificato un errore generico del database 
     * o se la tabella tableName non e' presente nel database
     * @throws EmptySetException se il dataset e' vuoto.
     * @throws DatabaseConnectionException se si e' verificato un errore di connessione al database
     * @throws NoValueException se viene letto un valore nullo;
     * @throws EmptyTypeException se viene letto un tipo non corretto dell'attributo
     * 
     */
    public Data(String tableName) throws SQLException, EmptySetException, DatabaseConnectionException, NoValueException, EmptyTypeException {
    	data = new ArrayList<>();
    	attributeSet = new LinkedList<>();
        DbAccess db = new DbAccess();
        db.initConnection();
        try {
            TableData tableData = new TableData(db);
            TableSchema schema = new TableSchema(db, tableName);
            int numColumns = schema.getNumberOfAttributes();
            if (numColumns != 5) {
                throw new IllegalArgumentException("Errore: numero di colonne nella tabella non valido!");
            }

            
            for (int i = 0; i < numColumns; i++) {
                TableSchema.Column col = schema.getColumn(i);
                if (i == 1) { 
                    if (!col.isNumber()) {
                        throw new EmptyTypeException("La colonna " + col.getColumnName() + " deve essere numerica!");
                    }
                    Object min = tableData.getAggregateColumnValue(tableName, col, QUERY_TYPE.MIN);
                    Object max = tableData.getAggregateColumnValue(tableName, col, QUERY_TYPE.MAX);
                    attributeSet.add(new ContinuousAttribute(col.getColumnName(), i, ((Number) min).doubleValue(), ((Number) max).doubleValue()));
                } else { 
                    if (col.isNumber()) {
                        throw new EmptyTypeException("La colonna " + col.getColumnName() + " deve essere discreta!");
                    }
                    Set<Object> values = tableData.getDistinctColumnValues(tableName, col);
                    if (values.isEmpty()) {
                        throw new EmptySetException();
                    }
                    TreeSet<String> stringValues = new TreeSet<>();
                    for (Object val : values) {
                        stringValues.add(val.toString());
                    }
                    attributeSet.add(new DiscreteAttribute(col.getColumnName(), i, stringValues));
                }
            }

           
            List<Example> tuples = tableData.getDistinctTransazioni(tableName);
            data.addAll(tuples);
            numberOfExamples = data.size();
            if (numberOfExamples == 0) {
                throw new EmptySetException();
            }

        } catch (NoValueException ex) {
            ex.getMessage();
        } finally {
            db.closeConnection();
        }
    }

    /**
     * Restituisce il numero di esempi presenti nel dataset.
     * 
     * @return numero di esempi nel dataset.
     */
    public int getNumberOfExamples() {
        return numberOfExamples;
    }

    /**
     * Restituisce il numero degli attributi.
     * 
     * @return numero degli attributi.
     */
    public int getNumberOfAttributes() {
        return attributeSet.size();
    }

    /**
     * Restituisce lo schema dei dati.
     * 
     * @return lista degli attributi.
     */
    public List<Attribute> getAttributeSchema() {
        return attributeSet;
    }

    /**
     * Restituisce il valore dell'attributo.
     * 
     * @param exampleIndex indice dell'esempio.
     * @param attributeIndex indice dell'attributo.
     * @return valore dell'attributo.
     */
    public Object getAttributeValue(int exampleIndex, int attributeIndex) {
        return data.get(exampleIndex).get(attributeIndex);
    }

    /**
     * Restituisce l'attributo.
     * 
     * @param index indice dell'attributo da restituire.
     * @return attributo {@link Attribute}.
     */
    public Attribute getAttribute(int index) {
        return attributeSet.get(index);
    }

    /**
     * Restituisce la tupla corrispondente all'esempio dell'indice specificato.
     * 
     * @param index indice dell'esempio da convertire in tupla.
     * @return tupla {@link Tuple} corrispondente all'esempio
     */
    public Tuple getItemSet(int index) {
        Tuple tuple = new Tuple(attributeSet.size());
        Example ex = data.get(index);

        for (int i = 0; i < attributeSet.size(); i++) {
            Attribute attr = attributeSet.get(i);
            Object value = ex.get(i);
            
            if (attr instanceof DiscreteAttribute) {
                tuple.add(new DiscreteItem((DiscreteAttribute) attr, value.toString()), i);
            } else if (attr instanceof ContinuousAttribute) {
                tuple.add(new ContinuousItem((ContinuousAttribute) attr, ((Number) value).doubleValue()), i);
            }
        }
        return tuple;
    }

    /**
     * Restituisce una rappresentazione testuale del dataset.
     * 
     * @return rappresentazione testuale del dataset.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Stampa nomi degli attributi
        for (int i = 0; i < attributeSet.size(); i++) {
            sb.append(attributeSet.get(i).getName());
            if (i < attributeSet.size() - 1) sb.append(",");
        }
        sb.append("\n");

        // Stampa esempi
        for (int i = 0; i < numberOfExamples; i++) {
            sb.append(i + 1).append(": ");
            Example ex = data.get(i);
            for (int j = 0; j < attributeSet.size(); j++) {
                sb.append(ex.get(j));
                if (j < attributeSet.size() - 1) sb.append(",");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
