package SyntaxTree.Parser.Matchers.StandardMatchers.Predicates;

import SyntaxTree.Parser.Builders.ExpressionBuilder;
import SyntaxTree.Parser.Matchers.StandardMatchers.ExpressionMatcher;
import SyntaxTree.Parser.Parser;
import SyntaxTree.Parser.StringWithPointer;
import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.Function;

import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

/**
* Created by marsermd on 05.02.2017.
*/
public class FunctionMatcher extends ExpressionMatcher
{
    private Pattern argumentDelimer = Pattern.compile(",");
    @Override
    protected String getRegexp()
    {
        return Function.FUNCTION_NAME_REGEX;
    }

    @Override
    protected ExpressionBuilder getBuilder(String matched, StringWithPointer unparsed, Parser parser)
    {
        List<Expression> arguments = parser.parseToList(unparsed, argumentDelimer);

        return new ExpressionBuilder(matched, 1)
        {
            private List<Expression> arguments;

            @Override
            public boolean shouldBuildImediately()
            {
                return true;
            }

            @Override
            public Expression createExpression(Stack<Expression> expressions, Parser parser)
            {
                return new Function(matched, arguments);
            }

            public ExpressionBuilder withInner(List<Expression> arguments)
            {
                this.arguments = arguments;
                return this;
            }
        }.withInner(arguments);
    }
}
