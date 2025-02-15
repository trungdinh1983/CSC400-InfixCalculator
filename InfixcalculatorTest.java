import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for InfixCalculator.
 * Contains test cases for all calculator functionality.
 */
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
    assertEquals(15, calculator.evaluateInfix("3*(4+1)"));
  }

  @Test
  public void testComplexExpressions() {
    assertEquals(14, calculator.evaluateInfix("2+3*4"));
    assertEquals(20, calculator.evaluateInfix("(2+3)*4"));
    assertEquals(13, calculator.evaluateInfix("25-12"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullExpression() {
    calculator.evaluateInfix(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEmptyExpression() {
    calculator.evaluateInfix("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCharacters() {
    calculator.evaluateInfix("2+3$5");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMismatchedParentheses() {
    calculator.evaluateInfix("(2+3))+2");
  }

  @Test(expected = ArithmeticException.class)
  public void testDivideByZero() {
    calculator.evaluateInfix("5/0");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidOperators() {
    calculator.evaluateInfix("2++3");
  }

  @Test
  public void testSpacesInExpression() {
    assertEquals(5, calculator.evaluateInfix("2 + 3"));
    assertEquals(14, calculator.evaluateInfix(" 2 + 3 * 4 "));
  }
}