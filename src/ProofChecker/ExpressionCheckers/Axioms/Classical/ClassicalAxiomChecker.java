package ProofChecker.ExpressionCheckers.Axioms.Classical;

import ProofChecker.ExpressionCheckers.Axioms.AxiomChecker;
import ProofChecker.Proof;
import SyntaxTree.Structure.Expression;

/**
 * Created by marsermd on 01.02.2017.
 */
public abstract class ClassicalAxiomChecker extends AxiomChecker
{
    protected abstract Expression getScheme();

    public boolean MatchesAxiom(Expression expression)
    {
        return getScheme().fairEquals(expression);
    }
}
