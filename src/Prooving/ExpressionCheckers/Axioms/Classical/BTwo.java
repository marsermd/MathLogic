package Prooving.ExpressionCheckers.Axioms.Classical;

import SyntaxTree.Structure.AnyFormula;
import SyntaxTree.Structure.BinaryOperators.Implication;
import SyntaxTree.Structure.Expression;

/**
 * Created by marsermd on 18.01.2017.
 */
public class BTwo extends ClassicalAxiomChecker
{
    @Override
    protected Expression getScheme()
    {
        AnyFormula a = new AnyFormula();
        AnyFormula b = new AnyFormula();
        AnyFormula c = new AnyFormula();

        return new Implication(
            new Implication(
                a,
                b
            ),
            new Implication(
                new Implication(
                    a,
                    new Implication(
                        b,
                        c
                    )
                ),
                new Implication(
                    a,
                    c
                )
            )
        );
    }
}
