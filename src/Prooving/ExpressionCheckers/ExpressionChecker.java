package Prooving.ExpressionCheckers;

import Prooving.Proof;
import SyntaxTree.Structure.BinaryOperators.Implication;
import SyntaxTree.Structure.Expression;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by marsermd on 22.01.2017.
 */
public interface ExpressionChecker
{
    public ExpressionCheckResult checkMatches(Proof proof,
                                              int currentLine,
                                              HashMap<Expression, Integer> checkedHashToLine,
                                              HashSet<Expression> assumptionsHashes,
                                              HashMap<Expression, List<Implication>> checkedImplicationsRightParts);
}
