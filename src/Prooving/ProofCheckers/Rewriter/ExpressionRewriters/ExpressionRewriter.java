package Prooving.ProofCheckers.Rewriter.ExpressionRewriters;

import Prooving.ExpressionCheckers.ExpressionCheckResult;
import Prooving.ExpressionCheckers.ExpressionChecker;
import Prooving.ProofCheckers.Rewriter.RewriterProofResult;
import sun.plugin.dom.exception.InvalidStateException;

/**
 * Created by marsermd on 05.02.2017.
 */
public abstract class ExpressionRewriter<ExpressionCheckResultT extends  ExpressionCheckResult>
{

    public boolean tryRewriteExpression(ExpressionChecker lineChecker, ExpressionCheckResult lineResult, RewriterProofResult result)
    {
        if (isApplicable(lineResult))
        {
            if (lineResult.isWrong())
            {
                throw new InvalidStateException("truing to rewrite wrong result!");
            }

            rewriteExpression(lineChecker,(ExpressionCheckResultT) lineResult, result);
            return true;
        }
        return false;
    }

    protected abstract boolean isApplicable(ExpressionCheckResult lineResult);
    protected abstract void rewriteExpression(ExpressionChecker lineChecker, ExpressionCheckResultT lineResult, RewriterProofResult result);
}
