import java.util.*;

public class InfixCalculator {
    // Store which operations should be done first (higher number = do first)
    private Map<Character, Integer> operationOrder;
    
    // Setup the calculator rules
    public InfixCalculator() {
        operationOrder = new HashMap<>();
        // Do these last
        operationOrder.put('+', 1);  // addition
        operationOrder.put('-', 1);  // subtraction
        // Do these first
        operationOrder.put('*', 2);  // multiplication
        operationOrder.put('/', 2);  // division
        operationOrder.put('%', 2);  // remainder
    }

    // Do the actual math operation
    private int doMathOperation(int firstNumber, int secondNumber, char mathSymbol) {
        switch (mathSymbol) {
            case '+': return firstNumber + secondNumber;
            case '-': return firstNumber - secondNumber;
            case '*': return firstNumber * secondNumber;
            case '/': 
                if (secondNumber == 0) throw new ArithmeticException("Cannot divide by zero!");
                return firstNumber / secondNumber;
            case '%': 
                if (secondNumber == 0) throw new ArithmeticException("Cannot find remainder when dividing by zero!");
                return firstNumber % secondNumber;
            default: 
                throw new IllegalArgumentException("Not a valid math symbol: " + mathSymbol);
        }
    }

    // Main method to solve the math expression
    public int evaluateInfix(String mathExpression) {
        // Check if expression is empty
        if (mathExpression == null || mathExpression.trim().isEmpty()) {
            throw new IllegalArgumentException("No math expression provided");
        }

        // Stacks to store numbers and math symbols
        Stack<Integer> numberStack = new Stack<>();
        Stack<Character> symbolStack = new Stack<>();

        try {
            // Process each character in the expression
            for (int position = 0; position < mathExpression.length(); position++) {
                char currentSymbol = mathExpression.charAt(position);
                
                // Skip spaces
                if (currentSymbol == ' ') continue;
                
                // If we find opening parenthesis
                if (currentSymbol == '(') {
                    symbolStack.push(currentSymbol);
                }
                // If we find a number
                else if (Character.isDigit(currentSymbol)) {
                    int fullNumber = 0;
                    // Read the complete number (could be multiple digits)
                    while (position < mathExpression.length() && 
                           Character.isDigit(mathExpression.charAt(position))) {
                        fullNumber = fullNumber * 10 + (mathExpression.charAt(position) - '0');
                        position++;
                    }
                    position--;
                    numberStack.push(fullNumber);
                }
                // If we find closing parenthesis
                else if (currentSymbol == ')') {
                    // Calculate everything inside parentheses
                    while (!symbolStack.isEmpty() && symbolStack.peek() != '(') {
                        int secondNum = numberStack.pop();
                        int firstNum = numberStack.pop();
                        numberStack.push(doMathOperation(firstNum, secondNum, symbolStack.pop()));
                    }
                    if (!symbolStack.isEmpty()) {
                        symbolStack.pop();  // remove '('
                    }
                }
                // If we find a math symbol
                else if (operationOrder.containsKey(currentSymbol)) {
                    while (!symbolStack.isEmpty() && symbolStack.peek() != '(' && 
                           operationOrder.get(symbolStack.peek()) >= operationOrder.get(currentSymbol)) {
                        int secondNum = numberStack.pop();
                        int firstNum = numberStack.pop();
                        numberStack.push(doMathOperation(firstNum, secondNum, symbolStack.pop()));
                    }
                    symbolStack.push(currentSymbol);
                }
                else {
                    throw new IllegalArgumentException("Invalid character found: " + currentSymbol);
                }
            }

            // Do any remaining operations
            while (!symbolStack.isEmpty()) {
                int secondNum = numberStack.pop();
                int firstNum = numberStack.pop();
                numberStack.push(doMathOperation(firstNum, secondNum, symbolStack.pop()));
            }

            return numberStack.pop();
        } catch (Exception error) {
            System.out.println("Error: " + error.getMessage());
            throw error;
        }
    }

    // Program start point
    public static void main(String[] args) {
        InfixCalculator calculator = new InfixCalculator();
        Scanner userInput = new Scanner(System.in);

        System.out.println("Simple Math Calculator (type 'exit' to quit)");
        System.out.println("You can type expressions like: 2+3, (4+2)*3, 15/5");

        while (true) {
            System.out.print("Enter your math problem > ");
            String expression = userInput.nextLine();
            
            if (expression.equalsIgnoreCase("exit")) {
                System.out.println("Thanks for using the calculator!");
                break;
            }
            
            try {
                System.out.println("Answer = " + calculator.evaluateInfix(expression));
            } catch (Exception error) {
                System.out.println("That's not a valid math expression. Please try again.");
            }
        }
        
        userInput.close();
    }
}