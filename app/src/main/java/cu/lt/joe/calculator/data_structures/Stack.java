package cu.lt.joe.calculator.data_structures;

public class Stack<T>
{
    private Node peak;
    private int length;

    public Stack()
    {
        this.length = 0;
    }

    public Stack(Queue<T> queue)
    {
        this.peak = null;
        while (!queue.isEmpty())
        {
            this.push(queue.extract());
        }
        Stack<T> tmp = new Stack<T>();
        while (!this.isEmpty())
        {
            tmp.push(this.pop());
        }
        this.peak = tmp.peak;
    }

    public Stack(T data)
    {
        this.peak = new Node(data);
        this.length = 1;
    }

    public boolean isEmpty()
    {
        return (this.peak == null);
    }

    @Deprecated
    public int length()
    {
        return this.length;
    }

    public int size()
    {
        int length = 0;  
        if (!this.isEmpty())
        {
            Node tmp = this.peak;
            length = 1;
            while (tmp.getNext() != null)
            {
                tmp = tmp.getNext();
                length += 1;
            }
        }
        return length;
    }

    public T pop()
    {
        if (!isEmpty())
        {
            Node tmp = this.peak;
            this.peak = peak.getNext();
            this.length -= 1;
            return (T)tmp.getData();
        }
        return null;
    }

    public void push(T data)
    {
        Node tmp = new Node(data);
        if (isEmpty())
        {
            this.peak = tmp;
        }
        else
        {
            tmp.setNext(peak);
            this.peak = tmp;
        }
        this.length += 1;
    }

    public T peak()
    {
        if (!isEmpty())
            return (T)this.peak.getData();
        return null;
    }

    public void print()
    {
        if (!this.isEmpty())
        {
            Node tmp = this.peak;
            do
            {
                System.out.println(tmp.getData());
                tmp = tmp.getNext();
            }while(tmp != null);
        }
    }

    @Override
    public String toString()
    {
        String result = "";
        if (!this.isEmpty())
        {
            Node tmp = this.peak;
            while (tmp != null)
            {
                result += (tmp.getData() + "; ");
                tmp = tmp.getNext();
            }
            result = result.trim();
            result = "{" + result.substring(0, result.length() - 1) + "}";
		}
        return result;
    }
}
