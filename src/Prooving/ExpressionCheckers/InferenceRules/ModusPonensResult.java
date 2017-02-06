package Prooving.ExpressionCheckers.InferenceRules;

import Prooving.ExpressionCheckers.ExpressionCheckResult;
import SyntaxTree.Structure.BinaryOperators.Implication;
import SyntaxTree.Structure.Expression;

/**
 * Created by marsermd on 06.02.2017.
 */
public class ModusPonensResult extends ExpressionCheckResult
{
    public final Expression alpha;
    public final Expression beta;
    public final Expression alphaBeta;


    private ModusPonensResult(Type type, String reason, Expression alpha, Expression beta)
    {
        super(type, reason);
        this.alpha = alpha;
        this.beta = beta;
        this.alphaBeta = new Implication(alpha, beta);
    }

    public static ModusPonensResult right(Expression alpha, Expression beta)
    {
        return new ModusPonensResult(Type.Right, null, alpha, beta);
    }
}
