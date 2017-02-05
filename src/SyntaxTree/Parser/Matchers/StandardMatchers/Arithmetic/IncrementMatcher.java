package SyntaxTree.Parser.Matchers.StandardMatchers.Arithmetic;

import SyntaxTree.Parser.Builders.ExpressionBuilder;
import SyntaxTree.Parser.Matchers.StandardMatchers.ExpressionMatcher;
import SyntaxTree.Parser.Parser;
import SyntaxTree.Parser.StringWithPointer;
import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.UnaryOperators.Increment;

import java.util.Stack;

/**
* Created by marsermd on 05.02.2017.
*/
public class IncrementMatcher extends ExpressionMatcher
{
    @Override
    protected String getRegexp()
    {
        return "'";
    }

    @Override
    protected boolean useAsString()
    {
        return true;
    }

    @Override
    protected ExpressionBuilder getBuilder(String matched, StringWithPointer unparsed, Parser parser)
    {
        return new ExpressionBuilder(matched, 4)
        {
            @Override
            public boolean shouldBuildImediately()
            {
                return true;
            }

            @Override
            public Expression createExpression(Stack<Expression> expressions, Parser parser)
            {
                return new Increment(expressions.pop());
            }
        };
    }
}
