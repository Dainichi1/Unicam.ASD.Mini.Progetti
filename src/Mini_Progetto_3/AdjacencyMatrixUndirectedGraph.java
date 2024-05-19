/**
 * 
 */
package Mini_Progetto_3;

import java.util.*;



// ATTENZIONE: è vietato includere import a pacchetti che non siano della Java SE

/**
 * Classe che implementa un grafo non orientato tramite matrice di adiacenza.
 * Non sono accettate etichette dei nodi null e non sono accettate etichette
 * duplicate nei nodi (che in quel caso sono lo stesso nodo).
 * 
 * I nodi sono indicizzati da 0 a nodeCoount() - 1 seguendo l'ordine del loro
 * inserimento (0 è l'indice del primo nodo inserito, 1 del secondo e così via)
 * e quindi in ogni istante la matrice di adiacenza ha dimensione nodeCount() *
 * nodeCount(). La matrice, sempre quadrata, deve quindi aumentare di dimensione
 * ad ogni inserimento di un nodo. Per questo non è rappresentata tramite array
 * ma tramite ArrayList.
 * 
 * Gli oggetti GraphNode<L>, cioè i nodi, sono memorizzati in una mappa che
 * associa ad ogni nodo l'indice assegnato in fase di inserimento. Il dominio
 * della mappa rappresenta quindi l'insieme dei nodi.
 * 
 * Gli archi sono memorizzati nella matrice di adiacenza. A differenza della
 * rappresentazione standard con matrice di adiacenza, la posizione i,j della
 * matrice non contiene un flag di presenza, ma è null se i nodi i e j non sono
 * collegati da un arco e contiene un oggetto della classe GraphEdge<L> se lo
 * sono. Tale oggetto rappresenta l'arco. Un oggetto uguale (secondo equals) e
 * con lo stesso peso (se gli archi sono pesati) deve essere presente nella
 * posizione j, i della matrice.
 * 
 * Questa classe non supporta i metodi di cancellazione di nodi e archi, ma
 * supporta tutti i metodi che usano indici, utilizzando l'indice assegnato a
 * ogni nodo in fase di inserimento.
 * 
 * @author Luca Tesei (template) **Implementation: MARCO TORQUATI - marco.torquati@studenti.unicam.it
 *
 *
 * 
 */
public class AdjacencyMatrixUndirectedGraph<L> extends Graph<L> {
    /*
     * Le seguenti variabili istanza sono protected al solo scopo di agevolare
     * il JUnit testing
     */

    /*
     * Insieme dei nodi e associazione di ogni nodo con il proprio indice nella
     * matrice di adiacenza
     */
    protected Map<GraphNode<L>, Integer> nodesIndex;
    //Set di archi:
    Set<GraphEdge<L>> arch = new HashSet<>();

    /*
     * Matrice di adiacenza, gli elementi sono null o oggetti della classe
     * GraphEdge<L>. L'uso di ArrayList permette alla matrice di aumentare di
     * dimensione gradualmente ad ogni inserimento di un nuovo nodo e di
     * ridimensionarsi se un nodo viene cancellato.
     */
    protected ArrayList<ArrayList<GraphEdge<L>>> matrix;


    /**
     * Crea un grafo vuoto.
     */
    public AdjacencyMatrixUndirectedGraph() {
        this.matrix = new ArrayList<ArrayList<GraphEdge<L>>>();
        this.nodesIndex = new HashMap<GraphNode<L>, Integer>();
    }

    @Override
    public int nodeCount() {
        return this.nodesIndex.size();
    }

    @Override
    public int edgeCount() {
        int nNodi = 0;
        Set<GraphNode<L>> nodi = this.nodesIndex.keySet();
        for (GraphNode<L> n : nodi) {
            // conto gli archi adiacenti ad ogni nodo
            nNodi = nNodi + this.getEdgesOf(n).size();
        }
        // divido per 2 perchè ho contato il doppio degli archi
        return nNodi/2;
    }

    @Override
    public void clear() {
        // utilizzo i costruttori inizializzati a 0
        this.matrix = new ArrayList<>();
        this.nodesIndex = new HashMap<>();
    }

    @Override
    public boolean isDirected() {
        // il grafo non è orientato
        return false;
    }

