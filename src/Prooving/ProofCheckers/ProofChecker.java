package Prooving.ProofCheckers;

import Prooving.ExpressionCheckers.Axioms.Arithmetical.*;
import Prooving.ExpressionCheckers.Axioms.Predicate.AxiomEach;
import Prooving.ExpressionCheckers.Axioms.Predicate.AxiomSome;
import Prooving.ExpressionCheckers.Axioms.Classical.*;
import Prooving.ExpressionCheckers.ExpressionCheckResult;
import Prooving.ExpressionCheckers.ExpressionChecker;
import Prooving.ExpressionCheckers.InferenceRules.EachRule;
import Prooving.ExpressionCheckers.InferenceRules.ModusPonens;
import Prooving.ExpressionCheckers.InferenceRules.SomeRule;
import Prooving.ExpressionCheckers.InferenceRules.UseAssumptionRule;
import Prooving.Proof;
import SyntaxTree.Structure.BinaryOperators.Implication;
import SyntaxTree.Structure.Expression;

import java.util.*;

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
            // classical logic
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
            new ModusPonens(),
            // Predicates
            new AxiomEach(),
            new AxiomSome(),
            new EachRule(),
            new SomeRule(),
            // Formal arithmetics
            new ArithmeticAOne(),
            new ArithmeticBTwo(),
            new ArithmeticCThree(),
            new ArithmeticDFour(),
            new ArithmeticEFive(),
            new ArithmeticFSix(),
            new ArithmeticGSeven(),
            new ArithmeticHEight(),
            new ArithmeticINine()
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
        HashMap<Expression, Integer> checkedHashToLine = new HashMap<Expression, Integer>();
        HashMap<Expression, List<Implication>> checkedImplicationsRightParts = new HashMap<Expression, List<Implication>>();
        HashSet<Expression> assumptionsHashes = new HashSet<Expression>();
        assumptionsHashes.addAll(proof.getAssumptions());

        stopped = false;
        for (int i = 0; i < proof.getProofLines().size(); i++)
        {
            if (i % 1000 == 0)
            {
                System.out.println(i);
            }
            ExpressionCheckResult lineResult = ExpressionCheckResult.wrong();
            for (ExpressionChecker checker: checkers)
            {
                //long t1 = System.nanoTime();
                ExpressionCheckResult tmpResult = checker.checkMatches(proof, i, checkedHashToLine, assumptionsHashes, checkedImplicationsRightParts);
                lineResult = ExpressionCheckResult.getBest(lineResult, tmpResult);
                //long t2 = System.nanoTime();
                //System.out.println("checker " + checker.getClass().getSimpleName() + (t2 - t1));
            }
            OnCheckResult(i, lineResult);
            if (stopped)
            {
                return;
            }

            Expression current = proof.getProofLines().get(i);
            checkedHashToLine.put(current, i);
            if (current instanceof Implication)
            {
                Implication currentImplication = (Implication) current;
                List<Implication> currentList = checkedImplicationsRightParts.getOrDefault(
                    currentImplication.getRight(),
                    new ArrayList<Implication>());
                currentList.add(currentImplication);
                checkedImplicationsRightParts.put(currentImplication.getRight(), currentList);
            }
        }
    }

    protected abstract void OnCheckResult(int currentLine, ExpressionCheckResult result);
    public abstract TProofResult Check();
}
