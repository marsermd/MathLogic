package SyntaxTree.Structure;

import SyntaxTree.Structure.Expression;

/**
 * Created by marsermd on 18.01.2017.
 */
public class AnyFormula extends Expression
{
    private Expression equalExpression = null;

    @Override
    public boolean fairEquals(Expression expression)
    {
        if (equalExpression == null)
        {
            equalExpression = expression;
            return true;
        }
        else
        {
            return equalExpression.fairEquals(expression);
        }
    }
}
