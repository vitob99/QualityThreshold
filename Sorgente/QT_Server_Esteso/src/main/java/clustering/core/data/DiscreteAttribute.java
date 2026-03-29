package clustering.core.data;

import java.io.Serializable;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Classe che estende {@link Attribute} e rappresenta un attributo discreto (categorico) nel dataset.
 * Un attributo discreto è caratterizzato da un insieme finito e ordinato
 * di valori simbolici (stringhe).
 */
class DiscreteAttribute extends Attribute implements Iterable<String>, Serializable {
    private static final long serialVersionUID = 1L;

    /** Insieme dei valori discreti possibili per l'attributo. */
    private TreeSet<String> values;

    /**
     * Costruttore parametrizzato della classe DiscreteAttribute.
     * Inizializza l'insieme dei valori discreti possibili.
     *
     * @param name   nome dell'attributo.
     * @param index  indice numerico dell'attributo.
     * @param values insieme ordinato dei valori discreti possibili.
     */
    DiscreteAttribute(String name, int index, TreeSet<String> values) {
        super(name, index);
        this.values = values;
    }

    /**
     * Restituisce il numero di valori distinti dell'attributo.
     *
     * @return numero di valori distinti.
     */
    int getNumberOfDistinctValues() {
        return values.size();
    }

    /**
     * Restituisce un iteratore sui valori discreti dell'attributo.
     *
     * @return iteratore dei valori discreti.
     */
    @Override
    public Iterator<String> iterator() {
        return values.iterator();
    }
}
