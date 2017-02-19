package Prooving.ExpressionCheckers.Axioms.Classical;

import Prooving.ExpressionCheckers.Axioms.SimpleAxiomSchemeChecker;

/**
 * Created by marsermd on 18.01.2017.
 */
public class AOne extends SimpleAxiomSchemeChecker
{
    public AOne()
    {
        super("a->b->a", "a", "b");
    }
}
