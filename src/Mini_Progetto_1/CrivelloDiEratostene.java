package Mini_Progetto_1;

/**
 * Il crivello di Eratostene è un modo per determinare tutti i numeri primi da
 * {@code 1} a un certo intero {@code n} assegnato. Il crivello può essere
 * semplicemente rappresentato da un array di booleani in cui la posizione i è
 * true se e solo se il numero i è primo. Per costruirlo si segue un semplice
 * algoritmo che parte dalla posizione 2 e la mette a true. Dopodiché vengono
 * messe a false tutte le posizioni multiple di 2. Poi viene determinata la
 * prima posizione successiva a 2 che non è stato messa a false, in questo caso
 * 3. Si passa quindi a mettere a false tutte le posizioni multiple di 3. Si
 * ripete la procedura per la posizione non false successiva, che sarà 5. E così
 * via fino ad aver visitato tutte le caselle minori o uguali alla capacità del
 * crivello.
 * 
 * Un oggetto di questa classe permette di conoscere direttamente se un numero
 * tra 2 e la capacità del crivello è primo tramite il metodo isPrime(int).
 * 
 * Inoltre un oggetto di questa classe fornisce la funzionalità di elencare
 * tutti i numeri primi da 2 alla capacità del crivello tramite una serie di
 * chiamate al metodo nextPrime(). L'elenco può essere fatto ripartire in
 * qualsiasi momento chiamando il metodo restartPrimeIteration() e si interrompe
 * non appena il metodo hasNextPrime() restituisce false.
 * 
 * @author Luca Tesei (template) // Marco, Torquati
 *  marco.torquati@studenti.unicam.it (implementazione)
 *
 */
public class CrivelloDiEratostene {
    /*
     * Array di booleani che rappresenta il crivello. La posizione i dell'arraya
     * è true se e solo se il numero i è primo altrimenti la posizione i deve
     * essere false. Le posizioni 0 e 1 dell'array non sono significative e non
     * vengono usate. L'ultima posizione dell'array deve essere uguale alla
     * capacità del crivello.
     */
    private boolean[] crivello;

    /*
     * Capacità del crivello, immutabile
     */
    private final int capacity;
    // numero corrente
    private int current;



    /**
     * Costruisce e inizializza il crivello di Eratostene fino alla capacità
     * data. La capacità deve essere almeno 2.
     *
     * @param capacity capacità del crivello, almeno 2
     * @throws IllegalArgumentException se il numero {@code capacity} è
     *                                  minore di {@code 2}
     */
    public CrivelloDiEratostene(int capacity) {
        if (capacity < 2)
            throw new IllegalArgumentException("La capacità deve essere maggiore o uguale a 2");

        this.capacity = capacity;
        this.crivello = new boolean[this.capacity + 1];
        this.current = 1;
        // scorro tutto l'array e imposto tutto a true
        for (int i = 2; i <= capacity; i++)
            crivello[i] = true;
        // scorro l'array e se il numero nella posizione i-esima dell'array è primo
        // procedo a impostare i multipli a false
        for (int i = 2; i <= Math.sqrt(capacity); i++) {
            if (crivello[i]) {
                for (int j = i; i * j <= capacity; j++) {
                    crivello[i * j] = false;
                }
            }
        }
    }




    /**
     * Restituisce la capacità di questo crivello, cioè il numero massimo di
     * entrate.
     *
     * @return la capacità di questo crivello
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Controlla se un numero è primo. Può rispondere solo se il numero passato
     * come parametro è minore o uguale alla capacità di questo crivello.
     *
     * @param n il numero da controllare
     * @return true se il numero passato è primo, false altrimenti
     * @throws IllegalArgumentException se il numero passato {@code n}
     *                                  eccede la capacità di questo
     *                                  crivello o se è un numero minore di
     *                                  2.
     */
    public boolean isPrime(int n) {
        if (!(n <= this.getCapacity()) || n <= 1)
            throw new IllegalArgumentException("Il numero deve essere maggiore o uguale a 2 e non deve eccedere la capacità del crivello");
        // 2 è primo
        if (n == 2)
            return true;
        // se un numero è divisibile per 2 non è primo
        if (n % 2 == 0)
            return false;
        // se il numero passato non è divisibile per 2 procedo a cercare tutti i dispari
        // se "n" diviso l'indice "i" restituisce 0 (non c'è il resto), il numero non è primo
        // altrimenti restituisco true (è primo)
        for (int i = 3; i <= Math.sqrt(n); i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Indica se l'elenco corrente dei numeri primi di questo crivello ha ancora
     * un numero disponibile da elencare o se l'elenco è giunto al termine
     * perché sono già stati elencati tutti i numeri primi minori uguali alla
     * capacità. Se il metodo restituisce true, può essere fatta una ulteriore
     * chiamata al metodo nextPrime() per ottenere il numero successivo
     * nell'elenco. Se il metodo restituisce false non si potrà più chiamare il
     * metodo nextPrime() fino a quando l'elenco non viene fatto ripartire
     * tramite il metodo restartPrimeIteration().
     *
     * @return true se c'è ancora un numero primo nell'elenco dei numeri primi
     * di questo livello, false se sono già stati elencati tutti i
     * numeri primi di questo crivello.
     */
    public boolean hasNextPrime() {
        // se il numero corrente è nell'ultima posizione dell'array, non ci sono più numeri primi
        if (current == crivello.length - 1)
            return false;
        // altrimenti ci sono ancora numeri primi da elencare
        return crivello.length > current;
    }

    /**
     * Restituisce il prossimo numero primo in questo crivello nell'elenco
     * corrente. L'elenco parte sempre dal numero 2 e si interrompe non appena
     * il metodo hasNextPrime() diventa false. Il metodo lancia l'eccezione
     * IllegalStateException se si prova a chiedere il prossimo numero primo
     * quando l'elenco corrente è terminato. L'elenco può essere fatto ripartire
     * in qualsiasi momento chiamando il metodo restartPrimeIteration().
     *
     * @return il prossimo numero primo nell'elenco corrente
     * @throws IllegalStateException se l'elenco è terminato e non è stato
     *                               ancora fatto ripartire.
     */
    public int nextPrime() {
        // imposto un flag per determinare se un numero è primo o meno.
        boolean prime;
        if (!hasNextPrime())
            throw new IllegalStateException("Elenco terminato e non è stato fatto ripartire");
        // Cambio il numero corrente a 2 per controllare il prossimo numero primo.
        // 1 non è nè un numero primo nè un numero non primo.
        if (current == 1) {
            current++;
        }
        // aumento i indefinitamente fino a che non trovo il prossimo numero primo.
        for (int i = current; ; i++) {
            prime = true;
            // Divido "i" per un numero metà di "i" per controllare se è un numero primo.
            for (int j = 2; j <= i / 2; j++) {
                if (i % j == 0) {
                    prime = false;
                }
            }
            this.current++;
            // Se è un numero primo ritono il numero.
            if (prime) {
                return i;
            }
        }
    }

    /**
     * Fa ripartire da 2 l'elenco corrente dei numeri primi fino alla capacità
     * di questo crivello. Questo metodo può essere chiamato in qualsiasi
     * momento, anche se l'elenco corrente non è ancora terminato. L'effetto è
     * comunque di ricominciare da 2.
     */
    public void restartPrimeIteration() {
        this.current = 1;
    }
}






