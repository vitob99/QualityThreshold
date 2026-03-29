package clustering.core.data;

import java.io.Serializable;

/**
 * Classe che estende {@link Item} e rappresenta un item discreto, cioe' un attributo discreto
 * associato a un valore categorico.
 * La distanza tra due DiscreteItem e' calcolata in modo binario:
 * 0 se i valori sono uguali, 1 se sono diversi.
 * 
 */
class DiscreteItem extends Item implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Costruttore parametrizzato della classe DiscreteItem.
     *
     * @param attribute attributo discreto a cui l'item si riferisce.
     * @param value     valore discreto associato all'attributo.
     */
    DiscreteItem(DiscreteAttribute attribute, String value) {
        super(attribute, value);
    }

    /**
     * Calcola la distanza tra il valore dell'item e un altro valore fornito.
     * Per un item discreto, la distanza è 0 se i valori sono uguali, 1 altrimenti.
     *
     * @param a valore con cui calcolare la distanza.
     * @return 0 se i valori sono uguali, 1 se diversi.
     * @throws EmptyDatasetException se il valore corrente e' "null".
     */
    @Override
    double distance(Object a) throws EmptyDatasetException {
        if (getValue().equals(a)) {
            return 0;
        } else {
            return 1;
        }
    }
}
