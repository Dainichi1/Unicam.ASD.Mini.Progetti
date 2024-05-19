/**
 * 
 */
package Mini_Progetto_3;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author Luca Tesei
 *
 */
class ElMamunCaravanSolverTest {
    
    private ObjectiveFunction max = new MaximumFunction();
    private ObjectiveFunction min = new MinimumFunction();


    @Test
    final void testElMamunCaravanSolver() {
        Expression f = new Expression("1+2*3*4+5");
        @SuppressWarnings("unused")
        ElMamunCaravanSolver solver = new ElMamunCaravanSolver(f); 
        assertThrows(NullPointerException.class, () -> new ElMamunCaravanSolver(null));
    }


    @Test
    final void testSolve1maxv() {
        Expression f = new Expression("1+2*3*4+5");
        ElMamunCaravanSolver solver = new ElMamunCaravanSolver(f);    
        solver.solve(max);
        assertTrue(solver.getOptimalSolution() == 81);
    }
    

    @Test
    final void testSolve1maxp() {
        Expression f = new Expression("1+2*3*4+5");
        ElMamunCaravanSolver solver = new ElMamunCaravanSolver(f);
        solver.solve(max);
        assertTrue(solver.getOptimalParenthesization().equals("((1+2)*(3*(4+5)))"));
    }
    

    @Test
    final void testSolve1minv() {
        Expression f = new Expression("1+2*3*4+5");
        ElMamunCaravanSolver solver = new ElMamunCaravanSolver(f);
        solver.solve(min);
        assertTrue(solver.getOptimalSolution() == 30);
    }
    

    @Test
    final void testSolve1minp() {
        Expression f = new Expression("1+2*3*4+5");
        ElMamunCaravanSolver solver = new ElMamunCaravanSolver(f);
        solver.solve(min);
        assertTrue(solver.getOptimalParenthesization().equals("(1+((2*(3*4))+5))"));
    }


    @Test
    final void testSolve2maxv() {
        Expression f = new Expression("1");
        ElMamunCaravanSolver solver = new ElMamunCaravanSolver(f);    
        solver.solve(max);
        assertTrue(solver.getOptimalSolution() == 1);
    }
    

    @Test
    final void testSolve2maxp() {
        Expression f = new Expression("1");
        ElMamunCaravanSolver solver = new ElMamunCaravanSolver(f);
        solver.solve(max);
        assertTrue(solver.getOptimalParenthesization().equals("1"));
    }
    

    @Test
    final void testSolve2minv() {
        Expression f = new Expression("1");
        ElMamunCaravanSolver solver = new ElMamunCaravanSolver(f);
        solver.solve(min);
        assertTrue(solver.getOptimalSolution() == 1);
    }
    

    @Test
    final void testSolve2minp() {
        Expression f = new Expression("1");
        ElMamunCaravanSolver solver = new ElMamunCaravanSolver(f);
        solver.solve(min);
        assertTrue(solver.getOptimalParenthesization().equals("1"));
    }


    @Test
    final void testSolve3maxv() {
        Expression f = new Expression("1*3");
        ElMamunCaravanSolver solver = new ElMamunCaravanSolver(f);    
        solver.solve(max);
        assertTrue(solver.getOptimalSolution() == 3);
    }
    

    @Test
    final void testSolve3maxp() {
        Expression f = new Expression("1*3");
        ElMamunCaravanSolver solver = new ElMamunCaravanSolver(f);
        solver.solve(max);
        assertTrue(solver.getOptimalParenthesization().equals("(1*3)"));
    }
    

    @Test
    final void testSolve3minv() {
        Expression f = new Expression("1*3");
        ElMamunCaravanSolver solver = new ElMamunCaravanSolver(f);
        solver.solve(min);
        assertTrue(solver.getOptimalSolution() == 3);
    }
    

    @Test
    final void testSolve3minp() {
        Expression f = new Expression("1*3");
        ElMamunCaravanSolver solver = new ElMamunCaravanSolver(f);
        solver.solve(min);
        assertTrue(solver.getOptimalParenthesization().equals("(1*3)"));
    }
    

    @Test
    final void testSolve4maxv() {
        Expression f = new Expression("9*3+2");
        ElMamunCaravanSolver solver = new ElMamunCaravanSolver(f);    
        solver.solve(max);
        assertTrue(solver.getOptimalSolution() == 45);
    }
    

    @Test
    final void testSolve4maxp() {
        Expression f = new Expression("9*3+2");
        ElMamunCaravanSolver solver = new ElMamunCaravanSolver(f);
        solver.solve(max);
        assertTrue(solver.getOptimalParenthesization().equals("(9*(3+2))"));
    }

    @Test
    final void testSolve4minv() {
        Expression f = new Expression("9*3+2");
        ElMamunCaravanSolver solver = new ElMamunCaravanSolver(f);
        solver.solve(min);
        assertTrue(solver.getOptimalSolution() == 29);
    }
    

    @Test
    final void testSolve4minp() {
        Expression f = new Expression("9*3+2");
        ElMamunCaravanSolver solver = new ElMamunCaravanSolver(f);
        solver.solve(min);
        assertTrue(solver.getOptimalParenthesization().equals("((9*3)+2)"));
    }
 
    

    @Test
    final void testSolve5maxv() {
        Expression f = new Expression("1+2+3*3*2*1");
        ElMamunCaravanSolver solver = new ElMamunCaravanSolver(f);    
        solver.solve(max);
        assertTrue(solver.getOptimalSolution() == 36);
    }
    

    @Test
    final void testSolve5maxp() {
        Expression f = new Expression("1+2+3*3*2*1");
        ElMamunCaravanSolver solver = new ElMamunCaravanSolver(f);
        solver.solve(max);
        assertTrue(solver.getOptimalParenthesization().equals("((1+(2+3))*(3*(2*1)))"));
    }

    @Test
    final void testSolve5minv() {
        Expression f = new Expression("1+2+3*3*2*1");
        ElMamunCaravanSolver solver = new ElMamunCaravanSolver(f);
        solver.solve(min);
        assertTrue(solver.getOptimalSolution() == 21);
    }

    @Test
    final void testSolve5minp() {
        Expression f = new Expression("1+2+3*3*2*1");
        ElMamunCaravanSolver solver = new ElMamunCaravanSolver(f);
        solver.solve(min);
        assertTrue(solver.getOptimalParenthesization().equals("(1+(2+(3*(3*(2*1)))))"));
    }


    @Test
    final void testIsSolved() {
        Expression f = new Expression("1+2*3*4+5");
        ElMamunCaravanSolver solver = new ElMamunCaravanSolver(f);
        assertFalse(solver.isSolved());
        solver.solve(max);
        assertTrue(solver.isSolved());
    }

}
