package Prooving.ExpressionCheckers.Axioms;

import Prooving.ExpressionCheckers.Axioms.AxiomChecker;
import Prooving.ExpressionCheckers.ExpressionCheckResult;
import SyntaxTree.Parser.Matchers.ConcreteFormulaMatcher;
import SyntaxTree.Parser.Parser;
import SyntaxTree.Structure.AnyFormula;
import SyntaxTree.Structure.Expression;

/**
 * Created by marsermd on 01.02.2017.
 */
public abstract class SimpleAxiomChecker extends AxiomChecker
{
    protected abstract Expression getScheme();

    public ExpressionCheckResult checkMatchesAxiom(Expression expression)
    {
        if (getScheme().fairEquals(expression))
        {
            return ExpressionCheckResult.right();
        }
        else
        {
            return ExpressionCheckResult.wrong();
        }
    }

    protected Expression fromString(String axiom, String... arguments)
    {
        Parser parser = new Parser();
        for (String argument: arguments)
        {
            parser.addMatcher(new ConcreteFormulaMatcher(argument, new AnyFormula()));
        }
        parser.addMatchers(Parser.getDefaultMatchers());
        return parser.parse(axiom);
    }
}
