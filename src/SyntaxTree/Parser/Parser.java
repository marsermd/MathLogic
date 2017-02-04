package SyntaxTree.Parser;

import SyntaxTree.Parser.Builders.ExpressionBuilder;
import SyntaxTree.Parser.Builders.ParenthesisHolder;
import SyntaxTree.Parser.Matchers.ExpressionMatcher;
import SyntaxTree.Parser.Matchers.ParenthesisMatcher;
import SyntaxTree.Structure.Expression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Created by marsermd on 16.01.2017.
 */
public class Parser
{
    private class ParseResult
    {
        private Expression expression;
        private boolean aborted;

        public boolean isAborted()
        {
            return aborted;
        }

        public void setAborted(boolean aborted)
        {
            this.aborted = aborted;
        }

        public Expression getExpression()
        {
            return expression;
        }

        public void setExpression(Expression expression)
        {
            this.expression = expression;
        }
    }

    public static class BadInputException extends RuntimeException
    {}

    private List<ExpressionMatcher> matchers = new ArrayList<ExpressionMatcher>();

    public Parser()
    {
    }

    public Parser(Collection<ExpressionMatcher> matchers)
    {
        for (ExpressionMatcher matcher: matchers)
        {
            addMatcher(matcher);
        }
    }

    public static List<ExpressionMatcher> getDefaultMatchers()
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
        matchers.add(new ExpressionMatcher.FunctionMatcher());

        return matchers;
    }

    public static Parser createDefault()
    {
        return new Parser(getDefaultMatchers());
    }

    public void addMatchers(List<ExpressionMatcher> matcher)
    {
        matchers.addAll(matcher);
    }

    public void addMatcher(ExpressionMatcher matcher)
    {
        matchers.add(matcher);
    }

    private String fixInputString(String input)
    {
        return input.replaceAll("\\s", "");
    }


    public static Expression parseDefault(String input)
    {
        return Parser.createDefault().parse(input);
    }

    /**
     * This is a pure function. That means it doesn't change the state of the class by itself.
     * The only thing could be done is that someone could call addMatcher from inside the call tree.
     * @param string to parse
     * @return parsed syntax tree
     */
    public Expression parse(String input)
    {
        StringWithPointer unparsed = new StringWithPointer(fixInputString(input));
        return parse(unparsed);
    }

    public Expression parse(StringWithPointer unparsed)
    {
        return parse(unparsed, null).getExpression();
    }

    public List<Expression> parseToList(String input)
    {
        StringWithPointer unparsed = new StringWithPointer(fixInputString(input));
        return parseToList(unparsed, Pattern.compile(","));
    }

    public List<Expression> parseToList(String input, Pattern stopSignRegexp)
    {
        StringWithPointer unparsed = new StringWithPointer(fixInputString(input));
        return parseToList(unparsed, stopSignRegexp);
    }

    public List<Expression> parseToList(StringWithPointer unparsed, Pattern stopSignRegexp)
    {
        List<Expression> expressions = new ArrayList<Expression>();
        while (!unparsed.isFinished())
        {
            ParseResult result = parse(unparsed, stopSignRegexp);
            expressions.add(result.getExpression());
            if (result.isAborted())
            {
                break;
            }
        }

        return expressions;
    }

    private ParseResult parse(StringWithPointer unparsed, Pattern splitSignRegexp)
    {
        Stack<ParenthesisHolder> parenthesis = new Stack<ParenthesisHolder>();

        //we could say that the whole expression is in one big parenthesis
        parenthesis.push(new ParenthesisHolder(this));

        ParenthesisMatcher parenthesisMatcher = new ParenthesisMatcher();

        ParseResult result = new ParseResult();

        while (!unparsed.isFinished())
        {
            // split sign found
            if (splitSignRegexp != null && unparsed.getNextToken(splitSignRegexp) != null)
            {
                break;
            }

            //opening parenthisis
            if (parenthesisMatcher.matchOpenParenthesis(unparsed))
            {
                parenthesis.push(new ParenthesisHolder(this));
                continue;
            }
            //closing parenthisis
            if (parenthesisMatcher.matchClosedParenthesis(unparsed))
            {
                if (parenthesis.size() == 1)
                {
                    // no real parenthesis to close
                    result.setAborted(true);
                    break;
                }
                ParenthesisHolder closed = parenthesis.pop();
                parenthesis.peek().takeOtherParenthesisAsExpression(closed);
                continue;
            }

            ExpressionBuilder currentBuilder = null;

            for (ExpressionMatcher matcher: matchers)
            {
                currentBuilder = matcher.createBiulder(unparsed, this);
                if (currentBuilder != null)
                {
                    break;
                }
            }

            parenthesis.peek().processBuilder(currentBuilder);
        }

        if (parenthesis.size() != 1)
        {
            throw new BadInputException();
        }

        result.setExpression(parenthesis.pop().closeParenthesis());

        return result;
    }
}
