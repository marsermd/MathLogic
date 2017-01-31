package ProofChecker.ExpressionCheckers.Axioms.Arithmetical;

import ProofChecker.ExpressionCheckers.Axioms.AxiomChecker;
import ProofChecker.ExpressionCheckers.Axioms.AxiomTest;
import org.junit.Test;

public class AxiomEachTest extends AxiomTest
{

    @Test
    public void testMatches() throws Exception
    {
        assertMatches("@a (a = a + a) -> b = b + b");
    }

    @Test
    public void testMatchesNotBinded() throws Exception
    {
        assertMatches("@a ?b(a = b') -> ?b (c = b')");
    }

    @Test
    public void testDoesntMatch() throws Exception
    {
        assertDoesntMatch("@a (a = a + a) -> b = a + b");
    }

    @Test
    public void testDoesntMatchBinded() throws Exception
    {
        assertDoesntMatch("@a ?b(a = b') -> ?b (b = b')");
    }

    @Override
    protected AxiomChecker getChecker()
    {
        return new AxiomEach();
    }
}