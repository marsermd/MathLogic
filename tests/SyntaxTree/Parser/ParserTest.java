package SyntaxTree.Parser;

import SyntaxTree.Structure.BinaryOperators.*;
import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.Predicate;
import SyntaxTree.Structure.UnaryOperators.Each;
import SyntaxTree.Structure.UnaryOperators.Increment;
import SyntaxTree.Structure.UnaryOperators.Negation;
import SyntaxTree.Structure.UnaryOperators.Some;
import SyntaxTree.Structure.Variable;
import org.junit.Assert;
import org.junit.Test;

public class ParserTest
{
    @Test(expected = Parser.BadInputException.class)
    public void parseFailsWithoutMatchers()
    {
        Parser parser = new Parser();
        parser.parse("A->B");
    }

    @Test
    public void parseWorksOnPredicate()
    {
        Parser parser = Parser.createDefault();
        Assert.assertEquals(parser.parse("A123"), new Predicate("A123"));
    }

    @Test
    public void parseWorksOnComplexPredicate()
    {
        Parser parser = Parser.createDefault();

        Assert.assertEquals(
            parser.parse("A1((a2 * x), a'')"),
            new Predicate("A1", new Expression[]
                {
                    new Multiply(new Variable("a2"), new Variable("x")),
                    new Increment(new Increment(new Variable("a")))
                })
        );
    }

    @Test(expected = Parser.BadInputException.class)
    public void parseFailsOnBadPredicate()
    {
        Parser parser = Parser.createDefault();
        parser.parse("A1A");
    }

    @Test
    public void parseWorksOnVariable()
    {
        Parser parser = Parser.createDefault();
        Assert.assertEquals(parser.parse("a"), new Variable("a"));
    }

    @Test
    public void parseWorksOnImplication()
    {
        Parser parser = Parser.createDefault();
        Assert.assertEquals(
            new Implication(
                new Predicate("A"),
                new Predicate("B")
            ),
            parser.parse("A->B")
        );
    }

    @Test
    public void parseWorksOnDisjunction()
    {
        Parser parser = Parser.createDefault();
        Assert.assertEquals(
            new Disjunction(
                new Predicate("A"),
                new Predicate("B")
            ),
            parser.parse("A|B")
        );
    }

    @Test
    public void parseWorksOnConjunction()
    {
        Parser parser = Parser.createDefault();
        Assert.assertEquals(
            new Conjunction(
                new Predicate("A"),
                new Predicate("B")
            ),
            parser.parse("A&B")
        );
    }

    @Test
    public void parseWorksOnEquals()
    {
        Parser parser = Parser.createDefault();
        Assert.assertEquals(
            new Equals(
                new Variable("a"),
                new Variable("b")
            ),
            parser.parse("a=b")
        );
    }

    @Test
    public void parseWorksOnPlus()
    {
        Parser parser = Parser.createDefault();
        Assert.assertEquals(
            new Plus(
                new Variable("a"),
                new Variable("b")
            ),
            parser.parse("a+b")
        );
    }

    @Test
    public void parseWorksOnMultiply()
    {
        Parser parser = Parser.createDefault();
        Assert.assertEquals(
            new Multiply(
                new Variable("a"),
                new Variable("b")
            ),
            parser.parse("a*b")
        );
    }

    @Test
    public void parseWorksOnEach()
    {
        Parser parser = Parser.createDefault();
        Assert.assertEquals(
            new Each(
                new Variable("a123"),
                new Predicate("B123")
            ),
            parser.parse("@a123 B123")
        );
    }

    @Test
    public void parseWorksOnSome()
    {
        Parser parser = Parser.createDefault();
        Assert.assertEquals(
            new Some(
                new Variable("a123"),
                new Predicate("B123")
            ),
            parser.parse("?a    123 B123")
        );
    }

    @Test
    public void parseWorksOnNegation()
    {
        Parser parser = Parser.createDefault();
        Assert.assertEquals(
            new Negation(
                new Predicate("A")
            ),
            parser.parse("!A")
        );
    }

    @Test
    public void parseWorksOnIncrement()
    {
        Parser parser = Parser.createDefault();
        Assert.assertEquals(
            new Increment(
                new Variable("a")
            ),
            parser.parse("a'")
        );
    }

    @Test
    public void parseWorksOnTwoSamePriority()
    {
        Parser parser = Parser.createDefault();
        Assert.assertEquals(
            new Implication(
                new Predicate("A"),
                new Implication(
                    new Predicate("B"),
                    new Predicate("C")
                )
            ),
            parser.parse("A->B->C")
        );
    }

    @Test
    public void parseWorksOnTwoDifferentPriority()
    {
        Parser parser = Parser.createDefault();
        Assert.assertEquals(
            new Implication(
                new Conjunction(
                    new Predicate("A"),
                    new Predicate("B")
                ),
                new Predicate("C")
            ),
            parser.parse("A&B->C")
        );
    }

    @Test
    public void parseWorksWithParenthesis()
    {
        Parser parser = Parser.createDefault();
        Assert.assertEquals(
            new Conjunction(
                new Implication(
                    new Predicate("A"),
                    new Predicate("B")
                ),
                new Predicate("C")
            ),
            parser.parse("(A->B)&C")
        );
    }

    @Test
    public void parseWorksWithComplex()
    {
        Parser parser = Parser.createDefault();
        Assert.assertEquals(
            new Implication(
                new Predicate("A"),
                new Disjunction(
                    new Conjunction(
                        new Predicate("B"),
                        new Predicate("C")
                    ),
                    new Disjunction(
                        new Conjunction(
                            new Predicate("D"),
                            new Each(
                                new Variable("e"),
                                new Negation(
                                    new Some(
                                        new Variable("f"),
                                        new Predicate("G")
                                    )
                                )
                            )
                        ),
                        new Predicate("H")
                    )
                )
            ),
            parser.parse("A->B&C|D&@e!?fG|H")
        );
    }
}