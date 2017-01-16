package SyntaxTree.Parser.Builders;

import SyntaxTree.Structure.Expression;

import java.util.Stack;
import java.util.regex.Pattern;

/**
 * Created by marsermd on 11.01.2017.
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

    public ExpressionBuilder(String matched, int priority)
    {
        this.matched = matched;
        this.priority = priority;
    }

    public abstract Expression createExpression(Stack<Expression> expressions);
}
