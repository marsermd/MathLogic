package Prooving.ExpressionCheckers.Axioms.Classical;

import Prooving.ExpressionCheckers.Axioms.SimpleAxiomSchemeChecker;

/**
 * Created by marsermd on 18.01.2017.
 */
public class FSix extends SimpleAxiomSchemeChecker
{
    public FSix()
    {
        super("a->a|b", "a", "b");
    }
}