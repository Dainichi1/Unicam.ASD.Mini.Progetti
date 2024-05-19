package Mini_Progetto_2;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Class that provides an implementation of a "dynamic" min-priority queue based
 * on a ternary heap. "Dynamic" means that the priority of an element already
 * present in the queue may be decreased, so possibly this element may become
 * the new minumum element. The elements that can be inserted may be of any
 * class implementing the interface <code>PriorityQueueElement</code>. This
 * min-priority queue does not have capacity restrictions, i.e., it is always
 * possible to insert new elements and the number of elements is unbound.
 * Duplicated elements are permitted while <code>null</code> elements are not
 * permitted.
 * 
 * @author Template: Luca Tesei, Implementation: MARCO TORQUATI - marco.torquati@studenti.unicam.it
 *
 */
public class TernaryHeapMinPriorityQueue {

    /*
     * ArrayList for representing the ternary heap. Use all positions, including
     * position 0 (the JUnit tests will assume so). You have to adapt
     * child/parent indexing formulas consequently.
     */
    private ArrayList<PriorityQueueElement> heap;

    /**
     * Create an empty queue.
     */
    public TernaryHeapMinPriorityQueue() {
        this.heap = new ArrayList<PriorityQueueElement>();
    }

    /**
     * Return the current size of this queue.
     * 
     * @return the number of elements currently in this queue.
     */
    public int size() {
        return this.heap.size();
    }

    /**
     * Add an element to this min-priority queue. The current priority
     * associated with the element will be used to place it in the correct
     * position in the ternary heap. The handle of the element will also be set
     * accordingly.
     * 
     * @param element
     *                    the new element to add
     * @throws NullPointerException
     *                                  if the element passed is null
     */
    public void insert(PriorityQueueElement element) {
        // Se l' elemento passato è null lancio l'eccezione
        if (element == null)
            throw new NullPointerException("Tentativo di inserire un elemento null");
        // Inserisco l'elemento in base alla priorità
        this.heap.add(element);
        int i = this.heap.size() - 1;
        this.heap.get(i).setHandle(i);
        while ((i > 0) && (this.heap.get(this.parent(i)).getPriority() > this.heap.get(i).getPriority())) {
            // Scambio il figlio con il padre
            PriorityQueueElement temp = this.heap.get(i);
            this.heap.set(i, this.heap.get(this.parent(i)));
            this.heap.set(this.parent(i), temp);
            // i = handle del nuovo figlio
            this.heap.get(i).setHandle(i);
            // L' handle del nuovo padre è il padre del nuovo figlio
            this.heap.get(this.parent(i)).setHandle(this.parent(i));
            // L' indice successivo è quello del padre del nuovo padre
            i = this.parent(i);
        }
    }

    /**
     * Returns the current minimum element of this min-priority queue without
     * extracting it. This operation does not affect the ternary heap.
     * 
     * @return the current minimum element of this min-priority queue
     * 
     * @throws NoSuchElementException
     *                                    if this min-priority queue is empty
     */
    public PriorityQueueElement minimum() {
        // se l'heap è vuoto lancio l'eccezione
        if (heap.isEmpty())
            throw new NoSuchElementException("Lista vuota");
        // altrimenti restituisco l'elemento minimo
        return heap.get(0);
    }

