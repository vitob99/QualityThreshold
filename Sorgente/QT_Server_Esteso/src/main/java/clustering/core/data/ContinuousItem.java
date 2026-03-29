package clustering.core.data;

import java.io.Serializable;

/**
 * Classe che estende {@link Item} e rappresenta un item continuo, cioe' un attributo continuo
 * associato a un valore numerico.
 * La distanza tra due ContinuousItem e' calcolata come
 * differenza assoluta dei valori normalizzati nell'intervallo [0,1].
 * 
 */
class ContinuousItem extends Item implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Costruttore parametrizzato della classe ContinuousItem.
     *
     * @param attribute attributo continuo a cui l'item si riferisce.
     * @param value     valore numerico associato all'attributo.
     */
    ContinuousItem(Attribute attribute, Double value) {
        super(attribute, value);
    }

    /**
     * Calcola la distanza tra il valore dell'item e un altro valore fornito.
     * La distanza e' la differenza assoluta tra i valori scalati (normalizzati)
     * nell'intervallo [0,1].
     *
     * @param a valore numerico con cui calcolare la distanza.
     * @return differenza assoluta dei valori scalati.
     * @throws EmptyDatasetException se il valore corrente e' "null".
     */
    @Override
    double distance(Object a) throws EmptyDatasetException {
        ContinuousAttribute continuous_attribute = (ContinuousAttribute) this.getAttribute();
        double v1 = continuous_attribute.getScaledValue((Double) this.getValue());
        double v2 = continuous_attribute.getScaledValue((Double) a);
        return Math.abs(v1 - v2);
    }
}
