package Prooving.ProofCheckers.Rewriter;

import Prooving.ExpressionCheckers.ExpressionCheckResult;
import Prooving.ExpressionCheckers.ExpressionChecker;
import Prooving.Proof;
import Prooving.ProofCheckers.ProofChecker;
import SyntaxTree.Structure.BinaryOperators.Implication;
import SyntaxTree.Structure.Expression;

/**
 * Created by marsermd on 05.02.2017.
 */
public class RewriterProofChecker extends ProofChecker<RewriterProofResult>
{
    public RewriterProofChecker(Proof proof, ExpressionChecker... checkers)
    {
        super(proof, checkers);
    }

    RewriterProofResult result;
    Expression beta;
    Expression alpha;
    Expression alphaBeta;


    @Override
    protected void OnCheckResult(int currentLine, ExpressionCheckResult expressionResult, ExpressionChecker lineChecker)
    {
        if (expressionResult.isWrong())
        {
            result.failed = true;
            result.failureReason = expressionResult.reason;
            stopChecking();
        }
        else
        {

        }
    }

    @Override
    public RewriterProofResult Check()
    {
        alpha = proof.getAssumptions().get(proof.getAssumptions().size() - 1);
        beta = proof.getGoal();
        alphaBeta = new Implication(
            alpha,
            beta
        );

        InitResult();

        RunCheck();

        return result;
    }

    private void InitResult()
    {
        result = new RewriterProofResult();
        result.generatedProof = new Proof();

        for (int i = 0; i < proof.getAssumptions().size() - 1; i++)
        {
            result.generatedProof.assumeThat(proof.getAssumptions().get(i));
        }

        result.generatedProof.setGoal(alphaBeta);
    }
}
