package cu.lt.joe.calculator.exceptions;

public class NotNumericResultException extends RuntimeException
{
    public NotNumericResultException(String text)
    {
        super(text);
    }
    
    public NotNumericResultException()
    {
        super();
    }
}
