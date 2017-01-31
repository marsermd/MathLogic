package SyntaxTree.Structure.UnaryOperators;

import SyntaxTree.Structure.AnyFormula;
import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.Variable;
import SyntaxTree.Utils.StringHash;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by marsermd on 08.01.2017.
 */
public abstract class Quantifier extends UnaryOperator
{
    private Expression quantified;

    public Quantifier(Expression quantified, Expression expression)
    {
        super(expression);
        this.quantified = quantified;
    }

    public Expression getQuantified()
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
        return quantified.fairEquals(((Quantifier) o).quantified) && super.fairEquals(o);
    }

    @Override
    public int getExpressionHash()
    {
        int result = super.getExpressionHash();

        result *= HASH_PRIME;
        result += quantified.getExpressionHash();

        return result;
    }

    @Override
    public void getBindedAndFree(Set<Variable> binded, Set<Variable> free, List<Variable> quantified)
    {
        Collection<Variable> toBind = this.quantified.getFree();
        binded.addAll(toBind);

        quantified.addAll(toBind);
        getExpression().getBindedAndFree(binded, free, quantified);
        for (int i = 0; i < toBind.size(); i++)
        {
            quantified.remove(quantified.size() - 1);
        }
    }
}
