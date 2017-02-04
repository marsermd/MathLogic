package Prooving.ExpressionCheckers.InferenceRules;

import Prooving.ExpressionCheckers.ExpressionCheckResult;
import Prooving.ExpressionCheckers.ExpressionChecker;
import Prooving.Proof;
import SyntaxTree.Structure.BinaryOperators.Implication;
import SyntaxTree.Structure.Expression;

import java.util.List;

/**
 * Created by marsermd on 03.02.2017.
 */
public class UseAssumptionRule implements ExpressionChecker
{
    @Override
    public ExpressionCheckResult checkMatches(Proof proof, int currentLine)
    {
        Expression current = proof.getProofLines().get(currentLine);
        for (Expression expression: proof.getAssumptions())
        {
            if (expression.equals(current)){
                return ExpressionCheckResult.right();
            }
        }
        return ExpressionCheckResult.wrong();
    }
}
