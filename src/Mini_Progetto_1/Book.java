/**
 * 
 */
package Mini_Progetto_1;

/**
 * Un oggetto di questa classe rappresenta un libro. Oltre le informazioni
 * tipiche ci sono la lunghezza, la larghezza e il peso. Queste servono per
 * implementare i metodi dell'interfaccia ShelfItem. Un libro è identificato dal
 * suo codice ISBN.
 * 
 * @author Luca Tesei (template) // Marco, Torquati
 * marco.torquati@studenti.unicam.it (implementazione)
 *
 */
public class Book implements ShelfItem {
    private final String author;

    private final String title;

    private final int year;

    private final String publisher;

    private final String iSBN;

    private final double length;

    private final double width;

    private final double weight;


    /**
     * @param author
     *                      stringa contenente l'autore
     * @param title
     *                      stringa contente il titolo
     * @param year
     *                      anno di pubblicazione
     * @param publisher
     *                      stringa con il nome dell'editore
     * @param iSBN
     *                      codice univoco del libro
     * @param length
     *                      lunghezza in cm
     * @param width
     *                      larghezza in cm
     * @param weight
     *                      peso in grammi
     */
    public Book(String author, String title, int year, String publisher,
            String iSBN, double length, double width, double weight) {
        this.author = author;
        this.title = title;
        this.year = year;
        this.publisher = publisher;
        this.iSBN = iSBN;
        this.length = length;
        this.width = width;
        this.weight = weight;
    }

    @Override
    public double getLength() {
        return this.length;
    }

    @Override
    public double getWidth() {
        return this.width;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @return the publisher
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * @return the iSBN
     */
    public String getiSBN() {
        return iSBN;
    }

    /*
     * Due libri sono uguali se e solo se hanno lo stesso codice ISBN.
     */
    @Override
    public boolean equals(Object obj) {
        // se è la stessa zona di memoria di obj
        if (this == obj)
            return true;
        // se obj è null
        if (obj == null)
            return false;
        // controllo se obj è un Book o meno
        if (!(obj instanceof Book))
            return false;
        // faccio un cast esplicito
        Book other = (Book) obj;
        // i due oggetti a questo punto sono uguali

        return (iSBN.equals(other.iSBN));
    }

    /*
     * Hashcode definito in base all'ISBN, per compatibilità con equals.
     */
    @Override
    public int hashCode() {
        int hash = 1 + 31 * iSBN.hashCode();

        return hash;
    }


}
