package Prooving.ExpressionCheckers;

import Prooving.Proof;

/**
 * Created by marsermd on 22.01.2017.
 */
public interface ExpressionChecker
{
    public ExpressionCheckResult checkMatches(Proof proof, int currentLine);
}