    /*
     * Gli indici dei nodi vanno assegnati nell'ordine di inserimento a partire
     * da zero
     */
    @Override
    public boolean addNode(GraphNode<L> node) {
        // se il nodo è null lancio l'eccezione
        if (node == null)
            throw new NullPointerException("Nodo nullo.");
        // se il nodo che voglio aggiungere è già presente ritorno false e non l'aggiungo
        if (nodesIndex.containsKey(node)) return false;
        // Se il nodo non esiste lo vado ad aggiungere nella prima posizione libera
        this.nodesIndex.put(node, this.nodeCount());
        // creo una nuova riga "null" da inserire
        ArrayList<GraphEdge<L>> newRow = new ArrayList<GraphEdge<L>>();
        for (int i = 0; i <= this.matrix.size(); i++){
            newRow.add(null);
        }
        //creo una colonna "null"
        for (ArrayList<GraphEdge<L>> row : this.matrix) {
            row.add(null);
        }
        //aggiungo la riga
        this.matrix.add(newRow);
        return true;
    }

    /*
     * Gli indici dei nodi vanno assegnati nell'ordine di inserimento a partire
     * da zero
     */
    @Override
    public boolean addNode(L label) {
        return addNode(new GraphNode<>(label));
    }

    /*
     * Gli indici dei nodi il cui valore sia maggiore dell'indice del nodo da
     * cancellare devono essere decrementati di uno dopo la cancellazione del
     * nodo
     */
    @Override
    public void removeNode(GraphNode<L> node) {
        // se il nodo è "nul" lancio l'eccezione
        if (node == null)
            throw new NullPointerException("Nodo Nullo!");
        // se il nodo non esiste lancio l'eccezione
        if (!this.getNodes().contains(node))
            throw new IllegalArgumentException("Il nodo non esiste!");
        int i = nodesIndex.remove(node);
        // scorro i nodi della mappa
        for (Map.Entry<GraphNode<L>, Integer> c : nodesIndex.entrySet()) {
            // controllo se "c" è maggiore di "i"
            if (c.getValue() > i) {
                int m = c.getValue() -1;
                // inserisco il nodo con il nuovo indice
                nodesIndex.put(c.getKey(), m);
            }
        }
        // rimuovo i
        matrix.remove(i);
        //Scorro la dimensione della mappa e aggiorno la matrice:
        for (int h = 0; h < nodesIndex.size(); h++) {
            matrix.get(h).remove(i);
        }
    }

    /*
     * Gli indici dei nodi il cui valore sia maggiore dell'indice del nodo da
     * cancellare devono essere decrementati di uno dopo la cancellazione del
     * nodo
     */
    @Override
    public void removeNode(L label) {
        removeNode(new GraphNode<>(label));
    }

    /*
     * Gli indici dei nodi il cui valore sia maggiore dell'indice del nodo da
     * cancellare devono essere decrementati di uno dopo la cancellazione del
     * nodo
     */
    @Override
    public void removeNode(int i) {
        // se "i" è minore di 0 lancio l'eccezione
        if (i < 0)
            throw new IndexOutOfBoundsException("Valore minore di 0");
        // se il valore di "i" e più grande del numero dei nodi lancio l'eccezione
        if (i > nodeCount() - 1)
            throw new IndexOutOfBoundsException("Valore errato");
        removeNode(getNode(i));
    }

    @Override
    public GraphNode<L> getNode(GraphNode<L> node) {
        // se il nodo è "null" lancio l'eccezione
        if (node == null)
            throw new NullPointerException("Nodo nullo");
        // scorro tutti i nodi presenti
        for (GraphNode<L> nodo : nodesIndex.keySet()) {
            // se il nodo già presente è uguale al nodo che ho inserito lo restituisco
            if (nodo.equals(node)) {
                return nodo;
            }
        }
        // altrimenti non ritorno nulla
        return null;
    }

    @Override
    public GraphNode<L> getNode(L label) {
        return getNode(new GraphNode<>(label));
    }

    @Override
    public GraphNode<L> getNode(int i) {
        // se il valore di "i" è minore di 0 o è più grande del numero dei nodi lancio l'eccezione
        if (i < 0 || i > this.matrix.size() - 1){
            throw new IndexOutOfBoundsException ("L'indice passato è errato");
        }
        // altrimenti scorro tutti i nodi e se il valore di "c" è uguale al valore di "i" lo ritorno
        for (GraphNode<L> c : this.nodesIndex.keySet()){
            if (this.nodesIndex.get(c) == i){
                return this.getNode(c);
            }
        }
        return null;
    }

