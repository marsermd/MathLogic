package Prooving.ExpressionCheckers.Axioms.Classical;

import Prooving.ExpressionCheckers.Axioms.SimpleAxiomSchemeChecker;

/**
 * Created by marsermd on 18.01.2017.
 */
public class BTwo extends SimpleAxiomSchemeChecker
{
    public BTwo()
    {
        super("(a->b)->(a->b->c)->(a->c)", "a", "b", "c");
    }
}
