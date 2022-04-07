package cu.lt.ult.jrja.rpn.calc.models;

public class HistoryItem
{
    private String operation, result;
    private long id;

    public HistoryItem(long id, String operation, String result)
    {
        this.id = id;
        this.operation = operation;
        this.result = result;
    }

    public String getOperation()
    {
        return this.operation;
    }
    public String getResult()
    {
        return this.result;
    }
    public long getId()
    {
        return this.id;
    }
    @Override
    public String toString()
    {
        return this.operation + " = " + this.result;
    }
}
