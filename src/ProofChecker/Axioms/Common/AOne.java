package ProofChecker.Axioms.Common;

import ProofChecker.Axioms.AxiomChecker;
import SyntaxTree.Structure.AnyFormula;
import SyntaxTree.Structure.BinaryOperators.Implication;
import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.Variable;

/**
 * Created by marsermd on 18.01.2017.
 */
public class AOne extends AxiomChecker
{
    @Override
    protected Expression getScheme()
    {
        AnyFormula a = new AnyFormula();
        AnyFormula b = new AnyFormula();

        return new Implication(
            a,
            new Implication(
                b,
                a
            )
        );
    }
}
