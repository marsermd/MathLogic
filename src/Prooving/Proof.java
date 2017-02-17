package Prooving;

import SyntaxTree.Structure.Expression;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marsermd on 18.01.2017.
 */
public class Proof
{
    public List<Expression> getAssumptions()
    {
        return assumptions;
    }

    private List<Expression> assumptions = new ArrayList<Expression>();
    private List<Expression> lines = new ArrayList<Expression>();
    private Expression goal;

    public Proof()
    {
    }

    public Proof(List<Expression> lines)
    {
        this.lines.addAll(lines);
    }

    public void addFirstAssumption(Expression expression)
    {
        assumptions.add(0, expression);
    }
    public void assumeThat(List<Expression> assumptions)
    {
        this.assumptions.addAll(assumptions);
    }
    public void assumeThat(Expression assumption)
    {
        assumptions.add(assumption);
    }

    public void setGoal(Expression goal)
    {
        this.goal = goal;
    }

    public Expression getGoal()
    {
        return goal;
    }

    public void addLines(List<Expression> lines)
    {
        this.lines.addAll(lines);
    }
    public void addLine(Expression line)
    {
        lines.add(line);
    }

    public List<Expression> getProofLines()
    {
        return lines;
    }

}
