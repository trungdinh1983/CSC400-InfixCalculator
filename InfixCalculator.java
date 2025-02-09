
import java.util.*;

public class InfixCalculator {
    // Math operations priority

        put('+', 1); put('-', 1);  // lower priority
        put('*', 2); put('/', 2); put('%', 2);  // higher priority
    }};

    // Calculate two numbers 
    private int calculate(int a,  t b, char op) {
        switch (op) { 
            case '+': return a +  
            case '-': return a -  
            case '*': return a * b;
            case '/': if (b == 0) throw new ArithmeticException("Can't divide by zero");
                     return a / b;
            case '%': if (b == 0) throw new ArithmeticException("Can't mod by zero");
                     return a % b;
            default:  throw new IllegalArgumentException("Invalid operator: " + op);
        }
    }

    public int evaluateInfix(String exp) {
        try {
            Stack<Int
                k<Character> ops = new Stack<>();

                rocess each character
            for (int 
                char c = exp.charAt(i);
                
                if (c == ' ') continue;  // skip spaces
                
                if (c == '(') {
                    ops.push(c);
                }
                else if (Character.isDigit(c)) {
                    int num = 0;
                    while (i < exp.length() && Character.isDigit(exp.charAt(i))) {
                        num = num * 10 + (exp.charAt(i) - '0');
                    
                    }
                    i--;
                    nums.push(num);
                }
                else if (c == ')') {
                    while (!ops.isEmpty() && ops.peek() != '(') {
                        nums.push(calculate(nums.pop(), nums.pop(), ops.pop()));
                    }
                    ops.pop();  // remove '('
                }
                else if (priority.containsKey(c)) {
                    while (!ops.isEmpty() && ops.peek() != '(' && 
                           priority.get(ops.peek()) >= priority.get(c)) {
                        nums.push(calculate(nums.pop(), nums.pop(), ops.pop()));
                    }
                    ops.push(c);
                }
            }

            // Process remaining operations
            while (!ops.isEmpty()) {
                nums.push(calculate(nums.pop(), nums.pop(), ops.pop()));
            }

            return nums.pop();
        } catch (Exception e) {
            System.out.println("Error: Invalid expression");
            throw e;
        }
    }

    public static void main(String[] args) {
        InfixCalculator calc = new InfixCalculator();
        Scanner input = new Scanner(System.in);

        System.out.println("Simple Calculator (type 'exit' to quit)");
        System.out.println("Example: 2+3, (4+2)*3, 15/5");

        while (true) {
            System.out.print("> ");
            String exp = input.nextLine();
            
            if (exp.equalsIgnoreCase("exit")) break;
            
            try {
                System.out.println("= " + calc.evaluateInfix(exp));
            } catch (Exception e) {
                System.out.println("Error: Invalid expression");
            }
        }
    }
}

    

    
    
        
        
    

        

        

            

            

        