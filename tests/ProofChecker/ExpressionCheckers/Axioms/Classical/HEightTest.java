package ProofChecker.ExpressionCheckers.Axioms.Classical;

import ProofChecker.ExpressionCheckers.Axioms.AxiomChecker;
import org.junit.Test;

/**
 * Created by marsermd on 29.01.2017.
 */
public class HEightTest extends AxiomTest
{
    @Test
    public void testMatchesAxiom() throws Exception
    {
        assertMatches("(A->C)->(B->C)->(A|B->C)");
    }

    @Test
    public void testNotMatchesAxiom() throws Exception
    {
        assertDoesntMatch("(A->B)->(B->C)->(A|B->C)");
    }

    @Override
    protected AxiomChecker getChecker()
    {
        return new HEight();
    }
}