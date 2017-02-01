package ProofChecker.ExpressionCheckers.Axioms.Classical;

import ProofChecker.ExpressionCheckers.Axioms.AxiomChecker;
import ProofChecker.ExpressionCheckers.Axioms.AxiomTest;
import org.junit.Test;

public class AOneTest extends AxiomTest
{
    @Test
    public void testMatchesAxiom() throws Exception
    {
        assertMatches("A->(A->B)->A");
        assertMatches("A->A->A");
    }

    @Test
    public void testNotMatchesAxiom() throws Exception
    {
        assertDoesntMatch("A->B->C->A");
        assertDoesntMatch("B->B->A");
        assertDoesntMatch("(A->A)->A");
    }

    @Override
    protected AxiomChecker getChecker()
    {
        return new AOne();
    }
}