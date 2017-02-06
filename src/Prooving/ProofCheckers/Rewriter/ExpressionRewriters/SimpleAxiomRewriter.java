package Prooving.ProofCheckers.Rewriter.ExpressionRewriters;

import Prooving.ExpressionCheckers.Axioms.SimpleAxiomResult;
import Prooving.ExpressionCheckers.ExpressionCheckResult;
import Prooving.ExpressionCheckers.ExpressionChecker;
import Prooving.ProofCheckers.Rewriter.RewriterProofResult;
import SyntaxTree.Structure.BinaryOperators.Implication;

/**
 * Created by marsermd on 05.02.2017.
 */
public class SimpleAxiomRewriter extends ExpressionRewriter<SimpleAxiomResult>
{
    @Override
    protected boolean isApplicable(ExpressionCheckResult lineResult)
    {
        return lineResult.getClass() == SimpleAxiomResult.class;
    }

    @Override
    protected void rewriteExpression(ExpressionChecker lineChecker, SimpleAxiomResult lineResult, RewriterProofResult result)
    {
        result.generatedProof.addLine(lineResult.proved);
        result.generatedProof.addLine(
            new Implication(
                lineResult.proved,
                new Implication(
                    result.alphaAssumption,
                    lineResult.proved
                )
            ).setComment("Simple Axiom 1")
        );
        result.generatedProof.addLine(
            new Implication(
                result.alphaAssumption,
                lineResult.proved
            ).setComment("Simple Axiom 2")
        );

    }
}
