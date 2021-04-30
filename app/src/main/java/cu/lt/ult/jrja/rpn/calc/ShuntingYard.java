package cu.lt.ult.jrja.rpn.calc;

public class ShuntingYard
{
	private String operation;
	private Cola<String> out;
	private Cola<String> normal;

	public ShuntingYard(String oper) throws ParéntesisSinEmparejar
	{
		this.operation = oper;
		this.out = new Cola<String>();
		this.normal = new Cola<String>();
		this.splitter();
	}

	private void splitter() throws ParéntesisSinEmparejar
	{
		if (this.parOnPair())
		{
			String tmp = this.operation;
			String things[] = tmp.replace("(", "( ").replace("+", " + ").replace("-", " - ").replace("*", " * ").replace("/", " / ").replace("%", " % ").replace("^", " ^ ").replace(")", " )").trim().split(" ");
			for (String s : things)
			{
				this.normal.add(s);
			}
		}
		else
		{
			throw new ParéntesisSinEmparejar("Falta algún paréntesis en la expresión");
		}
	}

	private boolean parOnPair()
	{
		int open=0, closure=0;
		for (char c : this.operation.toCharArray())
		{
			if (c == '(')
				open += 1;
			if (c ==  ')')
				closure += 1;
		}
		return (open == closure);
	}

	private boolean isNumber(String x)
	{
		return (x.matches("\\d+(\\.\\d+)?"));
	}

	private int precedenceOnStack(String oper)
	{
		switch (oper)
		{
			case "^": return 4;
			case "*": case "/": case "%": return 2;
			case "+": case "-": return 1;
			default: return 0;
		}
	}

	private int precedenceOnOperation(String oper)
	{
		switch (oper)
		{
			case "^": return 3;
			case "*": case "/": case "%": return 2;
			case "+": case "-": return 1;
			default: return 0;
		}
	}

	private boolean isOperator(String x)
	{
		return x.matches("[-+\\*/%^]");
	}

	private boolean darPrecedencia(String oper1, String oper2)
	{
		return precedenceOnOperation(oper1) <= precedenceOnStack(oper2);
	}

	public Cola<String> shuntingYard() throws ParéntesisSinEmparejar
	{
		Pila<String> stack = new Pila<String>();
		String elemento;
		int contador = 0;

		while (!normal.isEmpty())
		{
			elemento = normal.extract();
			if (isNumber(elemento))
			{
				this.out.add(elemento);
			}
			else if (elemento.equals("("))
			{
				stack.push(elemento);
				contador += 1;
			}
			else if (isOperator(elemento))
			{
				if (!stack.isEmpty() && darPrecedencia(elemento, stack.peak()))
				{
					out.add(stack.pop());
					stack.push(elemento);
				}
				else
				{
					stack.push(elemento);
				}
			}
			else if (elemento.equals(")"))
			{
				contador -= 1;
				if (!stack.isEmpty())
				{
					while (true)
					{
						if (!stack.isEmpty())
						{
							if (!stack.peak().equals("("))
							{
								out.add(stack.pop());
							}
							else
							{
								stack.pop();
								break;
							}
						}
						else
						{
							break;
						}
					}
				}
				else
				{
					throw new ParéntesisSinEmparejar("");
				}
			}
			if (contador < 0)
			{
				throw new ParéntesisSinEmparejar("");
			}
		}//mientras haya elementos por leer

		while (!stack.isEmpty())
		{
			if (!stack.peak().equals("("))
			{
				out.add(stack.pop());
			}
			else
			{
				throw new ParéntesisSinEmparejar("");
			}
		}

		return this.out;
	}

}
