package Prooving.ExpressionCheckers.Axioms;

import SyntaxTree.Parser.Parser;
import org.junit.Assert;

/**
 * Created by marsermd on 29.01.2017.
 */
public abstract class AxiomTest
{
    protected void assertMatches(String expression)
    {
        Assert.assertTrue(getChecker().checkMatchesAxiom(Parser.parseDefault(expression)).isRight());
    }

    protected void assertDoesntMatch(String expression)
    {
        Assert.assertTrue(getChecker().checkMatchesAxiom(Parser.parseDefault(expression)).isWrong());
    }

    protected abstract AxiomChecker getChecker();
}
