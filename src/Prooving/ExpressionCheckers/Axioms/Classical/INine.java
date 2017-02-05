package Prooving.ExpressionCheckers.Axioms.Classical;

import Prooving.ExpressionCheckers.Axioms.SimpleAxiomChecker;
import SyntaxTree.Structure.AnyFormula;
import SyntaxTree.Structure.BinaryOperators.Implication;
import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.UnaryOperators.Negation;

/**
 * Created by marsermd on 18.01.2017.
 */
public class INine extends SimpleAxiomChecker
{
    public INine()
    {
        super("(a->b)->(a->!b)->!a", "a", "b");
    }
}
