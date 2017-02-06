package Prooving.ExpressionCheckers.Axioms.Arithmetical;

import Prooving.ExpressionCheckers.Axioms.AxiomChecker;
import Prooving.ExpressionCheckers.Axioms.SimpleAxiomResult;
import Prooving.ExpressionCheckers.ExpressionCheckResult;
import SyntaxTree.Structure.AnyFormula;
import SyntaxTree.Structure.BinaryOperators.Conjunction;
import SyntaxTree.Structure.BinaryOperators.Implication;
import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.UnaryOperators.Each;
import SyntaxTree.Structure.UnaryOperators.Increment;
import SyntaxTree.Structure.Variable;

/**
 * Created by marsermd on 04.02.2017.
 */
public class ArithmeticINine extends AxiomChecker
{
    @Override
    public ExpressionCheckResult checkMatchesAxiom(Expression expression)
    {
        AnyFormula phi = new AnyFormula();
        AnyFormula phiWithZero = new AnyFormula();
        AnyFormula phiWithIncrementedX = new AnyFormula();
        AnyFormula x = new AnyFormula();
        Expression matcher = new Implication(
            new Conjunction(
                phiWithZero,
                new Each(
                    x,
                    new Implication(
                        phi,
                        phiWithIncrementedX
                    )
                )
            ),
            phi
        );

        if (!matcher.fairEquals(expression))
        {
            return ExpressionCheckResult.wrong();
        }

        if (!phiWithZero.fairEquals(phi.replace((Variable)x.getEqualExpression(), new Variable("0"))))
        {
            return ExpressionCheckResult.wrong();
        }

        if (!phiWithIncrementedX.fairEquals(phi.replace((Variable) x.getEqualExpression(), new Increment(x.getEqualExpression()))))
        {
            return ExpressionCheckResult.wrong();
        }

        if (!phi.getFree().contains(x.getEqualExpression()))
        {
            return ExpressionCheckResult.wrong();
        }

        return SimpleAxiomResult.right(expression);
    }
}
