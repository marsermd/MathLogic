package ProofChecker.ExpressionCheckers.InferenceRules;

import ProofChecker.ExpressionCheckers.ExpressionCheckResult;
import ProofChecker.ExpressionCheckers.ExpressionChecker;
import ProofChecker.Proof;
import SyntaxTree.Structure.BinaryOperators.Implication;
import SyntaxTree.Structure.Expression;

import java.util.List;

/**
 * Created by marsermd on 22.01.2017.
 */
public class ModusPonens implements ExpressionChecker
{
    @Override
    public ExpressionCheckResult checkMatches(Proof proof, int currentLine)
    {
        List<Expression> expressions = proof.GetProofLines();

        Expression beta = expressions.get(currentLine);

        for (int alphaBetaId = 0; alphaBetaId < expressions.size() && alphaBetaId < currentLine; alphaBetaId++)
        {
            for (int alphaId = 0; alphaId < alphaBetaId; alphaId++)
            {
                Expression alphaBeta = expressions.get(alphaBetaId);
                Expression alpha = expressions.get(alphaId);
                if (alphaBeta.fairEquals(
                    new Implication(
                        alpha,
                        beta
                    )
                ))
                {
                    return ExpressionCheckResult.right();
                }
            }
        }
        return ExpressionCheckResult.wrong();
    }
}
