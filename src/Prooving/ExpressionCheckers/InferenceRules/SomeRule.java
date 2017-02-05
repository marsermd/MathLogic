package Prooving.ExpressionCheckers.InferenceRules;

import Prooving.ExpressionCheckers.ExpressionCheckResult;
import Prooving.ExpressionCheckers.ExpressionChecker;
import Prooving.Proof;
import SyntaxTree.Structure.AnyFormula;
import SyntaxTree.Structure.BinaryOperators.Implication;
import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.UnaryOperators.Some;
import SyntaxTree.Structure.Variable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by marsermd on 01.02.2017.
 */
public class SomeRule implements ExpressionChecker
{
    AnyFormula phi = new AnyFormula();
    AnyFormula psi = new AnyFormula();
    AnyFormula x = new AnyFormula();

    Expression currentMatcher = new Implication(
        new Some(
            x,
            psi
        ),
        phi
    );

    @Override
    public ExpressionCheckResult checkMatches(Proof proof, int currentLine, HashMap<Expression, Integer> checkedHashToLine, HashSet<Expression> assumptionsHashes, HashMap<Expression, List<Implication>> checkedImplicationsRightParts)
    {
        Expression current = proof.getProofLines().get(currentLine);
        phi.reset();
        psi.reset();
        x.reset();

        if (!currentMatcher.fairEquals(current))
        {
            return ExpressionCheckResult.wrong();
        }

        Expression previousMatch = new Implication(
            psi.getEqualExpression(),
            phi.getEqualExpression()
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