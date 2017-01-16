package SyntaxTree.Structure.UnaryOperators;

import SyntaxTree.Structure.Expression;

/**
 * Created by marsermd on 16.01.2017.
 */
public class Increment extends UnaryOperator
{
    public Increment(Expression expression)
    {
        super(expression);
    }

    @Override
    public String getSymbol()
    {
        return "'";
    }
}
