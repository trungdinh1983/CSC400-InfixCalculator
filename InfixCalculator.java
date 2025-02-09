public class InfixCalculator {
    private java.util.Map<Character, Integer> priorities;

    public InfixCalculator() {
        priorities = new java.util.HashMap<>();
        priorities.put('+', 1);
        priorities.put('-', 1);
        priorities.put('*', 2);
        priorities.put('/', 2);
        priorities.put('%', 2);
    }

    private boolean isOperator(char c) {
        return priorities.containsKey(c);
    }

    private int calculate(int a, int b, char operator) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return a / b;
            case '%':
                if (b == 0) {
                    throw new ArithmeticException("Modulo by zero");
                }
                return a % b;
            default:
                throw new IllegalArgumentException("Invalid operator");
        }
    }

    public int evaluateInfix(String infixExpression) {
        if (infixExpression == null || infixExpression.isEmpty()) {
            throw new IllegalArgumentException("Empty expression");
        }

        infixExpression = infixExpression.replaceAll("\\s+", "");
        java.util.Stack<Integer> numbers = new java.util.Stack<>();
        java.util.Stack<Character> operators = new java.util.Stack<>();

        try {
            for (int i = 0; i < infixExpression.length(); i++) {
                char current = infixExpression.charAt(i);

                if (current == '(') {
                    operators.push(current);
                } else if (Character.isDigit(current)) {
                    int number = 0;
                    while (i < infixExpression.length() && Character.isDigit(infixExpression.charAt(i))) {
                        number = number * 10 + (infixExpression.charAt(i) - '0');
                        i++;
                    }
                    i--;
                    numbers.push(number);
                } else if (current == ')') {
                    while (!operators.isEmpty() && operators.peek() != '(') {
                        int num2 = numbers.pop();
                        int num1 = numbers.pop();
                        numbers.push(calculate(num1, num2, operators.pop()));
                    }
                    if (!operators.isEmpty()) {
                        operators.pop();
                    } else {
                        throw new IllegalArgumentException("Invalid expression");
                    }
                } else if (isOperator(current)) {
                    while (!operators.isEmpty() && operators.peek() != '(' &&
                            priorities.get(operators.peek()) >= priorities.get(current)) {
                        int num2 = numbers.pop();
                        int num1 = numbers.pop();
                        numbers.push(calculate(num1, num2, operators.pop()));
                    }
                    operators.push(current);
                } else {
                    throw new IllegalArgumentException("Invalid character: " + current);
                }
            }

            while (!operators.isEmpty()) {
                if (operators.peek() == '(') {
                    throw new IllegalArgumentException("Invalid expression");
                }
                int num2 = numbers.pop();
                int num1 = numbers.pop();
                numbers.push(calculate(num1, num2, operators.pop()));
            }

            if (numbers.size() != 1) {
                throw new IllegalArgumentException("Invalid expression");
            }
            return numbers.pop();
        } catch (Exception e) {
            System.err.println("Error: Invalid infix expression");
            throw e;
        }
    }

    public static void main(String[] args) {
        InfixCalculator calculator = new InfixCalculator();
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        
        while (true) {
            System.out.print("\nEnter an expression (or 'exit' to quit): ");
            String input = scanner.nextLine();
            
            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            
            try {
                int result = calculator.evaluateInfix(input);
                System.out.println("Result: " + result);
            } catch (Exception e) {
                System.err.println("Error: Invalid infix expression");
            }
        }
        
        scanner.close();
    }
}