package cu.lt.joe.calculator.exceptions;

public class UnregisteredOperationException extends RuntimeException
{
    public UnregisteredOperationException(String x)
    {
        super(x);
    }
}
