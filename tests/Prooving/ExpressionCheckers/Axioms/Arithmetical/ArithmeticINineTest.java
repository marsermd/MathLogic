package Prooving.ExpressionCheckers.Axioms.Arithmetical;

import Prooving.ExpressionCheckers.Axioms.AxiomChecker;
import Prooving.ExpressionCheckers.Axioms.AxiomTest;
import org.junit.Test;

/**
 * Created by marsermd on 04.02.2017.
 */
public class ArithmeticINineTest extends AxiomTest
{
    @Test
    public void testMatchesSimplest() throws Exception
    {
        assertMatches("(0 & @x(x -> x')) -> x");
    }

    @Override
    protected AxiomChecker getChecker()
    {
        return new ArithmeticINine();
    }
}
