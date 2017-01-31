package SyntaxTree.Structure;

import SyntaxTree.Parser.Parser;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created by marsermd on 31.01.2017.
 */
public class BindedAndFreeTest
{
    private void assertEqualsBindedAndFree(String rawExpression, String[] binded, String[] free)
    {
        Expression expression = Parser.parseDefault(rawExpression);

        //binded
        {
            Collection<Variable> bindedResult = expression.getBindedAndCache();

            Assert.assertEquals(binded.length, bindedResult.size());
            for (int i = 0; i < binded.length; i++)
            {
                Assert.assertTrue(bindedResult.contains(new Variable(binded[i])));
            }
        }

        //free
        {
            Collection<Variable> freeResult = expression.getFreeAndCache();

            Assert.assertEquals(free.length, freeResult.size());
            for (int i = 0; i < free.length; i++)
            {
                Assert.assertTrue(freeResult.contains(new Variable(free[i])));
            }
        }
    }

    @Test
    public void testSimple()
    {
        assertEqualsBindedAndFree("x", new String[0], new String[]{"x"});
        assertEqualsBindedAndFree("A1(x, y)", new String[0], new String[]{"x", "y"});

        assertEqualsBindedAndFree("@x x", new String[]{"x"}, new String[]{});
        assertEqualsBindedAndFree("@x A1(x, y)", new String[]{"x"}, new String[]{"y"});
    }

    @Test
    public void testComplex()
    {
        assertEqualsBindedAndFree("@x (x=x -> @x x)", new String[]{"x"}, new String[]{});
        assertEqualsBindedAndFree("@y @x A1(x + y) -> x = x", new String[]{"x", "y"}, new String[]{"x"});
        assertEqualsBindedAndFree("@y @x A1(x + y) -> x = y", new String[]{"x", "y"}, new String[]{"x", "y"});
    }
}
