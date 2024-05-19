package Mini_Progetto_3;



//ATTENZIONE: è vietato includere import a pacchetti che non siano della Java SE

import java.util.*;

/**
 * Classe singoletto che implementa l'algoritmo di Prim per trovare un Minimum
 * Spanning Tree di un grafo non orientato, pesato e con pesi non negativi.
 * 
 * L'algoritmo richiede l'uso di una coda di min priorità tra i nodi che può
 * essere realizzata con una semplice ArrayList (non c'è bisogno di ottimizzare
 * le operazioni di inserimento, di estrazione del minimo, o di decremento della
 * priorità).
 * 
 * Si possono usare i colori dei nodi per registrare la scoperta e la visita
 * effettuata dei nodi.
 * 
 * @author Luca Tesei (template) **Implementation: MARCO TORQUATI - marco.torquati@studenti.unicam.it
 * 
 * @param <L>
 *                tipo delle etichette dei nodi del grafo
 *
 */
public class PrimMSP<L> {
    private List<GraphNode<L>> queue;

    /*
     * In particolare: si deve usare una coda con priorità che può semplicemente
     * essere realizzata con una List<GraphNode<L>> e si deve mantenere un
     * insieme dei nodi già visitati
     */

    /**
     * Crea un nuovo algoritmo e inizializza la coda di priorità con una coda
     * vuota.
     */
    public PrimMSP() {
        this.queue = new ArrayList<>();
    }

    /**
     * Utilizza l'algoritmo goloso di Prim per trovare un albero di copertura
     * minimo in un grafo non orientato e pesato, con pesi degli archi non
     * negativi. Dopo l'esecuzione del metodo nei nodi del grafo il campo
     * previous deve contenere un puntatore a un nodo in accordo all'albero di
     * copertura minimo calcolato, la cui radice è il nodo sorgente passato.
     * 
     * @param g
     *              un grafo non orientato, pesato, con pesi non negativi
     * @param s
     *              il nodo del grafo g sorgente, cioè da cui parte il calcolo
     *              dell'albero di copertura minimo. Tale nodo sarà la radice
     *              dell'albero di copertura trovato
     * 
     * @throw NullPointerException se il grafo g o il nodo sorgente s sono nulli
     * @throw IllegalArgumentException se il nodo sorgente s non esiste in g
     * @throw IllegalArgumentException se il grafo g è orientato, non pesato o
     *        con pesi negativi
     */
    public void computeMSP(Graph<L> g, GraphNode<L> s) {
        // se "g" o "s" sono nulli lancio l'eccezione
        if(g == null || s == null) throw new NullPointerException("Tentativo di inserire elementi nulli");
        // se il nodo non appartiene al grafo lancio l'eccezione
        if(g.getNode(s) == null) throw new IllegalArgumentException("Il nodo non appartiene al grafo");
        // se il grafo è orientato lancio l'eccezione
        if(g.isDirected()) throw new IllegalArgumentException("Il Grafo è orientato");

        // scorro tutta la lista dei nodi
        for(GraphNode<L> node: g.getNodes()) {
            // imposto i colori dei nodi a bianco
            node.setColor(GraphNode.COLOR_WHITE);
            // imposto la distanza a infinito
            node.setFloatingPointDistance(Double.POSITIVE_INFINITY);
            // imposto il precedente a null
            node.setPrevious(null);
            queue.add(node);
        }

        s.setFloatingPointDistance(0);

        // finchè la coda non è vuota
        while(!this.queue.isEmpty()) {
            // seleziono il nodo u con priorità minima e imposto il colore a nero
            GraphNode<L> u = extractMin();
            u.setColor(GraphNode.COLOR_BLACK);
            // scorro tutti i nodi adiacenti
            for(GraphNode<L> v : g.getAdjacentNodesOf(u)) {
                // se il grafo non è pesato lancio l'eccezione
                if(!g.getEdge(u, v).hasWeight()) throw new IllegalArgumentException("Il grafo non è pesato");
                // se il grafo ha pesi negativi lancio l'eccezione
                if(g.getEdge(u, v).getWeight() < 0) throw new IllegalArgumentException("Il grafo ha pesi negativi");
                // se v è nella coda e il valore della sua distanza (getFloatingPointDistance()) è maggiore del
                // peso fra l'arco u e v, cambio il floatingDistance in accordo
                if(this.queue.contains(v) && g.getEdge(u, v).getWeight() < v.getFloatingPointDistance()) {
                    v.setFloatingPointDistance(g.getEdge(u, v).getWeight());
                    v.setPrevious(u);
                    // lo coloro di nero
                    v.setColor(GraphNode.COLOR_BLACK);
                }
            }
        }

    }

    // metodo privato per estrarre il minimo
    private GraphNode<L> extractMin() {
        // parto con quello in posizione 0
        GraphNode<L> min = this.queue.get(0);
        // scorro
        for(int i = 1; i < this.queue.size(); i++) {
            // se la distanza dell'elemento i è più piccola del minimo
            if(this.queue.get(i).getFloatingPointDistance() < min.getFloatingPointDistance())
                // questo elemento sarà il nuovo minimo
                min = this.queue.get(i);
        }
        // lo estraggo
        this.queue.remove(min);
        // ritorno "min"
        return min;
    }
}
