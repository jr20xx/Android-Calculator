package cu.lt.joe.calculator.utils;

import cu.lt.joe.calculator.data_structures.Stack;
import cu.lt.joe.calculator.exceptions.InfinityResultException;
import cu.lt.joe.calculator.exceptions.NotNumericResultException;
import cu.lt.joe.calculator.exceptions.UnregisteredOperationException;
import java.math.BigDecimal;

public class RPN
{
    public static void solve(Stack<String> stack)
    {
        String number1, number2;
        Stack<String> solution = new Stack<String>();
        while (!(stack.isEmpty()))
        {
            String poped = stack.pop();
            if (isOperator(poped))
            {
                number1 = solution.pop();
                if (solution.isEmpty())
                {
                    break;
                }
                number2 = solution.pop();

                if (number2 == null || number2.equalsIgnoreCase("nan") || number1 == null || number1.equalsIgnoreCase("nan"))
                    throw new NotNumericResultException();
                else if (number2.equalsIgnoreCase("infinity") || number1.equalsIgnoreCase("infinity"))
                    throw new InfinityResultException();
                else
                    solution.push(makeOperation(number2, poped, number1));
            }
            else
            {
                solution.push(poped);
            }
        }
        String result = solution.pop();
        if (result == null || result.equalsIgnoreCase("nan"))
            throw new NotNumericResultException();
        else if (result.equalsIgnoreCase("infinity"))
            throw new InfinityResultException();
        else
            stack.push(result);
    }

    private static boolean isOperator(String x)
    {
        return (x.equals("+")) || (x.equals("-")) || (x.equals("×")) || (x.equals("÷")) || (x.equals("%"));
    }

    private static String makeOperation(String x, String oper, String y)
    {
        BigDecimal a = new BigDecimal(x);
        BigDecimal b = new BigDecimal(y);
        switch (oper)
        {
            case("+"): return (a.add(b)) + "";
            case("-"): return (a.subtract(b)) + "";
            case("×"): return (a.multiply(b)) + "";
            case("÷"):
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
            default: throw new UnregisteredOperationException("Not declared operation");
        }
    }
}
