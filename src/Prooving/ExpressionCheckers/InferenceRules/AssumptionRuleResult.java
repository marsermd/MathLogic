package Prooving.ExpressionCheckers.InferenceRules;

import Prooving.ExpressionCheckers.ExpressionCheckResult;
import SyntaxTree.Structure.Expression;

/**
 * Created by marsermd on 06.02.2017.
 */
public class AssumptionRuleResult extends ExpressionCheckResult
{
    public final Expression assumption;
    private AssumptionRuleResult(Type type, String reason, Expression assumption)
    {
        super(type, reason);
        this.assumption = assumption;
    }

    public static AssumptionRuleResult right(Expression assumption)
    {
        return new AssumptionRuleResult(Type.Right, null, assumption);
    }
}