    @Override
    public int getNodeIndexOf(GraphNode<L> node) {
        // se i l nodo è "null" lancio l'eccezione
        if (node == null)
            throw new NullPointerException("Nodo nullo");
        // se il nodo non è presente lancio l'eccezione
        if (!nodesIndex.containsKey(node)) {
            throw new IllegalArgumentException("Il nodo passato non esiste in questo grafo.");
        }
        // altrimenti lo ritorno
        return nodesIndex.get(node);
    }

    @Override
    public int getNodeIndexOf(L label) {
        return getNodeIndexOf(new GraphNode<>(label));
    }

    @Override
    public Set<GraphNode<L>> getNodes() {
        return nodesIndex.keySet();
    }

    @Override
    public boolean addEdge(GraphEdge<L> edge) {
        //Se l'arco è "null" lancio l'eccezione
        if (edge == null)
            throw new NullPointerException("L'arco passato è nullo.");

        if (!nodesIndex.containsKey(edge.getNode1()) || !nodesIndex.containsKey(edge.getNode2())) throw new
                IllegalArgumentException("Uno dei due nodi dell'arco in addEdge non esiste");
        // se l'arco è orientato lancio l'eccezione
        if (edge.isDirected())
            throw new IllegalArgumentException("L'arco è orientato.");
        //Assegno a due variabili (node1 e node2) il corrispettivo nodo dell'arco
        int node1 = nodesIndex.get(edge.getNode1());
        int node2 = nodesIndex.get(edge.getNode2());
        // Controllo se l'arco è già presente
        if (edge.equals(matrix.get(node1).get(node2)) || edge.equals(matrix.get(node2).get(node1))) {
            return false;
        }
        // non è presente quindi lo aggiungo
        matrix.get(node1).set(node2, edge);
        matrix.get(node2).set(node1, edge);
        return true;
    }

    @Override
    public boolean addEdge(GraphNode<L> node1, GraphNode<L> node2) {
        // se i due nodi sono null lancio l'eccezione
        if (node1 == null || node2 == null)
            throw new NullPointerException("tentativo di inserire nodi null");
        return addEdge(new GraphEdge<>(node1, node2, isDirected()));
    }

    @Override
    public boolean addWeightedEdge(GraphNode<L> node1, GraphNode<L> node2,
            double weight) {
        // se i due nodi sono null lancio l'eccezione
        if (node1 == null || node2 == null)
            throw new NullPointerException("tentativo di inserire nodi null");
        return addEdge(new GraphEdge<L>(node1, node2, isDirected(), weight));
    }

    @Override
    public boolean addEdge(L label1, L label2) {
        // se i due nodi sono null lancio l'eccezione
        if (label1 == null || label2 == null)
            throw new NullPointerException("tentativo di inserire etichette null");
        return addEdge((new GraphEdge<L>(new GraphNode<>(label1), new GraphNode<>(label2), false)));
    }

    @Override
    public boolean addWeightedEdge(L label1, L label2, double weight) {
        // se i due nodi sono null lancio l'eccezione
        if (label1 == null || label2 == null)
            throw new NullPointerException("tentativo di inserire etichette null");
        return addEdge((new GraphEdge<L>(new GraphNode<>(label1), new GraphNode<>(label2), false, weight)));
    }

    @Override
    public boolean addEdge(int i, int j) {
        return addWeightedEdge(i, j, Double.NaN);
    }

    @Override
    public boolean addWeightedEdge(int i, int j, double weight) {
        // se il valore di "i" o il valore di "j" è minore di 0 lancio l'eccezione
        if (i < 0 || j < 0)
            throw new IndexOutOfBoundsException("Valore minore di 0.");
        if (i > nodeCount() || j > nodeCount())
            throw new IndexOutOfBoundsException("Valore maggiore " + nodeCount() + ".");
        // utilizzo il metodo addWeightedEdge()
        return this.addWeightedEdge(this.getNode(i), this.getNode(j), weight);
    }

