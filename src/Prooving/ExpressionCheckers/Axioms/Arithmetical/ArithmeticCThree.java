package Prooving.ExpressionCheckers.Axioms.Arithmetical;

import Prooving.ExpressionCheckers.Axioms.SimpleAxiomChecker;
import SyntaxTree.Structure.Expression;

/**
 * Created by marsermd on 04.02.2017.
 */
public class ArithmeticCThree extends SimpleAxiomChecker
{
    public ArithmeticCThree()
    {
        super("a'=b'->a=b", "a", "b");
    }
}
