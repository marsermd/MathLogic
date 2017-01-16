package SyntaxTree.Parser.Builders;

import SyntaxTree.Parser.Parser;
import SyntaxTree.Structure.Expression;

import java.util.Stack;

/**
 * Created by marsermd on 16.01.2017.
 */
public class ParenthesisHolder
{
    private Stack<Expression> expressions = new Stack<Expression>();
    private Stack<ExpressionBuilder> builders = new Stack<ExpressionBuilder>();

    private void compileBuilderToExpressions()
    {
        expressions.add(builders.pop().createExpression(expressions));
    }

    public void processBuilder(ExpressionBuilder currentBuilder)
    {
        if (currentBuilder == null)
        {
            throw new Parser.BadInputException();
        }
        if (currentBuilder.shouldBuildImediately())
        {
            expressions.add(currentBuilder.createExpression(expressions));
            return;
        }

        while (!builders.empty() && builders.peek().getPriority() < currentBuilder.getPriority())
        {
            compileBuilderToExpressions();
        }

        builders.push(currentBuilder);
    }

    public Expression closeParenthesis()
    {
        while (!builders.empty())
        {
            compileBuilderToExpressions();
        }

        if (expressions.size() != 1)
        {
            throw new Parser.BadInputException();
        }

        return expressions.pop();
    }

    public void takeOtherParenthesisAsExpression(ParenthesisHolder other)
    {
        expressions.push(other.closeParenthesis());
    }
}
