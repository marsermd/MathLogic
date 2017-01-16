package SyntaxTree.Parser;

import SyntaxTree.Parser.Builders.ExpressionBuilder;
import SyntaxTree.Parser.Matchers.ExpressionMatcher;
import SyntaxTree.Structure.BinaryOperators.Conjunction;
import SyntaxTree.Structure.BinaryOperators.Disjunction;
import SyntaxTree.Structure.BinaryOperators.Implication;
import SyntaxTree.Structure.UnaryOperators.Each;
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

        matchers.add(new ExpressionMatcher.EachMatcher());
        matchers.add(new ExpressionMatcher.SomeMatcher());
        matchers.add(new ExpressionMatcher.NegationMatcher());

        matchers.add(new ExpressionMatcher.VariableMatcher());

        return matchers;
    }

    @Test(expected = Parser.BadInputException.class)
    public void parseFailsWithoutMatchers()
    {
        Parser parser = new Parser();
        parser.parse("A->B");
    }

    @Test
    public void parseWorksOnVariable()
    {
        Parser parser = new Parser(getAllMatchers());
        Assert.assertEquals(parser.parse("A123"), new Variable("A123"));
    }

    @Test(expected = Parser.BadInputException.class)
    public void parseFailsOnBadVariable()
    {
        Parser parser = new Parser(getAllMatchers());
        parser.parse("A1A");
    }

    @Test
    public void parseWorksOnNegation()
    {
        Parser parser = new Parser(getAllMatchers());
        Assert.assertEquals(
                new Negation(
                        new Variable("A")
                ),
                parser.parse("!A")
        );
    }

    @Test
    public void parseWorksOnImplication()
    {
        Parser parser = new Parser(getAllMatchers());
        Assert.assertEquals(
                new Implication(
                        new Variable("A"),
                        new Variable("B")
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
                        new Variable("A"),
                        new Variable("B")
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
                        new Variable("A"),
                        new Variable("B")
                ),
                parser.parse("A&B")
        );
    }

    @Test
    public void parseWorksOnEach()
    {
        Parser parser = new Parser(getAllMatchers());
        Assert.assertEquals(
                new Each(
                        "A123",
                        new Variable("B123")
                ),
                parser.parse("@A123 B123")
        );
    }

    @Test
    public void parseWorksOnSome()
    {
        Parser parser = new Parser(getAllMatchers());
        Assert.assertEquals(
                new Some(
                        "A123",
                        new Variable("B123")
                ),
                parser.parse("?A123 B123")
        );
    }

    @Test
    public void parseWorksOnTwoSamePriority()
    {
        Parser parser = new Parser(getAllMatchers());
        Assert.assertEquals(
                new Implication(
                        new Variable("A"),
                        new Implication(
                                new Variable("B"),
                                new Variable("C")
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
                                new Variable("A"),
                                new Variable("B")
                        ),
                        new Variable("C")
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
                                new Variable("A"),
                                new Variable("B")
                        ),
                        new Variable("C")
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
                        new Variable("A"),
                        new Disjunction(
                                new Conjunction(
                                        new Variable("B"),
                                        new Variable("C")
                                ),
                                new Disjunction(
                                        new Conjunction(
                                                new Variable("D"),
                                                new Each(
                                                        "E",
                                                        new Negation(
                                                                new Some(
                                                                        "F",
                                                                        new Variable("G")
                                                                )
                                                        )
                                                )
                                        ),
                                        new Variable("H")
                                )
                        )
                ),
                parser.parse("A->B&C|D&@E!?FG|H")
        );
    }
}