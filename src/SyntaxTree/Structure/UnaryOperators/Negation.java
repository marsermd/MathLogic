package SyntaxTree.Structure.UnaryOperators;

import SyntaxTree.Structure.Expression;

/**
 * Created by marsermd on 08.01.2017.
 */
public class Negation extends UnaryOperator
{
    public Negation(Expression expression)
    {
        super(expression);
    }

    @Override
    public String getSymbol()
    {
        return "!";
    }
}
