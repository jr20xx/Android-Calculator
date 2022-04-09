package cu.lt.joe.calculator.exceptions;

public class UnbalancedParenthesesException extends RuntimeException
{
    public UnbalancedParenthesesException(String x)
    {
        super(x);
    }
}
