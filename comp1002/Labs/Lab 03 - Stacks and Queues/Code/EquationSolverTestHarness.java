public class EquationSolverTestHarness {
    public static void main(String[] args) {
        String equation = "10.3 * ( 14.0 + 3.2 ) / ( 5.0 + 2.0 - 4.0 * 3.0 )";
        double answerEquationSolver = EquationSolver.solve(equation);
        System.out.println("EquationSolver: " + equation + " = " + 
                           answerEquationSolver);
        double answerJava = 10.3 * ( 14.0 + 3.2 ) / ( 5.0 + 2.0 - 4.0 * 3.0 );
        System.out.println("Java:           " + equation + " = " + answerJava + 
                           "\n");

        equation = "23.0 / ( 1.0 + 3.0 ) + 9.0 + ( 32.0 / 17.0 )";
        answerEquationSolver = EquationSolver.solve(equation);
        System.out.println("EquationSolver: " + equation + " = " + 
                           answerEquationSolver);
        answerJava = 23.0 / ( 1.0 + 3.0 ) + 9.0 + ( 32.0 / 17.0 );
        System.out.println("Java:           " + equation + " = " + answerJava + 
                           "\n");

        equation = "1.0 + 98.0 + ( 45.0 * ( 13.0 * 23.0 ) ) + 78.4 + 3.0";
        answerEquationSolver = EquationSolver.solve(equation);
        System.out.println("EquationSolver: " + equation + " = " + 
                           answerEquationSolver);
        answerJava = 1.0 + 98.0 + ( 45.0 * ( 13.0 * 23.0 ) ) + 78.4 + 3.0;
        System.out.println("Java:           " + equation + " = " + answerJava + 
                           "\n");
    }
}