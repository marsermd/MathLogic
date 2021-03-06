package SyntaxTree.Structure.UnaryOperators;

import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.Variable;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by marsermd on 08.01.2017.
 */
public abstract class Quantifier extends UnaryOperator
{
    @Override
    public void toParsableString(StringBuilder builder)
    {
        builder.append("(");
        builder.append(getSymbol()); // get symbol also holds quantified
        builder.append("(");
        getExpression().toParsableString(builder);
        builder.append("))");
    }

    private Expression quantified;

    public Quantifier(Expression quantified, Expression expression)
    {
        super(expression);
        this.quantified = quantified;

        hash *= HASH_PRIME;
        hash += quantified.getExpressionHash();
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

    @Override
    public boolean isFreeToReplace(List<Variable> binded, Variable from, Set<Variable> toReplace)
    {
        Collection<Variable> toBind = this.quantified.getFree();
        binded.addAll(toBind);

        boolean result = getExpression().isFreeToReplace(binded, from, toReplace);

        for (int i = 0; i < toBind.size(); i++)
        {
            binded.remove(binded.size() - 1);
        }
        return result;
    }
}
