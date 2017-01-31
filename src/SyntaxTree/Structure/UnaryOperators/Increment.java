package SyntaxTree.Structure.UnaryOperators;

import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.Variable;

import java.util.List;

/**
 * Created by marsermd on 16.01.2017.
 */
public class Increment extends UnaryOperator
{
    public Increment(Expression expression)
    {
        super(expression);
    }

    @Override
    public String getSymbol()
    {
        return "'";
    }

    @Override
    public Expression replaceInternal(Variable toReplace, Expression result, List<Variable> quantified)
    {
        return new Increment(getExpression().replaceInternal(toReplace, result, quantified));
    }
}
