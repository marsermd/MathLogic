package SyntaxTree.Parser.Matchers.StandardMatchers.Predicates;

import SyntaxTree.Parser.Builders.ExpressionBuilder;
import SyntaxTree.Parser.Matchers.StandardMatchers.ExpressionMatcher;
import SyntaxTree.Parser.Parser;
import SyntaxTree.Parser.StringWithPointer;
import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.UnaryOperators.Each;
import SyntaxTree.Structure.Variable;

import java.util.Stack;

/**
* Created by marsermd on 05.02.2017.
*/ //region unary operators matchers
public class EachMatcher extends ExpressionMatcher
{
    @Override
    protected String getRegexp()
    {
        return "@" + Variable.VARIABLE_REGEX;
    }

    @Override
    protected ExpressionBuilder getBuilder(String matched, StringWithPointer unparsed, Parser parser)
    {
        return new ExpressionBuilder(matched, 4)
        {
            @Override
            public boolean shouldBuildImediately()
            {
                return false;
            }

            @Override
            public Expression createExpression(Stack<Expression> expressions, Parser parser)
            {
                Expression quantified = parser.parse(matched.substring(1));
                return new Each(quantified, expressions.pop());
            }
        };
    }
}
