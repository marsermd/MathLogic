package Prooving.ExpressionCheckers.Axioms.Classical;

import Prooving.ExpressionCheckers.Axioms.AxiomChecker;
import Prooving.ExpressionCheckers.Axioms.AxiomTest;
import org.junit.Test;


/**
 * Created by marsermd on 29.01.2017.
 */
public class INineTest extends AxiomTest
{
    @Test
    public void testMatchesAxiom() throws Exception
    {
       assertMatches("(A->B)->((A->!B)->!A)");
    }

    @Test
    public void testNotMatchesAxiom() throws Exception
    {
        assertDoesntMatch("(A->B)->(A->!B)->!B");
    }

    @Override
    protected AxiomChecker getChecker()
    {
        return new INine();
    }
}
