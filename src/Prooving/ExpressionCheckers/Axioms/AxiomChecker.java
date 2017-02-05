package Prooving.ExpressionCheckers.Axioms;

import Prooving.ExpressionCheckers.ExpressionCheckResult;
import Prooving.ExpressionCheckers.ExpressionChecker;
import Prooving.Proof;
import SyntaxTree.Structure.BinaryOperators.Implication;
import SyntaxTree.Structure.Expression;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by marsermd on 18.01.2017.
 */
public abstract class AxiomChecker implements ExpressionChecker
{
    public abstract ExpressionCheckResult checkMatchesAxiom(Expression expression);

    @Override
    public ExpressionCheckResult checkMatches(Proof proof, int currentLine, HashMap<Expression, Integer> checkedHashToLine, HashSet<Expression> assumptionsHashes, HashMap<Expression, List<Implication>> checkedImplicationsRightParts)
    {
        return checkMatchesAxiom(proof.getProofLines().get(currentLine));
    }
}
