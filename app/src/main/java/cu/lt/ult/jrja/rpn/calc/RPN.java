package cu.lt.ult.jrja.rpn.calc;

import java.util.*;

public class RPN<T>
{
	private Pila<T> pila;

	public RPN(Pila<T> stack)
	{
		this.pila = stack;
	}

	public void solve()
	{
		String number1, number2;
		Pila<String> solución = new Pila<String>();
		while (!(this.pila.isEmpty()))
		{
			String poped = this.pila.pop() + "";
			if (isOperator(poped))
			{
				number1 = solución.pop();
				if (solución.isEmpty())
				{
					break;
				}
				number2 = solución.pop();
				solución.push(makeOperation(number2, poped, number1));
			}
			else
			{
				solución.push(poped);
			}
		}
		this.pila.push((T)solución.pop());
	}

	private boolean isOperator(String x)
	{
		return (x.equals("+")) || (x.equals("-")) || (x.equals("*")) || (x.equals("/")) || (x.equals("%")) || (x.equals("^"));
	}

	private String makeOperation(String x, String oper, String y)
	{
		double a = Double.parseDouble(x);
		double b = Double.parseDouble(y);
		switch (oper)
		{
			case("+"): return (a + b) + "";
			case("-"): return (a - b) + "";
			case("*"): return (a * b) + "";
			case("/"): return (a / b) + "";
			case("%"): return (a % b) + "";
			case("^"): return (Math.pow(a, b)) + "";
			default: return "Error!";
		}
	}

}
