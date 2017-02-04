package Prooving.ExpressionCheckers.Axioms.Classical;

import SyntaxTree.Structure.AnyFormula;
import SyntaxTree.Structure.BinaryOperators.Disjunction;
import SyntaxTree.Structure.BinaryOperators.Implication;
import SyntaxTree.Structure.Expression;

/**
 * Created by marsermd on 18.01.2017.
 */
public class GSeven extends ClassicalAxiomChecker
{
    @Override
    protected Expression getScheme()
    {
        AnyFormula a = new AnyFormula();
        AnyFormula b = new AnyFormula();
        return new Implication(
            b,
            new Disjunction(
                a,
                b
            )
        );
    }
}