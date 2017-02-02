package ProofChecker.ExpressionCheckers;

import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.Variable;

/**
 * Created by marsermd on 02.02.2017.
 */
public class ExpressionCheckResult
{
    public ExpressionCheckResult(Type type, String comment)
    {
        this.type = type;
        this.comment = comment;
    }

    public static ExpressionCheckResult right()
    {
        return new ExpressionCheckResult(Type.Right, null);
    }

    public static ExpressionCheckResult wrong()
    {
        return new ExpressionCheckResult(Type.Wrong, null);
    }

    public static ExpressionCheckResult termIsNotFreeToReplace(Expression phi, Variable alpha, Expression theta)
    {
        return new ExpressionCheckResult(
            Type.Wrong,
            String.format("терм %s не свободен для подстановки в формулу %s вместо переменной %s.", theta, phi, alpha)
        );
    }

    public static ExpressionCheckResult variableIsFreeInFormula(Variable x, Expression phi)
    {
        return new ExpressionCheckResult(
            Type.Wrong,
            String.format("переменная %s входит свободно в формулу %s.", x, phi)
        );
    }

    public boolean isRight()
    {
        return type == Type.Right;
    }

    public boolean isWrong()
    {
        return type == Type.Wrong;
    }

    public final Type type;
    public final String comment;

    public enum Type
    {
        Right,
        Wrong
    }

}
