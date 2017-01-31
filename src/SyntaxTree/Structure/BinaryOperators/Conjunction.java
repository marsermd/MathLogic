package SyntaxTree.Structure.BinaryOperators;

import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.Variable;

import java.util.List;

/**
 * Created by marsermd on 08.01.2017.
 */
public class Conjunction extends BinaryOperator
{
    public Conjunction(Expression left, Expression right)
    {
        super(left, right);
    }

    @Override
    public String getSymbol()
    {
        return "&";
    }

    @Override
    public Expression replaceInternal(Variable toReplace, Expression result, List<Variable> quantified)
    {
        return new Conjunction(
            getLeft().replaceInternal(toReplace, result, quantified),
            getRight().replaceInternal(toReplace, result, quantified)
        );
    }
}
