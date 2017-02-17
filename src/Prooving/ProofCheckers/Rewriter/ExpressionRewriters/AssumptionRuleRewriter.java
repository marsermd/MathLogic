package Prooving.ProofCheckers.Rewriter.ExpressionRewriters;

import Prooving.ExpressionCheckers.ExpressionCheckResult;
import Prooving.ExpressionCheckers.ExpressionChecker;
import Prooving.ExpressionCheckers.InferenceRules.AssumptionRuleResult;
import Prooving.ProofCheckers.Rewriter.RewriterProofResult;
import SyntaxTree.Parser.Matchers.ConcreteFormulaMatcher;
import SyntaxTree.Parser.Parser;
import SyntaxTree.Structure.AnyFormula;
import SyntaxTree.Structure.BinaryOperators.Implication;
import SyntaxTree.Structure.Expression;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marsermd on 06.02.2017.
 */
public class AssumptionRuleRewriter extends ExpressionRewriter<AssumptionRuleResult>
{
    private List<Expression> tautologyProof = new ArrayList<Expression>();
    private AnyFormula alpha = new AnyFormula();

    public AssumptionRuleRewriter()
    {
        String[] tautologyProofSrc = new String[]
        {
            "A->A->A",
            "(A->A->A)->(A->(A->A)->A)->(A->A)",
            "(A->(A->A)->A)->(A->A)",
            "A->(A->A)->A",
            "A->A"
        };

        Parser parser = new Parser();
        parser.addMatcher(new ConcreteFormulaMatcher("A", alpha));
        parser.addMatchers(Parser.getDefaultMatchers());


        for (String line : tautologyProofSrc)
        {
            tautologyProof.add(parser.parse(line));
        }
    }

    @Override
    protected boolean isApplicable(ExpressionCheckResult lineResult)
    {
        return lineResult.getClass() == AssumptionRuleResult.class;
    }

    @Override
    protected void rewriteExpression(ExpressionChecker lineChecker, AssumptionRuleResult lineResult, RewriterProofResult result)
    {
        if (!result.alphaAssumption.equals(lineResult.assumption))
        {
            result.generatedProof.addLine(lineResult.assumption.setComment("assumption1"));
            result.generatedProof.addLine(
                new Implication(
                    lineResult.assumption,
                    new Implication(
                        result.alphaAssumption,
                        lineResult.assumption
                    )
                ).setComment("assumption2")
            );
            result.generatedProof.addLine(
                new Implication(
                    result.alphaAssumption,
                    lineResult.assumption
                ).setComment("assumption3")
            );
        }
        else
        {
            alpha.setEqualExpression(result.alphaAssumption);

            for (Expression line: tautologyProof)
            {
                result.generatedProof.addLine(line.clone().setComment("assumption: tautology" + line));
            }
        }
    }
}
