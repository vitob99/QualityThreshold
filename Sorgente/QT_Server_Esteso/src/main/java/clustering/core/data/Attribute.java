package clustering.core.data;

import java.io.Serializable;

/**
 * Classe astratta che rappresenta l'attributo generico del dateset.
 * Ogni attributo ha un nome e un indice numerico.
 */
abstract class Attribute implements Serializable {
	private static final long serialVersionUID = 1L;
	

	/** Nome dell'attributo. */
	private String name; 
	/** Identificativo numerico dell'attributo. */
    private int index; 

    /**
     * Costruttore parametrizzato della classe Attribute.
     * Inizializza nome e indice.
     *
     * @param name  nome simbolico dell'attributo.
     * @param index indice numerico dell'attributo.
     */
    Attribute(String name, int index){
        this.name = name;
        this.index = index;
    }

    /**
     * Restituisce il nome dell'attributo.
     *
     * @return nome dell'attributo.
     */
    String getName(){
        return name;
    }

    /**
     * Restituisce l'identificativo numerico dell'attributo.
     *
     * @return indice dell'attributo.
     */
    int getIndex(){
        return index;
    }

    /**
     * Restituisce una rappresentazione testuale dell'attributo.
     *
     * @return nome dell'attributo.
     */
    public String toString(){
        return name;
    }


}