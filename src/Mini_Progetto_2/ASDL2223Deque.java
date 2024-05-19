/**
 * 
 */
package Mini_Progetto_2;

import java.util.*;

/**
 * Implementation of the Java SE Double-ended Queue (Deque) interface
 * (<code>java.util.Deque</code>) based on a double linked list. This deque does
 * not have capacity restrictions, i.e., it is always possible to insert new
 * elements and the number of elements is unbound. Duplicated elements are
 * permitted while <code>null</code> elements are not permitted. Being
 * <code>Deque</code> a sub-interface of
 * <code>Queue<code>, this class can be used also as an implementaion of a <code>Queue</code>
 * and of a <code>Stack</code>.
 * 
 * The following operations are not supported:
 * <ul>
 * <li><code>public <T> T[] toArray(T[] a)</code></li>
 * <li><code>public boolean removeAll(Collection<?> c)</code></li>
 * <li><code>public boolean retainAll(Collection<?> c)</code></li>
 * <li><code>public boolean removeFirstOccurrence(Object o)</code></li>
 * <li><code>public boolean removeLastOccurrence(Object o)</code></li>
 * </ul>
 * 
 * @author Template: Luca Tesei, Implementation: MARCO TORQUATI - marco.torquati@studenti.unicam.it
 *
 *
 */
public class ASDL2223Deque<E> implements Deque<E> {

    /*
     * Current number of elements in this deque
     */
    private int size;

    /*
     * Pointer to the first element of the double-linked list used to implement
     * this deque
     */
    private Node<E> first;

    /*
     * Pointer to the last element of the double-linked list used to implement
     * this deque
     */
    private Node<E> last;

    // contatore modifiche
    private int nModifiche;


