package Prooving.ProofCheckers;

import Prooving.ExpressionCheckers.Axioms.Arithmetical.AxiomEach;
import Prooving.ExpressionCheckers.Axioms.Arithmetical.AxiomSome;
import Prooving.ExpressionCheckers.Axioms.Classical.*;
import Prooving.ExpressionCheckers.ExpressionCheckResult;
import Prooving.ExpressionCheckers.ExpressionChecker;
import Prooving.ExpressionCheckers.InferenceRules.EachRule;
import Prooving.ExpressionCheckers.InferenceRules.ModusPonens;
import Prooving.ExpressionCheckers.InferenceRules.SomeRule;
import Prooving.ExpressionCheckers.InferenceRules.UseAssumptionRule;
import Prooving.Proof;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marsermd on 18.01.2017.
 */
public abstract class ProofChecker<TProofResult>
{
    public final Proof proof;
    private List<ExpressionChecker> checkers = new ArrayList<ExpressionChecker>();
    private boolean stopped = false;

    public static ExpressionChecker[] getFormalArithmeticsCheckers()
    {
        return new ExpressionChecker[] {
            new UseAssumptionRule(),
            new AOne(),
            new BTwo(),
            new CThree(),
            new DFour(),
            new EFive(),
            new FSix(),
            new GSeven(),
            new HEight(),
            new INine(),
            new JTen(),
            new AxiomEach(),
            new AxiomSome(),
            new ModusPonens(),
            new EachRule(),
            new SomeRule()
        };
    }

    public ProofChecker(Proof proof, ExpressionChecker... checkers)
    {
        this.proof = proof;
        for (ExpressionChecker checker : checkers)
        {
            this.checkers.add(checker);
        }
    }

    protected void stopChecking()
    {
        stopped = true;
    }

    protected void RunCheck()
    {
        stopped = false;
        for (int i = 0; i < proof.getProofLines().size(); i++)
        {
            ExpressionCheckResult lineResult = ExpressionCheckResult.wrong();
            for (ExpressionChecker checker: checkers)
            {
                lineResult = ExpressionCheckResult.getBest(lineResult, checker.checkMatches(proof, i));
            }
            OnCheckResult(i, lineResult);
            if (stopped)
            {
                return;
            }
        }
    }

    protected abstract void OnCheckResult(int currentLine, ExpressionCheckResult result);
    public abstract TProofResult Check();
}
