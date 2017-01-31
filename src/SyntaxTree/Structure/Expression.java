package SyntaxTree.Structure;

import SyntaxTree.Structure.UnaryOperators.UnaryOperator;

import java.util.*;

/**
 * Created by marsermd on 08.01.2017.
 */
public abstract class Expression
{
    private Collection<Variable> cachedBinded = null;
    private Collection<Variable> cachedFree = null;

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Expression))
        {
            return false;
        }
        return fairEquals((Expression) obj);
    }

    @Override
    public int hashCode()
    {
        return getExpressionHash();
    }

    public abstract boolean fairEquals(Expression expression);

    private void CacheBindedAndFree()
    {
        if (cachedBinded == null)
        {
            HashSet<Variable> binded = new HashSet<Variable>();
            HashSet<Variable> free = new HashSet<Variable>();
            getBindedAndFree(binded, free, new ArrayList<Variable>());
            cachedBinded = binded;
            cachedFree = free;
        }
    }

    /**
     * Cache and
     * @return all binded variables in this expression
     */
    public Collection<Variable> getBindedAndCache()
    {
        CacheBindedAndFree();
        return cachedBinded;
    }

    /**
     * Cache and
     * @return all free variables in this expression
     */
    public Collection<Variable> getFreeAndCache()
    {
        CacheBindedAndFree();
        return cachedFree;
    }

    public abstract void getBindedAndFree(Set<Variable> binded, Set<Variable> free, List<Variable> quantified);

    protected static int HASH_PRIME = 1000003;
    public abstract int getExpressionHash();
}
