package ProofChecker.ExpressionCheckers.Axioms.Classical;

import ProofChecker.ExpressionCheckers.Axioms.AxiomChecker;
import ProofChecker.ExpressionCheckers.ExpressionCheckResult;
import SyntaxTree.Structure.Expression;

/**
 * Created by marsermd on 01.02.2017.
 */
public abstract class ClassicalAxiomChecker extends AxiomChecker
{
    protected abstract Expression getScheme();

    public ExpressionCheckResult checkMatchesAxiom(Expression expression)
    {
        if (getScheme().fairEquals(expression))
        {
            return ExpressionCheckResult.right();
        }
        else
        {
            return ExpressionCheckResult.wrong();
        }
    }
}
