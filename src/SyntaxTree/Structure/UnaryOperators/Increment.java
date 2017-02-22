package SyntaxTree.Structure.UnaryOperators;

import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.Variable;

import java.util.List;
import java.util.Set;

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
    public void toParsableString(StringBuilder builder)
    {
        getExpression().toParsableString(builder);
        builder.append("'");
    }

    @Override
    public Expression replaceInternal(Variable toReplace, Expression result, List<Variable> quantified)
    {
        return new Increment(getExpression().replaceInternal(toReplace, result, quantified));
    }

    @Override
    public boolean isFreeToReplace(List<Variable> binded, Variable from, Set<Variable> toReplace)
    {
        return getExpression().isFreeToReplace(binded, from, toReplace);
    }
}
