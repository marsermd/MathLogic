package Prooving.ExpressionCheckers.Axioms.Predicate;

import Prooving.ExpressionCheckers.Axioms.AxiomChecker;
import Prooving.ExpressionCheckers.Axioms.AxiomTest;
import Prooving.ExpressionCheckers.Axioms.Predicate.AxiomSome;
import org.junit.Test;

/**
 * Created by marsermd on 01.02.2017.
 */
public class AxiomSomeTest extends AxiomTest
{
    @Test
    public void testMatches() throws Exception
    {
        assertMatches("a = b + b -> ? c (c = b + b)");
    }

    @Test
    public void testMatchesNotBinded() throws Exception
    {
        assertMatches("@b (a = b') -> ?a @b(a = b')");
    }

    @Test
    public void testDoesntMatch() throws Exception
    {
        assertDoesntMatch("a = b + b -> ? c (e = b + b)");
    }

    @Test
    public void testDoesntMatchBinded() throws Exception
    {
        assertDoesntMatch("?b (a = b') -> ?b ?b(b = b')");
    }

    @Override
    protected AxiomChecker getChecker()
    {
        return new AxiomSome();
    }
}
