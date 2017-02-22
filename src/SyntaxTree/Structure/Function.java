package SyntaxTree.Structure;

import SyntaxTree.Utils.StringHash;

import java.util.List;
import java.util.Set;

/**
 * Created by marsermd on 04.02.2017.
 */
public class Function extends Expression
{
    public final static String FUNCTION_NAME_REGEX = "[a-z][0-9]*\\(";

    private int hash;
    private final String name;
    private final Expression[] expressions;

    public Function(String name)
    {
        this(name, new Expression[0]);
    }

    public Function(String name, Expression[] expressions)
    {
        this.name = name;
        this.expressions = expressions;
        initHash();
    }

    public Function(String name, List<Expression> expressions)
    {
        this.name = name;
        this.expressions = new Expression[expressions.size()];
        expressions.toArray(this.expressions);
        initHash();
    }

    private void initHash()
    {
        hash = StringHash.calculate(getClass().toString() + name, HASH_PRIME);
        for (Expression expression : expressions)
        {
            hash *= HASH_PRIME;
            hash += expression.getExpressionHash();
        }
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return name;
    }

    @Override
    public boolean fairEquals(Expression expression)
    {
        if (expression.getClass() != Function.class)
        {
            return false;
        }
        Function other = (Function) expression;
        if (!name.equals((other).getName()) || expressions.length != other.expressions.length)
        {
            return false;
        }
        for (int i = 0; i < expressions.length; i++)
        {
            if (!expressions[i].fairEquals(other.expressions[i]))
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public void toParsableString(StringBuilder builder)
    {
        builder.append(name);
        builder.append("(");
        for (int i = 0; i < expressions.length; i++)
        {
            if (i != 0)
            {
                builder.append(",");
            }
            expressions[i].toParsableString(builder);
        }
        builder.append(")");
    }

    @Override
    public Expression replaceInternal(Variable toReplace, Expression result, List<Variable> quantified)
    {
        Expression[] newExpressions = new Expression[expressions.length];
        for (int i = 0; i < expressions.length; i++)
        {
            newExpressions[i] = expressions[i].replaceInternal(toReplace, result, quantified);
        }
        return new Function(name, newExpressions);
    }

    @Override
    public int getExpressionHash()
    {
        return hash;
    }

    @Override
    public void getBindedAndFree(Set<Variable> binded, Set<Variable> free, List<Variable> quantified)
    {
        for (Expression expression : expressions)
        {
            expression.getBindedAndFree(binded, free, quantified);
        }
    }

    @Override
    public boolean isFreeToReplace(List<Variable> binded, Variable from, Set<Variable> toReplace)
    {
        for (Expression expression: expressions)
        {
            if (!expression.isFreeToReplace(binded, from, toReplace))
            {
                return false;
            }
        }
        return true;
    }
}