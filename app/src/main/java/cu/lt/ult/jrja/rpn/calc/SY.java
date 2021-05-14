package cu.lt.ult.jrja.rpn.calc;

import java.util.regex.*;

public class SY
{
	private static boolean isNumber(String x)
	{
		return x.matches("^[+-]?\\d+(?:\\.\\d*(?:[eE][+-]?\\d+)?)?$");
	}
	private static boolean isOperator(String x)
	{
		return x.equals("+") || x.equals("-") || x.equals("/") || x.equals("*") || x.equals("%") || x.equals("^");
	}
	private static boolean parOnPair(String expression)
	{
		if (expression.contains("(") || expression.contains(")"))
		{
			int counter = 0;
			for (char c : expression.toCharArray())
			{
				if (c == '(')
					counter += 1;
				else if (c == ')')
					counter -= 1;
			}
			return (counter == 0);
		}
		return true;
	}

	private static Cola<String> splitter(String expression)
	{
		Cola<String> elements = new Cola<String>();

		if (parOnPair(expression))
		{
			String tmp = "";
			int start = 0;
			if ((expression.charAt(start) == '-'))
			{
				tmp += "n";
				start += 1;
			}

			for (int i = start; i < expression.length(); i++)
			{
				if ((expression.charAt(i) == '-') && !isNumber(expression.charAt(i - 1) + "") && (!(expression.charAt(i - 1) + "").equals(")")))
				{
					tmp += "n";
				}
				else
				{
					tmp += expression.charAt(i);
				}
			}

			String things[] = tmp.replace("(", "( ").replace("+", " + ").replace("-", " - ").replace("*", " * ").replace("/", " / ").replace("%", " % ").replace("^", " ^ ").replace(")", " )").replace("n", "-").trim().split(" ");
			for (String s : things)
			{
				elements.add(s);
			}			
		}//if parenthesis are correctly balanced
		else
		{
			throw new ParéntesisSinEmparejar("Los paréntesis no están bien colocados");
		}
		return elements;
	}//end of splitter
	private static int precedence(String oper)
	{
		switch (oper)
		{
			case "^": return 3;
			case "*": case "/": return 2;
			case "+": case "-": return 1;
			default: return 0;
		}
	}

	public static Cola<String> translate(String expression)
	{
		Cola<String> elements=splitter(expression);
		Cola<String> out = new Cola<String>();
		Pila<String> operators=new Pila<String>();

		while (!elements.isEmpty())
		{
			String tmp = elements.extract();

			if (isNumber(tmp))
			{
				out.add(tmp);
			}
			else if (isOperator(tmp))
			{
				if (operators.isEmpty())
				{
					operators.push(tmp);
				}
				else
				{
					if(leftA(tmp))
					{
						while((!operators.isEmpty())&&(precedence(operators.peak()) >= precedence(tmp)))
						{
							out.add(operators.pop());
						}
						operators.push(tmp);
					}
					else
					{
						while((!operators.isEmpty())&&(precedence(operators.peak()) > precedence(tmp)))
						{
							out.add(operators.pop());
						}
						operators.push(tmp);
					}
				}				
			}
			else if (tmp.equals("("))
			{
				operators.push(tmp);
			}
			else if (tmp.equals(")"))
			{
				while (!operators.isEmpty())
				{
					if (operators.peak().equals("("))
					{
						operators.pop();
					}
					else
					{
						out.add(operators.pop());
					}
				}
			}
		}//while there are elements to take

		while (!operators.isEmpty())
		{
			out.add(operators.pop());
		}
		return out;
	}

	private static boolean leftA(String tmp)
	{
		return !(tmp.equals("^"));
	} 
}
