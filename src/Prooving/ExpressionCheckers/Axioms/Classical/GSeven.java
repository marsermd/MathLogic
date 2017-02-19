package Prooving.ExpressionCheckers.Axioms.Classical;

import Prooving.ExpressionCheckers.Axioms.SimpleAxiomSchemeChecker;

/**
 * Created by marsermd on 18.01.2017.
 */
public class GSeven extends SimpleAxiomSchemeChecker
{
    public GSeven()
    {
        super("b->a|b", "a", "b");
    }
}