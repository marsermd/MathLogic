package SyntaxTree.Structure;

import SyntaxTree.Structure.UnaryOperators.Each;
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
    public void returnsTrueOnTwoEqualTriesInsideQuantifier()
    {
        {
            AnyFormula formula = new AnyFormula();
            Each anyEach = new Each(formula, formula);
            Each eachA = new Each(new Variable("a"), new Variable("a"));
            Assert.assertTrue(anyEach.fairEquals(eachA));
            Assert.assertTrue(anyEach.fairEquals(eachA));
        }
        {
            AnyFormula formula = new AnyFormula();
            Each anyEach = new Each(formula, new Variable("b"));
            Each eachB = new Each(new Variable("a"), new Variable("b"));
            Assert.assertTrue(anyEach.fairEquals(eachB));
            Assert.assertTrue(anyEach.fairEquals(eachB));
        }
    }

    @Test
    public void returnsFalseOnTwoEqualTriesInsideQuantifier()
    {
        AnyFormula formula = new AnyFormula();
        Each anyEach = new Each(formula, new Variable("b"));
        Each eachA = new Each(new Variable("a"), new Variable("b"));
        Each eachB = new Each(new Variable("b"), new Variable("b"));
        Assert.assertTrue(anyEach.fairEquals(eachA));
        Assert.assertFalse(anyEach.fairEquals(eachB));
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