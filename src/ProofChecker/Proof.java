package ProofChecker;

import SyntaxTree.Structure.Expression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by marsermd on 18.01.2017.
 */
public class Proof
{
    private List<Expression> lines = new ArrayList<Expression>();

    public Proof()
    {
    }

    public Proof(List<Expression> lines)
    {
        this.lines.addAll(lines);
    }

    public void addLine(Expression line)
    {
        lines.add(line);
    }

    public List<Expression> GetProofLines()
    {
        return lines;
    }
}
