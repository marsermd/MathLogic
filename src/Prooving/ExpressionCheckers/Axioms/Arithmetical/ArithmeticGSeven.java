package Prooving.ExpressionCheckers.Axioms.Arithmetical;

import Prooving.ExpressionCheckers.Axioms.SimpleAxiomChecker;
import SyntaxTree.Structure.Expression;

/**
 * Created by marsermd on 04.02.2017.
 */
public class ArithmeticGSeven extends SimpleAxiomChecker
{
    public ArithmeticGSeven()
    {
        super("a*0=0", "a");
    }
}
