package Prooving.ExpressionCheckers.Axioms.Classical;

import SyntaxTree.Structure.AnyFormula;
import SyntaxTree.Structure.BinaryOperators.Conjunction;
import SyntaxTree.Structure.BinaryOperators.Implication;
import SyntaxTree.Structure.Expression;

/**
 * Created by marsermd on 18.01.2017.
 */
public class CThree extends ClassicalAxiomChecker
{
    @Override
    protected Expression getScheme()
    {
        AnyFormula a = new AnyFormula();
        AnyFormula b = new AnyFormula();
        return new Implication(
            a,
            new Implication(
                b,
                new Conjunction(
                    a,
                    b
                )
            )
        );
    }
}