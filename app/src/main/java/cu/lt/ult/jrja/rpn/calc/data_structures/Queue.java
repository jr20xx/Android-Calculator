package cu.lt.ult.jrja.rpn.calc.data_structures;

public class Queue<T>
{
    private Node first, last;
    private int length;

    public Queue()
    {
        this.last = this.first = null;
        this.length = 0;
    }

    public boolean isEmpty()
    {
        return (this.first == null);
    }

    public void add(T data)
    {
        Node newNode = new Node(data);
        if (this.isEmpty())
        {
            this.last = this.first = newNode;
            this.first.setNext(this.last);
        }
        else
        {
            this.last.setNext(newNode);
            this.last = newNode;
        }
        this.length += 1;
    }

    public T extract()
    {
        if (!this.isEmpty())
        {
            T data = (T)this.first.getData();
            if (this.first != this.last)
                this.first = this.first.getNext();
            else
                this.first = this.last = null;
            this.length -= 1;
            return data;
        }
        return null;
    }

    public T getFirst()
    {
        if (!this.isEmpty())
        {
            return (T)first.getData();
        }
        return null;
    }

    public T getLast()
    {
        if (!this.isEmpty())
        {
            return (T)last.getData();
        }
        return null;
    }

    @Deprecated
    public int length()
    {
        return this.length;
    }

    public int size()
    {
        int size = 0;

        if (!this.isEmpty())
        {
            Node tmp = this.first;
            size = 1;
            while (tmp.getNext() != null)
            {
                tmp = tmp.getNext();
                size += 1;
            }
        }

        return size;
    }

    public void print()
    {
        if (!this.isEmpty())
        {
            Node tmp = this.first;
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
        if (this.first != null)
        {
            Node tmp = this.first;
            while (tmp != null)
            {
                result += (tmp.getData() + "; ");
                tmp = tmp.getNext();
            }
            result = result.trim();
            result = "{" + result.substring(0, result.length() - 1) + "}";
            return result;
		}
        return result;
    }
}
