package SyntaxTree.Structure.BinaryOperators;

import SyntaxTree.Structure.Expression;

/**
 * Created by marsermd on 08.01.2017.
 */
public class Disjunction extends BinaryOperator
{
    public Disjunction(Expression left, Expression right)
    {
        super(left, right);
    }

    @Override
    public String getSymbol()
    {
        return "|";
    }
}
