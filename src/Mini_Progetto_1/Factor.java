package Mini_Progetto_1;

/**
 * Un oggetto di quest classe rappresenta un fattore primo di un numero naturale
 * con una certa molteplicità.
 * 
 * @author Luca Tesei (template) // Marco, Torquati
 *   marco.torquati@studenti.unicam.it (implementazione)
 *
 */
public class Factor implements Comparable<Factor> {

    /*
     * Numero primo corrispondente a questo fattore
     */
    private final int primeValue;

    /*
     * Molteplicità del numero primo di questo fattore, deve essere maggiore o
     * uguale a 1.
     */
    private final int multiplicity;

    /**
     * Crea un fattore primo di un numero naturale, formato da un numero primo e
     * dalla sua molteplicità.
     * 
     * @param primeValue,
     *                          numero primo
     * @param multiplicity,
     *                          valore della molteplicità, deve essere almeno 1
     * @throws IllegalArgumentException
     *                                      se la molteplicità è minore di 1
     *                                      oppure se primeValue è minore o
     *                                      uguale di 0.
     */
    public Factor(int primeValue, int multiplicity) {
        if (multiplicity < 1 || primeValue <= 1 )
            throw new IllegalArgumentException("La molteplicità deve essere maggiore di 1 e il Prime Value deve essere maggiore di 0");
        this.primeValue = primeValue;
        this.multiplicity = multiplicity;
    }

    /**
     * @return the primeValue
     */
    public int getPrimeValue() {
        return primeValue;
    }

    /**
     * @return the multiplicity
     */
    public int getMultiplicity() {
        return multiplicity;
    }

    /*
     * Calcola l'hashcode dell'oggetto in accordo ai valori usati per definire
     * il metodo equals.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        // si usa il valore intero corrispondente alla rappresentazione del
        // double bit a bit (64 bit, cioè un long)
        temp = Double.doubleToLongBits(primeValue);
        // si fa il bitwise XOR tra i 64 bit originali e il loro shift a destra
        // di 32 bit, poi si fa il cast a int
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(multiplicity);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /*
     * Due oggetti Factor sono uguali se e solo se hanno lo stesso numero primo
     * e la stessa molteplicità
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Factor))
            return false;
        Factor other = (Factor) obj;
        // controllo di uguaglianza di double effettuato bit a bit sulla
        // rappresentazione a 64 bit vista come un long
        // in questo caso consideriamo due oggetti Factor uguali solo se hanno
        // la stessa rappresentazione di "primeValue" e "multiplicity"
        if (Double.doubleToLongBits(this.primeValue) != Double.doubleToLongBits(other.primeValue))
            return false;
        if (Double.doubleToLongBits(this.multiplicity) != Double.doubleToLongBits(other.multiplicity))
            return false;
        return true;
    }

    /*
     * Un Factor è minore di un altro se contiene il numero primo minore. Se due
     * Factor hanno lo stesso numero primo allora il più piccolo dei due è
     * quello ce ha minore molteplicità.
     */
    @Override
    public int compareTo(Factor o) {
        if (o == null)
            throw new NullPointerException("Tentativo di confrontare con null");
        if (this.primeValue < o.primeValue)
            return -1;
        else if (this.primeValue > o.primeValue)
            return 1;
        // i "primeValue" sono uguali, controllo "multiplicity"
        return Integer.compare(this.multiplicity, o.multiplicity);
        // sono uguali
    }

    /*
     * Il fattore viene reso con la stringa primeValue^multiplicity
     */
    @Override
    public String toString() {
        String s = primeValue+"^"+multiplicity;
        return s;
    }


}
