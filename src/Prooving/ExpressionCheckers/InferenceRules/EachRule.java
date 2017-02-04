package Prooving.ExpressionCheckers.InferenceRules;

import Prooving.ExpressionCheckers.ExpressionCheckResult;
import Prooving.ExpressionCheckers.ExpressionChecker;
import Prooving.Proof;
import SyntaxTree.Structure.AnyFormula;
import SyntaxTree.Structure.BinaryOperators.Implication;
import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.UnaryOperators.Each;
import SyntaxTree.Structure.Variable;

/**
 * Created by marsermd on 01.02.2017.
 */
public class EachRule implements ExpressionChecker
{
    @Override
    public ExpressionCheckResult checkMatches(Proof proof, int currentLine)
    {
        Expression current = proof.getProofLines().get(currentLine);
        AnyFormula phi = new AnyFormula();
        AnyFormula psi = new AnyFormula();
        AnyFormula x = new AnyFormula();
        Expression currentMatcher = new Implication(
            phi,
            new Each(
                x,
                psi
            )
        );

        if (!currentMatcher.fairEquals(current))
        {
            return ExpressionCheckResult.wrong();
        }

        Expression previousMatch = new Implication(
            phi.getEqualExpression(),
            psi.getEqualExpression()
        );

        for (int i = 0; i < currentLine; i++)
        {
            Expression previous = proof.getProofLines().get(i);
            if (previous.equals(previousMatch))
            {
                if (phi.getFree().contains(x.getEqualExpression()))
                {
                    return ExpressionCheckResult.variableIsFreeInFormula((Variable) x.getEqualExpression(), phi);
                }
                else
                {
                    return ExpressionCheckResult.right();
                }
            }
        }

        return ExpressionCheckResult.wrong();
    }
}