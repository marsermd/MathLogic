package SyntaxTree.Structure;

import SyntaxTree.Utils.StringHash;

import java.util.*;

/**
 * Created by marsermd on 08.01.2017.
 */
public class Variable extends Expression
{
    // matches variable regex, but does not match function regex
    public final static String VARIABLE_REGEX = "([a-z][0-9]*|0)";

    private final String name;
    private final int hash;

    public Variable(String name)
    {
        this.name = name;
        this.hash = StringHash.calculate("var" + name, HASH_PRIME);
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
        if (expression instanceof Variable)
        {
            return name.equals(((Variable)expression).getName());
        }
        return false;
    }

    @Override
    public void toParsableString(StringBuilder builder)
    {
        builder.append(name);
    }

    @Override
    public Expression replaceInternal(Variable toReplace, Expression result, List<Variable> quantified)
    {
        if (this.fairEquals(toReplace) && !quantified.contains(this))
        {
            return result;
        }
        return new Variable(name);
    }

    @Override
    public int getExpressionHash()
    {
        return hash;
    }

    @Override
    public void getBindedAndFree(Set<Variable> binded, Set<Variable> free, List<Variable> quantified)
    {
        if (!quantified.contains(this) && !"0".equals(name))
        {
            free.add(this);
        }
    }

    @Override
    public boolean isFreeToReplace(List<Variable> binded, Variable from, Set<Variable> toReplace)
    {
        if (!equals(from))
        {
            return true;
        }
        if (binded.contains(this))
        {
            return true;
        }
        for (Variable variable: binded)
        {
            if (toReplace.contains(variable))
            {
                return false;
            }
        }
        return true;
    }
}
