package SyntaxTree.Parser;

import SyntaxTree.Parser.Builders.ExpressionBuilder;
import SyntaxTree.Parser.Matchers.ExpressionMatcher;
import SyntaxTree.Structure.BinaryOperators.*;
import SyntaxTree.Structure.Predicate;
import SyntaxTree.Structure.UnaryOperators.Each;
import SyntaxTree.Structure.UnaryOperators.Increment;
import SyntaxTree.Structure.UnaryOperators.Negation;
import SyntaxTree.Structure.UnaryOperators.Some;
import SyntaxTree.Structure.Variable;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ParserTest
{
    private List<ExpressionMatcher> getAllMatchers()
    {
        List<ExpressionMatcher> matchers = new ArrayList<ExpressionMatcher>();

        matchers.add(new ExpressionMatcher.ImplicationMatcher());
        matchers.add(new ExpressionMatcher.DisjunctionMatcher());
        matchers.add(new ExpressionMatcher.ConjunctionMatcher());
        matchers.add(new ExpressionMatcher.EqualsMatcher());
        matchers.add(new ExpressionMatcher.PlusMatcher());
        matchers.add(new ExpressionMatcher.MultiplyMatcher());

        matchers.add(new ExpressionMatcher.EachMatcher());
        matchers.add(new ExpressionMatcher.SomeMatcher());
        matchers.add(new ExpressionMatcher.NegationMatcher());
        matchers.add(new ExpressionMatcher.IncrementMatcher());

        matchers.add(new ExpressionMatcher.VariableMatcher());
        matchers.add(new ExpressionMatcher.PredicateMatcher());

        return matchers;
    }

    @Test(expected = Parser.BadInputException.class)
    public void parseFailsWithoutMatchers()
    {
        Parser parser = new Parser();
        parser.parse("A->B");
    }

    @Test
    public void parseWorksOnPredicate()
    {
        Parser parser = new Parser(getAllMatchers());
        Assert.assertEquals(parser.parse("A123"), new Predicate("A123"));
    }

    @Test
    public void parseWorksOnComplexPredicate()
    {
        Parser parser = new Parser(getAllMatchers());
        Assert.assertEquals(parser.parse("A1(1+(a9*0''), 2 = 3)"), new Predicate("A1(1+(a9*0''),2=3)"));
    }

    @Test(expected = Parser.BadInputException.class)
    public void parseFailsOnBadPredicate()
    {
        Parser parser = new Parser(getAllMatchers());
        parser.parse("A1A");
    }

    @Test
    public void parseWorksOnVariable()
    {
        Parser parser = new Parser(getAllMatchers());
        Assert.assertEquals(parser.parse("a"), new Variable("a"));
    }

    @Test
    public void parseWorksOnImplication()
    {
        Parser parser = new Parser(getAllMatchers());
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
        Parser parser = new Parser(getAllMatchers());
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
        Parser parser = new Parser(getAllMatchers());
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
        Parser parser = new Parser(getAllMatchers());
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
        Parser parser = new Parser(getAllMatchers());
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
        Parser parser = new Parser(getAllMatchers());
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
        Parser parser = new Parser(getAllMatchers());
        Assert.assertEquals(
                new Each(
                        "a123",
                        new Predicate("B123")
                ),
                parser.parse("@a123 B123")
        );
    }

    @Test
    public void parseWorksOnSome()
    {
        Parser parser = new Parser(getAllMatchers());
        Assert.assertEquals(
                new Some(
                        "a123",
                        new Predicate("B123")
                ),
                parser.parse("?a    123 B123")
        );
    }

    @Test
    public void parseWorksOnNegation()
    {
        Parser parser = new Parser(getAllMatchers());
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
        Parser parser = new Parser(getAllMatchers());
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
        Parser parser = new Parser(getAllMatchers());
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
        Parser parser = new Parser(getAllMatchers());
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
        Parser parser = new Parser(getAllMatchers());
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
        Parser parser = new Parser(getAllMatchers());
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
                                                        "e",
                                                        new Negation(
                                                                new Some(
                                                                        "f",
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