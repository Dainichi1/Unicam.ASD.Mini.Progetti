package Mini_Progetto_1;

/**
 * Un fattorizzatore è un agente che fattorizza un qualsiasi numero naturale nei
 * sui fattori primi.
 * 
 * @author Luca Tesei (template) // Marco, Torquati
 *  marco.torquati@studenti.unicam.it (implementazione)
 *
 */
public class Factoriser {

    private int numeroFattori = 0;

    /**
     * Crea un fattorizzatore.
     */

    /**
     * Fattorizza un numero restituendo la sequenza crescente dei suoi fattori
     * primi. La molteplicità di ogni fattore primo esprime quante volte il
     * fattore stesso divide il numero fattorizzato. Per convenzione non viene
     * mai restituito il fattore 1. Il minimo numero fattorizzabile è 1. In
     * questo caso viene restituito un array vuoto.
     *
     * @param n un numero intero da fattorizzare
     * @return un array contenente i fattori primi di n
     * @throws IllegalArgumentException se si chiede di fattorizzare un
     *                                  numero minore di 1.
     */
    public Factor[] getFactors(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Il numero deve essere maggiore di 0");
        }
        // Calcolo il numero di fattori
        Factor[] fattori = new Factor[n];
        // Calcolo i fattori
        int fattore = 2;
        // Indice dell'array
        int index = 0;
        // Calcolo i fattori
        while (n > 1) {
            // Calcolo la molteplicità
            // del fattore
            // corrente
            // e aggiorno il numero
            // del fattore
            // corrente

            int multiplicity = 0;
            while (n % fattore == 0) {
                n = n / fattore;
                multiplicity++;
            }
            if (multiplicity > 0) {
                fattori[index] = new Factor(fattore, multiplicity);
                index++;
            }
            fattore++;
        }
        // Riduco l'array
        // dei fattori
        // a quello
        // effettivamente
        // usato
        // Restituisco l'array
        // dei fattori
        // ridotto

        Factor[] fattoriRidotti = new Factor[index];
        System.arraycopy(fattori, 0, fattoriRidotti, 0, index);
        return fattoriRidotti;
    }

    /**
     * Restituisce la rappresentazione in stringa di un fattore. La
     * rappresentazione è una stringa che contiene il valore del fattore
     * seguito da un asterisco e dalla sua molteplicità.
     *
     * @param f il fattore da rappresentare
     * @return la stringa che rappresenta il fattore
     * @throws NullPointerException se il fattore è null
     */
    public String toString(Factor f) {
        if (f == null) {
            throw new NullPointerException("Il fattore non può essere null");
        }
        return f.getPrimeValue() + "*" + f.getMultiplicity();
    }

    /**
     * Restituisce la rappresentazione in stringa di un array di fattori. La
     * rappresentazione è una stringa che contiene la rappresentazione di ogni
     * fattore separati da una virgola.
     *
     * @param factors l'array di fattori da rappresentare
     * @return la stringa che rappresenta l'array di fattori
     * @throws NullPointerException se l'array è null
     */
    public String toString(Factor[] factors) {
        if (factors == null) {
            throw new NullPointerException("L'array non può essere null");
        }
        StringBuilder risultato = new StringBuilder();
        for (Factor factor : factors) {
            risultato.append(toString(factor));

        }
        return risultato.toString();
    }
}



