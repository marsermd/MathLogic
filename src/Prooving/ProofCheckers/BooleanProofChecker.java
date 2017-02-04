package Prooving.ProofCheckers;

import Prooving.ExpressionCheckers.ExpressionCheckResult;
import Prooving.ExpressionCheckers.ExpressionChecker;
import Prooving.Proof;

/**
 * Created by marsermd on 03.02.2017.
 */
public class BooleanProofChecker extends ProofChecker<Boolean>
{
    private boolean proofResult;

    public BooleanProofChecker(Proof proof, ExpressionChecker... checkers)
    {
        super(proof, checkers);
    }

    @Override
    protected void OnCheckResult(int currentLine, ExpressionCheckResult result)
    {
        if (result.isWrong())
        {
            proofResult = false;
            stopChecking();
        }
    }

    @Override
    public Boolean Check()
    {
        proofResult = true;
        RunCheck();
        return proofResult;
    }
}
