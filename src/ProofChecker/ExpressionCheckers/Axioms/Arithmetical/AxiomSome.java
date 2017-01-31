package ProofChecker.ExpressionCheckers.Axioms.Arithmetical;

import ProofChecker.ExpressionCheckers.Axioms.AxiomChecker;
import ProofChecker.ExpressionCheckers.ExpressionChecker;
import ProofChecker.Proof;
import SyntaxTree.Structure.AnyFormula;
import SyntaxTree.Structure.BinaryOperators.Implication;
import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.UnaryOperators.Each;
import SyntaxTree.Structure.UnaryOperators.Some;
import SyntaxTree.Structure.Variable;

/**
 * Created by marsermd on 01.02.2017.
 */
public class AxiomSome extends AxiomChecker
{
    @Override
    public boolean MatchesAxiom(Expression currentLine)
    {
        AnyFormula alpha = new AnyFormula();
        AnyFormula phi = new AnyFormula();
        AnyFormula phiWithTheta = new AnyFormula();

        Expression matcher = new Implication(
            phiWithTheta,
            new Some(
                alpha,
                phi
            )
        );

        if (!matcher.fairEquals(currentLine))
        {
            // it id not nearly this axiom
            return false;
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
            return false;
        }
        if (!(theta.getEqualExpression() instanceof Variable))
        {
            // theta is not a variable
            return false;
        }
        if (phiWithTheta.getBinded().contains(theta.getEqualExpression()))
        {
            // theta is not free for replacing
            return false;
        }

        return true;
    }
}
