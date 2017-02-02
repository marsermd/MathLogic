package SyntaxTree.Parser.Matchers;

import SyntaxTree.Parser.Builders.ExpressionBuilder;
import SyntaxTree.Parser.Parser;
import SyntaxTree.Structure.Expression;

import java.util.Stack;

/**
 * Created by marsermd on 02.02.2017.
 */
public class ConcreteFormulaMatcher extends ExpressionMatcher
{
    private String token;
    private Expression instance;

    /**
     * Matches given token to expression with lowest possible priotity
     * @param from token to match from
     * @param to resulting expression
     */
    public ConcreteFormulaMatcher(String from, Expression to)
    {
        token = from;
        instance = to;
    }

    @Override
    protected String getRegexp()
    {
        return token;
    }

    @Override
    protected ExpressionBuilder getBuilder(String matched)
    {
        return new ExpressionBuilder(matched, -1)
        {
            @Override
            public boolean shouldBuildImediately()
            {
                return false;
            }

            @Override
            public Expression createExpression(Stack<Expression> expressions, Parser parser)
            {
                return instance;
            }
        };
    }
}
