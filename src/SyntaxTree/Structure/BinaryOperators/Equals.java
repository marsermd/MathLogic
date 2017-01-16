package SyntaxTree.Structure.BinaryOperators;

import SyntaxTree.Structure.Expression;

/**
 * Created by marsermd on 16.01.2017.
 */
public class Equals extends BinaryOperator
{
    public Equals(Expression left, Expression right)
    {
        super(left, right);
    }

    @Override
    public String getSymbol()
    {
        return "=";
    }
}
