package SyntaxTree.Structure;

import SyntaxTree.Utils.StringHash;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by marsermd on 16.01.2017.
 */
public class Predicate extends Expression
{
    public final static String PREDICATE_NAME_REGEX = "[A-Z][0-9]*";
    public final static String PREDICATE_ARGUMENTS_REGEX = "(\\([a-z|0-9|+|*|,|'|(|)]+\\))?";

    private String name;
    private Expression[] expressions;

    public Predicate(String name)
    {
        this(name, new Expression[0]);
    }

    public Predicate(String name, Expression[] expressions)
    {
        this.name = name;
        this.expressions = expressions;
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
        if (expression.getClass() != Predicate.class)
        {
            return false;
        }
        Predicate other = (Predicate)expression;
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
    public int getExpressionHash()
    {
        int result = StringHash.calculate(getClass().toString() + name, HASH_PRIME);
        for (Expression expression: expressions)
        {
            result *= HASH_PRIME;
            result += expression.getExpressionHash();
        }
        return result;
    }

    @Override
    public void getBindedAndFree(Set<Variable> binded, Set<Variable> free, List<Variable> quantified)
    {
        for (Expression expression: expressions)
        {
            expression.getBindedAndFree(binded, free, quantified);
        }
    }
}
