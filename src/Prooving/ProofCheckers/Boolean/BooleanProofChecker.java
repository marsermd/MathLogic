package Prooving.ProofCheckers.Boolean;

import Prooving.ExpressionCheckers.ExpressionCheckResult;
import Prooving.ExpressionCheckers.ExpressionChecker;
import Prooving.Proof;
import Prooving.ProofCheckers.ProofChecker;

/**
 * Created by marsermd on 03.02.2017.
 */
public class BooleanProofChecker extends ProofChecker<BooleanProofResult>
{
    private BooleanProofResult proofResult;

    public BooleanProofChecker(Proof proof, ExpressionChecker... checkers)
    {
        super(proof, checkers);
    }

    @Override
    protected void OnCheckResult(int currentLine, ExpressionCheckResult result, ExpressionChecker lineChecker)
    {
        proofResult.lastExpressionResult = result;
        proofResult.lineID = currentLine;
        if (result.isWrong())
        {
            stopChecking();
        }
    }

    @Override
    public BooleanProofResult Check()
    {
        proofResult = new BooleanProofResult();
        RunCheck();
        return proofResult;
    }
}
