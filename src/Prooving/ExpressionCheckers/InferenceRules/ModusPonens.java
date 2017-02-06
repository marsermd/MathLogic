package Prooving.ExpressionCheckers.InferenceRules;

import Prooving.ExpressionCheckers.ExpressionCheckResult;
import Prooving.ExpressionCheckers.ExpressionChecker;
import Prooving.Proof;
import SyntaxTree.Structure.BinaryOperators.Implication;
import SyntaxTree.Structure.Expression;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by marsermd on 22.01.2017.
 */
public class ModusPonens implements ExpressionChecker
{
    @Override
    public ExpressionCheckResult checkMatches(Proof proof,
                                              int currentLine,
                                              HashMap<Expression, Integer> checkedHashToLine,
                                              HashSet<Expression> assumptionsHashes, HashMap<Expression, List<Implication>> checkedImplicationsRightParts)
    {
        List<Expression> expressions = proof.getProofLines();

        Expression beta = expressions.get(currentLine);

        List<Implication> aphaBetas = checkedImplicationsRightParts.getOrDefault(beta, Collections.<Implication>emptyList());
        for (Implication alphaBeta: aphaBetas)
        {
            if (checkedHashToLine.containsKey(alphaBeta.getLeft()))
            {
                return ModusPonensResult.right(alphaBeta.getLeft(), alphaBeta.getRight());
            }
        }
        return ExpressionCheckResult.wrong();
    }
}
