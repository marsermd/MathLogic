package SyntaxTree.Structure.UnaryOperators;

import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.Variable;

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
    public boolean FairEquals(Expression o)
    {
        if (!(o instanceof Quantifier))
        {
            return false;
        }
        return ((Quantifier) o).quantified.equals(quantified) && super.FairEquals(o);
    }
}
