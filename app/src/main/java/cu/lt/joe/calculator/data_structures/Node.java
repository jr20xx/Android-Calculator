package cu.lt.joe.calculator.data_structures;

public class Node<T>
{
    private T data;
    private Node next;

    public Node(T data)
    {
        this.data = data;
    }

    public void setData(T data)
    {
        this.data = data;
    }

    public T getData()
    {
        return this.data;
    }

    public void setNext(Node next)
    {
        this.next = next;
    }

    public Node getNext()
    {
        return this.next;
    }
    
    @Override
    public String toString()
    {
        return (this.data + "");
    }
}
