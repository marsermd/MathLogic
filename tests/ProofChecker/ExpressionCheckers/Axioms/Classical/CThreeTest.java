package ProofChecker.ExpressionCheckers.Axioms.Classical;

import ProofChecker.ExpressionCheckers.Axioms.AxiomChecker;
import ProofChecker.ExpressionCheckers.Axioms.AxiomTest;
import org.junit.Test;

/**
 * Created by marsermd on 29.01.2017.
 */
public class CThreeTest extends AxiomTest
{
    @Test
    public void testMatchesAxiom() throws Exception
    {
        assertMatches("A->B->A&B");
        assertMatches("A->(B->C)->A&(B->C)");
    }

    @Test
    public void testNotMatchesAxiom() throws Exception
    {
        assertDoesntMatch("A->B->B&A");
        assertDoesntMatch("(A->B->C)->A&B->C");
        assertDoesntMatch("(A->B->C)->A&(B->C)");
    }

    @Override
    protected AxiomChecker getChecker()
    {
        return new CThree();
    }
}