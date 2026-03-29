package clustering.core.database;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta l'esempio dei dati provenienti dal database.
 * 
 * Ogni esempio è costituito da una lista di valori generici 
 * che possono essere attributi discreti o continui.
 */
public class Example implements Comparable<Example> {

    /** Lista dei valori che compongono l'esempio. */
    private List<Object> example = new ArrayList<>();

    /**
     * Aggiunge un valore all'esempio.
     *
     * @param o valore da aggiungere.
     */
    public void add(Object o) {
        example.add(o);
    }

    /**
     * Restituisce il valore presente nella posizione specificata dall'indice i.
     *
     * @param i indice dell'elemento da recuperare
     * @return oggetto presente alla posizione i
     */
    public Object get(int i) {
        return example.get(i);
    }

    /**
     * Confronta l'esempio considerato con un altro esempio ex.
     * 
     * Il confronto viene effettuato elemento per elemento.
     *
     * @param ex esempio da confrontare.
     * @return un valore negativo, zero o positivo se questo esempio e' rispettivamente
     *         minore, uguale o maggiore dell'esempio passato.
     */
    public int compareTo(Example ex) {
        int i = 0;
        for (Object o : ex.example) {
            if (!o.equals(this.example.get(i))) {
                return ((Comparable) o).compareTo(example.get(i));
            }
            i++;
        }
        return 0;
    }

    /**
     * Restituisce una rappresentazione testuale dell'esempio.
     *
     * @return dalori dell'esempio in formato stringa.
     */
    @Override
    public String toString() {
        String str = "";
        for (Object o : example)
            str += o.toString() + " ";
        return str;
    }
}
