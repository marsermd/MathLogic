package Prooving.ExpressionCheckers.Axioms.Classical;

import Prooving.ExpressionCheckers.Axioms.SimpleAxiomSchemeChecker;

/**
 * Created by marsermd on 18.01.2017.
 */
public class HEight extends SimpleAxiomSchemeChecker
{
    public HEight()
    {
        super("(a->c)->(b->c)->(a|b->c)", "a", "b", "c");
    }
}
