package SyntaxTree.Structure;

import SyntaxTree.Parser.StringWithPointer;

import java.util.*;

/**
 * Created by marsermd on 08.01.2017.
 */
public abstract class Expression
{
    protected static int HASH_PRIME = 1000003;
    private Collection<Variable> cachedBinded = null;
    private Collection<Variable> cachedFree = null;
    private String comment = "";

    public String getComment()
    {
        return comment;
    }

    public Expression setComment(String comment)
    {
        this.comment = comment;
        return this;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Expression))
        {
            return false;
        }
        // This is not really reliable as comparisons ammount gets close to 10^6
        // You might want to add && fairEquals
        return getExpressionHash() == ((Expression) obj).getExpressionHash();
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
     * @return all binded variables in this expression
     */
    public Collection<Variable> getBinded()
    {
        if (cachedBinded != null)
        {
            return cachedBinded;
        }
        HashSet<Variable> binded = new HashSet<Variable>();
        HashSet<Variable> free = new HashSet<Variable>();
        getBindedAndFree(binded, free, new ArrayList<Variable>());
        return binded;
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

    /**
     * @return all free variables in this expression
     */
    public Collection<Variable> getFree()
    {
        if (cachedFree != null)
        {
            return cachedFree;
        }
        HashSet<Variable> binded = new HashSet<Variable>();
        HashSet<Variable> free = new HashSet<Variable>();
        getBindedAndFree(binded, free, new ArrayList<Variable>());
        return free;
    }

    public void getBindedAndFree(Set<Variable> binded, Set<Variable> free)
    {
        getBindedAndFree(binded, free, new ArrayList<Variable>());
    }

    public Expression clone()
    {
        return replace(null, null);
    }

    public Expression replace(Variable toReplace, Expression result)
    {
        return replaceInternal(toReplace, result, new ArrayList<Variable>());
    }

    public String toParsableString()
    {
        StringBuilder builder = new StringBuilder();
        toParsableString(builder);
        return builder.toString();
    }

    public abstract void toParsableString(StringBuilder builder);

    public abstract Expression replaceInternal(Variable toReplace, Expression result, List<Variable> quantified);

    public abstract void getBindedAndFree(Set<Variable> binded, Set<Variable> free, List<Variable> quantified);

    public abstract int getExpressionHash();
}
