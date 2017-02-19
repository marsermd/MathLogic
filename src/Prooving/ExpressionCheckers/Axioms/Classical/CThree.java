package Prooving.ExpressionCheckers.Axioms.Classical;

import Prooving.ExpressionCheckers.Axioms.SimpleAxiomSchemeChecker;

/**
 * Created by marsermd on 18.01.2017.
 */
public class CThree extends SimpleAxiomSchemeChecker
{
    public CThree()
    {
        super("a->b->(a&b)", "a", "b");
    }
}