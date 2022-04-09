package cu.lt.joe.calculator.exceptions;

public class InfinityResultException extends RuntimeException 
{
    public InfinityResultException(String text)
    {
        super(text);
    }
    
    public InfinityResultException()
    {
        super();
    }
}
