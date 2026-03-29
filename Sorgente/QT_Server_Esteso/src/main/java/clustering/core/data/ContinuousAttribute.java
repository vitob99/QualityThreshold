package clustering.core.data;

import java.io.Serializable;

/**
 * Classe che estende {@link Attribute} e rappresenta un attributo continuo (reale) nel dataset.
 * Un attributo continuo e' caratterizzato da un valore minimo e massimo,
 * utilizzati per normalizzare i valori.
 */
class ContinuousAttribute extends Attribute implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Valore massimo dell'attributo. */
    private double max;

    /** Valore minimo dell'attributo. */
    private double min;

    /**
     * Costruttore parametrizzato della classe ContinuousAttribute.
     * Inizializza i valori di minimo e massimo
     *
     * @param name  nome dell'attributo.
     * @param index indice numerico dell'attributo.
     * @param min   valore minimo possibile dell'attributo.
     * @param max   valore massimo possibile dell'attributo.
     */
    ContinuousAttribute(String name, int index, double min, double max) {
        super(name, index);
        this.max = max;
        this.min = min;
    }

    /**
     * Restituisce il valore scalato del parametro passato in input.
     * Il risultato e' compreso nell'intervallo [0,1].
     *
     * @param v valore da scalare.
     * @return valore scalato.
     */
    double getScaledValue(double v){
        return (v - min) / (max - min);
    }
}
