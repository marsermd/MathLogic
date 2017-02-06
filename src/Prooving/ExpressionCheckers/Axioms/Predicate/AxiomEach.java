package Prooving.ExpressionCheckers.Axioms.Predicate;

import Prooving.ExpressionCheckers.Axioms.AxiomChecker;
import Prooving.ExpressionCheckers.Axioms.SimpleAxiomResult;
import Prooving.ExpressionCheckers.ExpressionCheckResult;
import SyntaxTree.Structure.AnyFormula;
import SyntaxTree.Structure.BinaryOperators.Implication;
import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.UnaryOperators.Each;
import SyntaxTree.Structure.Variable;

/**
 * Created by marsermd on 01.02.2017.
 */
public class AxiomEach extends AxiomChecker
{
    AnyFormula alpha = new AnyFormula();
    AnyFormula phi = new AnyFormula();
    AnyFormula phiWithTheta = new AnyFormula();

    Expression matcher = new Implication(
        new Each(
            alpha,
            phi
        ),
        phiWithTheta
    );

    @Override
    public ExpressionCheckResult checkMatchesAxiom(Expression currentLine)
    {
        alpha.reset();
        phi.reset();
        phiWithTheta.reset();

        if (!matcher.fairEquals(currentLine))
        {
            // it id not nearly this axiom
            return ExpressionCheckResult.wrong();
        }
        if (!(alpha.getEqualExpression() instanceof Variable))
        {
            throw new AssertionError("Some has not a variable as quantified");
        }

        AnyFormula theta = new AnyFormula();
        Expression matcherForTheta = phi.replace(((Variable)alpha.getEqualExpression()), theta);

        if (!matcherForTheta.fairEquals(phiWithTheta.getEqualExpression()))
        {
            // can't replace alpha with same theta to get phiWithTheta
            return ExpressionCheckResult.wrong();
        }

        if (theta.getEqualExpression() == null)
        {
            // it defenitely can't be binded
            return SimpleAxiomResult.right(currentLine);
        }

        for (Variable thetaFree: theta.getFree())
        {
            if (thetaFree.equals(alpha))
            {
                continue;
            }
            if (phiWithTheta.getBindedAndCache().contains(thetaFree))
            {
                // theta is not free for replacing
                return PredicateAxiomResult.termIsNotFreeToReplace(phi, (Variable) alpha.getEqualExpression(), theta);
            }
        }

        return SimpleAxiomResult.right(currentLine);
    }
}
