package cu.lt.ult.jrja.rpn.calc.exceptions;

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
