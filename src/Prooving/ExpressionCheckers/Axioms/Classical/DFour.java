package Prooving.ExpressionCheckers.Axioms.Classical;

import Prooving.ExpressionCheckers.Axioms.SimpleAxiomChecker;
import SyntaxTree.Structure.AnyFormula;
import SyntaxTree.Structure.BinaryOperators.Conjunction;
import SyntaxTree.Structure.BinaryOperators.Implication;
import SyntaxTree.Structure.Expression;

/**
 * Created by marsermd on 18.01.2017.
 */
public class DFour extends SimpleAxiomChecker
{
    public DFour()
    {
        super("a&b->a", "a", "b");
    }
}
