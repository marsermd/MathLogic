package SyntaxTree.Structure.UnaryOperators;

import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.Variable;

/**
 * Created by marsermd on 08.01.2017.
 */
public class Each extends Quantifier
{
    public Each(String quantified, Expression expression)
    {
        super(quantified, expression);
    }

    @Override
    public String getSymbol()
    {
        return "@" + getQuantified();
    }
}
