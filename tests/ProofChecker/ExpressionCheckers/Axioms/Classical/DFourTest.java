package ProofChecker.ExpressionCheckers.Axioms.Classical;

import ProofChecker.ExpressionCheckers.Axioms.AxiomChecker;
import org.junit.Test;

/**
 * Created by marsermd on 29.01.2017.
 */
public class DFourTest extends AxiomTest
{
    @Test
    public void testMatchesAxiom() throws Exception
    {
        assertMatches("A&B->A");
    }

    @Test
    public void testNotMatchesAxiom() throws Exception
    {
        assertDoesntMatch("B&A->A");
    }

    @Override
    protected AxiomChecker getChecker()
    {
        return new DFour();
    }
}