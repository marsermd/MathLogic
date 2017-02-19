package Prooving.ExpressionCheckers.Axioms.Arithmetical;

import Prooving.ExpressionCheckers.Axioms.AxiomChecker;
import Prooving.ExpressionCheckers.Axioms.SimpleAxiomResult;
import Prooving.ExpressionCheckers.ExpressionCheckResult;
import SyntaxTree.Parser.Parser;
import SyntaxTree.Structure.Expression;

/**
 * Created by marsermd on 19.02.2017.
 */
public class SimpleAxiomChecker extends AxiomChecker
{
    private Expression axiom;

    protected SimpleAxiomChecker(String expression)
    {
        axiom = Parser.parseDefault(expression);
    }

    @Override
    public ExpressionCheckResult checkMatchesAxiom(Expression expression)
    {
        if (axiom.equals(expression))
        {
            return SimpleAxiomResult.right(expression);
        }
        else
        {
            return SimpleAxiomResult.wrong();
        }
    }
}
