package SyntaxTree.Structure;

/**
 * Created by marsermd on 16.01.2017.
 */
public class Predicate extends Expression
{
    public final static String PREDICATE_REGEX = "[A-Z][0-9]*(\\([a-z|0-9|+|*|=|,|'|(|)]+\\))?";
    private String name;

    public Predicate(String name)
    {
        this.name = name;
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
    public boolean FairEquals(Expression expression)
    {
        if (expression.getClass() == Predicate.class)
        {
            return name.equals(((Predicate)expression).getName());
        }
        return false;
    }
}
