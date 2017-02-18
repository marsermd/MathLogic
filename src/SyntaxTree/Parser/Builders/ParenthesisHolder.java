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
    private Parser parser;

    public ParenthesisHolder(Parser parser)
    {
        this.parser = parser;
    }

    private void compileBuilderToExpressions()
    {
        expressions.add(builders.pop().createExpression(expressions, parser));
    }

    public void processBuilder(ExpressionBuilder currentBuilder)
    {
        if (currentBuilder == null)
        {
            throw new Parser.BadInputException("no builder");
        }
        if (currentBuilder.shouldBuildImediately())
        {
            expressions.add(currentBuilder.createExpression(expressions, parser));
            return;
        }

        while (!builders.empty())
        {
            if (builders.peek().getPriority() > currentBuilder.getPriority())
            {
                break;
            }
            if (builders.peek().getPriority() == currentBuilder.getPriority() && currentBuilder.isRightAssociative())
            {
                break;
            }
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
            throw new Parser.BadInputException("parenthesis closed but expression count != 1");
        }

        return expressions.pop();
    }

    public void takeOtherParenthesisAsExpression(ParenthesisHolder other)
    {
        expressions.push(other.closeParenthesis());
    }
}
