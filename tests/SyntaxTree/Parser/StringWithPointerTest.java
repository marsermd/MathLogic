package SyntaxTree.Parser;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class StringWithPointerTest
{
    @Test
    public void testIsFinished() throws Exception
    {
        StringWithPointer test = new StringWithPointer("");
        Assert.assertTrue(test.isFinished());
    }

    @Test
    public void testMoveToNextPosition() throws Exception
    {
        StringWithPointer test = new StringWithPointer("ab");
        Assert.assertFalse(test.isFinished());
        test.moveToNextPosition();
        Assert.assertFalse(test.isFinished());
        test.moveToNextPosition();
        Assert.assertTrue(test.isFinished());

        boolean hasThrown = false;
        try
        {
            test.moveToNextPosition();
        }
        catch (Exception e)
        {
            hasThrown = true;
        }

        Assert.assertTrue(hasThrown);
    }

    @Test
    public void testGetNextTokenAll() throws Exception
    {
        String testString = "aabc   ff s@!#$%^&*()";
        StringWithPointer test = new StringWithPointer(testString);
        Pattern all = Pattern.compile(".*");
        String result = test.getNextToken(all);

        Assert.assertEquals(testString, result);
        Assert.assertTrue(test.isFinished());
    }

    @Test
    public void testGetNextTokenComplex() throws Exception
    {
        String first = "abcd";
        String second = "efgh";
        Assert.assertNotEquals(first, second);

        StringWithPointer test = new StringWithPointer(first + " " + second);
        Pattern all = Pattern.compile("[a-z]*");

        String result = test.getNextToken(all);
        Assert.assertEquals(first, result);

        test.moveToNextPosition();

        result = test.getNextToken(all);
        Assert.assertEquals(second, result);

        result = test.getNextToken(all);
        Assert.assertEquals(null, result);
        Assert.assertTrue(test.isFinished());
    }
}