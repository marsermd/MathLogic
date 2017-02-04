package Prooving.ExpressionCheckers.InferenceRules;

import Prooving.ExpressionCheckers.ExpressionCheckResult;
import Prooving.ExpressionCheckers.ExpressionChecker;
import Prooving.Proof;
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
        List<Expression> expressions = proof.getProofLines();

        Expression beta = expressions.get(currentLine);

        for (int alphaBetaId = 0; alphaBetaId < currentLine; alphaBetaId++)
        {
            for (int alphaId = 0; alphaId < currentLine; alphaId++)
            {
                Expression alphaBeta = expressions.get(alphaBetaId);
                Expression alpha = expressions.get(alphaId);
                if (
                    alphaBeta.fairEquals(
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
