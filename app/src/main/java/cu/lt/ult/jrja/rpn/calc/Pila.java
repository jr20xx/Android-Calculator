package cu.lt.ult.jrja.rpn.calc;

public class Pila<T>
{
	private Nodo cima;
	private int cant;

	public Pila()
	{
		this.cima = null;
		this.cant = 0;
	}

	public Pila(T dato)
	{
		this.cima = new Nodo(dato);
		this.cant += 1;
	}

	public boolean isEmpty()
	{
		return this.cima == null;
	}

	@Deprecated
	public int length()
	{
		return this.cant;
	}

	public int size()
	{
		int cantidad = 0;		
		if (!this.isEmpty())
		{
			Nodo tmp = this.cima;
			cantidad = 1;
			while(tmp.getPróximo()!=null)
			{
				tmp = tmp.getPróximo();
				cantidad+=1;
			}
		}
		return cantidad;
	}

	public T pop()
	{
		if (!isEmpty())
		{
			Nodo tmp = this.cima;
			this.cima = cima.getPróximo();
			this.cant -= 1;
			return (T)tmp.getDato();
		}
		return null;
	}

	public void push(T dato)
	{
		Nodo tmp = new Nodo(dato);
		if (isEmpty())
		{
			this.cima = tmp;
		}
		else
		{
			tmp.setPróximo(cima);
			cima = tmp;
		}
		this.cant += 1;
	}

	public T peak()
	{
		return (T)this.cima.getDato();
	}

	public void parsePila(Cola<T> queue)
	{
		this.cima = null;
		while (!queue.isEmpty())
		{
			this.push(queue.extract());
		}
		Pila<T> tmp = new Pila<T>();
		while(!this.isEmpty())
		{
			tmp.push(this.pop());
		}
		this.cima = tmp.cima;
	}

}
