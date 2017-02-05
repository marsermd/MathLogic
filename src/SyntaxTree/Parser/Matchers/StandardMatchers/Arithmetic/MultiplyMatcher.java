package SyntaxTree.Parser.Matchers.StandardMatchers.Arithmetic;

import SyntaxTree.Parser.Builders.ExpressionBuilder;
import SyntaxTree.Parser.Matchers.StandardMatchers.ExpressionMatcher;
import SyntaxTree.Parser.Parser;
import SyntaxTree.Parser.StringWithPointer;
import SyntaxTree.Structure.BinaryOperators.Multiply;
import SyntaxTree.Structure.Expression;

import java.util.Stack;

/**
* Created by marsermd on 05.02.2017.
*/
public class MultiplyMatcher extends ExpressionMatcher
{
    @Override
    protected String getRegexp()
    {
        return "*";
    }

    @Override
    protected boolean useAsString()
    {
        return true;
    }

    @Override
    protected ExpressionBuilder getBuilder(String matched, StringWithPointer unparsed, Parser parser)
    {
        return new ExpressionBuilder(matched, 5)
        {
            @Override
            public boolean shouldBuildImediately()
            {
                return false;
            }

            @Override
            public Expression createExpression(Stack<Expression> expressions, Parser parser)
            {
                Expression right = expressions.pop();
                Expression left = expressions.pop();
                return new Multiply(left, right);
            }
        };
    }
}
