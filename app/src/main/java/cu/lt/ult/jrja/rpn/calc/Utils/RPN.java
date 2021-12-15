package cu.lt.ult.jrja.rpn.calc.Utils;

import java.math.*;

import cu.lt.ult.jrja.rpn.calc.DataStructures.*;
import cu.lt.ult.jrja.rpn.calc.Exceptions.*;

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
		BigDecimal a = new BigDecimal(x);
		BigDecimal b = new BigDecimal(y);
		switch (oper)
		{
			case("+"): return (a.add(b)) + "";
			case("-"): return (a.subtract(b)) + "";
			case("*"): return (a.multiply(b)) + "";
			case("/"):
                {
                    try
                    {
                        return (a.divide(b)) + "";
                    }
                    catch (Exception e)
                    {
                        return a.doubleValue() / b.doubleValue() + "";
                    }
                }
			case("%"):
                {
                    try
                    {
                        return (a.remainder(b)) + "";
                    }
                    catch (Exception e)
                    {
                        return a.doubleValue() % b.doubleValue() + "";
                    }
                }
			case("^"): return (Math.pow(Double.parseDouble(x), Double.parseDouble(y))) + "";
			default: throw new OperaciónNoRegistrada("Operación no declarada");
		}
	}
}
