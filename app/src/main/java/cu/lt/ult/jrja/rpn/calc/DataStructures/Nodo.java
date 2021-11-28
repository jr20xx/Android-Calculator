package cu.lt.ult.jrja.rpn.calc.DataStructures;
public class Nodo<T>
{
	private T dato;
	private Nodo próximo;

	public Nodo(T dato)
	{
		this.dato = dato;
		this.próximo = null;
	}

	public void setDato(T dato)
	{
		this.dato = dato;
	}

	public T getDato()
	{
		return this.dato;
	}

	public void setPróximo(Nodo sucesor)
	{
		this.próximo = sucesor;
	}

	public Nodo getPróximo()
	{
		return this.próximo;
	}
	
}
