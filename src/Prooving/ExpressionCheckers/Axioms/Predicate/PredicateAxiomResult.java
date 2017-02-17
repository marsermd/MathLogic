package Prooving.ExpressionCheckers.Axioms.Predicate;

import Prooving.ExpressionCheckers.ExpressionCheckResult;
import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.Variable;

/**
 * Created by marsermd on 05.02.2017.
 */
public class PredicateAxiomResult extends ExpressionCheckResult
{
    public final Expression phi;
    public final Variable alpha;
    public final Expression theta;
    private PredicateAxiomResult(Type type, String reason, Expression phi, Variable alpha, Expression theta)
    {
        super(type, reason);
        this.phi = phi;
        this.alpha = alpha;
        this.theta = theta;
    }

    public static PredicateAxiomResult termIsNotFreeToReplace(Expression phi, Variable alpha, Expression theta)
    {
        String reason = String.format("терм %s не свободен для подстановки в формулу %s вместо переменной %s.",
            theta.toParsableString(),
            phi.toParsableString(),
            alpha.getName());
        return new PredicateAxiomResult(Type.Wrong, reason, phi, alpha, theta);
    }
}
