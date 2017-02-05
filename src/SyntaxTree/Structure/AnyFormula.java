package SyntaxTree.Structure;

import SyntaxTree.Structure.Expression;
import sun.plugin.dom.exception.InvalidStateException;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by marsermd on 18.01.2017.
 */
public class AnyFormula extends Expression
{
    private Expression equalExpression = null;

    public void reset()
    {
        this.equalExpression = null;
    }

    public void setEqualExpression(Expression equalExpression)
    {
        this.equalExpression = equalExpression;
    }

    @Override
    public String toString()
    {
        if (equalExpression != null)
        {
            return "[any as " + equalExpression + "]";
        }
        else
        {
            return "[any]";
        }
    }

    public Expression getEqualExpression()
    {
        return equalExpression;
    }

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
            while (expression instanceof AnyFormula)
            {
                expression = ((AnyFormula) expression).equalExpression;
            }
            return equalExpression.fairEquals(expression);
        }
    }

    @Override
    public Expression replaceInternal(Variable toReplace, Expression result, List<Variable> quantified)
    {
        if (equalExpression == null)
        {
            throw new InvalidStateException("equalExpression == null, but truing to replace");
        }
        return equalExpression.replaceInternal(toReplace, result, quantified);
    }

    @Override
    public int getExpressionHash()
    {
        if (equalExpression == null)
        {
            return 0;
        }
        return equalExpression.getExpressionHash();
    }

    @Override
    public void getBindedAndFree(Set<Variable> binded, Set<Variable> free, List<Variable> quantified)
    {
        if (equalExpression == null)
        {
            throw new InvalidStateException("equalExpression == null, but truing to get binded & free");
        }
        equalExpression.getBindedAndFree(binded, free, quantified);
    }
}
