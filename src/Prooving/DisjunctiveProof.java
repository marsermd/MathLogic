package Prooving;

import SyntaxTree.Structure.Expression;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by marsermd on 21.02.2017.
 */
public class DisjunctiveProof extends Proof
{
    private HashSet<Expression> usedExpressions = new HashSet<Expression>();

    @Override
    public void addLines(List<Expression> lines)
    {
        for (Expression line: lines)
        {
            addLine(line);
        }
    }

    @Override
    public void addLine(Expression line)
    {
        if (!usedExpressions.contains(line))
        {
            usedExpressions.add(line);
            lines.add(line);
        }
    }
}
