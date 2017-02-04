package Prooving.ExpressionCheckers.Axioms.Classical;

import Prooving.ExpressionCheckers.Axioms.AxiomChecker;
import Prooving.ExpressionCheckers.Axioms.AxiomTest;
import org.junit.Test;

/**
 * Created by marsermd on 29.01.2017.
 */
public class JTenTest extends AxiomTest
{
    @Test
    public void testMatchesAxiom() throws Exception
    {
        assertMatches("!!A->A");
        assertMatches("!!!!A->!!A");
    }

    @Test
    public void testNotMatchesAxiom() throws Exception
    {
        assertDoesntMatch("A->!!A");
        assertDoesntMatch("!!A->!!A");
        assertDoesntMatch("!!!!A->A");
    }

    @Override
    protected AxiomChecker getChecker()
    {
        return new JTen();
    }
}