package Prooving.ExpressionCheckers.Axioms.Classical;

import Prooving.ExpressionCheckers.Axioms.SimpleAxiomSchemeChecker;

/**
 * Created by marsermd on 18.01.2017.
 */
public class EFive extends SimpleAxiomSchemeChecker
{
    public EFive()
    {
        super("a&b->b", "a", "b");
    }
}
