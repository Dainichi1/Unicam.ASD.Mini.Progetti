package Mini_Progetto_1;

/**
 * Un oggetto di questa classe rappresenta una mensola su cui possono essere
 * appoggiati degli oggetti. Tali oggetti possono essere di diverso tipo, ma
 * tutti implementano l'interface ShelfItem. Un oggetto non può essere
 * appoggiato sulla mensola se ha lunghezza o larghezza che eccedono quelle
 * della mensola stessa. La mensola può contenere un numero non precisato di
 * oggetti, ma ad un certo punto non si possono appoggiare oggetti la cui
 * superficie occupata o il cui peso fanno eccedere la massima superficie
 * occupabile o il massimo peso sostenibile definiti nel costruttore della
 * mensola.
 * 
 * @author Luca Tesei (template) // Marco, Torquati
 *  marco.torquati@studenti.unicam.it (implementazione)
 *
 */
public class Shelf {
    /*
     * Dimensione iniziale dell'array items. Quando non è più sufficiente
     * l'array deve essere raddoppiato, anche più volte se necessario.
     */
    private final int INITIAL_SIZE = 5;

    /*
     * massima lunghezza di un oggetto che può essere appoggiato sulla mensola
     * in cm
     */
    private final double maxLength;

    /*
     * massima larghezza di un oggetto che può essere appoggiato sulla mensola
     * in cm
     */
    private final double maxWidth;

    /*
     * massima superficie occupabile della mensola in cm^2
     */
    private final double maxOccupableSurface;

    /*
     * massimo peso sostenibile dalla mensola in grammi
     */
    private final double maxTotalWeight;

    /*
     * array contenente tutti gli oggetti attualmente poggiati sulla mensola. In
     * caso di necessità viene raddoppiato nel momento che si poggia un nuovo
     * oggetto che fa superare la capacità dell'array.
     */
    private ShelfItem[] items;

    /*
     * variabile che indica il numero corrente di caselle nell'array che sono
     * occupate
     */
    private int numberOfItems;



    /**
     * Costruisce una mensola con le sue caratteristiche. All'inizio nessun
     * oggetto è posato sulla mensola.
     *
     * @param maxLength           lunghezza massima di un oggetto
     *                            appoggiabile in cm
     * @param maxWidth            larghezza massima di un oggetto
     *                            appoggiabile in cm
     * @param maxOccupableSurface massima superficie occupabile di questa
     *                            mensola in cm^2
     * @param maxTotalWeight      massimo peso sostenibile da questa mensola
     *                            in grammi
     */
    public Shelf(double maxLength, double maxWidth, double maxOccupableSurface,
                 double maxTotalWeight) {
        this.maxLength = maxLength;
        this.maxWidth = maxWidth;
        this.maxOccupableSurface = maxOccupableSurface;
        this.maxTotalWeight = maxTotalWeight;
        this.items = new ShelfItem[INITIAL_SIZE];
        this.numberOfItems = 0;
    }

    /**
     * Aggiunge un nuovo oggetto sulla mensola. Qualora non ci sia più spazio
     * nell'array che contiene gli oggetti correnti, tale array viene
     * raddoppiato per fare spazio al nuovo oggetto.
     *
     * @param i l'oggetto da appoggiare
     * @return true se l'oggetto è stato inserito, false se è già presente
     * @throws IllegalArgumentException se il peso dell'oggetto farebbe
     *                                  superare il massimo peso consentito
     *                                  oopure se la superficie dell'oggetto
     *                                  farebbe superare la massima
     *                                  superficie occupabile consentita,
     *                                  oppure se la lunghezza o larghezza
     *                                  dell'oggetto superano quelle massime
     *                                  consentite
     * @throws NullPointerException     se l'oggetto passato è null
     */
    public boolean addItem(ShelfItem i) {
        // se l'oggetto passato è null lancio l'eccezione
        if (i == null)
            throw new NullPointerException("Tentativo di aggiungere un oggetto null");
        // se la lunghezza e la larghezza dell'oggetto che provo ad aggiungere superano quelle
        // massime, lancio l'eccezione
        if (i.getLength() > maxLength && i.getWidth() > maxWidth)
            throw new IllegalArgumentException("La lunghezza e la larghezza dell'oggetto superano le misure massime consentite");
        // se la lunghezza dell'oggetto che provo ad aggiungere supera quella
        // massima, lancio l'eccezione
        if (i.getLength() > maxLength)
            throw new IllegalArgumentException("La lunghezza eccede il massimo consentito");
        // se la larghezza dell'oggetto che provo ad aggiungere supera quella
        // massima, lancio l'eccezione
        if (i.getWidth() > maxWidth)
            throw new IllegalArgumentException("La larghezza eccede il massimo consentito");
        // se il peso dell'oggetto che provo ad aggiungere supera il peso massimo, lancio
        // l'eccezione
        if (i.getWeight() > maxTotalWeight)
            throw new IllegalArgumentException("Il peso eccede il massimo consentito");
        // se la superficie occupata dell'oggetto che provo ad aggiungere supera quella massima
        // lancio l'eccezione
        if (i.getOccupiedSurface() > maxOccupableSurface)
            throw new IllegalArgumentException("La superfice supera il massimo consentito");
        // se ho degli oggetti nello scaffale che non superano la superficie massima permessa,
        // ne aggiungo un altro e così facendo supero il massimo permesso, lancio l'eccezione
        if (getCurrentTotalOccupiedSurface() + i.getOccupiedSurface() > maxOccupableSurface)
            throw new IllegalArgumentException("Superficie eccessiva");
        // se ho degli oggetti nello scaffale che non superano il peso totale permesso,
        // ne aggiungo un altro e così facendo suepero il massimo permesso, lancio l'eccezione
        if (getCurrentTotalWeight() + i.getWeight() > maxTotalWeight)
            throw new IllegalArgumentException("Peso eccessivo");

        // controllo se l'oggetto è già stato inserito
        for (int g = 0; g < this.numberOfItems; g++)
            // l'oggetto è già presente, non lo aggiungo.
            if (this.items[g].equals(i))
                return false;
        // l'oggetto non è presente e controllo se c'è spazio
        if (this.numberOfItems < this.items.length) {
            // c'è ancora spzio. aggiungo l'oggetto
            this.items[this.numberOfItems] = i;
            this.numberOfItems++;
        } else {
            // se non c'è più spazio raddoppio l'array
            this.items = (ShelfItem[]) raddoppiaArray(this.items);
            // dopo che ho raddoppiato l'array c'è di nuovo spazio e posso inserire un nuovo oggetto
            this.items[this.numberOfItems] = i;
            this.numberOfItems++;
        }
        return true;
    }

