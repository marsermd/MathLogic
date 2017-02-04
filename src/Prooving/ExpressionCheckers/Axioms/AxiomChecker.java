package Prooving.ExpressionCheckers.Axioms;

import Prooving.ExpressionCheckers.ExpressionCheckResult;
import Prooving.ExpressionCheckers.ExpressionChecker;
import Prooving.Proof;
import SyntaxTree.Structure.Expression;

/**
 * Created by marsermd on 18.01.2017.
 */
public abstract class AxiomChecker implements ExpressionChecker
{
    public abstract ExpressionCheckResult checkMatchesAxiom(Expression expression);

    @Override
    public ExpressionCheckResult checkMatches(Proof proof, int currentLine)
    {
        return checkMatchesAxiom(proof.getProofLines().get(currentLine));
    }
}
