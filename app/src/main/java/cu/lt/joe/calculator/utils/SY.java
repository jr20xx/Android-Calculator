package cu.lt.joe.calculator.utils;

import cu.lt.joe.calculator.data_structures.Queue;
import cu.lt.joe.calculator.data_structures.Stack;
import cu.lt.joe.calculator.exceptions.UnbalancedParenthesesException;

public class SY
{
    private static boolean isNumber(String x)
    {
        return x.matches("^[+-]?\\d+(?:\\.\\d*(?:[eE][+-]?\\d+)?)?$");
    }
    private static boolean isOperator(String x)
    {
        return x.equals("+") || x.equals("-") || x.equals("×") || x.equals("÷") || x.equals("%");
    }
    private static boolean parOnPair(String expression)
    {
        if (expression.contains("(") || expression.contains(")"))
        {
            Stack<Character> parentheses_stack = new Stack<>();
            for (char c : expression.toCharArray())
            {
                if (c == '(')
                    parentheses_stack.push(c);
                else if (c == ')')
                    if (!parentheses_stack.isEmpty() && parentheses_stack.peak() == '(')
                        parentheses_stack.pop();
                    else
                        return false;
            }
            while (!parentheses_stack.isEmpty())
            {
                char element = parentheses_stack.pop();
                if (element == '(' || element == ')')
                    return false;
            }
        }
        return true;
    }

    private static Queue<String> splitter(String expression)
    {
        Queue<String> elements = new Queue<String>();

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

            String things[] = tmp.replace("(", "( ").replace("+", " + ").replace("-", " - ").replace("×", " × ").replace("÷", " ÷ ").replace("%", " % ").replace(")", " )").replace("n", "-").trim().split(" ");
            for (String s : things)
            {
                elements.add(s);
            }           
        }//if parenthesis are correctly balanced
        else
        {
            throw new UnbalancedParenthesesException("Parentheses are not well placed");
        }
        return elements;
    }//end of splitter

    private static int precedence(String oper)
    {
        switch (oper)
        {
            case "×": case "÷": return 2;
            case "+": case "-": return 1;
            default: return 0;
        }
    }

    public static Queue<String> translate(String expression)
    {
        Queue<String> elements=splitter(expression);
        Queue<String> out = new Queue<String>();
        Stack<String> operators=new Stack<String>();

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
                    while ((!operators.isEmpty()) && (precedence(operators.peak()) > precedence(tmp)))
                    {
                        out.add(operators.pop());
                    }
                    operators.push(tmp);
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
}
