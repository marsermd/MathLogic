package SyntaxTree.Structure.UnaryOperators;

import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.Variable;
import SyntaxTree.Utils.StringHash;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by marsermd on 08.01.2017.
 */
public abstract class UnaryOperator extends Expression
{
    private Expression expression;

    public UnaryOperator(Expression expression)
    {
        this.expression = expression;
    }

    public Expression getExpression()
    {
        return expression;
    }

    /**
     * @return whole transcription of operator. For example, @A
     */
    public abstract String getSymbol();

    @Override
    public String toString()
    {
        return "[" + getSymbol() + ", " + getExpression() + "]";
    }

    @Override
    public boolean fairEquals(Expression o)
    {
        if (o.getClass() != getClass())
        {
            return false;
        }
        UnaryOperator other = (UnaryOperator) o;
        return expression.fairEquals(other.getExpression());
    }

    @Override
    public void getBindedAndFree(Set<Variable> binded, Set<Variable> free, List<Variable> quantified)
    {
        expression.getBindedAndFree(binded, free, quantified);
    }

    @Override
    public int getExpressionHash()
    {
        int result = StringHash.calculate(getClass().toString() + getSymbol(), HASH_PRIME);

        result *= HASH_PRIME;
        result += expression.getExpressionHash();

        return result;
    }
}
