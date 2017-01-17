package ProofChecker;

import SyntaxTree.Structure.Expression;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marsermd on 18.01.2017.
 */
public class Proof
{
    private List<Expression> expressions = new ArrayList<Expression>();

    public Proof()
    {

    }

    public Proof(List<Expression> expressions)
    {
        this.expressions.addAll(expressions);
    }
}
