package ProofChecker.Axioms.Common;

import ProofChecker.Axioms.AxiomChecker;
import SyntaxTree.Structure.AnyFormula;
import SyntaxTree.Structure.BinaryOperators.Conjunction;
import SyntaxTree.Structure.BinaryOperators.Disjunction;
import SyntaxTree.Structure.BinaryOperators.Implication;
import SyntaxTree.Structure.Expression;

/**
 * Created by marsermd on 18.01.2017.
 */
public class DFour extends AxiomChecker
{
    @Override
    protected Expression getScheme()
    {
        AnyFormula a = new AnyFormula();
        AnyFormula b = new AnyFormula();
        return new Implication(
            new Conjunction(
                a,
                b
            ),
            a
        );
    }
}