    /**
     * Extract the current minimum element from this min-priority queue. The
     * ternary heap will be updated accordingly.
     * 
     * @return the current minimum element
     * @throws NoSuchElementException
     *                                    if this min-priority queue is empty
     */
    public PriorityQueueElement extractMinimum() {
        // Se il minHeap è vuoto
        if (this.heap.isEmpty())
            throw new NoSuchElementException("Il min-Heap è vuoto!");
        // Se c'è almeno un nodo il minimo è la radice
        PriorityQueueElement min = this.minimum();
        this.heap.set(0, this.heap.get(this.size() - 1));
        // La nuova radice ha handle 0
        this.heap.get(0).setHandle(0);
        this.heap.remove(this.size() - 1);
        this.heapify(0);
        return min;
    }
    /**
     * Decrease the priority associated to an element of this min-priority
     * queue. The position of the element in the ternary heap must be changed
     * accordingly. The changed element may become the minimum element. The
     * handle of the element will also be changed accordingly.
     * 
     * @param element
     *                        the element whose priority will be decreased, it
     *                        must currently be inside this min-priority queue
     * @param newPriority
     *                        the new priority to assign to the element
     * 
     * @throws NoSuchElementException
     *                                      if the element is not currently
     *                                      present in this min-priority queue
     * @throws IllegalArgumentException
     *                                      if the specified newPriority is not
     *                                      strictly less than the current
     *                                      priority of the element
     */
    public void decreasePriority(PriorityQueueElement element,
            double newPriority) {
        if (element == null) {
            throw new NullPointerException("Tentativo di inserire un elemento nullo");
        }
        boolean trovato = false;
        int indexElement = 0;
        //cerco l'elemento nel heap se c'è prendo l'indice
        while (indexElement < heap.size()) {
            if (this.heap.get(indexElement).getPriority() == element.getPriority()) {
                trovato = true;
                break;
            }
            indexElement++;
        }
        if (!trovato) {
            throw new NoSuchElementException("Elemento non presente");
        }
        //verifico la priorità
        if (!(newPriority < heap.get(indexElement).getPriority())) {
            throw new IllegalArgumentException("Priorità non abbastanza bassa");
        }
        //setto la nuova priorità
        this.heap.get(indexElement).setPriority(newPriority);
        //index del nodo dell'elemento
        int last = parent(indexElement);
        //Ricostruisco min-heap con il nuovo elemento
        for (int i = last; i >= 0; i--) {
            heapify(i);
        }
    }

    /**
     * Erase all the elements from this min-priority queue. After this operation
     * this min-priority queue is empty.
     */
    public void clear() {
        this.heap.clear();
    }

    // ritorna padre del nodo
    private int parent (int i) {
        return (i - 1) / 3;
    }

    // ritorna il figlio sinistro
    private int left (int i) {
        return 3 * i + 1;
    }

    // ritorna il figlio centrale
    private int center (int i) {
        return 3 * i + 2;
    }

    // ritorna il figlio destro
    private int right (int i) {
        return 3 * i + 3;
    }

    // metodo per scambiare 2 nodi
    private void swap (int a, int b) {
        PriorityQueueElement temp1;
        PriorityQueueElement temp2;

        // copio il primo elemento con l'handle del secondo elemento
        temp1 = this.heap.get(a);
        temp1.setHandle(b);
        // copio il secondo elemento con l'handle del primo elemento
        temp2 = this.heap.get(b);
        temp2.setHandle(a);
        // scambio
        this.heap.set(a,temp2);
        this.heap.set(b,temp1);
    }

    // metodo privato per ricostruire il min-heap
    private void heapify (int m) {
        // Se il minHeap è vuoto
        if (this.heap.isEmpty())
            return;
        int min = m;
        int left = left(m);
        int right = right(m);
        int center = center(m);
        // caso in cui l'elemento sx è minore di root
        if (left < heap.size() && Double.compare(heap.get(left).getPriority(),(heap.get(min).getPriority()))<0)
            //lo metto su min
            min = left(m);
        // caso in cui l'elemento dx è minore di root
        if (right < heap.size() && Double.compare(heap.get(right).getPriority(),(heap.get(min).getPriority()))<0)
            //metto su min
            min = right(m);
        // caso in cui l'elemento centro è minore di root
        if (center < heap.size() && Double.compare(heap.get(center).getPriority(),(heap.get(min).getPriority()))<0)
            //metto su min
            min = center(m);
        // se min è diverso da root
        if (min != m) {
            swap(m,min);
            heapify(min);
        }
    }
    /*
     * This method is only for JUnit testing purposes.
     */
    protected ArrayList<PriorityQueueElement> getTernaryHeap() {
        return this.heap;
    }
}
