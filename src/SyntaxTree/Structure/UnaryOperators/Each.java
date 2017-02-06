package SyntaxTree.Structure.UnaryOperators;

import SyntaxTree.Structure.AnyFormula;
import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.Variable;

import java.util.Collection;
import java.util.List;

/**
 * Created by marsermd on 08.01.2017.
 */
public class Each extends Quantifier
{
    public Each(Expression quantified, Expression expression)
    {
        super(quantified, expression);
    }

    @Override
    public String getSymbol()
    {
        return "@" + getQuantified();
    }

    @Override
    public Expression replaceInternal(Variable toReplace, Expression result, List<Variable> quantified)
    {
        Collection<Variable> toBind = getQuantified().getFree();

        quantified.addAll(toBind);
        Each newEach = new Each(
            getQuantified().replaceInternal(toReplace, result, quantified),
            getExpression().replaceInternal(toReplace, result, quantified)
        );
        for (int i = 0; i < toBind.size(); i++)
        {
            quantified.remove(quantified.size() - 1);
        }
        return newEach;
    }
}
