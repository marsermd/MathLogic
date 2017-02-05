package Prooving.ExpressionCheckers.Axioms.Arithmetical;

import Prooving.ExpressionCheckers.Axioms.SimpleAxiomChecker;
import SyntaxTree.Structure.Expression;

/**
 * Created by marsermd on 04.02.2017.
 */
public class ArithmeticFSix extends SimpleAxiomChecker
{
    public ArithmeticFSix()
    {
        super("a+0=a", "a");
    }
}
