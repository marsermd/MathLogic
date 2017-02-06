package Prooving.ProofCheckers.Rewriter;

import Prooving.ExpressionCheckers.ExpressionCheckResult;
import Prooving.ExpressionCheckers.ExpressionChecker;
import Prooving.ExpressionCheckers.InferenceRules.ModusPonens;
import Prooving.Proof;
import Prooving.ProofCheckers.ProofChecker;
import Prooving.ProofCheckers.Rewriter.ExpressionRewriters.*;
import SyntaxTree.Structure.BinaryOperators.Implication;
import SyntaxTree.Structure.Expression;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marsermd on 05.02.2017.
 */
public class RewriterProofChecker extends ProofChecker<RewriterProofResult>
{
    private RewriterProofResult result;
    private List<ExpressionRewriter> rewriters;

    public RewriterProofChecker(Proof proof, ExpressionChecker... checkers)
    {
        super(proof, checkers);
    }

    public void setExpressionRewriters(List<ExpressionRewriter> rewriters)
    {
        this.rewriters = new ArrayList<ExpressionRewriter>();
        this.rewriters.addAll(rewriters);
    }

    public static List<ExpressionRewriter> getDefaultRewriters()
    {
        List<ExpressionRewriter> rewriters = new ArrayList<ExpressionRewriter>();

        rewriters.add(new SimpleAxiomRewriter());
        rewriters.add(new AssumptionRuleRewriter());
        rewriters.add(new ModusPonensRewriter());
        rewriters.add(new PredicateRuleRewriter());

        return rewriters;
    }

    public static RewriterProofChecker getDefaultChecker(Proof proof)
    {
        RewriterProofChecker checker = new RewriterProofChecker(proof, ProofChecker.getFormalArithmeticsCheckers());
        checker.setExpressionRewriters(getDefaultRewriters());

        return checker;
    }

    @Override
    protected void OnCheckResult(int currentLine, ExpressionCheckResult expressionResult, ExpressionChecker lineChecker)
    {
        if (expressionResult.isWrong())
        {
            result.failed = true;
            result.failureReason = expressionResult.reason;
            stopChecking();
        }
        else
        {
            boolean hasRewriter = false;
            for (ExpressionRewriter rewriter: rewriters)
            {
                if (rewriter.tryRewriteExpression(lineChecker, expressionResult, result))
                {
                    hasRewriter = true;
                    break;
                }
            }

            if (!hasRewriter)
            {
                throw new IllegalStateException("no rewriter setup for result" + expressionResult.getClass());
            }

            if (result.failed)
            {
                stopChecking();
            }
        }
    }

    @Override
    public RewriterProofResult Check()
    {
        InitResult();

        RunCheck();

        return result;
    }

    private void InitResult()
    {
        result = new RewriterProofResult();
        result.generatedProof = new Proof();

        for (int i = 0; i < proof.getAssumptions().size() - 1; i++)
        {
            result.generatedProof.assumeThat(proof.getAssumptions().get(i));
        }

        Expression alpha = proof.getAssumptions().get(proof.getAssumptions().size() - 1);
        Expression beta = proof.getGoal();
        Expression alphaBeta = new Implication(
            alpha,
            beta
        );

        result.generatedProof.setGoal(alphaBeta);
        result.alphaAssumption = alpha;
    }
}
