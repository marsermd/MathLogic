package ProofChecker.ExpressionCheckers.InferenceRules;

import ProofChecker.ExpressionCheckers.ExpressionChecker;
import ProofChecker.Proof;
import SyntaxTree.Structure.AnyFormula;
import SyntaxTree.Structure.BinaryOperators.Implication;
import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.UnaryOperators.Each;
import org.omg.CORBA.Any;

/**
 * Created by marsermd on 01.02.2017.
 */
public class EachRule implements ExpressionChecker
{
    @Override
    public boolean Matches(Proof proof, int currentLine)
    {
        Expression current = proof.GetProofLines().get(currentLine);
        AnyFormula Phi = new AnyFormula();
        AnyFormula Psi = new AnyFormula();
        AnyFormula x = new AnyFormula();
        Expression currentMatcher = new Implication(
            new Each(
                x,
                Psi
            ),
            Phi
        );

        if (!currentMatcher.fairEquals(current))
        {
            return false;
        }

        Expression previousMatch = new Implication(
            Psi.getEqualExpression(),
            Phi.getEqualExpression()
        );

        boolean result = false;
        for (int i = 0; i < currentLine; i++)
        {
            Expression previous = proof.GetProofLines().get(i);
            if (previous.equals(previousMatch))
            {
                result = true;
                break;
            }
        }
        if (result)
        {
            if (!Phi.getFree().contains(x.getEqualExpression()))
            {
                return true;
            }
        }
        return false;
    }
}
