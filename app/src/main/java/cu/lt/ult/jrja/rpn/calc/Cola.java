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
			this.first.setPróximo(this.last);
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

		if (!this.isEmpty())
		{
			Nodo tmp = this.first;
			cantidad = 1;
			while (tmp.getPróximo() != null)
			{
				tmp = tmp.getPróximo();
				cantidad += 1;
			}
		}

		return cantidad;
	}

	public void print()
	{
		if (!this.isEmpty())
		{
			Nodo tmp = this.first;
			do
			{
				System.out.println(tmp.getDato());
				tmp = tmp.getPróximo();
			}while(tmp != null);
		}
	}

	@Override
	public String toString()
	{
		if (this.first != null)
		{
			String result = "";
			Nodo tmp = this.first;
			while (tmp != null)
			{
				result += (tmp.getDato() + "; ");
				tmp = tmp.getPróximo();
			}
			result = result.trim();
			result = "{" + result.substring(0, result.length() - 1) + "}";
			return result;
		}
		return null;
	}

}
