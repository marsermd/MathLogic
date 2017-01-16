package SyntaxTree.Structure;

/**
 * Created by marsermd on 08.01.2017.
 */
public class Variable extends Expression
{
    private String name;

    public Variable(String name)
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
        if (expression.getClass() == Variable.class)
        {
            return name.equals(((Variable)expression).getName());
        }
        return false;
    }
}
