package cu.lt.ult.jrja.rpn.calc;

public class Cola<T>
{
	private Nodo first, last;
	private int cant;

	public Cola()
	{
		this.last = this.first = null;
		this.cant = 0;
	}

	public boolean isEmpty()
	{
		return (this.first == null);
	}

	public void add(T dato)
	{
		Nodo nuevo = new Nodo(dato);
		if (this.isEmpty())
		{
			this.last = this.first = nuevo;
			this.first.setPróximo(null);
			this.last.setPróximo(this.first);
		}
		else
		{
			this.last.setPróximo(nuevo);
			this.last = nuevo;
		}
		this.cant += 1;
	}

	public T extract()
	{
		if (!this.isEmpty())
		{
			T dato = (T)this.first.getDato();
			if (this.first != this.last)
				this.first = this.first.getPróximo();
			else
				this.first = this.last = null;
			this.cant -= 1;
			return dato;
		}
		return null;
	}

	public T getFirst()
	{
		if (!this.isEmpty())
		{
			return (T)first.getDato();
		}
		return null;
	}

	public T getLast()
	{
		if (!this.isEmpty())
		{
			return (T)last.getDato();
		}
		return null;
	}
	
	@Deprecated
	public int length()
	{
		return this.cant;
	}
	
	public int size()
	{
		int cantidad = 0;
		
		if(!this.isEmpty())
		{
			Nodo tmp = this.first;
			cantidad = 1;
			while(tmp.getPróximo()!=null)
			{
				tmp = tmp.getPróximo();
				cantidad += 1;
			}
		}
		
		return cantidad;
	}

}