    /**
     * Constructs an empty deque.
     */
    public ASDL2223Deque() {
        this.size = 0;
        this.first = null;
        this.last = null;
        this.nModifiche = 0;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Object[] toArray() {
        // inizializzo un array e un contatore a 0
        Object[] array = new Object[this.size];
        int i = 0;
        // tramite il "for each" inserisco nell'array tutti gli elementi della "Deque"
        for (E el : this) {
            array[i] = el;
            i++;
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException(
                "This class does not implement this service.");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        // controllo la collection e se ha elementi nulli o è vuota lancio l'eccezione
        if (c == null)
            throw new NullPointerException("La collection ha valori nulli o è vuota");
        // tramite il "for each" controllo se gli elementi di "c" sono contenuti in "e"
        for (Object e : c)
            if (!contains(e))
                return false;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        //Controllo della collection
        if (c.contains(null) || c.isEmpty()) {
            throw new NullPointerException("Collection con valori nulli o vuota.");
        }
        //Ciclo con un "for each" e inserisco ogni elemento della collection con il metodo "offerLast"
        for (E e : c) {
            this.offerLast(e);
        }
        // ritorno "true" perchè la collection è stata modificata tramite il metodo "addAll"
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException(
                "This class does not implement this service.");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException(
                "This class does not implement this service.");
    }

    @Override
    public void clear() {
        //Cancello i collegamenti
        Node<E> e = first;
        Node<E> next;
        while (e != null) {
            next = e.next;
            e.item = null;
            e.next = null;
            e.prev = null;
            e = next;
        }
        first = null;
        last = null;
        size = 0;
        nModifiche++;

    }

    @Override
    public void addFirst(E e) {
        if(e == null) {
            throw new NullPointerException("Tentativo di inserire un elemento nullo");
        }
        // copio il primo nodo
        // creo il nuovo nodo da mettere in testa
        Node<E> n = first;
        Node<E> addNode = new Node<>(null, e, n);
        first = addNode;
        //aggiungo nodo se non c'è un prossimo nodo
        if (n == null) {
            last = addNode;
        } else {
            //aggiungo nodo se c'è un prossimo nodo (
            n.prev = addNode;
        }
        //aumento la dimensione e il numero delle modifiche
        size++;
        nModifiche++;
    }

    @Override
    public void addLast(E e) {
        if(e == null) {
            throw new NullPointerException("Tentativo di inserire un elemento nullo");
        }
        //copio l'ultimo nodo
        //creo il nuovo nodo da mettere in coda
        Node<E> n = last;
        Node<E> newNode = new Node<>(n, e, null);
        last = newNode;

        //aggiungo nodo se non c'è un nodo prima
        if (n == null) {
            first = newNode;
        } else { //aggiungo nodo se c'è un nodo dopo successivo
            n.next = newNode;
        }
        //aumento la dimensione e il numero delle modifiche
        size++;
        nModifiche++;
    }

    @Override
    public boolean offerFirst(E e) {
        if(e == null)
            throw new NullPointerException("Tentativo di inserire un elemento nullo");
        // equivalenti
        addFirst(e);
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        if(e == null)
            throw new NullPointerException("Tentativo di inserire un elemento nullo");
        // equivalenti
        addLast(e);
        return true;

    }

    @Override
    public E removeFirst() {
        //controllo che first non sia nullo
        if (this.first == null)
            throw new NoSuchElementException("Lista vuota");
        //copio l'ultimo nodo e creo il nuovo nodo
        E element = this.first.item;
        Node<E> next = this.first.next;
        this.first.item = null;
        this.first.next = null;
        this.first = next;
        //rimozione nodo se non c'è un nodo precendente
        if (next == null) {
            last = null;
        } else { //rimozione nodo se è presente un successivo
            next.prev = null;
        }
        //diminuisco size e aumento modifiche
        size--;
        nModifiche++;
        //ritorno l'elemento rimosso
        return element;
    }

    @Override
    public E removeLast() {
        //controllo che first non sia nullo
        if (this.last == null)
            throw new NoSuchElementException("Lista vuota");
        //copio il primo nodo e creo il nuovo nodo
        E element = this.last.item;
        Node<E> prev = this.last.prev;
        this.last.item = null;
        this.last.prev = null;
        this.last = prev;
        //rimozione nodo se non c'è un nodo precendente
        if (prev == null) {
            first = null;
        } else { //rimozione nodo se è presente un successivo
            prev.next = null;
        }
        //diminuisco size e aumento modifiche
        size--;
        nModifiche++;
        //ritorno l'elemento rimosso
        return element;
    }

    @Override
    public E pollFirst() {
        //controllo che la deque non sia vuota
        if (this.isEmpty()) {
            return null;
        }
        //Sono equivalenti
        return removeFirst();
    }

    @Override
    public E pollLast() {
        //controllo che la deque non sia vuota
        if (this.isEmpty()) {
            return null;
        }
        //Sono equivalenti
        return removeLast();
    }

    @Override
    public E getFirst() {
        //Se la deque è vuota lancio l'eccezione
        if (this.isEmpty()) {
            throw new NoSuchElementException("Deque vuota");
        }
        // altrimenti ritorno il primo elemento
        return this.first.item;
    }

    @Override
    public E getLast() {
        //Se la deque è vuota lancio l'eccezione
        if (this.isEmpty()) {
            throw new NoSuchElementException("Deque vuota");
        }
        // altrimenti ritorno l'ultimo elemento
        return this.last.item;
    }


    @Override
    public E peekFirst() {
        // se la lista è vuota non ritorna nulla.
        if (this.isEmpty())
            return null;
        // altrimenti ritorna il primo elemento
        else return this.first.item;
    }

    @Override
    public E peekLast() {
        // se la lista è vuota non ritorna nulla.
        if (this.isEmpty())
            return null;
            // altrimenti ritorna l'ultimo elemento
        else return this.last.item;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        throw new UnsupportedOperationException(
                "This class does not implement this service.");
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        throw new UnsupportedOperationException(
                "This class does not implement this service.");
    }

    @Override
    public boolean add(E e) {
        //equivalenti
        return offerLast(e);
    }

    @Override
    public boolean offer(E e) {
        //equivalenti
        return offerLast(e);
    }

    @Override
    public E remove() {
        //equivalenti
        return removeFirst();
    }

    @Override
    public E poll() {
        //equivalenti
        return pollFirst();
    }


    @Override
    public E element() {
        //equivalenti
        return getFirst();
    }

    @Override
    public E peek() {
        //equivalenti
        return peekFirst();
    }


    @Override
    public void push(E e) {
        //Sono equivalenti ma non ritorna un boolean
        addFirst(e);
    }


    @Override
    public E pop() {
        //equivalenti
        return removeFirst();
    }



    @Override
    public boolean remove(Object o) {
        //Controllo dell'oggetto
        if (o == null) {
            throw new NullPointerException("Tentativo di inserire un oggetto nullo.");
        }
        //Controllo che l'oggetto sia presente
        if (!this.contains(o)) {
            return false;
        }
        Node<E> e = first;
        boolean rimosso = false;
        //ricerca elemento da eliminare
        while (!rimosso) {
            if (e.item.equals(o)) {
                //elemento nella posizione "first"
                if (e.prev == null) {
                    pollFirst();
                    //elemento in posizione "last"
                } else if (e.next == null) {
                    pollLast();
                } else {
                    //elemento in mezzo alla lista
                    e.prev.next = e.next;
                    e.next.prev = e.prev;
                    e.prev = null;
                    e.next = null;
                    this.size--;
                    this.nModifiche++;
                }
                rimosso = true;
            }
            e = e.next;
        }
        return true;
    }

    @Override
    public boolean contains(Object o) {
        //se l'elemento passato è null lancio l'eccezione
        if (o == null)
            throw new NullPointerException("Tentativo di inserire un oggetto null");
        Node<E> n = this.first;
        //Ricerca elemento uguale a o
        while (n != null) {
            if (o.equals(n.item)) {
                return true;
            } else
                n = n.next;
        }  return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    /*
     * Class for representing the nodes of the double-linked list used to
     * implement this deque. The class and its members/methods are protected
     * instead of private only for JUnit testing purposes.
     */
    protected static class Node<E> {
        protected E item;

        protected Node<E> next;

        protected Node<E> prev;

        protected Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    /*
     * Class for implementing an iterator for this deque. The iterator is
     * fail-fast: it detects if during the iteration a modification to the
     * original deque was done and, if so, it launches a
     * <code>ConcurrentModificationException</code> as soon as a call to the
     * method <code>next()</code> is done.
     */
    private class Itr implements Iterator<E> {

        private Node<E> lastReturned;
        private final int modifiche;
        Itr() {
            //Inizializzazione Iteratore
            this.lastReturned = null;
            this.modifiche = ASDL2223Deque.this.nModifiche;
        }

        public boolean hasNext() {
            //stato iniziale dell'iterazione
            if (this.lastReturned == null) {
                return ASDL2223Deque.this.first != null;
            } else {
                //è stato fatto almeno un next
                return this.lastReturned.next != null;
            }
        }

        public E next() {
            // REMEMBER that the iterator must be fail-safe: if
            // the Deque has been modified by a method of the main class the
            // first attempt to call next() must throw a
            // ConcurrentModificationException
            //compare numero modifiche per confermare il "fail-safe"
            if (this.modifiche != ASDL2223Deque.this.nModifiche) {
                throw new ConcurrentModificationException("C'è stata una modifica.");
            }
            //controllo che ci sia un next
            if (!this.hasNext()) {
                throw new NoSuchElementException("Non esiste il prossimo elemento");
            }
            // c'è un elemento di cui fare next
            // aggiorno lastReturned e restituisco l'elemento next
            if (lastReturned == null) {
                // sono all'inizio e la lista non è vuota
                lastReturned = ASDL2223Deque.this.first;
                return ASDL2223Deque.this.first.item;
            } else {
                //non sono all'inizio, ma ci sono ancora altri elementi
                lastReturned = lastReturned.next;
                //ritorno next item
                return lastReturned.item;
            }
        }
    }

    @Override
    public Iterator<E> descendingIterator() {
        return new DescItr();
    }

    /*
     * Class for implementing a descendign iterator for this deque. The iterator
     * is fail-fast: it detects if during the iteration a modification to the
     * original deque was done and, if so, it launches a
     * <code>ConcurrentModificationException</code> as soon as a call to the
     * method <code>next()</code> is done.
     */
    private class DescItr implements Iterator<E> {
        private Node<E> lastReturned;

        private final int modifiche;
        DescItr() {
            this.lastReturned = null;
            this.modifiche = ASDL2223Deque.this.nModifiche;
        }

        public boolean hasNext() {
        //stato iniziale dell'iterazione
            if (this.lastReturned == null) {
                return ASDL2223Deque.this.last != null;
            } else { //è stato fatto almeno un next
                return this.lastReturned.prev != null;
            }
        }

        public E next() {
            // REMEMBER that the iterator must be fail-safe: if
            // the Deque has been modified by a method of the main class the
            // first attempt to call next() must throw a
            // ConcurrentModificationException
            //compare numero modifiche per confermare il "fail-safe"
            if (this.modifiche != ASDL2223Deque.this.nModifiche) {
                throw new ConcurrentModificationException("C'è stata una modifica.");
            }
            //controllo che ci sia un next
            if (!this.hasNext()) {
                throw new NoSuchElementException("Non esiste il prossimo elemento");
            }

            //stato iniziale
            if (lastReturned == null) {
                lastReturned = ASDL2223Deque.this.last;
            } else {
                //l'iteratore si è mosso almeno una volta
                lastReturned = lastReturned.prev;
            }
            //ritorno l'elemento precedente
            return lastReturned.item;
        }

    }

    /*
     * This method is only for JUnit testing purposes.
     */
    protected Node<E> getFirstNode() {
        return this.first;
    }

    /*
     * This method is only for JUnit testing purposes.
     */
    protected Node<E> getLastNode() {
        return this.last;
    }
}
