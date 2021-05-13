package cu.lt.ult.jrja.rpn.calc;

import java.util.regex.*;

public class SY
{
	private String expression;
	private Cola<String> out;
	private Cola<String> elements;
	private Pila<String> operators;

	public SY(String expression)
	{
		this.expression = expression;
		this.out = new Cola<>();
		this.operators = new Pila<String>();
		this.elements = new Cola<String>();
		this.splitter();
	}

	private boolean isNumber(String x)
	{
		return x.matches("^[+-]?\\d+(?:\\.\\d*(?:[eE][+-]?\\d+)?)?$");
		//return x.matches("-?\\d+(\\.\\d+)?");
	}
	private boolean isOperator(String x)
	{
		return x.equals("+") || x.equals("-") || x.equals("/") || x.equals("*") || x.equals("%") || x.equals("^");
	}
	private boolean parOnPair()
	{
		if (this.expression.contains("(") || this.expression.contains(")"))
		{
			int counter = 0;
			for (char c : this.expression.toCharArray())
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

	private void splitter()
	{
		if (this.parOnPair())
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
				this.elements.add(s);
			}			
		}//if parenthesis are correctly balanced
		else
		{
			throw new ParéntesisSinEmparejar("Los paréntesis no están bien colocados");
		}
	}//end of splitter
	private int precedence(String oper)
	{
		switch (oper)
		{
			case "^": return 3;
			case "*": case "/": return 2;
			case "+": case "-": return 1;
			default: return 0;
		}
	}

	public Cola<String> translate()
	{

		while (!this.elements.isEmpty())
		{
			String tmp = this.elements.extract();

			if (isNumber(tmp))
			{
				this.out.add(tmp);
			}
			else if (isOperator(tmp))
			{
				if (this.operators.isEmpty())
				{
					this.operators.push(tmp);
				}
				else
				{
					if(leftA(tmp))
					{
						while((!this.operators.isEmpty())&&(precedence(this.operators.peak()) >= precedence(tmp)))
						{
							this.out.add(this.operators.pop());
						}
						this.operators.push(tmp);
					}
					else
					{
						while((!this.operators.isEmpty())&&(precedence(this.operators.peak()) > precedence(tmp)))
						{
							this.out.add(this.operators.pop());
						}
						this.operators.push(tmp);
					}
				}				
			}
			else if (tmp.equals("("))
			{
				this.operators.push(tmp);
			}
			else if (tmp.equals(")"))
			{
				while (!this.operators.isEmpty())
				{
					if (this.operators.peak().equals("("))
					{
						this.operators.pop();
					}
					else
					{
						this.out.add(this.operators.pop());
					}
				}
			}
		}//while there are elements to take

		while (!this.operators.isEmpty())
		{
			this.out.add(this.operators.pop());
		}
		return this.out;
	}

	private boolean leftA(String tmp)
	{
		return !(tmp.equals("^"));
	} 

}
