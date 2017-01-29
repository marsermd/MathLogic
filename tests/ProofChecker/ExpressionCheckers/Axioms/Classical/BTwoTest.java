package ProofChecker.ExpressionCheckers.Axioms.Classical;

import ProofChecker.ExpressionCheckers.Axioms.AxiomChecker;
import org.junit.Test;

/**
 * Created by marsermd on 29.01.2017.
 */
public class BTwoTest extends AxiomTest
{
    @Test
    public void testMatchesAxiom() throws Exception
    {
        assertMatches("(A->B)->(A->B->C)->(A->C)");
        assertMatches("(A->B)->(A->B->A)->(A->A)");
        assertMatches("(A->B)->(A->B->C->D)->(A->C->D)");
    }

    @Test
    public void testNotMatchesAxiom() throws Exception
    {
        assertDoesntMatch("(A->B)->(A->B->C)->(A->B)");
        assertDoesntMatch("(A->C)->(A->B->C)->(A->B)");
    }

    @Override
    protected AxiomChecker getChecker()
    {
        return new BTwo();
    }
}