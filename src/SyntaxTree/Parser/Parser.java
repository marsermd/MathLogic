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

/**
 * Created by marsermd on 16.01.2017.
 */
public class Parser
{
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
            addMather(matcher);
        }
    }

    public void addMather(ExpressionMatcher matcher)
    {
        matchers.add(matcher);
    }

    private String fixInputString(String input)
    {
        return input.replaceAll("\\s", "");
    }



    public Expression parse(String input)
    {
        input = fixInputString(input);
        StringWithPointer unparsed = new StringWithPointer(input);

        Stack<ParenthesisHolder> parenthesis = new Stack<ParenthesisHolder>();

        //we could say that the whole expression is in one big parenthesis
        parenthesis.push(new ParenthesisHolder());

        ParenthesisMatcher parenthesisMatcher = new ParenthesisMatcher();
        while (!unparsed.isFinished())
        {
            //opening parenthisis
            if (parenthesisMatcher.matchOpenParenthesis(unparsed))
            {
                parenthesis.push(new ParenthesisHolder());
                continue;
            }
            //closing parenthisis
            if (parenthesisMatcher.matchClosedParenthesis(unparsed))
            {
                ParenthesisHolder closed = parenthesis.pop();
                parenthesis.peek().takeOtherParenthesisAsExpression(closed);
                continue;
            }

            ExpressionBuilder currentBuilder = null;

            for (ExpressionMatcher matcher: matchers)
            {
                currentBuilder = matcher.createBiulder(unparsed);
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

        return parenthesis.pop().closeParenthesis();
    }
}
