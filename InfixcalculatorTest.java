import org.junit.Test;
import static org.junit.Assert.*;

public class InfixCalculatorTest {
  private InfixCalculator calculator = new InfixCalculator();

  @Test
  public void testBasicOperations() {
    assertEquals(5, calculator.evaluateInfix("2+3"));
    assertEquals(1, calculator.evaluateInfix("3-2"));
    assertEquals(6, calculator.evaluateInfix("2*3"));
    assertEquals(2, calculator.evaluateInfix("6/3"));
    assertEquals(2, calculator.evaluateInfix("5%3"));
  }

  @Test
  public void testMultiDigitNumbers() {
    assertEquals(55, calculator.evaluateInfix("22+33"));
    assertEquals(100, calculator.evaluateInfix("50*2"));
    assertEquals(45, calculator.evaluateInfix("100-55"));
  }

  @Test
  public void testParentheses() {
    assertEquals(20, calculator.evaluateInfix("(2+3)*4"));
    assertEquals(14, calculator.evaluateInfix("2+(3*4)"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullExpression() {
    calculator.evaluateInfix(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyExpression() {
    calculator.evaluateInfix("");
  }

  @Test(expected = ArithmeticException.class)
  public void testDivideByZero() {
    calculator.evaluateInfix("5/0");
  }
}