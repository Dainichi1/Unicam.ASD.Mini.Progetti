package Mini_Progetto_2;

/**
 * An object of this class is an actor that uses an ASDL2223Deque<Character> as
 * a Stack in order to check that a sequence containing the following
 * characters: '(', ')', '[', ']', '{', '}' in any order is a string of balanced
 * parentheses or not. The input is given as a String in which white spaces,
 * tabs and newlines are ignored.
 * 
 * Some examples:
 * 
 * - " (( [( {\t (\t) [ ] } ) \n ] ) ) " is a string o balanced parentheses - " " is a
 * string of balanced parentheses - "(([)])" is NOT a string of balanced
 * parentheses - "( { } " is NOT a string of balanced parentheses - "}(([]))" is
 * NOT a string of balanced parentheses - "( ( \n [(P)] \t ))" is NOT a string
 * of balanced parentheses
 * 
 * @author Template: Luca Tesei, Implementation: MARCO TORQUATI - marco.torquati@studenti.unicam.it
 *
 */
public class BalancedParenthesesChecker {

    // The stack is to be used to check the balanced parentheses
    private ASDL2223Deque<Character> stack;

    // array di caratteri accettati
    private Character[] cValidi = { '(', ')', '[', ']', '{', '}', ' ', '\t', '\n' };
    /**
     * Create a new checker.
     */
    public BalancedParenthesesChecker() {
        this.stack = new ASDL2223Deque<Character>();
    }

    /**
     * Check if a given string contains a balanced parentheses sequence of
     * characters '(', ')', '[', ']', '{', '}' by ignoring white spaces ' ',
     * tabs '\t' and newlines '\n'.
     * 
     * @param s
     *              the string to check
     * @return true if s contains a balanced parentheses sequence, false
     *         otherwise
     * @throws IllegalArgumentException
     *                                      if s contains at least a character
     *                                      different form:'(', ')', '[', ']',
     *                                      '{', '}', white space ' ', tab '\t'
     *                                      and newline '\n'
     */
    public boolean check(String s) {

        // NOTE: the stack must be cleared first every time
        // this method is called. Then it has to be used to perform the check.

        // rimuovo i caratteri da ignorare
        s = s.replaceAll("\\s+|\\t+|\\n+","");
        // se la stringa contiene caratteri non validi lancio l'eccezione
        if (this.caratteriNonAccetati(s))
            throw new IllegalArgumentException("La stringa contiene caratteri non validi");
        // scorro tutta la stringa
        for (int i = 0; i < s.length(); i++) {
            // ottengo il carattere attuale
            char ch = s.charAt(i);
            // se lo stack è vuoto metto il carattere nello stack
            if (stack.isEmpty()) {
                stack.push(ch);
            }
            // se il carattere attuale è una parentesi chiusa e in cima allo stack c'è una parentesi aperta
            // dello stesso tipo la rimuovo dallo stack
            else if ((ch==')' && stack.peek() == '(')
                    ||(ch=='}' && stack.peek() == '{')
                    ||(ch==']' && stack.peek() == '[')) {
                stack.pop();

            }
            // altrimenti lo inserisco nello stack
            else {
                stack.push(ch);
            }
        }
        // se la stack è vuota la stringa è bilanciata
        return (stack.isEmpty());
    }

    // metodo per controllare se la stringa contiene caratteri non validi
    private boolean caratteriNonAccetati (String s) {
        int i = 0;
        int j = 0;
        boolean nonAccetati = false;
        // scorro tutta la stringa
        while (i < s.length()) {
            nonAccetati = false;
            j = 0;
            // scorro tutti i caratteri validi e se non ne trovo ritorno false
            while (j < cValidi.length && !nonAccetati) {
                if (s.toCharArray()[i] == cValidi[j])
                    nonAccetati = true;
                j++;
            }
            if (!nonAccetati)
                return true;
            i++;
        }
        // la stringa è valida
        return false;
    }


}
