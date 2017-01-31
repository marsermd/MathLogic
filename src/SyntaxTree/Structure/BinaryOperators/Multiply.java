package SyntaxTree.Structure.BinaryOperators;

import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.Variable;

import java.util.List;

/**
 * Created by marsermd on 16.01.2017.
 */
public class Multiply extends BinaryOperator
{
    public Multiply(Expression left, Expression right)
    {
        super(left, right);
    }

    @Override
    public String getSymbol()
    {
        return "*";
    }

    @Override
    public Expression replaceInternal(Variable toReplace, Expression result, List<Variable> quantified)
    {
        return new Multiply(
            getLeft().replaceInternal(toReplace, result, quantified),
            getRight().replaceInternal(toReplace, result, quantified)
        );
    }
}
