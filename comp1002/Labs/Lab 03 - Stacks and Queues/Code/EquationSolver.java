public class EquationSolver {
    // PRIVATE CLASS CONSTANTS

    // NONE

    // PROTECTED CLASS CONSTANTS

    // NONE

    // PRIVATE CLASS FIELDS

    // NONE

    // PROTECTED CLASS FIELDS

    // NONE

    // CONSTRUCTORS

    // NONE

    // ACCESSORS

    // NONE

    // MUTATORS

    // NONE

    // OPERATORS

    /*
     * NAME: solve
     * IMPORT(S): equationInfix (String)
     * EXPORT(S): finalAnswer (double)
     * PURPOSE: Act as wrapper for parseInfixToPostfix and evaluatePostfix
     * CREATION: 23/08/2020
     * LAST MODIFICATION: 23/08/2020
     */

    public static double solve(String equationInfix) {
        DSACircularQueue equationPostfix = parseInfixToPostfix(equationInfix);
        double finalAnswer = evaluatePostfix(equationPostfix);

        return finalAnswer;
    }

    // PRIVATE SUBMODULES

    /*
     * NAME: parseInfixToPostfix
     * IMPORT(S): equationInfix (String)
     * EXPORT(S): equationPostfix (DSACircularQueue)
     * PURPOSE: Parse equation from infix to postfix
     * CREATION: 23/08/2020
     * LAST MODIFICATION: 23/08/2020
     */

    public static DSACircularQueue parseInfixToPostfix(String equationInfix) {
        String[] equationInfixSplit = equationInfix.split(" ");
        DSACircularQueue equationPostfix = new DSACircularQueue();
        DSAStack operatorStack = new DSAStack();

        for (int i = 0; i < equationInfixSplit.length; i++) {
            char termChar = equationInfixSplit[i].charAt(0); // Extract term
            String termString = equationInfixSplit[i];
            
            if (termString.equals("(")) {
                operatorStack.push("("); // "(" are put straight onto the stack
            }
            else if (termString.equals(")")) {
                // Find corresponding "(" by casting Object to String and
                // checking if it is equal to "("
                while (!((String) operatorStack.peek()).equals("(")) {
                    equationPostfix.enqueue(operatorStack.pop());
                }
                operatorStack.pop(); // Pop "(" and discard it
            }
            else if (termString.equals("+") || termString.equals("-") ||
                     termString.equals("*") || termString.equals("/")) {
                while (!operatorStack.isEmpty() && 
                       !(((String) operatorStack.peek()).equals("(")) &&
                       (precedenceOf(((String) operatorStack.peek()).charAt(0)) 
                       >= (precedenceOf(termChar)))) {
                    // Move operators of >= precedence to equationPostfix
                    equationPostfix.enqueue(operatorStack.pop());
                }
                operatorStack.push(termString); // Put new operator onto stack
            }
            else { // Term must be an operand if it isn't an operator
                equationPostfix.enqueue(Double.valueOf(termString));
            }
        }

        while (!operatorStack.isEmpty()) {
            // Pop any remaining operators off stack
            equationPostfix.enqueue(operatorStack.pop());
        }

        return equationPostfix;
    }

    /*
     * NAME: evaluatePostfix
     * IMPORT(S): equationPostfix (DSACircularQueue)
     * EXPORT(S): answer (double)
     * PURPOSE: Evaluates postfix equivalent of infix equation
     * CREATION: 23/08/2020
     * LAST MODIFICATION: 23/08/2020
     */

    private static double evaluatePostfix(DSACircularQueue equationPostfix) {
        DSAStack operandStack = new DSAStack();

        while (!equationPostfix.isEmpty()) {
            // Element is an operand
            if (equationPostfix.peek() instanceof Double) {
                operandStack.push(equationPostfix.dequeue());
            }
            // Element is an operator
            else if (equationPostfix.peek() instanceof String) {
                char operator = ((String) equationPostfix.dequeue()).charAt(0);
                double operand2 = (double) operandStack.pop(); // Preserve order
                double operand1 = (double) operandStack.pop(); // Preserve order

                double subAnswer = evaluateOperation(operator, operand1, 
                                                     operand2);

                operandStack.push((Double) subAnswer);
            }
        }

        // Last item on the stack is final answer
        double finalAnswer = (double) operandStack.pop();
        
        return finalAnswer;
    }

    /*
     * NAME: precedenceOf
     * IMPORT(S): operator (char)
     * EXPORT(S): precedence (int)
     * PURPOSE: Evaluates the precendence of operator
     * CREATION: 23/08/2020
     * LAST MODIFICATION: 23/08/2020
     */

    private static int precedenceOf(char operator) {
        int precedence;

        if (operator == '+' || operator == '-') {
            precedence = 1;
        }
        else {
            precedence = 2;
        }

        return precedence;
    }

    /*
     * NAME: evaluateOperation
     * IMPORT(S): operator (char), operand1 (double), operand2 (double)
     * EXPORT(S): answer (double)
     * PURPOSE: Evaluates given binary operation
     * CREATION: 23/08/2020
     * LAST MODIFICATION: 23/08/2020
     */

    private static double evaluateOperation(char operator, double operand1, 
                                            double operand2) {
        double subAnswer;
        switch (operator) {
            case '+':
                subAnswer = operand1 + operand2;
                break;
            case '-':
                subAnswer = operand1 - operand2;
                break;
            case '*':
                subAnswer = operand1 * operand2;
                break;
            case '/':
                subAnswer = operand1 / operand2;
                break;
            default:
                subAnswer = 0;
                break;
        }

        return subAnswer;
    }

    // PROTECTED SUBMODULES

    // NONE
}