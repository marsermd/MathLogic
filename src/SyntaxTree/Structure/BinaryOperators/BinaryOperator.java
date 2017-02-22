package SyntaxTree.Structure.BinaryOperators;

import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.Operator;
import SyntaxTree.Structure.Variable;
import SyntaxTree.Utils.StringHash;

import java.util.List;
import java.util.Set;

/**
 * Created by marsermd on 08.01.2017.
 */
public abstract class BinaryOperator extends Operator
{
    private Expression left, right;
    protected int hash;

    public BinaryOperator(Expression left, Expression right)
    {
        this.left = left;
        this.right = right;

        hash = StringHash.calculate(getSymbol(), HASH_PRIME);

        hash *= HASH_PRIME;
        hash += left.getExpressionHash();


        hash *= HASH_PRIME;
        hash += right.getExpressionHash();
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
    public void toParsableString(StringBuilder builder)
    {
        builder.append("(");
        left.toParsableString(builder);
        builder.append(getSymbol());
        right.toParsableString(builder);
        builder.append(")");
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
        return hash;
    }

    @Override
    public void getBindedAndFree(Set<Variable> binded, Set<Variable> free, List<Variable> quantified)
    {
        left.getBindedAndFree(binded, free, quantified);
        right.getBindedAndFree(binded, free, quantified);
    }

    @Override
    public boolean isFreeToReplace(List<Variable> binded, Variable from, Set<Variable> toReplace)
    {
        return
            left.isFreeToReplace(binded, from, toReplace) &&
            right.isFreeToReplace(binded, from, toReplace);
    }
}
