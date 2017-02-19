package Prooving.ExpressionCheckers.Axioms;

import Prooving.ExpressionCheckers.ExpressionCheckResult;
import SyntaxTree.Parser.Matchers.ConcreteFormulaMatcher;
import SyntaxTree.Parser.Parser;
import SyntaxTree.Structure.AnyFormula;
import SyntaxTree.Structure.Expression;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marsermd on 01.02.2017.
 */
public abstract class SimpleAxiomSchemeChecker extends AxiomChecker
{
    private Expression scheme;
    private List<AnyFormula> arguments = new ArrayList<AnyFormula>();

    public SimpleAxiomSchemeChecker(String axiom, String... arguments)
    {
        Parser parser = new Parser();
        for (String argument: arguments)
        {
            AnyFormula formula = new AnyFormula();
            this.arguments.add(formula);
            parser.addMatcher(new ConcreteFormulaMatcher(argument, formula));
        }
        parser.addMatchers(Parser.getDefaultMatchers());
        scheme = parser.parse(axiom);
    }

    public ExpressionCheckResult checkMatchesAxiom(Expression expression)
    {
        for (AnyFormula argument: arguments)
        {
            argument.reset();
        }

        if (scheme.fairEquals(expression))
        {
            return SimpleAxiomResult.right(expression);
        }
        else
        {
            return ExpressionCheckResult.wrong();
        }
    }
}
