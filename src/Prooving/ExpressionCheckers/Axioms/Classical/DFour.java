package Prooving.ExpressionCheckers.Axioms.Classical;

import Prooving.ExpressionCheckers.Axioms.SimpleAxiomSchemeChecker;

/**
 * Created by marsermd on 18.01.2017.
 */
public class DFour extends SimpleAxiomSchemeChecker
{
    public DFour()
    {
        super("a&b->a", "a", "b");
    }
}