    @Override
    public void removeEdge(GraphEdge<L> edge) {
        // se "edge" è null lancio l'eccezione
        if (edge == null)
            throw new NullPointerException("Nodo nullo.");
        // se l'arco è diretto lancio l'eccezione
        if (edge.isDirected())
            throw new IllegalArgumentException("L' arco è diretto.");
        // se l'arco passato non appartiene al grafo lancio l'eccezione
        if (!nodesIndex.containsKey(edge.getNode1()) || !nodesIndex.containsKey(edge.getNode2()))
            throw new IllegalArgumentException("Arco passato non appartenente al grafo.");
        if (matrix.get(nodesIndex.get(edge.getNode1())).get(nodesIndex.get(edge.getNode2())) == null)
            throw new IllegalArgumentException("Arco passato non appartenente al grafo.");

        // variabile per node1 e node2
        int indexOfNode1 = nodesIndex.get(edge.getNode1());
        int indexOfNode2 = nodesIndex.get(edge.getNode2());
        // setto a "null" le celle della matrice
        matrix.get(indexOfNode1).set(indexOfNode2, null);
        matrix.get(indexOfNode2).set(indexOfNode1, null);
    }

    @Override
    public void removeEdge(GraphNode<L> node1, GraphNode<L> node2) {
        removeEdge(new GraphEdge<>(node1, node2, false));
    }

    @Override
    public void removeEdge(L label1, L label2) {
        removeEdge(new GraphNode<>(label1), new GraphNode<>(label2));
    }

    @Override
    public void removeEdge(int i, int j) {
        // se "i" o "j" sono minori di 0 lancio l'eccezione
        if (i < 0 || j < 0)
            throw new IndexOutOfBoundsException("Valore minore di 0.");
        // se il valore di "i" o "j" è maggiore di "nodeCount" lancio l'eccezione
        if (i > nodeCount() || j > nodeCount())
            throw new IndexOutOfBoundsException("Valore maggiore " + nodeCount() + ".");
        matrix.get(i).set(j, null);
        matrix.get(j).set(i, null);
    }

    @Override
    public GraphEdge<L> getEdge(GraphEdge<L> edge) {
        // se edge è null lancio l'eccezione
        if (edge == null)
            throw new NullPointerException("Arco nullo.");
        if (edge.isDirected())
            return null;
        if (!nodesIndex.containsKey(edge.getNode1()) || !nodesIndex.containsKey(edge.getNode2()))
            throw new IllegalArgumentException("Arco inesistente nel grafo.");

        int indexNode1 = nodesIndex.get(edge.getNode1());
        int indexNode2 = nodesIndex.get(edge.getNode2());

        if (edge.equals(matrix.get(indexNode1).get(indexNode2)))
            return matrix.get(indexNode1).get(indexNode2);

        return null;
    }
    @Override
    public GraphEdge<L> getEdge(GraphNode<L> node1, GraphNode<L> node2) {
        // se il node1 o il node2 sono nulli lancio l'eccezione
        if (node1 == null || node2 == null)
            throw new NullPointerException("Nodo nullo.");
        // se il node1 o il node2 non appartengono al grafo lancio l'eccezione
        if (!nodesIndex.containsKey(node1) || !nodesIndex.containsKey(node2))
            throw new IllegalArgumentException("Il nodo non appartiene al grafo.");

        return getEdge(new GraphEdge<>(node1, node2, false));
    }

    @Override
    public GraphEdge<L> getEdge(L label1, L label2) {
        return getEdge(new GraphEdge<>(new GraphNode<>(label1), new GraphNode<>(label2), false));
    }

    @Override
    public GraphEdge<L> getEdge(int i, int j) {
        // se "i" o "j" sono minori di 0 lancio l'eccezione
        if (i < 0 || j < 0)
            throw new IndexOutOfBoundsException("Valore minore di 0.");
        // se il valore di "i" o "j" è maggiore di "nodeCount" lancio l'eccezione
        if (i > nodeCount() || j > nodeCount())
            throw new IndexOutOfBoundsException("Valore maggiore " + nodeCount() + ".");

        return matrix.get(i).get(j);
    }
    @Override
    public Set<GraphNode<L>> getAdjacentNodesOf(GraphNode<L> node) {
        // se il nodo è null lancio l'eccezione
        if (node == null)
            throw new NullPointerException("Nodo nullo.");
        // se il nodo non apaartiene al grafo lancio l'eccezione
        if (!nodesIndex.containsKey(node))
            throw new IllegalArgumentException("Il nodo non appartiene al grafo.");

        // lista di supporto per inserire i nodi adiacenti a quello passato
        Set<GraphNode<L>> adjNode = new HashSet<>();

        // scorro tutti gli archi
        for (GraphEdge<L> edge : this.getEdgesOf(node)) {
            // se il nodo passato è il nodo originale inserisco il nodo di destinazione
            if (edge.getNode1().equals(node))
                adjNode.add(edge.getNode2());
                // caso contrario
            else
                adjNode.add(edge.getNode1());
        }
        return adjNode;
    }

