package SyntaxTree.Parser.Builders;

import SyntaxTree.Parser.Parser;
import SyntaxTree.Parser.StringWithPointer;
import SyntaxTree.Structure.Expression;

import java.util.Stack;

/**
 * Created by marsermd on 04.02.2017.
 */
public abstract class ExpressionBuilder
{
    protected String matched;
    private int priority;

    /**
     * @return true if you should build expression and put it to expression's stack immediately.
     * Currently for variables only
     */
    public abstract boolean shouldBuildImediately();

    public int getPriority()
    {
        return priority;
    }

    public boolean isRightAssociative()
    {
        return true;
    }

    public ExpressionBuilder(String matched, int priority)
    {
        this.matched = matched;
        this.priority = priority;
    }

    public abstract Expression createExpression(Stack<Expression> expressions, Parser parser);
}
