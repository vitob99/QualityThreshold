package clustering.core.data;

import java.io.Serializable;

/**
 * Classe astratta che rappresenta un{@link Item}, cioe' una coppia {@link Attribute} - Valore
 * 
 * Ogni {@link Item} associa un attributo ad un valore specifico
 * (discreto o continuo). Le sottoclassi {@link DiscreteItem} e
 * {@link ContinuousItem} specializzano il comportamento a seconda
 * del tipo di attributo.
 * 
 */
abstract class Item implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/** Attributo a cui l'item si riferisce. */
	private Attribute attribute;
	/** Valore associato all'attributo. */
    private Object value;

    /**
     * Costruttore parametrizzato dell'item.
     * Inizializza la coppia attributo-valore.
     *
     * @param attribute attributo a cui l'item si riferisce.
     * @param value     valore associato all'attributo.
     */ 
    Item(Attribute attribute, Object value){
        this.attribute = attribute;
        this.value = value;
    }

    /**
     * Restituisce l'attributo associato all'item.
     *
     * @return attributo dell'item.
     */
    Attribute getAttribute(){
        return attribute;
    }
    
    /**
     * Restituisce il valore associato all'item.
     *
     * @return valore dell'item.
     * @throws EmptyDatasetException se il valore è nullo.
     */
    Object getValue() throws EmptyDatasetException{
        if(value == null){
            throw new EmptyDatasetException();
        }
        return value;
    }
    /**
     * Restituisce una rappresentazione testuale dell'item.
     *
     * @return il valore in formato stringa, oppure "null" se non presente.
     */
    public String toString(){
        Object val = null;
        try {
            val = getValue();
        } catch (EmptyDatasetException e) {
            throw new RuntimeException(e);
        }
        return val != null ? val.toString() : "null";
    }

    /**
     * Calcola la distanza tra il valore dell'item e un altro valore fornito.
     * L'implementazione concreta è fornita dalle sottoclassi
     * {@link DiscreteItem} e {@link ContinuousItem}.
     *
     * @param a valore con cui calcolare la distanza.
     * @return distanza fra i due valori.
     * @throws EmptyDatasetException se il valore corrente è "null".
     */
    abstract double distance(Object a) throws EmptyDatasetException;
}
