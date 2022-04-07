package cu.lt.ult.jrja.rpn.calc.exceptions;

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
