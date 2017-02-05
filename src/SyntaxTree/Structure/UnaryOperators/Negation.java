package SyntaxTree.Structure.UnaryOperators;

import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.Variable;

import java.util.List;

/**
 * Created by marsermd on 08.01.2017.
 */
public class Negation extends UnaryOperator
{
    public Negation(Expression expression)
    {
        super(expression);
    }

    @Override
    public String getSymbol()
    {
        return "!";
    }

    @Override
    public void toParsableString(StringBuilder builder)
    {
        builder.append("!");
        getExpression().toParsableString(builder);
    }

    @Override
    public Expression replaceInternal(Variable toReplace, Expression result, List<Variable> quantified)
    {
        return new Negation(getExpression().replaceInternal(toReplace, result, quantified));
    }
}
