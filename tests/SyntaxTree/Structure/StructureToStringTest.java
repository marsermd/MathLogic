package SyntaxTree.Structure;

import SyntaxTree.Structure.BinaryOperators.Conjunction;
import SyntaxTree.Structure.BinaryOperators.Disjunction;
import SyntaxTree.Structure.BinaryOperators.Implication;
import SyntaxTree.Structure.UnaryOperators.Each;
import SyntaxTree.Structure.UnaryOperators.Negation;
import SyntaxTree.Structure.UnaryOperators.Some;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by marsermd on 10.01.2017.
 */
public class StructureToStringTest
{
    @Test
    public void variableWorks()
    {
        Variable variable = new Variable("A12");
        Assert.assertEquals("A12", variable.toString());
    }

    @Test
    public void eachWorks()
    {
        Variable variable = new Variable("A");
        Each unary = new Each("A", variable);
        Assert.assertEquals("[@A, A]", unary.toString());
    }

    @Test
    public void SomeWorks()
    {
        Variable variable = new Variable("A");
        Some unary = new Some("A", variable);
        Assert.assertEquals("[?A, A]", unary.toString());
    }

    @Test
    public void NegationWorks()
    {
        Variable variable = new Variable("A");
        Negation unary = new Negation(variable);
        Assert.assertEquals("[!, A]", unary.toString());
    }

    @Test
    public void ConjunctionWorks()
    {
        Variable a = new Variable("A");
        Variable b = new Variable("B");
        Conjunction binary = new Conjunction(a, b);
        Assert.assertEquals("[A, &, B]", binary.toString());
    }

    @Test
    public void DisjunctionWorks()
    {
        Variable a = new Variable("A");
        Variable b = new Variable("B");
        Disjunction binary = new Disjunction(a, b);
        Assert.assertEquals("[A, |, B]", binary.toString());
    }

    @Test
    public void ImplicationWorks()
    {
        Variable a = new Variable("A");
        Variable b = new Variable("B");
        Implication binary = new Implication(a, b);
        Assert.assertEquals("[A, ->, B]", binary.toString());
    }
}
