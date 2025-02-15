import java.util.*;

/**
 * A calculator that evaluates mathematical expressions in infix notation.
 * Supports basic arithmetic operations (+, -, *, /, %) and parentheses.
 */
public class InfixCalculator {
    private final Map<Character, Integer> mathPriority;

    /**
     * Initializes the calculator with operation priorities.
     */
    public InfixCalculator() {
        mathPriority = new HashMap<>();
        mathPriority.put('+', 1); // Basic operations (do last)
        mathPriority.put('-', 1);
        mathPriority.put('*', 2); // Complex operations (do first)
        mathPriority.put('/', 2);
        mathPriority.put('%', 2);
    }

    /**
     * Evaluates an infix mathematical expression and returns the computed result.
     * 
     * @param expression the mathematical expression to evaluate (e.g., "2+3" or
     *                   "(4+2)*3")
     * @return the computed result of the expression
     * @throws IllegalArgumentException if the expression is null, empty, or invalid
     * @throws ArithmeticException      if attempting to divide or find modulo by
     *                                  zero
     */
    public int evaluateInfix(String expression) {
        // Validate input
        if (expression == null) {
            throw new IllegalArgumentException("Expression cannot be null");
        }

        expression = expression.trim();
        if (expression.isEmpty()) {
            throw new IllegalArgumentException("Expression cannot be empty");
        }

        Deque<Integer> numbers = new ArrayDeque<>();
        Deque<Character> operators = new ArrayDeque<>();

        try {
            for (int i = 0; i < expression.length(); i++) {
                char current = expression.charAt(i);

                if (current == ' ')
                    continue;

                if (current == '(') {
                    operators.push(current);
                } else if (Character.isDigit(current)) {
                    int number = 0;
                    while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                        number = number * 10 + (expression.charAt(i) - '0');
                        i++;
                    }
                    i--;
                    numbers.push(number);
                } else if (current == ')') {
                    while (!operators.isEmpty() && operators.peek() != '(') {
                        int secondNum = numbers.pop();
                        int firstNum = numbers.pop();
                        numbers.push(doMath(firstNum, secondNum, operators.pop()));
                    }
                    if (!operators.isEmpty()) {
                        operators.pop();
                    } else {
                        throw new IllegalArgumentException("Mismatched parentheses");
                    }
                } else if (mathPriority.containsKey(current)) {
                    while (!operators.isEmpty() && operators.peek() != '(' &&
                            mathPriority.get(operators.peek()) >= mathPriority.get(current)) {
                        int secondNum = numbers.pop();
                        int firstNum = numbers.pop();
                        numbers.push(doMath(firstNum, secondNum, operators.pop()));
                    }
                    operators.push(current);
                } else {
                    throw new IllegalArgumentException("Invalid character: " + current);
                }
            }

            while (!operators.isEmpty()) {
                if (operators.peek() == '(') {
                    throw new IllegalArgumentException("Mismatched parentheses");
                }
                int secondNum = numbers.pop();
                int firstNum = numbers.pop();
                numbers.push(doMath(firstNum, secondNum, operators.pop()));
            }

            if (numbers.size() != 1) {
                throw new IllegalArgumentException("Invalid expression format");
            }
            return numbers.pop();

        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Invalid expression format");
        }
    }

    /**
     * Performs basic arithmetic operation between two numbers.
     */
    private int doMath(int first, int second, char operation) {
        switch (operation) {
            case '+':
                return first + second;
            case '-':
                return first - second;
            case '*':
                return first * second;
            case '/':
                if (second == 0)
                    throw new ArithmeticException("Cannot divide by zero");
                return first / second;
            case '%':
                if (second == 0)
                    throw new ArithmeticException("Cannot find remainder when dividing by zero");
                return first % second;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operation);
        }
    }
}