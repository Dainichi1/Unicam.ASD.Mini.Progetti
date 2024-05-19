package Mini_Progetto_3;


import java.util.*;

/**
 *
 * Class that solves an instance of the the El Mamun's Caravan problem using
 * dynamic programming.
 *
 * Template: Daniele Marchei and Luca Tesei, **Implementation: MARCO TORQUATI - marco.torquati@studenti.unicam.it
 *
 */
public class ElMamunCaravanSolver {

    // the expression to analyse
    private final Expression expression;

    // table to collect the optimal solution for each sub-problem,
    // protected just for Junit Testing purposes
    protected Integer[][] table;

    // table to record the chosen optimal solution among the optimal solution of
    // the sub-problems, protected just for JUnit Testing purposes
    protected Integer[][] tracebackTable;

    // flag indicating that the problem has been solved at least once
    private boolean solved;

    /**
     * Create a solver for a specific expression.
     *
     * @param expression
     *                       The expression to work on
     * @throws NullPointerException
     *                                  if the expression is null
     */
    public ElMamunCaravanSolver(Expression expression) {
        if (expression == null)
            throw new NullPointerException(
                    "Creazione di solver con expression null");
        this.expression = expression;
        // Allocazione matrice soluzioni ottime dei sottoproblemi
        this.table = new Integer[expression.size()][expression.size()];
        // Allocazione matrice per eseguire il traceBack
        this.tracebackTable = new Integer[expression.size()][expression.size()];
        // All' inizio il problema non è stato risolto
        this.solved = false;
    }

    /**
     * Returns the expression that this solver analyse.
     *
     * @return the expression of this solver
     */
    public Expression getExpression() {
        return this.expression;
    }

    /**
     * Solve the problem on the expression of this solver by using a given
     * objective function.
     *
     * @param function
     *                     The objective function to be used when deciding which
     *                     candidate to choose
     * @throws NullPointerException
     *                                  if the objective function is null
     */
    public void solve(ObjectiveFunction function) {
        // se la funzione è nulla lancio l'eccezione
        if (function == null) throw new NullPointerException("Funzione nulla.");
        // scorro l'espressione e riempio la matrice solo con i valori numerici
        for (int i = 0; i < this.expression.size(); i += 2) {
            this.table[i][i] = (Integer) this.expression.get(i).getValue();
        }
        // questo for fa avanzare i cioè il livello in cui l'algoritmo sta operando
        for (int i = 0; i < this.expression.size(); i = i + 2) {
            // questo for fa avanzare j
            for (int j = 0; j < this.expression.size() - 2 - i; j = j + 2 ) {
                // l dipende da j e i
                int l = j + 2 + i;
                // variabile "Temp" per raccogliere i valori calcolati
                List<Integer> temp = new ArrayList<>();
                // avanzamento di k
                for (int k = 0; j + k + 2 <= l; k = k + 2) {
                    // i valori da calcolare utilizzando una variabile d'appoggio
                    Integer a = this.table[j][j + k];
                    Integer b = this.table[j + k + 2][l];
                    // in caso di addizione
                    if (this.expression.get(j + k + 1).getValue().equals("+"))
                        temp.add(a + b);
                        // in caso di moltiplicazione
                    else
                        temp.add(a * b);
                    // Calcolo della soluzione ottima
                    this.table[j][l] = function.getBest(temp);
                    // k = indice della soluzione ottima
                    this.tracebackTable[j][l] = function.getBestIndex(temp) * 2;
                }
            }
        }
        this.solved = true;
    }


    /**
     * Returns the current optimal value for the expression of this solver. The
     * value corresponds to the one obtained after the last solving (which used
     * a particular objective function).
     *
     * @return the current optimal value
     * @throws IllegalStateException
     *                                   if the problem has never been solved
     */
    public int getOptimalSolution() {
        // Se almeno un problema non è stato ancora risolto da questo solver
        if (!this.solved)
            throw new IllegalStateException("ERRORE: il problema non è stato ancora risolto!");
        // Altrimenti se il problema è stato risolto ritorno la soluzione ottima
        return this.table[0][this.expression.size() - 1];
    }

    /**
     * Returns an optimal parenthesization corresponding to an optimal solution
     * of the expression of this solver. The parenthesization corresponds to the
     * optimal value obtained after the last solving (which used a particular
     * objective function).
     *
     * If the expression is just a digit then the parenthesization is the
     * expression itself. If the expression is not just a digit then the
     * parethesization is of the form "(<parenthesization>)". Examples: "1",
     * "(1+2)", "(1*(2+(3*4)))"
     *
     * @return the current optimal parenthesization for the expression of this
     *         solver
     * @throws IllegalStateException
     *                                   if the problem has never been solved
     */
    public String getOptimalParenthesization() {
        // Se almeno un problema non è stato ancora risolto da questo solver
        if (!this.solved)
            throw new IllegalStateException("ERRORE: il problema non è stato ancora risolto!");
        // Altrimenti se il problema è stato risolto ritorno la soluzione ottima
        return this.traceBack(0, this.expression.size() - 1);
    }

    /**
     * Determines if the problem has been solved at least once.
     *
     * @return true if the problem has been solved at least once, false
     *         otherwise.
     */
    public boolean isSolved() {
        return this.solved;
    }

    @Override
    public String toString() {
        return "ElMamunCaravanSolver for " + expression;
    }

    private String traceBack(int i, int j) {
        // caso base
        if (i == j)
            return this.expression.get(i).getValue().toString();
        // Altrimenti eseguo la ricorsione del traceBack
        return "(" + this.traceBack(i, i + this.tracebackTable[i][j])
                + this.expression.get(i + this.tracebackTable[i][j] + 1).getValue()
                + this.traceBack(i + this.tracebackTable[i][j] + 2, j) + ")";
    }
}
