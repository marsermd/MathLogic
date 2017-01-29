package ProofChecker.ExpressionCheckers.InferenceRules;

import ProofChecker.Proof;
import SyntaxTree.Structure.BinaryOperators.Implication;
import SyntaxTree.Structure.Expression;
import SyntaxTree.Structure.Predicate;
import org.junit.Assert;
import org.junit.Test;

public class ModusPonensTest
{

    @Test
    public void testMatches()
    {
        Proof proof = new Proof();
        proof.addLine(new Predicate("A"));
        proof.addLine(new Implication(
            new Predicate("A"),
            new Predicate("B")
        ));
        proof.addLine(
            new Predicate("B")
        );
        proof.addLine(
            new Predicate("C")
        );

        ModusPonens modusPonens = new ModusPonens();
        Assert.assertTrue(modusPonens.Matches(proof, 2));
        Assert.assertFalse(modusPonens.Matches(proof, 3));
    }

    @Test
    public void testMatchesComplex()
    {
        Proof proof = new Proof();
        proof.addLine(new Implication(
            new Predicate("A"),
            new Predicate("B")
        ));

        proof.addLine(
            new Predicate("A")
        );
        proof.addLine(new Implication(
            new Predicate("A"),
            new Implication(
                new Predicate("B"),
                new Predicate("C")
            )
        ));
        proof.addLine(
            new Predicate("B")
        );
        proof.addLine(
            new Predicate("C")
        );
        proof.addLine(
            new Implication(
                new Predicate("B"),
                new Predicate("C")
            )
        );

        ModusPonens modusPonens = new ModusPonens();
        Assert.assertFalse(modusPonens.Matches(proof, 3));
        Assert.assertFalse(modusPonens.Matches(proof, 4));
        Assert.assertTrue(modusPonens.Matches(proof, 5));
    }
}