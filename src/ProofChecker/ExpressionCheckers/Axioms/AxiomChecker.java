package ProofChecker.ExpressionCheckers.Axioms;

import ProofChecker.ExpressionCheckers.ExpressionChecker;
import ProofChecker.Proof;
import SyntaxTree.Structure.Expression;

/**
 * Created by marsermd on 18.01.2017.
 */
public abstract class AxiomChecker implements ExpressionChecker
{
    public abstract boolean MatchesAxiom(Expression expression);

    @Override
    public boolean Matches(Proof proof, int currentLine)
    {
        return MatchesAxiom(proof.GetProofLines().get(currentLine));
    }
}
