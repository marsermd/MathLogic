package SyntaxTree.Structure.BinaryOperators;

import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.Operator;

/**
 * Created by marsermd on 08.01.2017.
 */
public abstract class BinaryOperator extends Operator
{
    private Expression left, right;

    public BinaryOperator(Expression left, Expression right)
    {
        this.left = left;
        this.right = right;
    }

    public Expression getLeft()
    {
        return left;
    }

    public Expression getRight()
    {
        return right;
    }

    public abstract String getSymbol();

    @Override
    public String toString()
    {
        return "[" + left + ", " + getSymbol() + ", " + right + "]";
    }

    @Override
    public boolean fairEquals(Expression o)
    {
        if (o.getClass() != getClass())
        {
            return false;
        }

        BinaryOperator other = (BinaryOperator) o;
        return other.getLeft().fairEquals(left) && other.getRight().fairEquals(right);
    }
}
