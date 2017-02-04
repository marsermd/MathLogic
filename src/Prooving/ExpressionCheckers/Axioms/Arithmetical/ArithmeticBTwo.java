package Prooving.ExpressionCheckers.Axioms.Arithmetical;

import Prooving.ExpressionCheckers.Axioms.SimpleAxiomChecker;
import SyntaxTree.Structure.Expression;

/**
 * Created by marsermd on 04.02.2017.
 */
public class ArithmeticBTwo extends SimpleAxiomChecker
{
    @Override
    protected Expression getScheme()
    {
        return fromString("a=b->a=c->b=c", "a", "b", "c");
    }
}