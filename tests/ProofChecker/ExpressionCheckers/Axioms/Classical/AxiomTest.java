package ProofChecker.ExpressionCheckers.Axioms.Classical;

import ProofChecker.ExpressionCheckers.Axioms.AxiomChecker;
import SyntaxTree.Parser.Parser;
import org.junit.Assert;

/**
 * Created by marsermd on 29.01.2017.
 */
public abstract class AxiomTest
{
    protected void assertMatches(String expression)
    {
        Assert.assertTrue(getChecker().MatchesAxiom(Parser.parseDefault(expression)));
    }

    protected void assertDoesntMatch(String expression)
    {
        Assert.assertFalse(getChecker().MatchesAxiom(Parser.parseDefault(expression)));
    }

    protected abstract AxiomChecker getChecker();
}
