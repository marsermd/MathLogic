package ProofChecker.ExpressionCheckers.Axioms;

import ProofChecker.ExpressionCheckers.ExpressionCheckResult;
import ProofChecker.ExpressionCheckers.ExpressionChecker;
import ProofChecker.Proof;
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
        return checkMatchesAxiom(proof.GetProofLines().get(currentLine));
    }
}
