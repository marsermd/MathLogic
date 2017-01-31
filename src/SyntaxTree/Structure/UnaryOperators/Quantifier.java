package SyntaxTree.Structure.UnaryOperators;

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
    private Variable quantified;

    public Quantifier(String quantified, Expression expression)
    {
        super(expression);
        this.quantified = new Variable(quantified);
    }

    public Variable getQuantified()
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
        binded.add(this.quantified);

        quantified.add(this.quantified);
        getExpression().getBindedAndFree(binded, free, quantified);
        quantified.remove(quantified.size() - 1);
    }
}
