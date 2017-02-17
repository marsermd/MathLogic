package Prooving.ProofCheckers.Rewriter.ExpressionRewriters;

import Prooving.ExpressionCheckers.ExpressionCheckResult;
import Prooving.ExpressionCheckers.ExpressionChecker;
import Prooving.ExpressionCheckers.InferenceRules.Predicate.EachRule;
import Prooving.ExpressionCheckers.InferenceRules.Predicate.PredicateRuleResult;
import Prooving.ExpressionCheckers.InferenceRules.Predicate.SomeRule;
import Prooving.ProofCheckers.Rewriter.RewriterProofResult;
import SyntaxTree.Parser.Matchers.ConcreteFormulaMatcher;
import SyntaxTree.Parser.Parser;
import SyntaxTree.Structure.AnyFormula;
import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.Variable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marsermd on 05.02.2017.
 */
public class PredicateRuleRewriter extends ExpressionRewriter<PredicateRuleResult>
{
    private final List<Expression> eachRule = new ArrayList<Expression>();
    private final List<Expression> someRule = new ArrayList<Expression>();
    private final AnyFormula alpha, phi, psi, x;


    public PredicateRuleRewriter()
    {
        String[] eachRuleProofSrc = new String[] {
            "A->C->B",
            "A&C->A",
            "A&C->C",
            "A->C->B",
            "(A->C->B)->A&C->A->C->B",
            "A&C->A->C->B",
            "(A&C->A)->(A&C->A->C->B)->A&C->C->B",
            "(A&C->A->C->B)->A&C->C->B",
            "A&C->C->B",
            "(A&C->C)->(A&C->C->B)->(A&C->B)",
            "(A&C->C->B)->(A&C->B)",
            "A&C->B",
            "A&C->@xB",
            "A->C->A&C",
            "A&C->@xB",
            "(A&C->@xB)->A->A&C->@xB",
            "A->A&C->@xB",
            "(A&C->@xB)->C->A&C->@xB",
            "((A&C->@xB)->C->A&C->@xB)->A->(A&C->@xB)->C->A&C->@xB",
            "A->(A&C->@xB)->C->A&C->@xB",
            "(A->A&C->@xB)->(A->((A&C->@xB)->C->A&C->@xB))->A->C->A&C->@xB",
            "(A->((A&C->@xB)->C->A&C->@xB))->A->C->A&C->@xB",
            "A->C->A&C->@xB",
            "(C->A&C)->(C->A&C->@xB)->C->@xB",
            "((C->A&C)->(C->A&C->@xB)->C->@xB)->A->(C->A&C)->(C->A&C->@xB)->C->@xB",
            "A->(C->A&C)->(C->A&C->@xB)->C->@xB",
            "(A->C->A&C)->(A->(C->A&C)->(C->A&C->@xB)->C->@xB)->A->(C->A&C->@xB)->C->@xB",
            "(A->(C->A&C)->(C->A&C->@xB)->C->@xB)->A->(C->A&C->@xB)->C->@xB",
            "A->(C->A&C->@xB)->C->@xB",
            "(A->C->A&C->@xB)->(A->(C->A&C->@xB)->C->@xB)->A->C->@xB",
            "(A->(C->A&C->@xB)->C->@xB)->A->C->@xB",
            "A->C->@xB"
        };

        String[] someRuleProofSrc = new String[] {
            "B->A->B",
            "A->B->C",
            "(A->B->C)->B->A->B->C",
            "B->A->B->C",
            "(A->B)->(A->B->C)->A->C",
            "((A->B)->(A->B->C)->A->C)->B->(A->B)->(A->B->C)->A->C",
            "B->(A->B)->(A->B->C)->A->C",
            "(B->A->B)->(B->(A->B)->(A->B->C)->A->C)->B->(A->B->C)->A->C",
            "(B->(A->B)->(A->B->C)->A->C)->B->(A->B->C)->A->C",
            "B->(A->B->C)->A->C",
            "(B->A->B->C)->(B->(A->B->C)->A->C)->B->A->C",
            "(B->(A->B->C)->A->C)->B->A->C",
            "B->A->C",
            "?xB->A->C",
            "A->?xB->A",
            "?xB->A->C",
            "(?xB->A->C)->A->?xB->A->C",
            "A->?xB->A->C",
            "(?xB->A)->(?xB->A->C)->?xB->C",
            "((?xB->A)->(?xB->A->C)->?xB->C)->A->(?xB->A)->(?xB->A->C)->?xB->C",
            "A->(?xB->A)->(?xB->A->C)->?xB->C",
            "(A->?xB->A)->(A->(?xB->A)->(?xB->A->C)->?xB->C)->A->(?xB->A->C)->?xB->C",
            "(A->(?xB->A)->(?xB->A->C)->?xB->C)->A->(?xB->A->C)->?xB->C",
            "A->(?xB->A->C)->?xB->C",
            "(A->?xB->A->C)->(A->(?xB->A->C)->?xB->C)->A->?xB->C",
            "(A->(?xB->A->C)->?xB->C)->A->?xB->C",
            "A->?xB->C"
        };

        alpha = new AnyFormula();

        phi = new AnyFormula();
        psi = new AnyFormula();
        x = new AnyFormula();

        Parser parser = new Parser();
        parser.addMatcher(new ConcreteFormulaMatcher("A", alpha));
        parser.addMatcher(new ConcreteFormulaMatcher("B", psi));
        parser.addMatcher(new ConcreteFormulaMatcher("C", phi));
        parser.addMatcher(new ConcreteFormulaMatcher("x", x));
        parser.addMatchers(Parser.getDefaultMatchers());


        for (String line : eachRuleProofSrc)
        {
            eachRule.add(parser.parse(line));
        }

        for (String line : someRuleProofSrc)
        {
            someRule.add(parser.parse(line));
        }
    }


    @Override
    protected boolean isApplicable(ExpressionCheckResult lineResult)
    {
        return lineResult.getClass() == PredicateRuleResult.class;
    }

    @Override
    protected void rewriteExpression(ExpressionChecker lineChecker, PredicateRuleResult lineResult, RewriterProofResult result)
    {
        if (result.alphaAssumption.getFreeAndCache().contains(lineResult.x))
        {
            result.failed = true;
            result.failureReason = String.format(
                "используется правило с квантором по переменной {1}, входящей свободно в допущение {2}.",
                lineResult.x.getName(),
                result.alphaAssumption.toParsableString()
            );
            return;
        }
        else
        {
            List<Expression> proof = null;
            if (lineChecker instanceof EachRule)
            {
                proof = eachRule;
            }
            else if (lineChecker instanceof SomeRule)
            {
                proof = someRule;
            }

            alpha.setEqualExpression(result.alphaAssumption);
            phi.setEqualExpression(lineResult.phi);
            psi.setEqualExpression(lineResult.psi);
            x.setEqualExpression(lineResult.x);

            for (Expression line: proof)
            {
                result.generatedProof.addLine(line.clone().setComment("predicate for " + lineChecker.getClass()));
            }
            result.generatedProof.getProofLines().get(
                result.generatedProof.getProofLines().size() - 1
            ).setComment("finished predicate for " + lineChecker.getClass().getName());
        }
    }
}
