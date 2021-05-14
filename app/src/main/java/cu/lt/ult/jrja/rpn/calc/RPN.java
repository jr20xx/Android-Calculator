package cu.lt.ult.jrja.rpn.calc;

import java.util.*;

public class RPN
{
	public static void solve(Pila<String> pila)
	{
		String number1, number2;
		Pila<String> solución = new Pila<String>();
		while (!(pila.isEmpty()))
		{
			String poped = pila.pop();
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
		pila.push(solución.pop());
	}

	private static boolean isOperator(String x)
	{
		return (x.equals("+")) || (x.equals("-")) || (x.equals("*")) || (x.equals("/")) || (x.equals("%")) || (x.equals("^"));
	}

	private static String makeOperation(String x, String oper, String y)
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
			default: throw new OperaciónNoRegistrada("Operación no declarada");
		}
	}
}
