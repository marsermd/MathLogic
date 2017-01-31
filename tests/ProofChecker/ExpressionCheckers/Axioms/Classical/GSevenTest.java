package ProofChecker.ExpressionCheckers.Axioms.Classical;

import ProofChecker.ExpressionCheckers.Axioms.AxiomChecker;
import ProofChecker.ExpressionCheckers.Axioms.AxiomTest;
import org.junit.Test;

/**
 * Created by marsermd on 29.01.2017.
 */
public class GSevenTest extends AxiomTest
{
    @Test
    public void testMatchesAxiom() throws Exception
    {
        assertMatches("A->B|A");
    }

    @Test
    public void testNotMatchesAxiom() throws Exception
    {
        assertDoesntMatch("A->A|B");
    }

    @Override
    protected AxiomChecker getChecker()
    {
        return new GSeven();
    }
}