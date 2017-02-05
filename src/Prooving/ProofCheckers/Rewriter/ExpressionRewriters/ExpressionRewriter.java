package Prooving.ProofCheckers.Rewriter.ExpressionRewriters;

import Prooving.ExpressionCheckers.ExpressionCheckResult;
import Prooving.ExpressionCheckers.ExpressionChecker;
import Prooving.ProofCheckers.Rewriter.RewriterProofResult;

/**
 * Created by marsermd on 05.02.2017.
 */
public abstract class ExpressionRewriter<ExpressonCheckerT extends  ExpressionChecker>
{

    public void tryRewriteExpression(ExpressionChecker lineChecker, ExpressionCheckResult lineResult, RewriterProofResult result)
    {
        if (isApplicable(lineChecker))
        {
            rewriteExpression((ExpressonCheckerT) lineChecker, lineResult, result);
        }
    }

    protected abstract boolean isApplicable(ExpressionChecker lineChecker);
    protected abstract void rewriteExpression(ExpressonCheckerT lineChecker, ExpressionCheckResult lineResult, RewriterProofResult result);
}
