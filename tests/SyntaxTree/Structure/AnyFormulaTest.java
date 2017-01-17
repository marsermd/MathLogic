package SyntaxTree.Structure;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class AnyFormulaTest
{
    @Test
    public void returnsTrueOnFirstTry()
    {
        AnyFormula formula = new AnyFormula();
        Variable a = new Variable("a");
        Assert.assertTrue(formula.fairEquals(a));
    }

    @Test
    public void returnsTrueOnTwoEqualTries()
    {
        AnyFormula formula = new AnyFormula();
        Variable a = new Variable("a");
        Assert.assertTrue(formula.fairEquals(a));
        Assert.assertTrue(formula.fairEquals(a));
    }

    @Test
    public void returnsFalseOnTwoDifferentTries()
    {
        AnyFormula formula = new AnyFormula();
        Variable a = new Variable("a");
        Predicate b = new Predicate("b");
        Assert.assertTrue(formula.fairEquals(a));
        Assert.assertFalse(formula.fairEquals(b));
    }
}