package ProofChecker.Axioms;

import SyntaxTree.Structure.AnyFormula;
import SyntaxTree.Structure.BinaryOperators.Implication;
import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.Variable;

/**
 * Created by marsermd on 18.01.2017.
 */
public abstract class AxiomChecker
{
    protected abstract Expression getScheme();

    public boolean MatchesAxiom(Expression expression)
    {
        return getScheme().fairEquals(expression);
    }
}
