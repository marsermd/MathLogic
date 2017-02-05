package Prooving.ExpressionCheckers.Axioms.Predicate;

import Prooving.ExpressionCheckers.ExpressionCheckResult;
import SyntaxTree.Structure.Expression;

/**
 * Created by marsermd on 05.02.2017.
 */
public class PredicateCheckResult extends ExpressionCheckResult
{
    public final Expression phi, psi, alpha;
    private PredicateCheckResult(Type type, String reason, Expression phi, Expression psi, Expression alpha)
    {
        super(type, reason);
        this.phi = phi;
        this.psi = psi;
        this.alpha = alpha;
    }

    public PredicateCheckResult right(Expression phi, Expression psi, Expression alpha)
    {
        return new PredicateCheckResult(Type.Right, null, phi, psi, alpha);
    }

}
