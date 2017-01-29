package ProofChecker.ExpressionCheckers.Axioms.Classical;

import ProofChecker.ExpressionCheckers.Axioms.AxiomChecker;
import org.junit.Test;

/**
 * Created by marsermd on 29.01.2017.
 */
public class EFiveTest extends AxiomTest
{
    @Test
    public void testMatchesAxiom() throws Exception
    {
        assertMatches("B&A->A");
    }

    @Test
    public void testNotMatchesAxiom() throws Exception
    {
        assertDoesntMatch("A&B->A");
    }

    @Override
    protected AxiomChecker getChecker()
    {
        return new EFive();
    }
}