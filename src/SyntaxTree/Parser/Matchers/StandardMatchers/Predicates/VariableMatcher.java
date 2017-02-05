package SyntaxTree.Parser.Matchers.StandardMatchers.Predicates;

import SyntaxTree.Parser.Builders.ExpressionBuilder;
import SyntaxTree.Parser.Matchers.StandardMatchers.ExpressionMatcher;
import SyntaxTree.Parser.Parser;
import SyntaxTree.Parser.StringWithPointer;
import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.Variable;

import java.util.Stack;

/**
* Created by marsermd on 05.02.2017.
*/ //region values
public class VariableMatcher extends ExpressionMatcher
{
    @Override
    protected String getRegexp()
    {
        return Variable.VARIABLE_REGEX + "(?![0-9]*\\()";
    }

    @Override
    protected ExpressionBuilder getBuilder(String matched, StringWithPointer unparsed, Parser parser)
    {
        return new ExpressionBuilder(matched, 1)
        {
            @Override
            public boolean shouldBuildImediately()
            {
                return true;
            }

            @Override
            public Expression createExpression(Stack<Expression> expressions, Parser parser)
            {
                return new Variable(matched);
            }
        };
    }
}
