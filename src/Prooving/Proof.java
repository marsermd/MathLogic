package Prooving;

import SyntaxTree.Structure.Expression;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
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
    protected List<Expression> lines = new ArrayList<Expression>();
    private Expression goal;
    int temporaryCnt = 0;

    public Proof()
    {
    }

    public Proof(List<Expression> lines)
    {
        this.lines.addAll(lines);
    }

    public void addTemporaryAssumption(Expression expression)
    {
        assumptions.add(0, expression);
        temporaryCnt++;
    }

    public void removeTemporaryAssumptions()
    {
        assumptions = assumptions.subList(temporaryCnt, assumptions.size());
        temporaryCnt = 0;
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

    public void printProof(BufferedWriter out) throws IOException
    {
        for (int i = 0; i < getAssumptions().size(); i++)
        {
            if (i != 0)
            {
                out.write(",");
            }
            out.write(getAssumptions().get(i).toParsableString());
        }
        out.write("|-");
        out.write(getGoal().toParsableString());
        out.newLine();
        for (Expression line : getProofLines())
        {
            out.write(line.toParsableString());
            out.newLine();
        }
    }
}