    @Override
    public Set<GraphNode<L>> getAdjacentNodesOf(L label) {
        return getAdjacentNodesOf(new GraphNode<>(label));
    }

    @Override
    public Set<GraphNode<L>> getAdjacentNodesOf(int i) {
        // se "i" è minore di 0 lancio l'eccezione
        if (i < 0)
            throw new IndexOutOfBoundsException("I valori devono essere maggiori o uguali a 0.");
        // se il valore di "i" è maggiore di "nodeCount" lancio l'eccezione
        if (i > nodeCount())
            throw new IndexOutOfBoundsException("I valori devono essere minori di " + nodeCount() + ".");

        return getAdjacentNodesOf(this.getNode(i));
    }

    @Override
    public Set<GraphNode<L>> getPredecessorNodesOf(GraphNode<L> node) {
        throw new UnsupportedOperationException(
                "Operazione non supportata in un grafo non orientato");
    }

    @Override
    public Set<GraphNode<L>> getPredecessorNodesOf(L label) {
        throw new UnsupportedOperationException(
                "Operazione non supportata in un grafo non orientato");
    }

    @Override
    public Set<GraphNode<L>> getPredecessorNodesOf(int i) {
        throw new UnsupportedOperationException(
                "Operazione non supportata in un grafo non orientato");
    }

    @Override
    public Set<GraphEdge<L>> getEdgesOf(GraphNode<L> node) {
      // se il nodo è nullo lancio l'eccezione
        if (node == null){
            throw new NullPointerException("Nodo nullo");
        }
        // se il nodo non è presente nel grafo lancio l'eccezione
        if (!this.getNodes().contains(node)){
            throw new IllegalArgumentException("Nodo non presente nel grafo");
        }
        Set<GraphEdge<L>> archiAdiacenti = new HashSet<GraphEdge<L>>();
        // scorro tutti gli archi
        for (GraphEdge<L> edge : this.getEdges()) {
            //se node1 o node 2 sono uguali a node li aggiungo
            if (edge.getNode1().equals(node) || edge.getNode2().equals(node))
                archiAdiacenti.add(edge);
        }
        return archiAdiacenti;
    }

    @Override
    public Set<GraphEdge<L>> getEdgesOf(L label) {
        // se la label è nulla lancio l'eccezione
        if (label == null){
            throw new NullPointerException("Label nulla");
        }
        return this.getEdgesOf(new GraphNode<L>(label));
    }

    @Override
    public Set<GraphEdge<L>> getEdgesOf(int i) {
        // se i è minore di 0 o maggiore della dimensione della matrice lancio l'eccezione
        if (i < 0 || i > this.matrix.size() - 1){
            throw new IndexOutOfBoundsException ("L'indice passato è errato");
        }
        for (GraphNode<L> key : this.nodesIndex.keySet()){
            if (this.nodesIndex.get(key) == i){
                return this.getEdgesOf(key);
            }
        }
        return null;
    }

    @Override
    public Set<GraphEdge<L>> getIngoingEdgesOf(GraphNode<L> node) {
        throw new UnsupportedOperationException(
                "Operazione non supportata in un grafo non orientato");
    }

    @Override
    public Set<GraphEdge<L>> getIngoingEdgesOf(L label) {
        throw new UnsupportedOperationException(
                "Operazione non supportata in un grafo non orientato");
    }

    @Override
    public Set<GraphEdge<L>> getIngoingEdgesOf(int i) {
        throw new UnsupportedOperationException(
                "Operazione non supportata in un grafo non orientato");
    }

    @Override
    public Set<GraphEdge<L>> getEdges() {
        Set<GraphEdge<L>> tot = new HashSet<>();
        // scorro tutta la matrice
        for (ArrayList<GraphEdge<L>> graphEdges : this.matrix) {
            for (GraphEdge<L> e : graphEdges)
                if (e != null)
                    tot.add(e);
            }
        return tot;
    }
}
