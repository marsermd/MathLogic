package SyntaxTree.Structure.UnaryOperators;

import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.Variable;

import java.util.List;

/**
 * Created by marsermd on 08.01.2017.
 */
public class Some extends Quantifier
{
    public Some(String quantified, Expression expression)
    {
        super(quantified, expression);
    }

    @Override
    public String getSymbol()
    {
        return "?" + getQuantified();
    }

    @Override
    public Expression replaceInternal(Variable toReplace, Expression result, List<Variable> quantified)
    {
        quantified.add(getQuantified());
        Some newSome = new Some(
            getQuantified().getName(),
            getExpression().replaceInternal(toReplace, result, quantified)
        );
        quantified.remove(quantified.size() - 1);
        return newSome;
    }
}
