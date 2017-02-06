package Prooving.ExpressionCheckers.InferenceRules.Predicate;

import Prooving.ExpressionCheckers.ExpressionCheckResult;
import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.Variable;

/**
 * Created by marsermd on 05.02.2017.
 */
public class PredicateRuleResult extends ExpressionCheckResult
{
    public final Expression psi;
    public final Variable x;
    public final Expression phi;

    private PredicateRuleResult(Type type, String reason, Variable x, Expression phi, Expression psi)
    {
        super(type, reason);
        this.psi = psi;
        this.x = x;
        this.phi = phi;
    }

    public static ExpressionCheckResult right(Variable x, Expression phi, Expression psi)
    {
        return new PredicateRuleResult(
            Type.Right, null, x, phi, psi);
    }

    public static ExpressionCheckResult variableIsFreeInFormula(Variable x, Expression phi, Expression psi)
    {
        return new PredicateRuleResult(
            Type.Wrong,
            String.format("переменная %s входит свободно в формулу %s.", x, phi),
            x, phi, psi);
    }
}
