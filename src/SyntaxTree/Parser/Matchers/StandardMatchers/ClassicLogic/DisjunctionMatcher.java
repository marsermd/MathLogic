package SyntaxTree.Parser.Matchers.StandardMatchers.ClassicLogic;

import SyntaxTree.Parser.Builders.ExpressionBuilder;
import SyntaxTree.Parser.Matchers.StandardMatchers.ExpressionMatcher;
import SyntaxTree.Parser.Parser;
import SyntaxTree.Parser.StringWithPointer;
import SyntaxTree.Structure.BinaryOperators.Disjunction;
import SyntaxTree.Structure.Expression;

import java.util.Stack;

/**
* Created by marsermd on 05.02.2017.
*/
public class DisjunctionMatcher extends ExpressionMatcher
{
    @Override
    protected String getRegexp()
    {
        return "|";
    }


    @Override
    protected boolean useAsString()
    {
        return true;
    }

    @Override
    protected ExpressionBuilder getBuilder(String matched, StringWithPointer unparsed, Parser parser)
    {
        return new ExpressionBuilder(matched, 9)
        {
            @Override
            public boolean isRightAssociative()
            {
                return false;
            }

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
                return new Disjunction(left, right);
            }
        };
    }
}
