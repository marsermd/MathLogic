package ProofChecker.ExpressionCheckers;

import ProofChecker.Proof;
import SyntaxTree.Structure.Expression;

/**
 * Created by marsermd on 22.01.2017.
 */
public interface ExpressionChecker
{
    public boolean Matches(Proof proof, int currentLine);
}
