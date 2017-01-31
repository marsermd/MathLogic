package SyntaxTree.Structure.BinaryOperators;

import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.Operator;
import SyntaxTree.Structure.Variable;
import SyntaxTree.Utils.StringHash;

import java.util.Collection;
import java.util.List;
import java.util.Set;

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
        return left.fairEquals(other.getLeft()) && right.fairEquals(other.getRight());
    }

    @Override
    public int getExpressionHash()
    {
        int result = StringHash.calculate(getClass().toString() + getSymbol(), HASH_PRIME);

        result *= HASH_PRIME;
        result += left.getExpressionHash();


        result *= HASH_PRIME;
        result += right.getExpressionHash();

        return result;
    }

    @Override
    public void getBindedAndFree(Set<Variable> binded, Set<Variable> free, List<Variable> quantified)
    {
        left.getBindedAndFree(binded, free, quantified);
        right.getBindedAndFree(binded, free, quantified);
    }
}
