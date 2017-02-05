package SyntaxTree.Parser.Matchers.StandardMatchers;

import SyntaxTree.Parser.Builders.ExpressionBuilder;
import SyntaxTree.Parser.Parser;
import SyntaxTree.Parser.StringWithPointer;
import SyntaxTree.Structure.BinaryOperators.*;
import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.Function;
import SyntaxTree.Structure.Predicate;
import SyntaxTree.Structure.UnaryOperators.Each;
import SyntaxTree.Structure.UnaryOperators.Increment;
import SyntaxTree.Structure.UnaryOperators.Negation;
import SyntaxTree.Structure.UnaryOperators.Some;
import SyntaxTree.Structure.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Created by marsermd on 11.01.2017.
 */
public abstract class ExpressionMatcher
{
    protected abstract String getRegexp();
    protected abstract ExpressionBuilder getBuilder(String matched, StringWithPointer unparsed, Parser parser);

    private Pattern pattern;

    public ExpressionMatcher()
    {
        if (!useAsString())
        {
            pattern = Pattern.compile(getRegexp());
        }
    }

    protected ExpressionMatcher(String regexp)
    {
        pattern = Pattern.compile(regexp);
    }

    protected boolean useAsString()
    {
        return false;
    }

    public ExpressionBuilder createBiulder(StringWithPointer unparsed, Parser parser)
    {
        String matched;
        if (useAsString())
        {
            matched = unparsed.getNextTokenNonRegexp(getRegexp());
        }
        else
        {
            matched = unparsed.getNextToken(pattern);
        }
        if (matched != null)
        {
            return getBuilder(matched, unparsed, parser);
        }
        return null;
    }
}
