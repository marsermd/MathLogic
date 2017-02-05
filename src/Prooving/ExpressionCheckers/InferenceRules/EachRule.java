package Prooving.ExpressionCheckers.InferenceRules;

import Prooving.ExpressionCheckers.ExpressionCheckResult;
import Prooving.ExpressionCheckers.ExpressionChecker;
import Prooving.Proof;
import SyntaxTree.Structure.AnyFormula;
import SyntaxTree.Structure.BinaryOperators.Implication;
import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.UnaryOperators.Each;
import SyntaxTree.Structure.Variable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by marsermd on 01.02.2017.
 */
public class EachRule implements ExpressionChecker
{
    private AnyFormula phi = new AnyFormula();
    private AnyFormula psi = new AnyFormula();
    private AnyFormula x = new AnyFormula();
    Expression currentMatcher = new Implication(
        phi,
        new Each(
            x,
            psi
        )
    );

    public EachRule()
    {
    }

    @Override
    public ExpressionCheckResult checkMatches(Proof proof, int currentLine, HashMap<Expression, Integer> checkedHashToLine, HashSet<Expression> assumptionsHashes, HashMap<Expression, List<Implication>> checkedImplicationsRightParts)
    {
        phi.reset();
        psi.reset();
        x.reset();

        Expression current = proof.getProofLines().get(currentLine);

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
