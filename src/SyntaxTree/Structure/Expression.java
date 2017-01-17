package SyntaxTree.Structure;

/**
 * Created by marsermd on 08.01.2017.
 */
public abstract class Expression
{
    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Expression))
        {
            return false;
        }
        return fairEquals((Expression) obj);
    }

    public abstract boolean fairEquals(Expression expression);
}
