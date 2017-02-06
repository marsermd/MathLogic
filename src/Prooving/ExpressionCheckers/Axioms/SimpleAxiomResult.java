package Prooving.ExpressionCheckers.Axioms;

import Prooving.ExpressionCheckers.ExpressionCheckResult;
import SyntaxTree.Structure.Expression;

/**
 * Created by marsermd on 05.02.2017.
 */
public class SimpleAxiomResult extends ExpressionCheckResult
{
    public final Expression proved;
    private SimpleAxiomResult(Type type, String reason, Expression proved)
    {
        super(type, reason);
        this.proved = proved;
    }

    public static SimpleAxiomResult right(Expression proved)
    {
        return new SimpleAxiomResult(Type.Right, null, proved);
    }
}