    /**
     * Cerca se è presente un oggetto sulla mensola. La ricerca utilizza il
     * metodo equals della classe dell'oggetto.
     *
     * @param i un oggetto per cercare sulla mensola un oggetto uguale a i
     * @return null se sulla mensola non c'è nessun oggetto uguale a i,
     * altrimenti l'oggetto x che si trova sulla mensola tale che
     * i.equals(x) == true
     * @throws NullPointerException se l'oggetto passato è null
     */
    public ShelfItem search(ShelfItem i) {
        // se l'oggetto passato è null, lancio l'eccezione
        if (i == null)
            throw new NullPointerException("Oggetto passato è null");
        // scorro gli oggettti presenti
        for (int o = 0; o < numberOfItems; o++) {
            // se l'oggetto nella posizione o-esima e una "round lamp"
            if (items[o] instanceof RoundLamp ) {
                // se la "round lamp" che passo come oggetto da cercare è uguale alla "round lamp"
                // nello scaffale, ritorno le caratteristiche della "round lamp"
                if (i.equals(items[o])) {
                    RoundLamp search = new RoundLamp(((RoundLamp) items[o]).getDiameter(), (items[o]).getWeight(),
                            ((RoundLamp) items[o]).getName(), ((RoundLamp) items[o]).getBrandName());
                    return search;
                }
            }
        }
        // scorro gli oggetti presenti
        for (int o = 0; o < numberOfItems; o++) {
            // se l'oggetto nella posizione o-esima e un "book"
            if (items[o] instanceof Book ) {
                // se il "book" che passo come oggetto da cercare è uguale al "book" nello scaffale
                // ritorno il "book" passato
                if (i.equals(items[o])) {
                    return i;
                }
            }
        }
        // se non c'è nessun oggetto uguale a "i" ritorno null
        return null;
    }


    /**
     * @return il numero attuale di oggetti appoggiati sulla mensola
     */
    public int getNumberOfItems() {
        return this.numberOfItems;
    }

    /*
     * protected, per solo scopo di JUnit testing
     */
    protected ShelfItem[] getItems() {
        return this.items;
    }

    /**
     * @return the currentTotalWeight
     */
    public double getCurrentTotalWeight() {
        double total = 0.0;
        // scorro gli oggetti presenti nello scaffale e sommo il loro peso per ottenere il peso
        // totale attuale
        for (int i = 0; i < numberOfItems; i++)
            total += items[i].getWeight();
        return total;
    }

    /**
     * @return the currentTotalOccupiedSurface
     */
    public double getCurrentTotalOccupiedSurface() {
        double total = 0.0;
        // scorro gli oggetti presenti nello scaffale e sommo il loro peso per ottenere la superficie occupata
        // totale attuale
        for (int i = 0; i < numberOfItems; i++)
            total += items[i].getOccupiedSurface();
        return total;
    }

    /**
     * @return the maxLength
     */
    public double getMaxLength() {
        return maxLength;
    }

    /**
     * @return the maxWidth
     */
    public double getMaxWidth() {
        return maxWidth;
    }

    /**
     * @return the maxOccupableSurface
     */
    public double getMaxOccupableSurface() {
        return maxOccupableSurface;
    }

    /**
     * @return the maxTotalWeight
     */
    public double getMaxTotalWeight() {
        return maxTotalWeight;
    }

    // Con questo metodo raddoppio l'array ShelfItem
    private Object[] raddoppiaArray (Object[] array) {
        if (array == null)
            throw new NullPointerException("Tentativo di raddoppiare un array nullo");
        // creo un nuovo array di lunghezza doppia di quello attuale
        ShelfItem[] array2 = new ShelfItem[array.length * 2];
                // scorro la lunghezza dell'array attuale e copio gli elementi presenti nel nuovo array
                for (int i = 0; i < array.length; i++) {
                    array2[i] = (ShelfItem) array[i];
                }
                return array2;
    }
}
