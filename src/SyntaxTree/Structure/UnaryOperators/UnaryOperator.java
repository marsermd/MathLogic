package SyntaxTree.Structure.UnaryOperators;

import SyntaxTree.Structure.Expression;

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
    public boolean FairEquals(Expression o)
    {
        if (o.getClass() != getClass())
        {
            return false;
        }
        UnaryOperator other = (UnaryOperator) o;
        return other.getExpression().FairEquals(expression);
    }
}
