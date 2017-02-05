package Prooving.ExpressionCheckers.Axioms.Classical;

import Prooving.ExpressionCheckers.Axioms.SimpleAxiomChecker;
import SyntaxTree.Structure.AnyFormula;
import SyntaxTree.Structure.BinaryOperators.Implication;
import SyntaxTree.Structure.Expression;

/**
 * Created by marsermd on 18.01.2017.
 */
public class BTwo extends SimpleAxiomChecker
{
    public BTwo()
    {
        super("(a->b)->(a->b->c)->(a->c)", "a", "b", "c");
    }
}
