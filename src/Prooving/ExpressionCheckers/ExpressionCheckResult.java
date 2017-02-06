package Prooving.ExpressionCheckers;

import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.Variable;

/**
 * Created by marsermd on 02.02.2017.
 */
public class ExpressionCheckResult
{
    public final Type type;
    public final String reason;

    public ExpressionCheckResult(Type type, String reason)
    {
        this.type = type;
        this.reason = reason;
    }

    public static ExpressionCheckResult wrong()
    {
        return new ExpressionCheckResult(Type.Wrong, null);
    }

    public boolean isRight()
    {
        return type == Type.Right;
    }

    public boolean isWrong()
    {
        return type == Type.Wrong;
    }

    public boolean isBetterThan(ExpressionCheckResult other)
    {
        if (isRight())
        {
            return true;
        }
        if (other.isRight())
        {
            return false;
        }
        if (reason != null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public enum Type
    {
        Right,
        Wrong
    }
}
