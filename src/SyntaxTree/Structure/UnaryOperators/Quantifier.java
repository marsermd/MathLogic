package SyntaxTree.Structure.UnaryOperators;

import SyntaxTree.Structure.Expression;

/**
 * Created by marsermd on 08.01.2017.
 */
public abstract class Quantifier extends UnaryOperator
{
    private String quantified;

    public Quantifier(String quantified, Expression expression)
    {
        super(expression);
        this.quantified = quantified;
    }

    public String getQuantified()
    {
        return quantified;
    }

    @Override
    public boolean fairEquals(Expression o)
    {
        if (!(o instanceof Quantifier))
        {
            return false;
        }
        return quantified.equals(((Quantifier) o).quantified) && super.fairEquals(o);
    }
}
