package Prooving.ProofCheckers.Rewriter.ExpressionRewriters;

import Prooving.ExpressionCheckers.ExpressionCheckResult;
import Prooving.ExpressionCheckers.ExpressionChecker;
import Prooving.ExpressionCheckers.InferenceRules.ModusPonensResult;
import Prooving.ProofCheckers.Rewriter.RewriterProofResult;
import SyntaxTree.Structure.BinaryOperators.Implication;

/**
 * Created by marsermd on 06.02.2017.
 */
public class ModusPonensRewriter extends ExpressionRewriter<ModusPonensResult>
{
    @Override
    protected boolean isApplicable(ExpressionCheckResult lineResult)
    {
        return lineResult.getClass() == ModusPonensResult.class;
    }

    @Override
    protected void rewriteExpression(ExpressionChecker lineChecker, ModusPonensResult lineResult, RewriterProofResult result)
    {
        result.generatedProof.addLine(
            new Implication(
                new Implication(
                    result.alphaAssumption,
                    lineResult.alpha
                ),
                new Implication(
                    new Implication(
                        result.alphaAssumption,
                        lineResult.alphaBeta
                    ),
                    new Implication(
                        result.alphaAssumption,
                        lineResult.beta
                    )
                )
            ).setComment("Modus Ponens 1")
        );
        result.generatedProof.addLine(
            new Implication(
                new Implication(
                    result.alphaAssumption,
                    lineResult.alphaBeta
                ),
                new Implication(
                    result.alphaAssumption,
                    lineResult.beta
                )
            ).setComment("Modus Ponens 2")
        );
        result.generatedProof.addLine(
            new Implication(
                result.alphaAssumption,
                lineResult.beta
            ).setComment("Modus Ponens 3")
        );
    }
}
